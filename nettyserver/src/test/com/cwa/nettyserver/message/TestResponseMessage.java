/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package test.com.cwa.nettyserver.message;

import com.cwa.gamecore.io.GameOutput;
import com.cwa.gamecore.message.GameResponse;

/**
 * @author zero
 */
public class TestResponseMessage implements GameResponse {
	byte[] content;
	
	@Override
	public void encodeBody(GameOutput arg0) {
		content = new byte[1000];
		arg0.putBytes(content);
	}

	@Override
	public int getCommandId() {
		return 8889;
	}

}
