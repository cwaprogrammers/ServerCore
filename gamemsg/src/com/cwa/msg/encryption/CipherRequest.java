package com.cwa.msg.encryption;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.message.AbstractRequest;
import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.util.MessageUtil;

/**
 * 加密的请求消息。
 */
public abstract class CipherRequest extends AbstractRequest {

    private byte[] cipherData; // 加密数据
    private GameRequest plainRequest; // 解密后的请求
    
    public CipherRequest(int cmdId) {
        super(cmdId);
    }

    /**
     * 返回解密之后的请求消息。
     */
    public GameRequest getPlainRequest() {
        return plainRequest;
    }

    @Override
    public void decodeBody(GameInput in) {
        cipherData = in.getBytes();
    }
    
    /**
     * 解密消息。
     */
    public void decrypt() {
        byte[] plainData = decrypt(cipherData);
        plainRequest = MessageUtil.decode(plainData);
    }
    
    /**
     * 解密消息。解密算法由子类确定。
     * @param cipherData 消息密文
     * @return 消息明文
     */
    protected abstract byte[] decrypt(byte[] cipherData);
    
}
