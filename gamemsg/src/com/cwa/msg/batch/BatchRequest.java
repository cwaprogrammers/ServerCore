package com.cwa.msg.batch;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.message.AbstractRequest;
import com.cwa.gamecore.message.GameMessage;
import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.message.MessageFactory;
import com.cwa.gamecore.util.MessageUtil;

/**
 * 批量请求，只针对HTTP游戏设计。
 */
public class BatchRequest extends AbstractRequest {

    private GameRequest[] requests;
    
    public BatchRequest() {
        super(GameMessage.CMD_BATCH_ACTION);
    }

    public GameRequest[] getRequests() {
        return requests;
    }

    public void setRequests(GameRequest[] requests) {
        this.requests = requests;
    }

    @Override
    public void decodeBody(GameInput in) {
        int batchSize = in.get();
        requests = new GameRequest[batchSize];
        
        for (int i = 0; i <batchSize; i++) {
            requests[i] = readRequest(in);
        }
    }
    
    private GameRequest readRequest(GameInput in) {
        return MessageUtil.decode(in, MessageFactory.getInstance());
    }
    
}
