package com.cwa.gamecore.action;

import com.cwa.gamecore.message.TestRequest;
import com.cwa.gamecore.message.TestResponse;

public class TestGameAction extends GameAction<TestRequest, TestResponse> {

    @Override
    public TestResponse execute(TestRequest req) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
