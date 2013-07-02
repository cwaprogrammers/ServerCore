/**
 * 
 */
package com.cwa.bio.core;

import java.util.HashMap;
import java.util.Map;

import com.cwa.gamecore.message.AbstractMessage;

/**
 * @author zero
 */
public enum BIOMessageFactory
{
	INSTANCE;
	
	BIOMessageFactory()
	{
		msgPool = new HashMap<Integer, Class<?>>();
	}
	
	Map<Integer, Class<?>> msgPool;
	
	public void register(int commandId, Class<?> msgClass)
	{
		msgPool.put(commandId, msgClass);
	}
	
	public AbstractMessage get(int commandId)
	{
		try {
			return (AbstractMessage) msgPool.get(commandId).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
