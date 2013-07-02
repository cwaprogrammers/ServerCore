package com.cwa.msg.batch;

import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.AbstractResponse;
import com.cwa.gamecore.message.GameMessage;
import com.cwa.gamecore.message.GameResponse;
import com.cwa.gamecore.util.MessageUtil;

/**
 * 批量响应，只针对HTTP游戏设计。
 */
public class BatchResponse extends AbstractResponse {

    private GameResponse[] responses;
    
    public BatchResponse(GameResponse[] responses) {
        super(GameMessage.CMD_BATCH_ACTION);
        this.responses = responses;
    }

    public GameResponse[] getResponses() {
        return responses;
    }

    @Override
    public void encodeBody(GameOutput out) {
        out.put((byte) responses.length);
        for (GameResponse resp : responses) {
            out.putBytes(MessageUtil.encode(resp));
        }
    }
    
}
