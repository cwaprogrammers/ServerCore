/**
 * 
 */
package com.cwa.bio.action;

import com.cwa.bio.msg.ArenaAddWarriorMessage;

/**
 * 竞技场添加新的战士
 * @author zero
 */
public class ArenaAddWarriorAction extends
		AbstractAction<ArenaAddWarriorMessage, ArenaAddWarriorMessage>
{
	@Override
	public ArenaAddWarriorMessage action(ArenaAddWarriorMessage input)
	{
		ArenaAddWarriorMessage output = (ArenaAddWarriorMessage) sendAndReceive(input);
		input.setSuccess(output.isSuccess());
		return input;
	}
}
