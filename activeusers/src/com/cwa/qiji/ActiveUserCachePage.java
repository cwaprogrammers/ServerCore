package com.cwa.qiji;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ActiveUserCachePage缓存某几个等级的玩家。
 */
class ActiveUserCachePage {

    private final int capacity;
    private final Cache<Integer, Integer> cache; // guava cache

    public ActiveUserCachePage(int capacity) {
        this.capacity = capacity;
        cache = CacheBuilder.newBuilder().maximumSize(capacity).build();
    }

    /**
     * 将活跃玩家放入缓存。
     */
    public void update(int uid) {
        cache.put(uid, Integer.MAX_VALUE);
    }

    /**
     * 返回n个随机ID。
     */
    public List<Integer> getRandomUsers(int n) {
        ArrayList<Integer> users = new ArrayList<Integer>(cache.asMap().keySet());
        
        int size = users.size();
        if (size <= n || n >= capacity) {
            // 缓存中的ID不够n个
            return users;
        } else {
            int startIdx = new Random().nextInt(size - n);
            int endIdx = startIdx + n;
            return users.subList(startIdx, endIdx);
        }
    }
    
    /**
     * 返回全部ID。
     */
    public List<Integer> getAllUsers() {
        return new ArrayList<Integer>(cache.asMap().keySet());
    }

}
