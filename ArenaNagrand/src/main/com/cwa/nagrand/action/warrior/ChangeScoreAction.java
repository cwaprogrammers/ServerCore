/**
 * 
 */
package com.cwa.nagrand.action.warrior;

import com.cwa.nagrand.action.BasicAction;
import com.cwa.nagrand.memory.ArenaManager;
import com.cwa.nagrand.message.warrior.ChangeScoreMessage;

/**
 * 改变玩家竞技场积分的action。
 * @author zero
 */
public class ChangeScoreAction extends BasicAction<ChangeScoreMessage, ChangeScoreMessage> 
{
	@Override
	public ChangeScoreMessage execute(ChangeScoreMessage msg)
	{
		int[] rank = new int[msg.getUserIds().length];
		for (int i = 0; i < msg.getUserIds().length; ++i) {
			rank[i] = ArenaManager.INSTANCE.changeScore(msg.getArenaIds()[i], msg.getUserIds()[i], msg.getChangeValues()[i]);
		}
		msg.setNewRanks(rank);
		return msg;
	}
}
