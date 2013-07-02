package com.cwa.gamecore.session;

import com.cwa.gamecore.action.TestAction;
import com.cwa.gamecore.message.TestMessage;
import junit.framework.Assert;
import org.junit.Test;

public class CatchResponseGameSessionTest {
    
    @Test
    public void test() {
        CatchResponseGameSession session = new CatchResponseGameSession();
        Assert.assertNull(session.getResponse());
        
        TestMessage msg = new TestMessage();
        new TestAction().execute(session, msg);
        
        Assert.assertNotNull(session.getResponse());
        Assert.assertSame(session.getResponse(), msg);
    }
    
}
