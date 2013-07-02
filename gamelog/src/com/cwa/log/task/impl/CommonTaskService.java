package com.cwa.log.task.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.cwa.log.task.AbstractTaskService;


/**
 * 
 * @author 
 * @descirption 通用的任务服务提供器
 */
public class CommonTaskService extends AbstractTaskService {

	/**
	 * 具体执行的线程池对象
	 */
	private final ScheduledThreadPoolExecutor scheduler = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(16);
	/**
	 * 第一次执行的延迟时间
	 */
	private static long initialDelay=20001;
	
	/**
	 * 周期默认为100ms
	 */
	private static long milliSecondPerFrame = 100;
	
	/**
	 * 单例对象
	 */
	private static  CommonTaskService instance=new CommonTaskService();
	
	private CommonTaskService(){}
	
	public static CommonTaskService getInstance(){
		return instance;
	}

	@Override
	protected long getDelay() {
		return initialDelay;
	}

	@Override
	protected ScheduledThreadPoolExecutor getExecutor() {
		return scheduler;
	}

	@Override
	protected String getName() {
		return "通用任务服务器";
	}

	@Override
	protected long getPeriod() {
		return milliSecondPerFrame;
	}
}
