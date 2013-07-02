package com.cwa.msg;

import com.cwa.gamecore.message.GameRequest;

public abstract class WrapperRequest implements GameRequest {
    
    protected GameRequest wrappedRequest;

    public GameRequest getWrappedRequest() {
        return wrappedRequest;
    }
    
}
