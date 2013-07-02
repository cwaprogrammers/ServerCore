package com.cwa.gamecore.action;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class ActionFactoryTest {

    @Test
    public void testFindActions() {
        ActionFactory.getInstance().init(Arrays.asList("com.cwa"));
        Assert.assertNotNull(ActionFactory.getInstance().getAction(1));
        Assert.assertNotNull(ActionFactory.getInstance().getAction(2));
    }
    
}
