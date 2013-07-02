package com.cwa.msg.mac;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.util.MessageUtil;
import com.cwa.msg.WrapperRequest;
import java.util.Arrays;

/**
 * 带消息验证码（MAC）的请求消息。
 * 
 * MacRequest数据由消息数据和MAC两部分组成，如下所示：
 * [消息数据][MAC]
 * 
 * MAC由消息数据和秘密数据摘要产生：
 * [消息数据] + [秘密数据] = [MAC]
 */
public abstract class MacRequest extends WrapperRequest {
    
    private byte[] msgData;
    private byte[] msgMac;
    private byte[] msgMac2; // 服务器计算得到的MAC

    /**
     * 返回消息数据。
     */
    public byte[] getMsgData() {
        return msgData;
    }

    /**
     * 返回消息MAC。
     */
    public byte[] getMsgMac() {
        return msgMac;
    }

    /**
     * 返回服务器计算得到的MAC。
     */
    public byte[] getMsgMac2() {
        return msgMac2;
    }

    @Override
    public void decodeBody(GameInput in) {
        msgData = in.getBytes();
        msgMac = in.getBytes();
    }
    
    /**
     * 验证MAC。
     * @param secretData 
     * @return true 如果验证成功
     *          false 如果验证失败
     */
    public boolean checkMac(byte[] secretData) {
        msgMac2 = mac(msgData, secretData);
        boolean isMatch = Arrays.equals(msgMac, msgMac2);
        if (isMatch) {
            // MAC校验通过
            wrappedRequest = MessageUtil.decode(msgData);
        }
        
        return isMatch;
    }
    
    /**
     * 计算消息验证码（MAC）。算法由子类确定。
     * @param msgData 消息数据
     * @param secretData 
     * @return MAC
     */
    protected abstract byte[] mac(byte[] msgData, byte[] secretData);
    
}
