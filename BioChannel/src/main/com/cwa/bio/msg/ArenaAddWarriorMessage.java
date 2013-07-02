/**
 * 
 */
package com.cwa.bio.msg;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.AbstractMessage;

/**
 * @author zero
 *
 */
public class ArenaAddWarriorMessage extends AbstractMessage
{
	/**
	 * 加入的竞技场的Id 
	 */
	private String arenaId;
	
	/**
	 * 战士的Id
	 */
	private String warId;
	
	/**
	 * 初始的点数
	 */
	private int initScore;

	/**
	 * 是否成功
	 */
	private boolean success;
	
	public ArenaAddWarriorMessage()
	{
		super(ArenaMessageType.ADD_WARRIOR);
	}

	@Override
	public void decodeBody(GameInput arg0)
	{
		success = arg0.getBoolean();
	}

	@Override
	public void encodeBody(GameOutput arg0)
	{
		arg0.putString(arenaId);
		arg0.putString(warId);
		arg0.putInt(initScore);
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setArenaId(String arenaId)
	{
		this.arenaId = arenaId;
	}

	public void setWarId(String warId)
	{
		this.warId = warId;
	}

	public void setInitScore(int initScore)
	{
		this.initScore = initScore;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}
}
