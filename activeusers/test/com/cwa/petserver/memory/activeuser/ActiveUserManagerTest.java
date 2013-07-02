/**
 * Author: zero
 * Created: 2012-8-22
 */
package com.cwa.petserver.memory.activeuser;

import java.util.ArrayList;
import java.util.List;
import org.junit.Ignore;


/**
 * 测试类
 * @author zero
 */
@Ignore
public class ActiveUserManagerTest
{
	public static void main(String args[])
	{
		ActiveUserManager.INSTANCE.pushUser(5, genUsers(5, 1000));
		List<Integer> ids = ActiveUserManager.INSTANCE.randomUsers(10, 5);
		printList(ids);
	}

	private static void printList(List<?> ids)
	{
		for (Object id : ids) {
			System.out.println(id);
		}
	}

	private static List<ActiveUser> genUsers(int level, int amount)
	{
		List<ActiveUser> r = new ArrayList<ActiveUser>();
		for (int i = 0; i < amount; ++i)
			r.add(new ActiveUser(i, level));
		return r;
	}
}
