package com.cwa.httpserver.message;

import com.cwa.gamecore.io.GameOutput;

/**
 * 这个响应只是简单的返回一个状态码。
 */
public class SimpleResponse extends HttpResponse {
    
    public SimpleResponse(HttpRequest req) {
        this(req.getCommandId());
    }
    
    public SimpleResponse(int cmdId) {
        super(cmdId);
    }
    
    @Override
    public void encode(GameOutput out) {
        // 
    }
    
    @Override
    public String toString() {
        return "SimpleResponse{CMD=" + getCommandId()
                + ", RC=" + getResponseCode() + '}';
    }
    
}
