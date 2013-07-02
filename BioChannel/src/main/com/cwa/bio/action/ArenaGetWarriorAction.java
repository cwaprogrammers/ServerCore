/**
 * 
 */
package com.cwa.bio.action;

import com.cwa.bio.msg.ArenaGetWarriorMessage;

/**
 * 竞技场获得指定战士信息
 * @author zero
 */
public class ArenaGetWarriorAction extends AbstractAction<ArenaGetWarriorMessage, ArenaGetWarriorMessage>
{
	@Override
	public ArenaGetWarriorMessage action(ArenaGetWarriorMessage input)
	{
		ArenaGetWarriorMessage output = (ArenaGetWarriorMessage) sendAndReceive(input);
		input.setScore(output.getScore());
		input.setRank(output.getRank());
		return input;
	}

}
