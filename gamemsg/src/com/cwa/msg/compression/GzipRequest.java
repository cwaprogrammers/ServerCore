package com.cwa.msg.compression;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.message.GameMessage;
import com.cwa.gamecore.util.MessageUtil;
import com.cwa.msg.WrapperRequest;

public class GzipRequest extends WrapperRequest {

    @Override
    public int getCommandId() {
        return GameMessage.CMD_GZIP_ACTION;
    }

    @Override
    public void decodeBody(GameInput in) {
        byte[] data = in.getGzip();
        wrappedRequest = MessageUtil.decode(data);
    }
    
}
