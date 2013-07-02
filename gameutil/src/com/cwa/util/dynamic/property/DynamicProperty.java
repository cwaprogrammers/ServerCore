package com.cwa.util.dynamic.property;

/**
 * 动态属性从某个地方（比如磁盘文件、URL、数据库等）加载一个字符串类型的值。
 * 这个值会被缓存起来，并且会定期刷新。
 */
public abstract class DynamicProperty {

    private long refreshRate; // 刷新周期
    private long lastRefresh; // 上次刷新时间
    private String value;

    public DynamicProperty() {
        
    }

    public long getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(long refreshRate) {
        this.refreshRate = refreshRate;
    }

    public long getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(long lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

    public String getValue() {
        return value;
    }
    
    /**
     * 刷新属性。
     * @throws Exception 
     */
    public void refresh() throws Exception {
        value = reload();
    }
    
    /**
     * 加载属性。
     * @return
     * @throws Exception 
     */
    public abstract String reload() throws Exception;
    
}
