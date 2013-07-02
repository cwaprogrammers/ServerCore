package com.cwa.crypto;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RsaKeyHolderTest {
    
    @BeforeClass
    public static void loadKeyAndCert() {
        RsaKeyHolder.getInstance().loadKeyAndCert();
    }
    
    @Test
    public void encryptDecrypt() {
        byte[] cipher = RsaKeyHolder.getInstance().encrypt("hello".getBytes());
        byte[] plain = RsaKeyHolder.getInstance().decrypt(cipher);
        Assert.assertEquals("hello", new String(plain));
    }
    
    @Test
    public void signVerify() {
        byte[] sig = RsaKeyHolder.getInstance().sign("hello".getBytes());
        Assert.assertTrue(RsaKeyHolder.getInstance().verify("hello".getBytes(), sig));
    }
    
}
