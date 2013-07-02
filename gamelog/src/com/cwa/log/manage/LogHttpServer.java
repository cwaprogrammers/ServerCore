package com.cwa.log.manage;

import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;

import com.cwa.httpserver.GameJettyServer;
import com.cwa.log.queue.service.LogService;
import com.cwa.log.task.TaskCallbackHandler;

/**
 * @author Pai
 * @descirption 服务启动
 * 12.12.24
 */
public class LogHttpServer {

	/**
	 * 日志服务的启动类
	 */
	private static final Logger logger = Logger.getLogger(LogHttpServer.class);
	private static TaskCallbackHandler schedulePeriodicTask=null;

	public static void main(String[] args) throws Exception {
//		//计划任务服务器
//		schedulePeriodicTask = TaskManager.getInstance().getCommonTaskService()
//				.scheduleAtFixedRate(new Task() {
//					public void run() {
//					}
//				}, 2000L, 100L);
		//计划任务启动
		new Thread(new Runnable()
        {
                    public void run() {
                    	LogService.getInstance().start();
                    }
        }
        ).start();		
		PropertyConfigurator.configure("./config/logger.properties");
		PropertyConfigurator.configure("./config/slf4j.properties");

		//消息服务器
		logger.debug("Initialize Spring...");
		ApplicationContext appCtx = GameContext.getInstance().getAppContext();

		logger.debug("Start Jetty Server...");
		GameJettyServer gameServer = (GameJettyServer) appCtx
				.getBean("gameServer");
		//消息服务器启动
		gameServer.start();
		
		
		
		/*****************关闭服务器******************************/
		Scanner input = new Scanner(System.in);
		String s = input.nextLine();

		while (!s.equals("shutdown")) {
			System.out.println(s);

			s = null;
			s = input.nextLine();
		}

		LogService.getInstance().shutdown();
		System.exit(0);
	}

}
