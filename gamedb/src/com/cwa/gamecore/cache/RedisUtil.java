/**
 * Nov 23, 2011 4:58:43 PM
 */
package com.cwa.gamecore.cache;

import com.cwa.gamecore.context.AppContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.Logger;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 
 * @author Landu
 * @descirption 功能描述
 */
/**
 * 连接和使用数据库资源的工具类
 */
public class RedisUtil {
        private final static Logger logger = Logger.getLogger(RedisUtil.class);

        /**
         * 数据源
         */
        private static ShardedJedisPool shardedJedisPool = AppContext.getInstance().getShardedJedisPoolContext().getShardedJedisPool();

        /**
         * 获取连接
         * 
         * @return conn
         */
        public static ShardedJedis getConnection() {
                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                } catch (Exception e) {
                        logger.error("[获取连接失败]", e);
                }

                return jedis;
        }

        /**
         * 关闭连接
         * 
         * @param conn
         */
        public static void closeConnection(ShardedJedis jedis) {
                if (null != jedis) {
                        try {
                                shardedJedisPool.returnResource(jedis);
                        } catch (Exception e) {
                                logger.error("关闭连接失败", e);
                        }
                }
        }

        /**
         * 存放对象
         */
        public static boolean storeObj(Object obj) {
                ShardedJedis jedis = null;
                String mapKey = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        Class<?> clazz = obj.getClass();
                        String className = clazz.getSimpleName();
                        Method getIdMethod = clazz.getMethod("getId");
                        int id = (Integer) getIdMethod.invoke(obj);
                        mapKey = className + ":" + id;

                        Method[] methods = clazz.getMethods();
                        for (Method method : methods) {
                                String methodName = method.getName();
                                String fieldName = null;
                                if (methodName.startsWith("get"))
                                        fieldName = methodName.substring(3);
                                else if (methodName.startsWith("is"))
                                        fieldName = methodName.substring(2);
                                else
                                        continue;

                                if (fieldName.length() == 0)
                                        continue;

                                Class<?> returnType = method.getReturnType();
                                // 暂只缓存primitive,String类型的数据
                                String value = null;
                                if (returnType.isPrimitive() || String.class.equals(returnType)) {
                                        value = invokeGetMethod(method, obj, returnType);
                                        String returnTypeName = returnType.getCanonicalName();
                                        // 考虑用数字来替换returnTypeName
                                        jedis.hset(mapKey, fieldName, returnTypeName + ":" + (String) value);
                                }
                        }

                        return true;
                } catch (Exception e) {
                        if (mapKey != null)
                                jedis.del(mapKey);
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[存放对象失败,mapKey=" + mapKey + "]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }

                return false;
        }

        /**
         * 从redis中获取对象
         */
        public static <T> T restoreObj(Class<T> clazz, int id) {
                String mapKey = clazz.getSimpleName() + ":" + id;
                ShardedJedis jedis = null;
                try {
                        T obj = clazz.newInstance();
                        jedis = shardedJedisPool.getResource();
                        Map<String, String> objMap = jedis.hgetAll(mapKey);

                        Set<Entry<String, String>> entrySet = objMap.entrySet();
                        for (Entry<String, String> entry : entrySet) {
                                String fieldName = entry.getKey();
                                String compoundValue = entry.getValue();
                                int splitIndex = compoundValue.indexOf(":");
                                String strValueType = compoundValue.substring(0, splitIndex);
                                String fieldValue = compoundValue.substring(splitIndex + 1);
                                Class<?> fieldType = parseClass(strValueType);
                                Method method = clazz.getMethod("set" + fieldName, fieldType);
                                if (method == null)
                                        continue;
                                invokeSetMethod(method, obj, fieldType, fieldValue);
                        }

                        return obj;
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[获取对象，mapKey=" + mapKey + "失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }
                return null;
        }

        /**
         * @param strClassName
         *                类名,如java.lang.String,int
         * @return
         * @throws ClassNotFoundException
         * @description 从字符串中解析获得Class对象
         */
        private static Class<?> parseClass(String strClassName) throws ClassNotFoundException {
                Class<?> retType = null;
                if (boolean.class.getCanonicalName().equals(strClassName))
                        retType = boolean.class;
                else if (char.class.getCanonicalName().equals(strClassName))
                        retType = char.class;
                else if (byte.class.getCanonicalName().equals(strClassName))
                        retType = byte.class;
                else if (short.class.getCanonicalName().equals(strClassName))
                        retType = short.class;
                else if (int.class.getCanonicalName().equals(strClassName))
                        retType = int.class;
                else if (long.class.getCanonicalName().equals(strClassName))
                        retType = long.class;
                else if (float.class.getCanonicalName().equals(strClassName))
                        retType = float.class;
                else if (double.class.getCanonicalName().equals(strClassName))
                        retType = double.class;
                else
                        retType = Class.forName(strClassName);
                return retType;
        }

        /***
         * @param method
         * @param obj
         * @param valueType
         *                值的Class类型
         * @param valueStr
         *                值的字符串值
         * @throws IllegalArgumentException
         * @throws IllegalAccessException
         * @throws InvocationTargetException
         * @description 调用set方法设值
         */
        private static void invokeSetMethod(Method method, Object obj, Class<?> valueType, String valueStr) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
                if (boolean.class.equals(valueType))
                        method.invoke(obj, Boolean.parseBoolean(valueStr));
                else if (char.class.equals(valueType))
                        method.invoke(obj, valueStr.charAt(0));
                else if (byte.class.equals(valueType))
                        method.invoke(obj, Byte.parseByte(valueStr));
                else if (short.class.equals(valueType))
                        method.invoke(obj, Short.parseShort(valueStr));
                else if (int.class.equals(valueType))
                        method.invoke(obj, Integer.parseInt(valueStr));
                else if (long.class.equals(valueType))
                        method.invoke(obj, Long.parseLong(valueStr));
                else if (float.class.equals(valueType))
                        method.invoke(obj, Float.parseFloat(valueStr));
                else if (double.class.equals(valueType))
                        method.invoke(obj, Double.parseDouble(valueStr));
                else
                        method.invoke(obj, valueStr);
        }

        /**
         * @param method
         * @param obj
         * @param returnType
         *                返回值类型
         * @return
         * @throws IllegalArgumentException
         * @throws IllegalAccessException
         * @throws InvocationTargetException
         * @description 调用get方法
         */
        private static String invokeGetMethod(Method method, Object obj, Class<?> returnType) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
                String retValue = null;
                if (returnType.isPrimitive()) {
                        if (char.class.equals(returnType)) {
                                retValue = ((Character) method.invoke(obj)).toString();
                        } else if (boolean.class.equals(returnType)) {
                                retValue = ((Boolean) method.invoke(obj)).toString();
                        } else if (byte.class.equals(returnType)) {
                                retValue = ((Byte) method.invoke(obj)).toString();
                        } else if (short.class.equals(returnType)) {
                                retValue = ((Short) method.invoke(obj)).toString();
                        } else if (int.class.equals(returnType)) {
                                retValue = ((Integer) method.invoke(obj)).toString();
                        } else if (long.class.equals(returnType)) {
                                retValue = ((Long) method.invoke(obj)).toString();
                        } else if (float.class.equals(returnType)) {
                                retValue = ((Float) method.invoke(obj)).toString();
                        } else if (double.class.equals(returnType)) {
                                retValue = ((Double) method.invoke(obj)).toString();
                        }
                } else if (String.class.equals(returnType))
                        retValue = (String) method.invoke(obj);

                return retValue;
        }

        public static boolean storeInt(String key, int value) {
                return storeString(key, Integer.toString(value));
        }

        public static int restoreInt(String key) {
                int retInt = Integer.MIN_VALUE;
                String tempStr = "";
                try {
                        tempStr = restoreString(key);
                        if(tempStr.equals(""))
                                tempStr="0";
                        retInt = Integer.parseInt(tempStr);
                } catch (NumberFormatException e) {
                        logger.error("获取[key=" + key + ",tempvalue=" + tempStr + "失败]", e);
                }
                return retInt;
        }

        public static boolean storeShort(String key, short value) {
                return storeString(key, Short.toString(value));
        }

        public static int restoreShort(String key) {
                short retShort = Short.MIN_VALUE;
                String tempStr = "";
                try {
                        tempStr = restoreString(key);
                        retShort = Short.parseShort(tempStr);
                } catch (NumberFormatException e) {
                        logger.error("获取[key=" + key + ",tempvalue=" + tempStr + "失败]", e);
                }
                return retShort;
        }

        /**
         * 设置数据
         * 
         * @param conn
         */
        public static boolean storeString(String key, String value) {
                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        jedis.set(key, value);

                        return true;
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[key=" + key + ",value=" + value + "失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }
                return false;
        }

        /**
         * 获取数据
         * 
         * @param conn
         */
        public static String restoreString(String key) {
                String value = null;
                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        value = jedis.get(key);
                        if(value==null)
                                value="0";
                        return value;
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[获取key=" + key + "对应的值失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }

                return value;
        }

        /**
         * 存放列表
         */
        public static boolean storeList(String key, List<String> listValue) {
                if (key == null || listValue == null || listValue.size() == 0)
                        return false;

                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        for (String value : listValue) {
                                jedis.rpush(key, value);
                        }

                        return true;
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[存放key=" + key + ",listValue=" + listValue + "失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }
                return false;
        }

        /**
         * 获取列表
         */
        public static List<String> restoreList(String key) {
                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        return jedis.lrange(key, 0, -1);

                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[获取key=" + key + "失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }
                return Collections.emptyList();
        }

        /**
         * 存放Set
         */
        public static boolean storeSet(String key, Set<String> setValue) {
                if (key == null || setValue == null || setValue.size() == 0)
                        return false;

                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        for (String value : setValue) {
                                jedis.sadd(key, value);
                        }

                        return true;
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[存放key=" + key + ",setValue=" + setValue + "失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }
                return false;
        }

        /**
         * 获取Set
         */
        public static Set<String> restoreSet(String key) {
                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        return jedis.smembers(key);
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[获取key=" + key + "失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }
                return Collections.emptySet();
        }

        public static long del(String key) {
                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        return jedis.del(key);
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[删除key=" + key + "失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }

                return -1;
        }

        /**
         * 存放Hash值
         */
        public static boolean storeHash(String key, Map<String, String> hashValue) {
                if (key == null || hashValue == null || hashValue.size() == 0)
                        return false;

                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        // Set<Entry<String, String>> entrySet =
                        // hashValue.entrySet();
                        // for (Entry<String, String> entry : entrySet) {
                        // String fieldName = entry.getKey();
                        // String fieldValue = entry.getValue();
                        // jedis.hset(key, fieldName, fieldValue);
                        // }
                        jedis.hmset(key, hashValue);

                        return true;
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[存放key=" + key + ",hashValue=" + hashValue + "失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }
                return false;
        }

        /**
         * 获取Hash值
         */
        public static Map<String, String> restoreHash(String key) {
                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        return jedis.hgetAll(key);
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[获取key=" + key + "失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }
                return Collections.emptyMap();
        }

        /**
         * 设置单个字段的值
         */
        public static long setHashByField(String key, String field, String value) {
                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        return jedis.hset(key, field, value);
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[设置key=" + key + "失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }
                return -1;
        }

        /**
         * 获取单个字段的值
         */
        public static String getHashByField(String key, String field) {
                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        return jedis.hget(key, field);
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[获取key=" + key + "失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }
                return null;
        }

        /**
         * 得到全部key
         */
        public static Set<String> getAllKeys(String pattern) {
                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        return jedis.getAllKeys(pattern);
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[取所全部key时失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }
                return null;
        }

        /**
         * 设置连接池
         * 
         * @return 数据源
         */
        public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
                this.shardedJedisPool = shardedJedisPool;
        }

        /**
         * 获取连接池
         * 
         * @return 数据源
         */
        public ShardedJedisPool getShardedJedisPool() {
                return shardedJedisPool;
        }

        public static void subscribe(JedisPubSub jedisPubSub, String key, String... channels) {
                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        jedis.subscribe(jedisPubSub, key, channels);
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[订阅时失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }               
        }

        public static Long publish(String key, String channel, String message) {
                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        return jedis.publish(key, channel, message);
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[发布时失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }
                return 0L;
        }

        public static void psubscribe(JedisPubSub jedisPubSub, String key, String... patterns) {
                ShardedJedis jedis = null;
                try {
                        jedis = shardedJedisPool.getResource();
                        jedis.psubscribe(jedisPubSub, key, patterns);
                } catch (Exception e) {
                        shardedJedisPool.returnBrokenResource(jedis);
                        logger.error("[订阅时失败]", e);
                } finally {
                        if (jedis != null)
                                shardedJedisPool.returnResource(jedis);
                }
        }

        public static long incr(String key){
        	ShardedJedis jedis = null;
            try {
                    jedis = shardedJedisPool.getResource();
                    return jedis.incr(key);
            } catch (Exception e) {
                    logger.error("[key=" + key + "]", e);
            } finally {
                    if (jedis != null)
                            shardedJedisPool.returnResource(jedis);
            }
            return -1;
        }
}
