package com.cwa.msg.mac;

import com.cwa.crypto.DigestUtil;
import com.cwa.gamecore.message.GameMessage;

/**
 * 用MD5算法产生MAC。
 */
public class Md5Request extends MacRequest {

    @Override
    public int getCommandId() {
        return GameMessage.CMD_MD5_ACTION;
    }

    @Override
    protected byte[] mac(byte[] msgData, byte[] secretData) {
        return DigestUtil.md5(msgData, secretData);
    }
    
}
