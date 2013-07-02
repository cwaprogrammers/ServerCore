/**
 * 
 */
package com.cwa.bio.msg;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.AbstractMessage;

/**
 * 竞技场获取展示新型
 * @author zero
 */
public class ArenaGetWarriorMessage extends AbstractMessage
{
	private String arenaId;
	private String warId;
	
	private int score;
	private int rank;
	
	public ArenaGetWarriorMessage()
	{
		super(ArenaMessageType.GET_WARRIOR);
	}

	@Override
	public void decodeBody(GameInput arg0)
	{
		score = arg0.getInt();
		rank = arg0.getInt();
	}

	@Override
	public void encodeBody(GameOutput arg0)
	{
		arg0.putString(arenaId);
		arg0.putString(warId);
	}

	public int getScore()
	{
		return score;
	}

	public int getRank()
	{
		return rank;
	}

	public void setArenaId(String arenaId)
	{
		this.arenaId = arenaId;
	}

	public void setWarId(String warId)
	{
		this.warId = warId;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public void setRank(int rank)
	{
		this.rank = rank;
	}
	
}
