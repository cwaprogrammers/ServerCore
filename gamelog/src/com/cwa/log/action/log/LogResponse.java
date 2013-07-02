package com.cwa.log.action.log;
/**
 * @author Pai
 * @descirption 消息发送类
 * 12.12.24
 */
import org.apache.log4j.Logger;

import com.cwa.gamecore.io.GameOutput;
import com.cwa.httpserver.message.HttpResponse;
import com.cwa.log.manage.CommandID;

public class LogResponse extends HttpResponse {
	private static Logger logger = Logger.getLogger(LogResponse.class);
    private int gameVersion;
    
    public LogResponse(int gameVersion) {
        super(CommandID.CMD_LOGIN);
        this.gameVersion = gameVersion;
    }

    public int getGameVersion() {
        return gameVersion;
    }

    @Override
    public void encode(GameOutput out) {
        out.putInt(gameVersion);
    }
    
}
