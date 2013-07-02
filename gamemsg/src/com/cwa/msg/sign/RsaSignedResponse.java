package com.cwa.msg.sign;

import com.cwa.crypto.RsaKeyHolder;
import com.cwa.crypto.RsaUtil;
import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.GameMessage;
import com.cwa.gamecore.message.GameResponse;
import com.cwa.gamecore.util.MessageUtil;
import com.cwa.msg.WrapperResponse;

/**
 * 用服务器私钥签名的响应消息。
 */
public class RsaSignedResponse extends WrapperResponse {

    public RsaSignedResponse(GameResponse wrappedResponse) {
        this.wrappedResponse = wrappedResponse;
    }

    @Override
    public int getCommandId() {
        return GameMessage.CMD_SIGNED_ACTION;
    }

    @Override
    public void encodeBody(GameOutput out) {
        byte[] msgData = MessageUtil.encode(wrappedResponse);
        byte[] msgSign = RsaUtil.sign(msgData, RsaKeyHolder.getInstance().getPrivateKey());
        
        out.putBytes(msgData);
        out.putBytes(msgSign);
    }
    
}
