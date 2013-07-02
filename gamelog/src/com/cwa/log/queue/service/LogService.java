package com.cwa.log.queue.service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.cwa.log.manage.Config;
import com.cwa.log.queue.domain.BaseLog;
import com.cwa.log.queue.persistence.LogDAO;
import com.cwa.log.task.TaskCallbackHandler;
import com.cwa.log.task.TaskManager;

/**
 * @author Pai
 * @descirption 队列操作 12.12.24
 */
public class LogService {
	private final static Logger logger = Logger.getLogger(LogDAO.class);
	private static LogService instance = new LogService();

	private final Queue<BaseLog> logQueue = new ConcurrentLinkedQueue<BaseLog>();
	private TaskCallbackHandler schedulePeriodicTask;

	private final LogDAO logDao = new LogDAO();

	private LogService() {
	}

	public static LogService getInstance() {
		return instance;
	}

	/**
	 * 启动日志服务器的计划任务
	 */
	public void start() {
		if (logger.isInfoEnabled())
			logger.info("启动日志计划任务");
		schedulePeriodicTask = TaskManager
				.getInstance()
				.getCommonTaskService()
				.scheduleAtFixedRate(new LogBatchTask(), Config.LOG_TASK_DELAY,
						Config.LOG_TASK_PERIOD);
	}

	/**
	 * 停止日志服务器
	 */
	public void shutdown() {
		TaskManager.getInstance().shutDownAll();

		if (schedulePeriodicTask != null) {
			schedulePeriodicTask.cancel();
		}

		if (logger.isInfoEnabled())
			logger.info("停止日志计划任务");
		schedulePeriodicTask.cancel();
		storeLog();
	}

	/**
	 * 添加消息到队列中
	 * 
	 * @param obj
	 */
	public void addLog(BaseLog obj) {
		// logger.info("gamelog start join queue");
		this.logQueue.offer(obj);
	}

	/**
	 * 执行发送日志的方法，由计划任务调用
	 */
	protected void storeLog() {

		logDao.batchSave(logQueue);
		// logger.info("gamelog sending");
	}

}
