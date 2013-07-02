package com.cwa.log.task;

import com.cwa.log.task.impl.CommonTaskService;


/**
 * 系统所有的计划任务都是通过这个类进行管理的
 *
 */
public class TaskManager {
	/** 通用任务服务器 **/
	private AbstractTaskService commonTaskService=CommonTaskService.getInstance();
	
	/** 单例对象  **/
	private static TaskManager manager=new TaskManager();
	private TaskManager(){}
	public static TaskManager getInstance(){
		return manager;
	}
	

	public AbstractTaskService getCommonTaskService(){
		return commonTaskService;
	}

	/**
	 * 停止所有的计划任务。这个执行是有顺序的。
	 */
	public void shutDownAll(){		
		commonTaskService.shutDown();	
	}
}
