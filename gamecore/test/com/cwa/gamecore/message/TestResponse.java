package com.cwa.gamecore.message;

import com.cwa.gamecore.io.GameOutput;

public class TestResponse implements GameResponse {

    @Override
    public int getCommandId() {
        return 1;
    }

    @Override
    public void encodeBody(GameOutput out) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
