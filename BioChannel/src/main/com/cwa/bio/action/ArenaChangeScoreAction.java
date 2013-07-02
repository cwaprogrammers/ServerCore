/**
 * 
 */
package com.cwa.bio.action;

import com.cwa.bio.msg.ArenaChangeScoreMessage;

/**
 * 竞技场改变积分
 * @author zero
 */
public class ArenaChangeScoreAction extends AbstractAction<ArenaChangeScoreMessage, ArenaChangeScoreMessage>
{
	@Override
	public ArenaChangeScoreMessage action(ArenaChangeScoreMessage input)
	{
		ArenaChangeScoreMessage output = (ArenaChangeScoreMessage) sendAndReceive(input);
		input.setNewRanks(output.getNewRanks());
		return input;
	}
}
