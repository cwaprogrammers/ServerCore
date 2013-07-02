package com.cwa.minaserver;

import com.cwa.minaserver.config.GameServerConfig;
import org.junit.Ignore;

@Ignore
public class GameMsgServerTest {

    public static void main(String[] args) {
        GameServerConfig config = new GameServerConfig();
        config.setServerPort(11223);
        
        GameMsgServer server = new GameMsgServer(config);
        server.start();
    }
    
}
