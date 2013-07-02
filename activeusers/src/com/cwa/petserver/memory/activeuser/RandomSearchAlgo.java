/**
 * Author: zero
 * Created: 2012-8-22
 */
package com.cwa.petserver.memory.activeuser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机查找算法，最基本的实现，通过给定一个等级，随机找出该等级的指定数目的玩家。
 * @author zero
 */
public class RandomSearchAlgo implements ISearchAlgo
{
	Random random;
	
	RandomSearchAlgo()
	{
		random = new Random();
	}
	
	@Override
	public List<Integer> getRandomUser(ActiveUserPool pool, int lvl, int amount)
	{
		List<Integer> users = pool.getUserListClone(lvl);
		// 如果备选玩家数目都不够，直接返回所有的
		if (amount >= users.size()) {
			return users;
		}
		
		List<Integer> result = new ArrayList<Integer>();
		
		while (result.size() < amount && users.size() > 0) {
			moveList(result, users, random.nextInt(users.size()));
		}
		
		return result;
	}
	
	private void moveList(List<Integer> output, List<Integer> input, int i)
	{
		output.add(input.remove(i));
	}
}
