package com.cwa.qiji;

import com.cwa.IActiveUserManager;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 对外使用的活跃玩家随机抓取工具。
 * @see IActiveUserManager
 */
public class ActiveUserManager implements IActiveUserManager {
    
    private static final Logger logger = Logger.getLogger(ActiveUserManager.class);
    
    private ActiveUserCache cache;
    private ActiveUserStore store;
    
    public ActiveUserManager(ActiveUserManagerConfig config) {
        cache = new ActiveUserCache(config.getMinLevel(), config.getMaxLevel(),
                config.getLevelsPerGroup(), config.getUsersPerGroup());
        
        store = new FileActiveUserStore(config.getDataFile());
        store.load(cache);
    }
    
    /**
     * @see IActiveUserManager#randomUsers(int, int) 
     */
    @Override
    public List<Integer> randomUsers(int amount, int level) {
        return cache.getRaodomUsers(level, amount);
    }

    /**
     * @see IActiveUserManager#randomUsersAroundLevel(int, int) 
     */
    @Override
    public List<Integer> randomUsersAroundLevel(int amount, int level) {
        return cache.getRandomUsersAround(level, amount);
    }
    
    /**
     * @see IActiveUserManager#userOffline(int, int) 
     */
    @Override
    public void userOffline(int roleId, int level) {
        // 啥也不干
    }

    /**
     * @see IActiveUserManager#userOnline(int, int) 
     */
    @Override
    public void userOnline(int roleId, int level) {
        cache.update(roleId, level);
    }
    
    // 将内存缓存保存到某个地方一遍下次服务器重启时恢复
    public void saveActiveUsers() {
        logger.debug("Save active users...");
        store.save(cache);
    }
    
    public String toDebugString() {
        StringBuilder buf = new StringBuilder("ActiveUserManager:\n");
        
        ActiveUserCachePage[] page = cache.getPages();
        for (int i = 0; i < page.length; i++) {
            buf.append("page ").append(i + 1).append(":");
            buf.append(page[i].getAllUsers());
            buf.append("\n");
        }
        
        return buf.toString();
    }
    
}
