/**
 * 
 */
package com.cwa.bio.action;

import com.cwa.bio.core.BioPool;
import com.cwa.bio.core.IBioChannel;
import com.cwa.gamecore.message.AbstractMessage;

/**
 * 基础action，负责收发消息
 * @author zero
 */
public abstract class AbstractAction<Req extends AbstractMessage, Res extends AbstractMessage> 
{
	public abstract Res action(Req input);
	
	protected AbstractMessage sendAndReceive(AbstractMessage input)
	{
		IBioChannel channel = BioPool.INSTANCE.popChannel();
		AbstractMessage result = null;
		try {
			channel.send(input);
			result = channel.receive();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BioPool.INSTANCE.free(channel);
		}
		throw new IllegalStateException(String.format("message [%s] i/o error", input.getCommandId()));
	}
	
	protected void send(AbstractMessage input)
	{
		IBioChannel channel = BioPool.INSTANCE.popChannel();
		try {
			channel.send(input);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BioPool.INSTANCE.free(channel);
		}
	}
}
