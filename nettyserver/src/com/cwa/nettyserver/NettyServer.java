/**
 * Author: zero
 * Created: 2012-7-25
 * Copyright: CWA HummingBird 2012
 */
package com.cwa.nettyserver;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.cwa.nettyserver.codec.GameDecoder;
import com.cwa.nettyserver.codec.GameEncoder;
import com.cwa.nettyserver.framework.AbstractNettyDecoder;
import com.cwa.nettyserver.framework.AbstractNettyEncoder;
import com.cwa.nettyserver.framework.NettyLogicHandler;

/**
 * Netty服务器
 * @author zero
 */
public class NettyServer {
	private AbstractNettyDecoder decoder;
	private AbstractNettyEncoder encoder;
	private NettyLogicHandler logic;
	private int port;
	
	public void init(int port, 
			AbstractNettyDecoder decoder, AbstractNettyEncoder encoder, 
			NettyLogicHandler logic) {
		this.decoder = decoder;
		this.encoder = encoder;
		this.logic = logic;
		this.port = port;
	}
	
	public void init(int port) {
		init(port, new GameDecoder(), new GameEncoder(), new NettyLogicHandler());
	}
	
	public void start() {
		ChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", decoder);
				pipeline.addLast("encoder", encoder);
				pipeline.addLast("logic", logic);
				return pipeline;
			}
		});
		
		bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        bootstrap.bind(new InetSocketAddress(port));
	}
}
