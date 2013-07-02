package com.cwa.petserver.timer;

/**
 * Author: zero
 * Created: 2012-7-9
 * Copyright: CWA HummingBird 2012
 */
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cwa.petserver.memory.activeuser.ActiveUserManager;

/**
 * 维护在线的活跃玩家列表，将非活跃的玩家从列表中剔除出去。
 * @author zero
 */
public class ActiveUserMaintainer extends QuartzJobBean 
{
	static final Logger logger = Logger.getLogger(ActiveUserMaintainer.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException
	{
		run();
	}
	
	public void run() 
	{
		ActiveUserManager.INSTANCE.maintain();
	}
}
