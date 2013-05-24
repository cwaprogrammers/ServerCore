/**
 * 
 */
package com.cwa.nagrand.memory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cwa.nagrand.log.Log;

/**
 * @author zero
 */
public class WarriorPool implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Map<String, Warrior> warriorMap;
	
	public WarriorPool()
	{
		warriorMap = new ConcurrentHashMap<String, Warrior>();
	}
	
	public boolean add(Warrior warrior)
	{
		if (!warriorMap.containsKey(warrior.warId)) {
			warriorMap.put(warrior.warId, warrior);
			return true;
		} else {
			Log.fatal("Arena alread has warrior %s.", warrior.warId);
			return false;
		}
	}
	
	public int getRank(String warId)
	{
		Warrior warrior = warriorMap.get(warId);
		if (null == warrior)
			return 0;
		return getRank(warrior);
	}
	
	public int getRank(Warrior warrior)
	{
		int rank = 1;
		for (Warrior wa : warriorMap.values()) {
			if (wa.score > warrior.score) {
				rank++;
			}
		}
		
		return rank;
	}
	
	public Warrior get(String warId)
	{
		return warriorMap.get(warId);
	}
	
	public void update(Warrior warrior)
	{
		warriorMap.put(warrior.warId, warrior);
	}
}
