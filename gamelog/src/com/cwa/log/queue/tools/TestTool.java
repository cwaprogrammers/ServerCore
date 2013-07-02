package com.cwa.log.queue.tools;

import java.util.Date;

import com.cwa.log.queue.domain.TestLog;
/**
 * @author Pai
 * @descirption 测试日志操作
 * 12.12.24
 */

public class TestTool extends BaseLogTool {
	public static void write(int userId,String gameLog,int cmdId,String colName) {
		/**
		 * 第一步：构建日志对象
		 */
		TestLog log = new TestLog();
		log.init(userId,gameLog,cmdId,colName);
		/**
		 * 第二步：记录日志
		 */
		add(log);
	}
}
