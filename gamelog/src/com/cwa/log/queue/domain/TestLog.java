package com.cwa.log.queue.domain;

import java.util.Date;
import java.util.HashMap;
/**
 * @author Pai
 * @descirption 测试日志
 * 12.12.24
 */
public class TestLog extends BaseLog {

	public void init(int userId,String gameLog, int cmdId,String colName) {
		this.userId = userId;
		this.gameLog = gameLog;
		this.cmdId = cmdId;
		this.colName = colName;
	}

}
