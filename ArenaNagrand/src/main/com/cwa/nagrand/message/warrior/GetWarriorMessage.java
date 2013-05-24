/**
 * 
 */
package com.cwa.nagrand.message.warrior;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.AbstractMessage;
import com.cwa.nagrand.message.Messages;

/**
 * @author zero
 *
 */
public class GetWarriorMessage extends AbstractMessage
{
	private String arenaId;
	private String warId;
	
	private int rank;
	private int score;
	
	public GetWarriorMessage()
	{
		super(Messages.GET_RANK);
	}

	@Override
	public void decodeBody(GameInput input)
	{
		arenaId = input.getString();
		warId = input.getString();
	}

	@Override
	public void encodeBody(GameOutput output)
	{
		output.putInt(score);
		output.putInt(rank);
	}

	public String getArenaId()
	{
		return arenaId;
	}

	public String getWarId()
	{
		return warId;
	}

	public void setRank(int rank)
	{
		this.rank = rank;
	}

	public void setScore(int score)
	{
		this.score = score;
	}
}
