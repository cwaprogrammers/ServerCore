package com.cwa.httpserver;

public class TestGameJettyServer {

    public static void main(String[] args) throws Exception {
        GameServerConfig config = new GameServerConfig();
        config.setServerPort(8080);
        new GameJettyServer(config).start();
    }
    
}
