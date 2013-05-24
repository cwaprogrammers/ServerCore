/**
 * 
 */
package com.cwa.nagrand.message.warrior;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.AbstractMessage;
import com.cwa.nagrand.message.Messages;

/**
 * 添加一个新的战士
 * @author zero
 */
public class AddWarriorMessage extends AbstractMessage
{
	private boolean success;
	
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

	public AddWarriorMessage()
	{
		super(Messages.ADD_WARRIOR);
	}

	@Override
	public void decodeBody(GameInput input)
	{
		arenaId = input.getString();
		warId = input.getString();
		initScore = input.getInt();
	}

	@Override
	public void encodeBody(GameOutput output)
	{
		output.putBoolean(success);
	}

	/**
	 * @return the warId
	 */
	public String getWarId()
	{
		return warId;
	}

	public String getArenaId()
	{
		return arenaId;
	}

	public int getInitScore()
	{
		return initScore;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}
}
