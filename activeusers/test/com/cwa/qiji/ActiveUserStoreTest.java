package com.cwa.qiji;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.Assert;
import org.junit.Test;

public class ActiveUserStoreTest {
    
    @Test
    public void load_noFile() {
        ActiveUserCache pool = new ActiveUserCache(1, 2, 2, 10);
        ActiveUserStore loader = new FileActiveUserStore("not_existed_file.data");
        loader.load(pool);
        
        Assert.assertEquals(0, pool.getRaodomUsers(1, 10).size());
        Assert.assertEquals(0, pool.getRaodomUsers(2, 10).size());
    }
    
    @Test
    public void saveLoad() {
        ActiveUserCache pool = new ActiveUserCache(1, 5, 2, 100);
        pool.update(101, 1);
        pool.update(201, 2);
        pool.update(301, 3);
        pool.update(302, 3);
        
        ActiveUserStore loader = new FileActiveUserStore("test.data");
        loader.save(pool);
        
        ActiveUserCache pool2 = new ActiveUserCache(1, 5, 2, 100);
        loader.load(pool2);
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(101, 201)), 
                new HashSet<Integer>(pool2.getRaodomUsers(1, 10)));
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(101, 201)), 
                new HashSet<Integer>(pool2.getRaodomUsers(2, 10)));
        Assert.assertEquals(
                new HashSet<Integer>(Arrays.asList(301, 302)), 
                new HashSet<Integer>(pool2.getRaodomUsers(3, 10)));
    }
    
}
