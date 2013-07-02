package com.cwa.minaserver.config;

import com.cwa.gamecore.config.GameConfig;
import com.cwa.minaserver.GameCoreConstants;

/**
 * 服务器配置。
 */
public class GameServerConfig extends GameConfig {
    
    private int readBufferSize = GameCoreConstants.READ_BUFFER_SIZE;
    private int receiveBufferSize = GameCoreConstants.RECEIVE_BUFFER_SIZE;
    private int encodeBufferSize = GameCoreConstants.MSG_ENCODE_BUFFER_SIZE;
    private int maxMsgDataSize = GameCoreConstants.MAX_MSG_DATA_SIZE;
    
    public int getReadBufferSize() {
        return readBufferSize;
    }

    public void setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
    }

    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public void setReceiveBufferSize(int receiveBufferSize) {
        this.receiveBufferSize = receiveBufferSize;
    }
    
    public int getEncodeBufferSize() {
        return encodeBufferSize;
    }

    public void setEncodeBufferSize(int encodeBufferSize) {
        this.encodeBufferSize = encodeBufferSize;
    }

    public int getMaxMsgDataSize() {
        return maxMsgDataSize;
    }

    public void setMaxMsgDataSize(int maxMsgDataSize) {
        this.maxMsgDataSize = maxMsgDataSize;
    }

}
