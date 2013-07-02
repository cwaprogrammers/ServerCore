package com.cwa.qiji;

/**
 * 从某个地方（比如数据库或磁盘文件）保存和加载ActiveUserPool。
 */
interface ActiveUserStore {
    
    public void save(ActiveUserCache pool);
    public void load(ActiveUserCache pool);
    
}
