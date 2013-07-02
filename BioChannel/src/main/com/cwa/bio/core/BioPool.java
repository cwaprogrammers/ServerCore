/**
 * 
 */
package com.cwa.bio.core;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zero
 */
public enum BioPool
{
	INSTANCE;
	
	BioPool()
	{
		pool = new LinkedBlockingQueue<IBioChannel>();
	}
	
	public void init(int size, String host, int port)
	{
		if (!pool.isEmpty()) {
			throw new IllegalStateException("BioPool has already inited.");
		}
		this.size = size;
		for (int i = 0; i < size; ++i) {
			pool.add(new SocketBioChannel(host, port));
		}
	}
	
	private int size;
	private LinkedBlockingQueue<IBioChannel> pool;
	
	public IBioChannel popChannel()
	{
		return pool.poll();
	}
	
	public void free(IBioChannel channel)
	{
		try {
			pool.put(channel);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
