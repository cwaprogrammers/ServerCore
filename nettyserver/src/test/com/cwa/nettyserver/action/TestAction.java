/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package test.com.cwa.nettyserver.action;

import test.com.cwa.nettyserver.message.TestRequestMessage;
import test.com.cwa.nettyserver.message.TestResponseMessage;

import com.cwa.gamecore.action.IAction;
import com.cwa.gamecore.session.GameSession;

/**
 * 测试Action
 * @author zero
 */
public class TestAction implements IAction<TestRequestMessage>{
	@Override
	public void execute(GameSession arg0, TestRequestMessage arg1) {
		arg0.write(new TestResponseMessage());
	}
}
