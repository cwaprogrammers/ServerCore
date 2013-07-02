package com.cwa.gamecore.action;

import com.cwa.gamecore.message.TestMessage;
import com.cwa.gamecore.session.GameSession;

public class TestAction implements IAction<TestMessage> {

    @Override
    public void execute(GameSession session, TestMessage message) {
        session.write(message);
    }
    
}
