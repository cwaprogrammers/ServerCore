package com.cwa.msg.mac;

import com.cwa.crypto.DigestUtil;
import com.cwa.gamecore.message.GameMessage;

/**
 * 用SHA256算法产生MAC。
 */
public class Sha256Request extends MacRequest {

    @Override
    public int getCommandId() {
        return GameMessage.CMD_SHA256_ACTION;
    }
    
    @Override
    protected byte[] mac(byte[] msgData, byte[] secretData) {
        return DigestUtil.sha256(msgData, secretData);
    }
    
}
