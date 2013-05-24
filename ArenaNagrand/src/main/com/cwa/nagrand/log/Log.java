/**
 * 
 */
package com.cwa.nagrand.log;

import org.apache.log4j.Logger;

/**
 * @author zero
 */
public class Log
{
	private static final Logger log = Logger.getLogger(Log.class);
	
	public static void info(Object obj, Object... args)
	{
		try {
			log.info(String.format(obj.toString(), args));
		} catch (Exception e) {
			log.fatal(e, e);
		}
	}
	
	public static void debug(Object obj, Object... args)
	{
		try {
			log.debug(String.format(obj.toString(), args));
		} catch (Exception e) {
			log.fatal(e, e);
		}
	}

	public static void fatal(Object obj, Object... args)
	{
		try {
			log.fatal(String.format(obj.toString(), args));
		} catch (Exception e) {
			log.fatal(e, e);
		}
	}

}
