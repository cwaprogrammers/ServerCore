/**
 * Author: zero
 * Created: 2012-8-17
 */
package com.cwa.petserver.loader;

import java.util.List;

import com.cwa.petserver.memory.activeuser.ActiveUser;

/**
 * 从DB中将活跃玩家载入到内存里，通过IAccountDAO接口来实现抓取的SQL逻辑。
 * @author zero
 */
public class ActiveUserDBLoader implements IActiveUserLoader
{
	IAccountDAO loader;
	
	@Override
	public ActiveUserMap load(int minLvl, int maxLvl, int amountPerLvl,
			Object... args)
	{
		ActiveUserMap userMap = new ActiveUserMap();
		for (int i = minLvl; i < maxLvl; ++i) {
			userMap.putUsers(i, (List<ActiveUser>) loader.load(i, amountPerLvl));
		}
		return userMap;
	}
}
