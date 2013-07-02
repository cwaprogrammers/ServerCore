/**
 * 
 */
package com.cwa.bio.core;

import com.cwa.gamecore.message.AbstractMessage;

/**
 * 阻塞式的SocketChannel。
 * @author zero
 */
public interface IBioChannel
{
	public void send(AbstractMessage msg);
	public AbstractMessage receive();
}
