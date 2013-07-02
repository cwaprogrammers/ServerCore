package com.cwa.qiji;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ActiveUserSaveJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
        ActiveUserManager manager = (ActiveUserManager) ctx.getMergedJobDataMap().get("activeUserManager");
        manager.saveActiveUsers();
    }
    
}
