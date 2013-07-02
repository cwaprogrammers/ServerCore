package com.cwa.qiji;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.Assert;
import org.junit.Test;

public class ActiveUserCachePageTest {
    
    @Test
    public void capacity() {
        ActiveUserCachePage pool = new ActiveUserCachePage(12);
        for (int i = 0; i < 100; i++) {
            pool.update(i);
        }
        
        Assert.assertEquals(12, pool.getAllUsers().size());
    }
    
    @Test
    public void userCount() {
        ActiveUserCachePage pool = new ActiveUserCachePage(12);
        for (int i = 0; i < 12; i++) {
            pool.update(i);
            Assert.assertEquals(i + 1, pool.getAllUsers().size());
        }
        
        pool.update(13);
        Assert.assertEquals(12, pool.getAllUsers().size());
        
        pool.update(5);
        Assert.assertEquals(12, pool.getAllUsers().size());
    }
    
    @Test
    public void update() {
        ActiveUserCachePage pool = new ActiveUserCachePage(12);
        Assert.assertEquals(Arrays.asList(), pool.getAllUsers());
        
        pool.update(15); 
        Assert.assertEquals(Arrays.asList(15), pool.getAllUsers());
        
        pool.update(15);
        pool.update(15);
        pool.update(15);
        Assert.assertEquals(Arrays.asList(15), pool.getAllUsers());
    }
    
    @Test
    public void eviction() {
        ActiveUserCachePage pool = new ActiveUserCachePage(3);
        
        pool.update(1);
        pool.update(2);
        pool.update(3); // 1, 2, 3
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(1, 2, 3)), 
                new HashSet<Integer>(pool.getAllUsers()));
        
        pool.update(1); // 2, 3, 1
        pool.update(4); // 3, 1, 4
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(1, 3, 4)), 
                new HashSet<Integer>(pool.getAllUsers()));
        
        pool.update(5); // 1, 4, 5
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(1, 4, 5)), 
                new HashSet<Integer>(pool.getAllUsers()));
    }
    
    @Test
    public void getRandomUsers_noUsers() {
        ActiveUserCachePage pool = new ActiveUserCachePage(12);
        Assert.assertEquals(Arrays.asList(), pool.getRandomUsers(20));
    }
    
    @Test
    public void getRandomUsers_notEnoughUsers() {
        ActiveUserCachePage pool = new ActiveUserCachePage(12);
        pool.update(1);
        pool.update(2);
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(1, 2)), 
                new HashSet<Integer>(pool.getRandomUsers(20)));
    }
    
    @Test
    public void getRandomUsers_enoughUsers() {
        ActiveUserCachePage pool = new ActiveUserCachePage(12);
        pool.update(1);
        pool.update(2);
        pool.update(3);
        pool.update(4);
        pool.update(5);
        Assert.assertEquals(3, new HashSet<Integer>(pool.getRandomUsers(3)).size());
    }
    
    @Test
    public void getRandomUsers_fullOfUsers() {
        ActiveUserCachePage pool = new ActiveUserCachePage(12);
        for (int i = 0; i < 12; i++) {
            pool.update(i);
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertEquals(4, new HashSet<Integer>(pool.getRandomUsers(4)).size());
        }
    }
    
}
