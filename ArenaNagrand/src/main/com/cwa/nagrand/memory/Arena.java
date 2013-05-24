/**
 * 
 */
package com.cwa.nagrand.memory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * @author zero
 *
 */
public class Arena
{
	private String arenaId;
	private WarriorPool warriorPool;
	
	//private byte[] content;
	
	public Arena()
	{
		
	}
	
	public Arena(String arenaId)
	{
		this.arenaId = arenaId;
		warriorPool = new WarriorPool();
	}
	
	public Warrior getWarrior(String warId)
	{
		return warriorPool.get(warId);
	}
	
	public int getRank(String warId)
	{
		return warriorPool.getRank(warId);
	}
	
	public boolean addWarrior(String warId, int initScore)
	{
		Warrior warrior = new Warrior();
		warrior.arenaId = arenaId;
		warrior.warId = warId;
		warrior.score = initScore;
		return warriorPool.add(warrior);
	}
	
	public int changeScore(String warId, int changeValue)
	{
		Warrior warrior = warriorPool.get(warId);
		warrior.score += changeValue;
		warrior.score = (warrior.score > 0) ? warrior.score : 0;
		
		warriorPool.update(warrior);
		return warriorPool.getRank(warrior);
	}
	
	public String getArenaId()
	{
		return arenaId;
	}
	
	public byte[] getContent()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(warriorPool);
			baos.flush();
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("ERR: read arena error.");
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setContent(byte[] content)
	{
		InputStream input = new ByteArrayInputStream(content);
		
		try {
			ObjectInputStream ois = new ObjectInputStream(input);
			this.warriorPool = (WarriorPool) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void setArenaId(String arenaId)
	{
		this.arenaId = arenaId;
	}
}
