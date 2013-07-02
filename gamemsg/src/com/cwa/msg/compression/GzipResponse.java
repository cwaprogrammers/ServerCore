package com.cwa.msg.compression;

import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.GameMessage;
import com.cwa.gamecore.message.GameResponse;
import com.cwa.gamecore.util.MessageUtil;
import com.cwa.msg.WrapperResponse;

public class GzipResponse extends WrapperResponse {

    public GzipResponse(GameResponse wrappedResponse) {
        super.wrappedResponse = wrappedResponse;
    }

    @Override
    public int getCommandId() {
        return GameMessage.CMD_GZIP_ACTION;
    }

    @Override
    public void encodeBody(GameOutput out) {
        byte[] rawData = MessageUtil.encode(wrappedResponse);
        out.putGzip(rawData);
    }
    
}
