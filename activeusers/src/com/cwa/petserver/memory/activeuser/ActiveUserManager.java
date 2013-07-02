/**
 * Author: zero
 * Created: 2012-8-22
 */
package com.cwa.petserver.memory.activeuser;

import com.cwa.IActiveUserManager;
import java.util.List;

/**
 * 对外使用的活跃玩家随机抓取工具
 * @author zero
 */
public enum ActiveUserManager implements IActiveUserManager
{
	// 单例，拒绝继承
	INSTANCE;
	
	ActiveUserPool pool;
	ISearchAlgo algo;
	IMaintainer maintainer;
	
	ActiveUserManager()
	{
		pool 		= new ActiveUserPool();
		maintainer  = new InactiveUserMaintainer();
		algo		= new RandomSearchAlgo();
	}
	
	/**
	 * 开始清理非活跃用户 
	 */
	public void maintain()
	{
		maintainer.filter(pool);
	}
	
	/**
     * @see IActiveUserManager#randomUsers(int, int) 
     */
    @Override
    public List<Integer> randomUsers(int amount, int level)
	{
		return algo.getRandomUser(pool, level, amount);
	}

    /**
     * @see IActiveUserManager#randomUsersAroundLevel(int, int) 
     */
    @Override
    public List<Integer> randomUsersAroundLevel(int amount, int level)
    {
        return randomUsers(amount, level);
    }
	
	/**
	 * 将一个列表的玩家放入到指定等级的玩家容器中。放入后会将之前的列表移除。（在初始化的
	 * 时候读取DB使用）
	 * @param level		指定的等级
	 * @param users		待放入的玩家列表
	 */
	public void pushUser(int level, List<ActiveUser> users)
	{
		pool.pushUsers(level, users);
	}
	
	/**
     * @see IActiveUserManager#userOnline(int, int) 
     */
    @Override
	public void userOnline(int roleId, int level)
	{
		ActiveUser user = pool.getUser(roleId);
		if (null == user) {
			pool.addUser(level, new ActiveUser(roleId, level));
		} else {
			user.setOnline(true);
			user.setLastUpdateTime(System.currentTimeMillis());
			pool.updateUser(user);
		}
	}
	
	/**
     * @see IActiveUserManager#userOffline(int, int) 
     */
    @Override
	public void userOffline(int roleId, int level)
	{
		ActiveUser user = pool.getUser(roleId);
		if (null == user) {
			user = new ActiveUser(roleId, level);
			user.setOnline(false);
			pool.addUser(level, user);
		} else {
			user.setOnline(false);
			user.setLastUpdateTime(System.currentTimeMillis());
			pool.updateUser(user);
		}
	}
}


