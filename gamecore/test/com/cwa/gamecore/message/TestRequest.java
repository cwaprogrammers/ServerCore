package com.cwa.gamecore.message;

import com.cwa.gamecore.io.GameInput;

public class TestRequest implements GameRequest {

    @Override
    public int getCommandId() {
        return 1;
    }

    @Override
    public void decodeBody(GameInput in) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
