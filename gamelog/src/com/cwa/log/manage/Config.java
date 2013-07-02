package com.cwa.log.manage;
/**
 * @author Pai
 * 
 * 12.12.24
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	
	public final static String mongodb_ip;
	
	public final static int mongodb_port;
	
	public final static int mongodb_poolsize;
	
	public final static String GAME_NAME = "HERO";
	
	/**
	 * 延时5秒
	 */
	public final static long LOG_TASK_DELAY = 5 * 1000;
	/**
	 * 周期60秒
	 */
	public final static long LOG_TASK_PERIOD = 60 * 1000;
	
	static{
		Properties properties = new Properties();
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("./config/mongodb.properties");
			properties.load(inputStream);
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mongodb_ip = properties.getProperty("mongodb_ip");
		mongodb_port = Integer.valueOf(properties.getProperty("mongodb_port"));
		mongodb_poolsize = Integer.valueOf(properties.getProperty("mongodb_poolsize"));
	}

}
