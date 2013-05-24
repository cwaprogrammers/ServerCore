/**
 * 
 */
package com.cwa.nagrand.action.warrior;

import com.cwa.nagrand.action.BasicAction;
import com.cwa.nagrand.memory.ArenaManager;
import com.cwa.nagrand.message.warrior.AddWarriorMessage;

/**
 * @author zero
 */
public class AddWarriorAction extends BasicAction<AddWarriorMessage, AddWarriorMessage>
{
	@Override
	public AddWarriorMessage execute(AddWarriorMessage msg)
	{
		msg.setSuccess(ArenaManager.INSTANCE.addWarrior(msg.getArenaId(),
				msg.getWarId(), msg.getInitScore()));
		return msg;
	}
}
