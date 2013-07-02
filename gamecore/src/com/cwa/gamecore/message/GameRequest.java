package com.cwa.gamecore.message;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.IDecodable;

/**
 * 客户端发送给游戏服务器的请求消息。
 */
public interface GameRequest extends IDecodable {

    /**
     * 返回请求消息的命令ID。
     */
    public int getCommandId();
    
    /**
     * 解码请求消息。
     */
    @Override
    public void decodeBody(GameInput in);
    
}
