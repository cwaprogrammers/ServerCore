/**
 * Author: zero
 * Created: 2012-8-22
 */
package com.cwa.petserver.memory.activeuser;

import java.util.List;


/**
 * 非活跃玩家的内存维护工具，将非活跃的玩家从内存池中清除出去。
 * @author zero
 */
public class InactiveUserMaintainer implements IMaintainer
{
	/**
	 * 玩家多久不上算非活跃 
	 */
	private static final long INACTIVE_USER_THRESHOLD = 6L * 60L * 1000L;
	
	@Override
	public void filter(ActiveUserPool pool)
	{
		maintain(pool, ActiveUserConstant.MIN_LEVEL, ActiveUserConstant.MAX_LEVEL);
	}

	private void maintain(ActiveUserPool pool, int minLevel, int maxLevel)
	{
		long now = System.currentTimeMillis();
		for (int i = minLevel; i < maxLevel; ++i) {
			List<Integer> idList = pool.getUserListClone(i);
			for (Integer id : idList) {
				ActiveUser user = pool.getUser(id);
				if (isInactiveUser(user, now, INACTIVE_USER_THRESHOLD)) {
					pool.removeUser(id);
				}
			}
		}
	}
	
	/**
	 * 是否是非活跃玩家
	 * @param user			待判断的玩家
	 * @param now			当前时间
	 * @param threshold		多久没上线算是非活跃玩家，毫秒
	 * @return
	 */
	private boolean isInactiveUser(ActiveUser user, long now, long threshold)
	{
		if (user.isOnline() || 
				user.getLastUpdateTime() < now - threshold) {
			return false;
		}
		return true;
	}
}
