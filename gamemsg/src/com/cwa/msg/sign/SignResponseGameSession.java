package com.cwa.msg.sign;

import com.cwa.gamecore.message.GameResponse;
import com.cwa.gamecore.session.GameSession;
import java.util.Set;

/**
 * 给响应消息增加RSA签名。
 */
public class SignResponseGameSession implements GameSession {

    private GameSession unsecureSession;

    public SignResponseGameSession(GameSession unsecureSession) {
        this.unsecureSession = unsecureSession;
    }
    
    @Override
    public void write(GameResponse resp) {
        RsaSignedResponse signedResp = new RsaSignedResponse(resp);
        unsecureSession.write(signedResp);
    }

    // 转发方法调用
    @Override
    public void broadcast(GameResponse resp) {
        unsecureSession.broadcast(resp);
    }
    @Override
    public Object setAttribute(Object key, Object val) {
        return unsecureSession.setAttribute(key, val);
    }
    @Override
    public Object getAttribute(Object key) {
        return unsecureSession.getAttribute(key);
    }
    @Override
    public Set<Object> getAttributeKeys() {
        return unsecureSession.getAttributeKeys();
    }
    @Override
    public void close() {
        unsecureSession.close();
    }
    
}
