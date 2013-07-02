/**
 * 
 */
package com.cwa.bio.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.cwa.gamecore.io.ByteArrayGameInput;
import com.cwa.gamecore.io.ByteArrayGameOutput;
import com.cwa.gamecore.message.AbstractMessage;

/**
 * @author zero
 */
public class SocketBioChannel implements IBioChannel
{
	Socket socket;
	DataOutputStream dos;
	DataInputStream dis;
	
	public SocketBioChannel(String host, int port)
	{
		try {
			socket = new Socket();
			socket.setTcpNoDelay(true);
			socket.connect(new InetSocketAddress(host, port));
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("ERR: Connnect to a unavailable arena server.");
		}
	}
	
	@Override
	public void send(AbstractMessage msg)
	{
		ByteArrayGameOutput output = new ByteArrayGameOutput();
		msg.encodeBody(output);
		try {
			dos.write(AbstractMessage.MAGIC_NUMBERS);
			byte[] bytes = output.toByteArray();
			dos.writeShort((short) (2 + bytes.length));
			dos.writeShort((short) msg.getCommandId());
			dos.write(bytes);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public AbstractMessage receive()
	{
		byte[] content = new byte[1400];
		try {
			int len = socket.getInputStream().read(content);
			ByteBuffer bb = ByteBuffer.wrap(content, 0, len);
			// head
			bb.getShort();
			short msgLen = bb.getShort();
			int commandId = bb.getShort();
			byte[] msgContent = new byte[msgLen - 2];
			bb.get(msgContent);
			AbstractMessage msg = BIOMessageFactory.INSTANCE.get(commandId);
			ByteArrayGameInput input = new ByteArrayGameInput(msgContent);
			msg.decodeBody(input);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
