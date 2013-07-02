package com.cwa.log.queue.tools;

import com.cwa.log.queue.domain.BaseLog;
import com.cwa.log.queue.service.LogService;
/**
 * @author Pai
 * @descirption 基础日志操作
 * 12.12.24
 */

public abstract class BaseLogTool {
	protected static void add(BaseLog log) {
		LogService.getInstance().addLog(log);
	}
}
