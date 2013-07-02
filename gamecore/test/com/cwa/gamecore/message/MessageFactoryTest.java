package com.cwa.gamecore.message;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class MessageFactoryTest {
    
    @Test
    public void testFindMessages() {
        MessageFactory.getInstance().init(Arrays.asList("com.cwa"), Arrays.asList("Message", "Request"));
        Assert.assertEquals(1, MessageFactory.getInstance().getMessage(1).getCommandId());
        Assert.assertEquals(2, MessageFactory.getInstance().getMessage(2).getCommandId());
    }
    
}
