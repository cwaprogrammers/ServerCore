/**
 * 
 */
package com.cwa.bio.msg;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.AbstractMessage;

/**
 * 竞技场改变积分的消息（可以一次性改变很多人的积分）
 * @author zero
 */
public class ArenaChangeScoreMessage extends AbstractMessage
{
	private String[] arenaIds;
	private String[] userIds;
	private int[] changeValues;
	
	private int[] newRanks;
	
	public ArenaChangeScoreMessage()
	{
		super(ArenaMessageType.CHANGE_SCORE);
	}

	@Override
	public void decodeBody(GameInput arg0)
	{
		int size = arg0.getInt();
		for (int i = 0; i < size; ++i) {
			newRanks[i] = arg0.getInt();
		}
	}

	@Override
	public void encodeBody(GameOutput arg0)
	{
		arg0.putInt(arenaIds.length);
		for (int i = 0; i < arenaIds.length; ++i) {
			arg0.putString(arenaIds[i]);
			arg0.putString(userIds[i]);
			arg0.putInt(changeValues[i]);
		}
	}

	public String[] getArenaIds()
	{
		return arenaIds;
	}

	public void setArenaIds(String[] arenaIds)
	{
		this.arenaIds = arenaIds;
	}

	public String[] getUserIds()
	{
		return userIds;
	}

	public void setUserIds(String[] userIds)
	{
		this.userIds = userIds;
	}

	public int[] getChangeValues()
	{
		return changeValues;
	}

	public void setChangeValues(int[] changeValues)
	{
		this.changeValues = changeValues;
	}

	public int[] getNewRanks()
	{
		return newRanks;
	}

	public void setNewRanks(int[] newRanks)
	{
		this.newRanks = newRanks;
	}
}
