package com.cwa.gamecore.message;

import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.io.IEncodable;

/**
 * 游戏服务器发送给客户端的响应消息。
 */
public interface GameResponse extends IEncodable {
    
    /**
     * 返回响应消息的命令ID。
     */
    public int getCommandId();
    
    /**
     * 编码响应消息。
     */
    @Override
    public void encodeBody(GameOutput out);
    
}
