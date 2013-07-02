/**
 * Author: zero
 * Created: 2012-8-22
 */
package com.cwa.petserver.memory.activeuser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
 * 活跃玩家的内存池，只针对包内的ISearchAlgo相关类暴露接口。
 * @author zero
 */
public class ActiveUserPool
{
	private final static Logger logger = Logger.getLogger(ActiveUserPool.class);
	
	private Map<Integer, Vector<Integer>> userLvlMap;
	private Map<Integer, ActiveUser> userMap;
	
	protected ActiveUserPool()
	{
		userLvlMap 	= new ConcurrentHashMap<Integer, Vector<Integer>>();
		userMap 	= new ConcurrentHashMap<Integer, ActiveUser>();
	}
	
	/**
	 * 通过玩家等级获取ActiveUser的列表，如果没有，就返回一个空列表。
	 * @param lvl
	 * @return
	 */
	protected List<Integer> getUserListClone(int lvl)
	{
		return cloneToList(userLvlMap.get(lvl));
	}
		
	private List<Integer> cloneToList(Vector<Integer> src)
	{
        return new ArrayList<Integer>(src);
	}
    
	/**
	 * 将一个玩家添加到指定等级的容器里
	 * @param lvl
	 * @param user
	 */
	protected void addUser(int lvl, ActiveUser user)
	{
		userMap.put(user.getRoleId(), user);
		add(userLvlMap, lvl, user.getRoleId());
	}
	
	/**
	 * 添加一对值到map包含的list中，如果对应的list是空的，就新建一个list。
	 * @param map
	 * @param key
	 * @param obj
	 */
	private void add(Map<Integer, Vector<Integer>> map, Integer key, Integer obj)
	{
		Vector<Integer> list = map.get(key);
		if (null == list) {
			list = new Vector<Integer>(); 
			map.put(key, list);
		}
		list.add(obj);
	}
	
	/**
	 * 将一个列表的玩家放置到指定等级的列表中。
	 * @param lvl		等级
	 * @param users		待载入的玩家列表
	 */
	protected void pushUsers(int lvl, List<ActiveUser> users)
	{
		removeAllUser(lvl);
		userLvlMap.put(lvl, cloneToVector(users));
		for (ActiveUser u : users) {
			userMap.put(u.getRoleId(), u);
		}
	}
    
	private void removeAllUser(int lvl)
	{
		List<Integer> userList = getUserListClone(lvl);
		for (Integer id : userList) {
			removeUser(id);
		}
	}
	
    private Vector<Integer> cloneToVector(List<ActiveUser> src)
	{
		Vector<Integer> dest = new Vector<Integer>();
		if (src != null) {
			for (ActiveUser u : src) {
				dest.add(u.getRoleId());
			}
		}
		return dest;
	}
	
	/**
	 * 更新玩家的状态
	 * @param user
	 */
	protected void updateUser(ActiveUser user)
	{
		removeUser(user.getRoleId());
		addUser(user.getLevel(), user);
	}
	
	/**
	 * 获取玩家
	 * @param roleId
	 * @return
	 */
	protected ActiveUser getUser(int roleId)
	{
		ActiveUser user = userMap.get(roleId);
		return user;
	}
	
	/**
	 * 将某个玩家从指定等级的容器中移除。
	 * @param lvl	等级
	 * @param roleId	待移除的玩家的Id
	 */
	protected void removeUser(int roleId)
	{
		ActiveUser user = userMap.remove(roleId);
		if (null == user)
			return;
		int lvl = user.getLevel();
		try {
			userLvlMap.get(lvl).remove(new Integer(roleId));
		} catch (NullPointerException e) {
			// 以防止数据不对称的异常，在userLvlMap中已经没有这个等级的玩家列表了。
			logger.fatal(e, e);
		}
	}
	
}
