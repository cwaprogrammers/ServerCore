package com.cwa.gamecore.context;

import org.junit.Ignore;

@Ignore
public class AppContextTest {

    public static void main(String[] args) {
        System.out.println("GameCoreService starting...");
        AppContext.getInstance();
        System.out.println("GameCoreService started!");
    }
    
}
