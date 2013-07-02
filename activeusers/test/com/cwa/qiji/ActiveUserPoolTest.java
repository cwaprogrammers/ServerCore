package com.cwa.qiji;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.Assert;
import org.junit.Test;

public class ActiveUserPoolTest {
    
    @Test
    public void pageSize() {
        ActiveUserCache pool = new ActiveUserCache(1, 9, 3, 20);
        Assert.assertEquals(3, pool.getPages().length); // 9/3
        
        ActiveUserCache pool2 = new ActiveUserCache(1, 10, 3, 20);
        Assert.assertEquals(4, pool2.getPages().length); // 10/3
        
        ActiveUserCache pool3 = new ActiveUserCache(1, 11, 3, 20);
        Assert.assertEquals(4, pool3.getPages().length); // 11/3
        
        ActiveUserCache pool4 = new ActiveUserCache(1, 12, 3, 20);
        Assert.assertEquals(4, pool4.getPages().length); // 12/3
        
        ActiveUserCache pool5 = new ActiveUserCache(1, 10, 5, 20);
        Assert.assertEquals(2, pool5.getPages().length); // 10/5
    }
    
    @Test
    public void update_allLevels() {
        ActiveUserCache pool = new ActiveUserCache(1, 5, 2, 20);
        pool.update(1, 1);
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(1, 10));
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(2, 10));
        Assert.assertEquals(Arrays.asList( ), pool.getRaodomUsers(3, 10));
        Assert.assertEquals(Arrays.asList( ), pool.getRaodomUsers(4, 10));
        Assert.assertEquals(Arrays.asList( ), pool.getRaodomUsers(5, 10));
        
        pool.update(1, 2);
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(1, 10));
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(2, 10));
        Assert.assertEquals(Arrays.asList( ), pool.getRaodomUsers(3, 10));
        Assert.assertEquals(Arrays.asList( ), pool.getRaodomUsers(4, 10));
        Assert.assertEquals(Arrays.asList( ), pool.getRaodomUsers(5, 10));
        
        pool.update(1, 3);
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(1, 10));
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(2, 10));
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(3, 10));
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(4, 10));
        Assert.assertEquals(Arrays.asList(), pool.getRaodomUsers(5, 10));
        
        pool.update(1, 4);
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(1, 10));
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(2, 10));
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(3, 10));
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(4, 10));
        Assert.assertEquals(Arrays.asList( ), pool.getRaodomUsers(5, 10));
        
        pool.update(1, 5);
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(1, 10));
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(2, 10));
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(3, 10));
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(4, 10));
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(5, 10));
    }
    
    @Test
    public void update_oneLevel() {
        ActiveUserCache pool = new ActiveUserCache(1, 5, 2, 20);
        pool.update(1, 1);
        pool.update(1, 1);
        Assert.assertEquals(Arrays.asList(1), pool.getRaodomUsers(1, 10));
        
        pool.update(2, 1);
        pool.update(3, 1);
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(1, 2, 3)), 
                new HashSet<Integer>(pool.getRaodomUsers(1, 10)));
    }
    
    @Test
    public void getRandomUsers_levelRange() {
        ActiveUserCache pool = new ActiveUserCache(1, 5, 2, 20);
        pool.update(1, 1);
        pool.update(2, 2);
        pool.update(5, 3);
        pool.update(6, 4);
        pool.update(7, 5);
        pool.update(8, 5);
        
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(1, 2)), 
                new HashSet<Integer>(pool.getRaodomUsers(1, 10)));
        
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(1, 2)), 
                new HashSet<Integer>(pool.getRaodomUsers(2, 10)));
        
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(5, 6)), 
                new HashSet<Integer>(pool.getRaodomUsers(3, 10)));
        
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(5, 6)), 
                new HashSet<Integer>(pool.getRaodomUsers(4, 10)));
        
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(7, 8)), 
                new HashSet<Integer>(pool.getRaodomUsers(5, 10)));
    }
    
    @Test
    public void getRandomUsersAroundLevel() {
        ActiveUserCache pool = new ActiveUserCache(1, 5, 2, 20);
        pool.update(1, 1);
        pool.update(2, 2);
        pool.update(5, 3);
        pool.update(6, 4);
        pool.update(7, 5);
        pool.update(8, 6);
        
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(1,2,5,6,7,8)), 
                new HashSet<Integer>(pool.getRandomUsersAround(2, 10)));
    }
    
}
