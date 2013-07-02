package com.cwa.minaserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class TCPServer {
    /** 最大线程数 **/
    private static final int THREAD_NUM = 120;
    protected boolean binded = false;

    protected int serverPort;
    protected InetSocketAddress serverAddress;
    protected NioSocketAcceptor acceptor;
    protected IoHandler sessionHandler;
    private ProtocolCodecFactory protocolCodeFactory;

    private int readBufferSize = GameCoreConstants.READ_BUFFER_SIZE;
    private int receiveBufferSize = GameCoreConstants.RECEIVE_BUFFER_SIZE;

    public TCPServer() {

    }

    public boolean PortBinded() {
            return binded;
    }

    public final void setServerPort(int serverPort) {
            this.serverPort = serverPort;
    }

    public final int getServerPort() {
            return serverPort;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }

    public void setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
    }

    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public void setReceiveBufferSize(int receiveBufferSize) {
        this.receiveBufferSize = receiveBufferSize;
    }

    public final void start() throws IOException {
        serverAddress = new InetSocketAddress(serverPort);
        acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(protocolCodeFactory));
        acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(new OrderedThreadPoolExecutor(10, THREAD_NUM)));
        acceptor.getSessionConfig().setReadBufferSize(readBufferSize);
        acceptor.getSessionConfig().setReceiveBufferSize(receiveBufferSize);// 设置输入缓冲区的大小

        // 设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
        acceptor.getSessionConfig().setTcpNoDelay(true);
        acceptor.setHandler(sessionHandler);
        acceptor.bind(serverAddress);
        binded = true;

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                acceptor.unbind(serverAddress);
            }
        });
    }

    public final void stop() {
        acceptor.unbind(serverAddress);

        binded = false;

        acceptor.dispose();
    }

    public IoHandler getSessionHandler() {
        return sessionHandler;
    }

    public void setSessionHandler(IoHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    public ProtocolCodecFactory getProtocolCodeFactory() {
        return protocolCodeFactory;
    }

    public void setProtocolCodeFactory(ProtocolCodecFactory protocolCodeFactory) {
        this.protocolCodeFactory = protocolCodeFactory;
    }
}// Class TCPServer