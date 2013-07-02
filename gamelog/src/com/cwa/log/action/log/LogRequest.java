package com.cwa.log.action.log;
/**
 * @author Pai
 * @descirption 消息接收类
 * 12.12.24
 */
import java.io.DataOutputStream;

import org.apache.log4j.Logger;

import com.cwa.gamecore.io.GameInput;
import com.cwa.httpserver.message.HttpRequest;
import com.cwa.log.manage.CommandID;

public class LogRequest extends HttpRequest {
	private static Logger logger = Logger.getLogger(LogRequest.class);
    public LogRequest() {
        super(CommandID.CMD_LOGIN);
    }
    private DataOutputStream out;
    private int cId;
    private String colName;
    private String gameLog;
    
   
    @Override
    public void decode(GameInput in) {
    	colName = in.getString();
    	gameLog = in.getString();
//    	gameLog = "";
        
    }

	@Override
	public int getCommandId() {
		// TODO Auto-generated method stub
		return super.getCommandId();
	}

	/**
	 * @return the gameLog
	 */
	public String getGameLog() {
		return gameLog;
	}

	/**
	 * @return the colName
	 */
	public String getColName() {
		return colName;
	}
    
    
}