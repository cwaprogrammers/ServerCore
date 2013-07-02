package com.cwa.crypto;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RsaUtilTest {
    
    @BeforeClass
    public static void loadKeyAndCert() {
        RsaKeyHolder.getInstance().loadKeyAndCert();
    }
    
    @Test
    public void encryptDecrypt() {
        byte[] cipher = RsaUtil.encrypt("hello".getBytes(),
                RsaKeyHolder.getInstance().getCertificate());
        byte[] plain = RsaUtil.decrypt(cipher,
                RsaKeyHolder.getInstance().getPrivateKey());
        Assert.assertEquals("hello", new String(plain));
    }
    
    @Test
    public void signVerify() {
        byte[] sig = RsaUtil.sign("hello".getBytes(), 
                RsaKeyHolder.getInstance().getPrivateKey());
        Assert.assertTrue(RsaUtil.verify("hello".getBytes(), sig,
                RsaKeyHolder.getInstance().getCertificate()));
    }
    
}
