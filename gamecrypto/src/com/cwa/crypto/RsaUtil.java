package com.cwa.crypto;

import java.security.*;
import java.security.cert.Certificate;
import javax.crypto.Cipher;

/**
 * RSA非对称加密算法工具类。
 */
public class RsaUtil {
    
    /** 
     * 用公钥加密。
     */
    public static byte[] encrypt(byte[] data, Certificate cert) {  
        try {
            Cipher cipher = Cipher.getInstance("RSA");  
            cipher.init(Cipher.ENCRYPT_MODE, cert);  

            return cipher.doFinal(data);  
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt data.", e);
        }
    }  
    
    /** 
     * 用私钥解密。
     */  
    public static byte[] decrypt(byte[] data, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt data.", e);
        }
    }
    
    /**
     * 用私钥对数据签名（SHA1withRSA）。
     */
    public static byte[] sign(byte[] data, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privateKey, new SecureRandom());
            signature.update(data);
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate signature.", e);
        }
    }
    
    /**
     * 用公钥验证签名。
     */
    public static boolean verify(byte[] data, byte[] sigBytes, Certificate cert) {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(cert);
            signature.update(data);
            return signature.verify(sigBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify signature.", e);
        }
    }

}
