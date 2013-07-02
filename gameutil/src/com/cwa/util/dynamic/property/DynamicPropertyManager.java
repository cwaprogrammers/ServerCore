package com.cwa.util.dynamic.property;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/**
 * 动态属性管理器。
 */
public class DynamicPropertyManager {
    
    private static Logger logger = Logger.getLogger(DynamicPropertyManager.class);
    
    // 单例
    private static final DynamicPropertyManager INSTANCE = new DynamicPropertyManager();
    
    public static DynamicPropertyManager getInstance() {
        return INSTANCE;
    }
    
    private final Map<String, DynamicProperty> propMap;
    private ScheduledExecutorService executorService;
    
    private DynamicPropertyManager() {
        propMap = new HashMap<String, DynamicProperty>();
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                }, 
                1, // initialDelay 1分钟
                1, // period       1分钟
                TimeUnit.MINUTES);
    }
    
    /**
     * 注册动态属性。
     * @param key
     * @param refreshPeriodSecond
     * @param prop 
     */
    public void register(String key, long refreshPeriodSecond, DynamicProperty prop) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid key: " + key);
        }
        if (refreshPeriodSecond <= 0) {
            throw new IllegalArgumentException("Invalid refresh period:" + refreshPeriodSecond);
        }
        if (prop == null) {
            throw new IllegalArgumentException("Dynamic property is null.");
        }
        
        try {
            prop.setRefreshRate(refreshPeriodSecond * 1000);
            prop.refresh();
            prop.setLastRefresh(System.currentTimeMillis());
        } catch (Exception e) {
            throw new RuntimeException("Can not initialize dynamic property", e);
        }
        
        synchronized (propMap) {
            if (propMap.containsKey(key)) {
                throw new IllegalArgumentException("Dynamic property with key " + key + " already registered.");
            }
            
            propMap.put(key, prop);
        }
    }
    
    // 刷新动态属性
    private void refresh() {
        long now = System.currentTimeMillis();
        
        for (String key : propMap.keySet()) {
            DynamicProperty prop = propMap.get(key);
            
            if (now - prop.getLastRefresh() > prop.getRefreshRate()) {
                prop.setLastRefresh(now);
                try {
                    prop.refresh();
                } catch (Exception e) {
                    logger.error("Error refresh dynamic property: " + key, e);
                }
            }
        }
    }
    
    /**
     * 返回动态属性的值。
     * @param key
     * @return 
     */
    public String getStringValue(String key) {
        return propMap.get(key).getValue();
    }
    
    public boolean getBooleanValue(String key) {
        return Boolean.parseBoolean(getStringValue(null));
    }
    
    public int getIntValue(String key) {
        return Integer.parseInt(getStringValue(null));
    }
    
    public long getLongValue(String key) {
        return Long.parseLong(getStringValue(null));
    }
    
}
