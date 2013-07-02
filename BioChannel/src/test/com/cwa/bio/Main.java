package com.cwa.bio;

import com.cwa.bio.action.ArenaAddWarriorAction;
import com.cwa.bio.core.BIOMessageFactory;
import com.cwa.bio.core.BioPool;
import com.cwa.bio.core.IBioChannel;
import com.cwa.bio.msg.ArenaAddWarriorMessage;
import com.cwa.bio.msg.ArenaChangeScoreMessage;
import com.cwa.bio.msg.ArenaGetWarriorMessage;
import com.cwa.bio.msg.ArenaMessageType;

public class Main
{
	public static void main(String args[])
	{
		BioPool.INSTANCE.init(10, "127.0.0.1", 11224);
		BIOMessageFactory.INSTANCE.register(ArenaMessageType.ADD_WARRIOR,
				ArenaAddWarriorMessage.class);
		BIOMessageFactory.INSTANCE.register(ArenaMessageType.CHANGE_SCORE,
				ArenaChangeScoreMessage.class);
		BIOMessageFactory.INSTANCE.register(ArenaMessageType.GET_WARRIOR,
				ArenaGetWarriorMessage.class);

		// for example
		ArenaAddWarriorMessage msg = new ArenaAddWarriorMessage();
		msg.setArenaId("arena1");
		msg.setWarId("user001");
		msg.setInitScore(0);
		ArenaAddWarriorMessage response = new ArenaAddWarriorAction()
				.action(new ArenaAddWarriorMessage());
		System.out.println(response.isSuccess());
	}
}
