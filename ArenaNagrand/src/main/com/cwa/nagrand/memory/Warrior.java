/**
 * 
 */
package com.cwa.nagrand.memory;

import java.io.Serializable;

/**
 * 竞技场战士的内存数据
 * @author zero
 */
public class Warrior implements Comparable<Warrior>, Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String warId;
	public String arenaId;
	public int score;
	
	@Override
	public int compareTo(Warrior o)
	{
		if (score > o.score)
			return 1;
		if (score < o.score)
			return -1;
		return 0;
	}
}
