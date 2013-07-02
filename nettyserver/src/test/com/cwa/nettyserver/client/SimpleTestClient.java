/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package test.com.cwa.nettyserver.client;

import com.cwa.gamecore.message.GameMessage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * 简单的netty流程测试类，发送8888消息，并接受结果，输出Response包的长度
 * @author zero
 */
public class SimpleTestClient {
	public static void main(String args[]) {
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(21314));
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.write(GameMessage.MAGIC_NUMBERS);
			dos.writeInt(1004);
			dos.writeInt(8888);
			dos.write(new byte[1000]);
			dos.flush();
			
			byte[] content = new byte[10000];
			int len;
			do {
				len = socket.getInputStream().read(content);
			} while (len <= 0);
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(content));
			dis.read(new byte[2]);
			len = dis.readInt();
			System.out.println("message len:" + len);
			System.out.println("message commandId:" + dis.readInt());
			dis.read(new byte[len]);
			System.out.println("finish.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
