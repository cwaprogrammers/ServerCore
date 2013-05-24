/**
 * 
 */
package com.cwa.nagrand.action;

import com.cwa.gamecore.action.IAction;
import com.cwa.gamecore.message.AbstractMessage;
import com.cwa.gamecore.session.GameSession;

/**
 * @author zero
 *
 */
public abstract class BasicAction <T extends AbstractMessage, R extends AbstractMessage> implements IAction<T>
{

	@Override
	public void execute(GameSession session, T request)
	{
		try {
			AbstractMessage response = execute(request);
			if (null != response)
				session.write(response);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public abstract R execute(T arg1);
}
