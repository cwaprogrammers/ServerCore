/**
 * Author: zero
 * Created: 2012-8-21
 */
package com.cwa.petserver.loader;

/**
 * 从持久化层中根据等级分别读取每等级活跃玩家列表的接口
 * @author zero
 */
public interface IActiveUserLoader
{
	/**
	 * 从持久化层抓取活跃玩家列表。
	 * @param minLvl		最小等级
	 * @param maxLvl		最大等级
	 * @param amountPerLvl	每等级最多获取的个数
	 * @param args			可能会用到的参数列表
	 * @return
	 */
	public ActiveUserMap load(int minLvl, int maxLvl, int amountPerLvl,
			Object... args);
}
