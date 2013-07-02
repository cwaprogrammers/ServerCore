/**
 * Author: zero
 * Created: 2012-8-17
 */
package com.cwa.petserver.loader;

import java.util.List;

import com.cwa.petserver.memory.activeuser.ActiveUser;

/**
 * 用来和每一个项目的DAO兼容的装饰性接口。实际使用的时候用自己的DAO实现他。
 * @author zero
 */
public interface IAccountDAO
{
	/**
	 * 根据等级和数量，从DB中抓取指定数量的玩家
	 * @param lvl		抓取什么等级的玩家
	 * @param amount	抓取多少玩家
	 * @return
	 */
	public List<ActiveUser> load(int lvl, int amount);
}
