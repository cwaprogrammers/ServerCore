package com.cwa.qiji;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActiveUserSaveJobTest {

    @Test
    public void test() throws Exception {
        ApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[] {"config/timer.xml"});
        Assert.assertNotNull(appCtx);
        
        ActiveUserManager aum = (ActiveUserManager) appCtx.getBean("activeUserManager");
        Assert.assertNotNull(aum);
        
        //Thread.sleep(6000000);
    }
    
}
