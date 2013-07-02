package com.cwa.msg.encryption;

import com.cwa.crypto.RsaKeyHolder;
import com.cwa.crypto.RsaUtil;
import com.cwa.gamecore.message.GameMessage;

/**
 * 用RSA公钥加密的请求消息。
 */
public class RsaRequest extends CipherRequest {
    
    public RsaRequest() {
        super(GameMessage.CMD_RSA_ACTION);
    }

    @Override
    protected byte[] decrypt(byte[] cipherData) {
        return RsaUtil.decrypt(cipherData, RsaKeyHolder.getInstance().getPrivateKey());
    }
    
}
