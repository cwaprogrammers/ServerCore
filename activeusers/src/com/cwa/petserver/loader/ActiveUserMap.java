/**
 * Author: zero
 * Created: 2012-8-17
 */
package com.cwa.petserver.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cwa.petserver.memory.activeuser.ActiveUser;

/**
 * 从持久层抓取玩家列表后返回的临时容器
 * @author zero
 */
public class ActiveUserMap
{
	Map<Integer, List<ActiveUser>> usrLvlMap;
	Map<Integer, ActiveUser> userMap;
	
	ActiveUserMap()
	{
		usrLvlMap = new HashMap<Integer, List<ActiveUser>>();
		userMap = new HashMap<Integer, ActiveUser>();
	}
	
	public void putUsers(int lvl, List<ActiveUser> userList)
	{
		usrLvlMap.put(lvl, userList);
	}
	
	public List<ActiveUser> getUserList(int lvl)
	{
		return usrLvlMap.get(lvl);
	}

	public void addUser(int lvl, ActiveUser user)
	{
		List<ActiveUser> userList = usrLvlMap.get(lvl);
		if (null == userList) {
			userList = new ArrayList<ActiveUser>();
			usrLvlMap.put(lvl, userList);
		}
		userList.add(user);
	}

	public void removeUser(int userId)
	{
		ActiveUser user = userMap.get(userId);
		if (null == user)
			return;
		List<ActiveUser> userList = usrLvlMap.get(user.getLevel());
		if (null != userList) 
			userList.remove(user);
	}
}
