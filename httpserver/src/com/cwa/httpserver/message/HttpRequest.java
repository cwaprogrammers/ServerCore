package com.cwa.httpserver.message;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.message.AbstractRequest;

/**
 * 如果服务器采用HTTP协议，那么请求消息需要玩家ID来标识该请求是谁发送的。
 * HttpRequest的解码方法首先会读一个int型的玩家ID，然后会调用方法decode()读剩余
 * 数据。
 */
public abstract class HttpRequest extends AbstractRequest {

    private int userId;
    
    public HttpRequest(int cmdId) {
        super(cmdId);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public final void decodeBody(GameInput in) {
        readUserId(in);
        decode(in);
    }
    
    /**
     * 读（整型）玩家ID。
     */
    protected void readUserId(GameInput in) {
        userId = in.getInt();
    }
    
    /**
     * 读其余数据。这个方法由子类实现。
     */
    public abstract void decode(GameInput in);
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{userId=" + userId + '}';
    }
    
}
