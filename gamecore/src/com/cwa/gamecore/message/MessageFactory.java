/**
 * 2011-6-14 下午02:10:53
 */
package com.cwa.gamecore.message;

import com.cwa.gamecore.util.ClassFinder;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * @author zpc
 * @descirption 消息管理类
 */
public class MessageFactory implements IMessageFactory {
        private static final Logger logger = Logger.getLogger(MessageFactory.class);

        // 单例
        private static MessageFactory instance = new MessageFactory();
        public static MessageFactory getInstance() {
                return instance;
        }
        
        private Map<Integer, Class<? extends GameRequest>> messageMap = new HashMap<Integer, Class<? extends GameRequest>>();
        
        private MessageFactory() {
        }

        public void init(List<String> packageList) {
            init(packageList, "Message");
        }
        
        public void init(List<String> packageList, String classSuffix) {
            init(packageList, Arrays.asList(classSuffix));
        }
        
        public void init(List<String> packageList, List<String> classSuffixList) {
                if (packageList == null) {
                        packageList = Arrays.asList("com.cwa");
                }
                if (classSuffixList == null) {
                        classSuffixList = Arrays.asList("Message");
                }

                logger.debug("init MessageFactory, packages:" + packageList + " suffix:" + classSuffixList);
                List<Class> messageList = ClassFinder.findClasses(packageList, classSuffixList);
                for (Class clazz : messageList) {
                        try {
                                int mod = clazz.getModifiers();
                                if (Modifier.isAbstract(mod)
                                        || Modifier.isInterface(mod) 
                                        || (!GameRequest.class.isAssignableFrom(clazz))) {
                                    continue;
                                }

                                logger.trace("find Class:" + clazz);
                                GameRequest message = (GameRequest) clazz.newInstance();
                                int commandId = message.getCommandId();
                                if (messageMap.containsKey(commandId)) {
                                        Class oldClazz = messageMap.get(commandId);
                                        logger.error("MessageId重复：" + commandId + ",oldMessageId:" + oldClazz.getSimpleName() + ",newMessage:" + clazz.getSimpleName());
                                } else {
                                    logger.debug("注册Message: " + commandId + "->" + clazz);
                                    messageMap.put(message.getCommandId(), clazz);
                                }
                        } catch (InstantiationException e) {
                                logger.error("加载message时出错：class=" + clazz.getName(), e);
                        } catch (IllegalAccessException e) {
                                logger.error("加载message时出错(无访问权限)：class=" + clazz.getName(), e);
                        }
                }
        }

        @Override
        public GameRequest getMessage(int commandId) {
                Class<? extends GameRequest> clazz = messageMap.get(commandId);
                if (clazz != null) {
                        try {
                                return (GameRequest) clazz.newInstance();
                        } catch (InstantiationException e) {
                                logger.error("获取message时出错：class=" + clazz.getName() + ",commandId=" + commandId, e);
                        } catch (IllegalAccessException e) {
                                logger.error("获取message时出错(无访问权限)：class=" + clazz.getName() + ",commandId=" + commandId, e);
                        }
                }
                logger.error("Message class not found. Command ID:" + commandId);
                return null;
        }
}
