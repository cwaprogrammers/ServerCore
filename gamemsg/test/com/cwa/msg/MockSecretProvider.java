package com.cwa.msg;

import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.session.GameSession;

public class MockSecretProvider implements SecretProvider {

    @Override
    public byte[] getSecret(GameSession session, GameRequest req) {
        return new byte[0];
    }
    
}
