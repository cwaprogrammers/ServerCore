package com.cwa.log.action.log;

/**
 * @author Pai
 * @descirption 消息逻辑类
 * 12.12.24
 */
import org.apache.log4j.Logger;

import com.cwa.httpserver.action.HttpAction;
import com.cwa.httpserver.message.HttpResponse;
import com.cwa.log.queue.tools.TestTool;

//登录，告诉客户端服务器版本
public class LogAction extends HttpAction<LogRequest, HttpResponse> {
	private static Logger logger = Logger.getLogger(LogAction.class);

	@Override
	public HttpResponse execute(LogRequest req) {
		// TODO 记录登录状态
		int commandId = req.getCommandId();
		int userId = req.getUserId();
		String colName = req.getColName();
		String log = req.getGameLog();

		TestTool.write(userId, log, commandId,colName);
//		logger.info(colName + log);
		int gameVersion = 1;
		return new LogResponse(gameVersion);
//		return null;
	}

}