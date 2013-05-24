/**
 * 
 */
package com.cwa.nagrand.memory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 战士管理器
 * @author zero
 */
public enum ArenaManager
{
	INSTANCE;
	
	private Map<String, Arena> arenaPool;
	
	ArenaManager()
	{
		arenaPool = new ConcurrentHashMap<String, Arena>();
	}
	
	public Map<String, Arena> getAllArena()
	{
		return arenaPool;
	}
	
	public void addArena(Arena a)
	{
		arenaPool.put(a.getArenaId(), a);
	}
	
	public Warrior getWarrior(String arenaId, String warId)
	{
		Arena arena = arenaPool.get(arenaId);
		if (null == arena)
			return null;
		return arena.getWarrior(warId);
	}
	
	public int getRank(String arenaId, String warId)
	{
		Arena arena = arenaPool.get(arenaId);
		if (null == arena)
			return 0;
		return arena.getRank(warId);
	}
	
	public boolean addWarrior(String arenaId, String warId, int initScore)
	{
		Arena arena = arenaPool.get(arenaId);
		if (arena == null) {
			arena = new Arena(arenaId);
			arenaPool.put(arenaId, arena);
		}
		
		if (arena.addWarrior(warId, initScore)) {
			return true;
		}
		return false;
	}
	
	public int changeScore(String arenaId, String warId, int changeValue)
	{
		Arena arena = arenaPool.get(arenaId);
		int rank = arena.changeScore(warId, changeValue);
		return rank;
	}
}
