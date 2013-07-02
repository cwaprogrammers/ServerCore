package com.cwa.log.queue.domain;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author Pai
 * @descirption 基础日志 12.12.24
 */
public abstract class BaseLog {

	// 用户ID
	protected int userId;
	// 日志
	protected String gameLog;
	// 行为命令编号
	protected int cmdId;
	// collection
	protected String colName;

	public BaseLog() {
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the gameLog
	 */
	public String getGameLog() {
		return gameLog;
	}

	/**
	 * @param gameLog
	 *            the gameLog to set
	 */
	public void setGameLog(String gameLog) {
		this.gameLog = gameLog;
	}

	/**
	 * @return the cmdId
	 */
	public int getCmdId() {
		return cmdId;
	}

	/**
	 * @param cmdId
	 *            the cmdId to set
	 */
	public void setCmdId(int cmdId) {
		this.cmdId = cmdId;
	}

	/**
	 * @return the colName
	 */
	public String getColName() {
		return colName;
	}

	/**
	 * @param colName
	 *            the colName to set
	 */
	public void setColName(String colName) {
		this.colName = colName;
	}

}
