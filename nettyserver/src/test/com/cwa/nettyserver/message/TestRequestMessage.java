/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package test.com.cwa.nettyserver.message;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.message.GameResponse;

/**
 * 测试消息
 * @author zero
 */
public class TestRequestMessage implements GameRequest, GameResponse {
	byte[] content;
	
	@Override
	public void decodeBody(GameInput arg0) {
		System.out.println("decode TestRequest");
		content = arg0.getBytes();
		System.out.println(content.length);
	}

	@Override
	public int getCommandId() {
		return 8888;
	}

	@Override
	public void encodeBody(GameOutput arg0) {
		arg0.putBytes(content);
	}

}
