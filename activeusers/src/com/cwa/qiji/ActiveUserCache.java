package com.cwa.qiji;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 将玩家ID按等级分组放在内存中。
 * 内存中的玩家分布类似这样：
 * level range |   user id
 *   [1 - 5]     [1, 2, 3, 4, 5]
 *   [6 -10]     [6, 7, 8, 9,10] 
 */
class ActiveUserCache {
    
    private final int levelsPerPage; // 每一页有多少等级
    private final ActiveUserCachePage[] pages;
    
    /**
     * 构造函数。
     * @param minLevel 最小等级
     * @param maxLevel 最大等级
     * @param levelsPerPage 每页几个等级
     * @param usersPerPage  每页多少玩家
     */
    public ActiveUserCache(int minLevel, int maxLevel, int levelsPerPage, int usersPerPage) {
        this.levelsPerPage = levelsPerPage;
        int pageCount = calcPageCount(maxLevel, levelsPerPage);
        this.pages = new ActiveUserCachePage[pageCount];
        for (int i = 0; i < pageCount; i++) {
            pages[i] = new ActiveUserCachePage(usersPerPage);
        }
    }
    
    private int calcPageCount(int maxLevel, int levelsPerPage) {
        return maxLevel/levelsPerPage + (maxLevel%levelsPerPage == 0 ? 0 : 1);
    }
    
    public ActiveUserCachePage[] getPages() {
        return pages;
    }
    
    /*
     * 更新活跃玩家。
     * @param uid
     * @param level 
     */
    public void update(int uid, int level) {
        int pageIdx = getPageIndex(level);
        pages[pageIdx].update(uid);
    }
    
    /*
     * 返回随机玩家。
     * @param level
     * @param n
     * @return 
     */
    public List<Integer> getRaodomUsers(int level, int n) {
        int pageIdx = getPageIndex(level);
        return pages[pageIdx].getRandomUsers(n);
    }
    
    /*
     * 尽量返回足够数量的随机玩家。 
     * @param level
     * @param n
     * @return 
     */
    public List<Integer> getRandomUsersAround(int level, int n) {
        int pageIdx = getPageIndex(level);
        
        Set<Integer> result = new HashSet<Integer>();
        result.addAll(pages[pageIdx].getRandomUsers(n));
        
        // 有足够数量的活跃玩家和玩家等级差不多
        if (result.size() >= n) {
            return new ArrayList<Integer>(result);
        }
        
        // 在附近等级中查找
        for (int step = 1; step < pages.length; step++) {
            int pageUp = pageIdx + step;
            int pageDown = pageIdx - step;
            
            // 所有组都找遍了
            if (pageUp >= pages.length && pageDown < 0) {
                break;
            }
            
            // 优先查找比玩家等级高的玩家
            if (pageUp < pages.length) {
                result.addAll(pages[pageUp].getRandomUsers(n - result.size()));
                if (result.size() >= n) {
                    return new ArrayList<Integer>(result);
                }
            } 
            
            // 查找等级比玩家低的玩家
            if (pageDown >= 0) {
                result.addAll(pages[pageDown].getRandomUsers(n - result.size()));
                if (result.size() >= n) {
                    return new ArrayList<Integer>(result);
                }
            }
        }
        
        return new ArrayList<Integer>(result);
    }
    
    private int getPageIndex(int level) {
        return (level - 1) / levelsPerPage;
    }
    
}
