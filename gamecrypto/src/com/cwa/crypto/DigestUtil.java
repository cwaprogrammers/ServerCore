package com.cwa.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要工具类。
 */
public class DigestUtil {
    
    /**
     * 计算SHA-256消息摘要。
     * @param data 进行消息摘要的数据
     * @return 
     */
    public static byte[] sha256(byte[] data) {
        return sha256(data, null);
    }
    
    /**
     * 计算SHA-256消息摘要。
     * @param data 进行消息摘要的数据
     * @param salt 干扰数据
     * @return 
     */
    public static byte[] sha256(byte[] data, byte[] salt) {
        return digest("SHA-256", data, salt);
    }
    
    /**
     * 计算MD5消息摘要。
     * @param data 进行消息摘要的数据
     * @return 
     */
    public static byte[] md5(byte[] data) {
        return md5(data, null);
    }
    
    /**
     * 计算MD5消息摘要。
     * @param data 进行消息摘要的数据
     * @param salt 干扰数据
     * @return 
     */
    public static byte[] md5(byte[] data, byte[] salt) {
        return digest("MD5", data, salt);
    }
    
    private static byte[] digest(String algorithm, byte[] data, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            
            md.update(data);
            if (salt != null) {
                md.update(salt);
            }
            
            byte[] mdBytes = md.digest();

            return mdBytes;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
}
