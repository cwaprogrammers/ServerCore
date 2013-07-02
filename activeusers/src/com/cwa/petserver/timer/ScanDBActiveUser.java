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

import com.cwa.petserver.loader.ActiveUserDBLoader;
import com.cwa.petserver.loader.ActiveUserMap;
import com.cwa.petserver.loader.IActiveUserLoader;
import com.cwa.petserver.memory.activeuser.ActiveUserManager;

/**
 * 定时扫描DB，将等级符合的活跃玩家选出一部分缓存起来。
 * @author zero
 */
public class ScanDBActiveUser extends QuartzJobBean 
{
	final static int MIN_LEVEL = 1;
	final static int MAX_LEVEL = 100;
	final static int LIMIT = 1000;

	static final Logger logger = Logger.getLogger(ScanDBActiveUser.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException 
	{
		// 设置一个Loader的类型。
		IActiveUserLoader loader = new ActiveUserDBLoader();
		ActiveUserMap map = loader.load(MIN_LEVEL, MAX_LEVEL, LIMIT);
		for (int i = MIN_LEVEL; i < MAX_LEVEL; ++i) {
			ActiveUserManager.INSTANCE.pushUser(i, map.getUserList(i));
		}
	}
}
