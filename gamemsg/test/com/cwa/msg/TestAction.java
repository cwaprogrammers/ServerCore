package com.cwa.msg;

import com.cwa.gamecore.action.IAction;
import com.cwa.gamecore.session.GameSession;

public class TestAction implements IAction<TestMessage> {

    @Override
    public void execute(GameSession session, TestMessage message) {
        session.write(message);
    }
    
}
