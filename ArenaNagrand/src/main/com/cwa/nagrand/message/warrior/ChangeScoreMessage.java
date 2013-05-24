/**
 * 
 */
package com.cwa.nagrand.message.warrior;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.AbstractMessage;
import com.cwa.nagrand.message.Messages;

/**
 * 玩家改变积分的消息，可以发送多个玩家
 * @author zero
 */
public class ChangeScoreMessage extends AbstractMessage
{
	private String[] arenaIds;
	private String[] userIds;
	private int[] changeValues;
	
	private int[] newRanks;
	
	public ChangeScoreMessage()
	{
		super(Messages.CHANGE_SCORE);
	}

	@Override
	public void decodeBody(GameInput input)
	{
		int size = input.getInt();
		arenaIds = new String[size];
		userIds = new String[size];
		changeValues = new int[size];
		for (int i = 0; i < size; ++i) {
			arenaIds[i] = input.getString();
			userIds[i] = input.getString();
			changeValues[i] = input.getInt();
		}
	}

	@Override
	public void encodeBody(GameOutput output)
	{
		output.putInt(newRanks.length);
		for (int i = 0; i < newRanks.length; ++i) {
			output.putInt(newRanks[i]);
		}
	}

	public String[] getUserIds()
	{
		return userIds;
	}

	public int[] getChangeValues()
	{
		return changeValues;
	}

	public void setNewRanks(int[] newRanks)
	{
		this.newRanks = newRanks;
	}

	public String[] getArenaIds()
	{
		return arenaIds;
	}

}
