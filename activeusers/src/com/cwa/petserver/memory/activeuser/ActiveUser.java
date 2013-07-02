/**
 * Author: zero
 * Created: 2012-8-6
 */
package com.cwa.petserver.memory.activeuser;

/**
 * 内存中临时记录的活跃玩家数据。
 * @author zero
 */
public class ActiveUser
{
	private int roleId;
	private boolean online;
	private long lastUpdateTime;
	private int level;
	
	public ActiveUser()
	{
		this.roleId = -1;
		this.online = false;
		this.lastUpdateTime = 0L;
	}
		
	public ActiveUser(int roleId, int level)
	{
		this.roleId 	= roleId;
		this.level 		= level;
		this.online 	= true;
		this.lastUpdateTime = System.currentTimeMillis();
	}
	
	public int getRoleId()
	{
		return roleId;
	}
	
	public boolean isOnline()
	{
		return online;
	}
	
	public long getLastUpdateTime()
	{
		return lastUpdateTime;
	}
	
	public int getLevel()
	{
		return level;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId)
	{
		this.roleId = roleId;
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline(boolean online)
	{
		this.online = online;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(long lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level)
	{
		this.level = level;
	}
}
