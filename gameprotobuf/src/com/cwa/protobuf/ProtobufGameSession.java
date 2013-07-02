package com.cwa.protobuf;

import com.cwa.gamecore.session.GameSession;
import com.google.protobuf.MessageLite;

/**
 * GameSession包装类。
 */
public class ProtobufGameSession {
    
    public static final String ROLE_ID_KEY = "ROLE_ID_KEY";
    
    private int msgId;
    private GameSession gameSession;
    
    public ProtobufGameSession(int msgId, GameSession gameSession) {
        this.msgId = msgId;
        this.gameSession = gameSession;
    }
    
    public GameSession getGameSession() {
        return gameSession;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }
    
    public int getMsgId() {
        return msgId;
    }
    
    public void putRoleId(Long roleId) {
        gameSession.setAttribute(ROLE_ID_KEY, roleId);
    }
    
    public Long getRoleId() {
        Object roleId = gameSession.getAttribute(ROLE_ID_KEY);
        if (roleId == null) {
            throw new ProtobufException("No role ID attached to session " + gameSession);
        }
        
        return (Long) roleId;
    }
    
    public Long safeGetRoleId() {
        Object roleId = gameSession.getAttribute(ROLE_ID_KEY);
        return roleId == null ? (-1) : ((Long) roleId);
    }
    
    public void write(MessageLite msg) {
        gameSession.write(ProtobufGameMessage.wrap(msg));
    }
    
}
