/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package com.cwa.nettyserver;

import java.util.ArrayList;
import java.util.List;

import com.cwa.gamecore.action.ActionFactory;
import com.cwa.gamecore.message.MessageFactory;

/**
 * 测试Netty的启动类
 * @author zero
 */
public class TestStart {
	public static void main(String args[]) {
		List<String> packageList = new ArrayList<String>();
		packageList.add("test.com.cwa.nettyserver");
		ActionFactory.getInstance().init(packageList);
		MessageFactory.getInstance().init(packageList);

		NettyServer server = new NettyServer();
		server.init(21314);
		server.start();
	}
}
