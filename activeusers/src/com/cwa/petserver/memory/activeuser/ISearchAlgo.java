/**
 * Author: zero
 * Created: 2012-8-22
 */
package com.cwa.petserver.memory.activeuser;

import java.util.List;


/**
 * 针对内存活跃玩家池的分析算法抽象
 * @author zero
 */
public interface ISearchAlgo
{
	/**
	 * 通过指定一个等级和数量，返回该等级随机出来的玩家列表。
	 * @param pool		需要分析的活跃玩家池
	 * @param lvl		指定的随机等级
	 * @param amount	指定的数量
	 * @return			随机的玩家Id的列表结果（如果没有，返回一个0元素的列表）
	 */
	public List<Integer> getRandomUser(ActiveUserPool pool, int lvl, int amount);
}
