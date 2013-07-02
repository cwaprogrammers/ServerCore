package com.cwa.httpserver.message;

import com.cwa.gamecore.io.GameInput;

public class TestHttpRequest extends HttpRequest{

    private String username;
    
    public TestHttpRequest() {
        super(1001);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void decode(GameInput in) {
        username = in.getString();
    }
    
}
