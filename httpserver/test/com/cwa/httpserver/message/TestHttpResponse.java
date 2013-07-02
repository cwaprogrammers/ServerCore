package com.cwa.httpserver.message;

import com.cwa.gamecore.io.GameOutput;

public class TestHttpResponse extends HttpResponse {

    private String message;
    
    public TestHttpResponse() {
        super(1001);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void encode(GameOutput out) {
        out.putString(message);
    }
    
}
