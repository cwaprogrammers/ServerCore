package com.cwa.msg;

import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.session.GameSession;

/**
 * 计算消息验证码（MAC）需要一个秘密数据。这个数据只有服务器和客户端自己知道（比如
 * 玩家自己的密码）。SecretProvider提供这个秘密数据。
 */
public interface SecretProvider {
    
    public abstract byte[] getSecret(GameSession session, GameRequest req);
    
}
