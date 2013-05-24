/**
 * 
 */
package com.cwa.nagrand.action.warrior;

import com.cwa.nagrand.action.BasicAction;
import com.cwa.nagrand.memory.ArenaManager;
import com.cwa.nagrand.memory.Warrior;
import com.cwa.nagrand.message.warrior.GetWarriorMessage;

/**
 * @author zero
 */
public class GetWarriorAction extends BasicAction<GetWarriorMessage, GetWarriorMessage>
{
	@Override
	public GetWarriorMessage execute(GetWarriorMessage msg)
	{
		Warrior warrior = ArenaManager.INSTANCE.getWarrior(msg.getArenaId(), msg.getWarId());
		msg.setRank(ArenaManager.INSTANCE.getRank(msg.getArenaId(), msg.getWarId()));
		msg.setScore(warrior.score);
		return msg;
	}
}
