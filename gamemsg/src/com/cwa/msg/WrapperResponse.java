package com.cwa.msg;

import com.cwa.gamecore.message.GameResponse;

public abstract class WrapperResponse implements GameResponse {
    
    protected GameResponse wrappedResponse;

    public GameResponse getWrappedResponse() {
        return wrappedResponse;
    }
    
}
