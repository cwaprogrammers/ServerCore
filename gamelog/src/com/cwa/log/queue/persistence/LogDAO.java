/**
 * 2011-8-11 下午03:51:09
 */
package com.cwa.log.queue.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.zip.Deflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.log.manage.Config;
import com.cwa.log.mongo.StorageManager;
import com.cwa.log.queue.domain.BaseLog;
import com.mongodb.util.Hash;

/**
 * @author Pai
 * @descirption 从队列存入MongoDB
 * 12.12.24
 */

public class LogDAO {
	private final static Logger logger = LoggerFactory.getLogger(LogDAO.class);

	public LogDAO() {
		// super(daoManager);
	}

	public void batchSave(Queue<BaseLog> logQueue) {
		// logger.error("batchSave");
		if (logQueue == null)
			return;
		long startTime = System.currentTimeMillis();
		int queueSize = logQueue.size();
		if (queueSize == 0)
			return;

		int insertNum = 0;
		for (int i = 0; i < queueSize; i++) {
			BaseLog log = logQueue.poll();
			if (log == null) {
				logger.error("[记录日志] null");
				break;
			}
			insertNum++;
			save(log);
		}

		long logTime = System.currentTimeMillis() - startTime;
		logger.error("[记录日志] [大小:" + insertNum + "] [时间:" + logTime + "]");
	}

	private void save(BaseLog log) {
		// try {
//		logger.error("gamelog try write queue{},{},  {}", log.getUserId()
//				+ "  " + log.getCmdId(), log.getGameLog());
		String colName = log.getColName();
		String gameLog = log.getGameLog();
		StorageManager.getInstance(Config.mongodb_ip, Config.mongodb_port, Config.GAME_NAME,Config.mongodb_poolsize).saveStorage(
				colName, gameLog);
//		logger.error("gamelog write queue over" + colName
//				+ log.getGameLog());
		// // StorageManager.
		// // String saveMethod = "insert" + log.getClass().getSimpleName();
		// // insert(saveMethod, log);
		// } catch (Exception e) {
		// // logger.error("saveLog", e);
		// e.printStackTrace();
		// }
	}

}
