package com.cwa.crypto;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import org.apache.log4j.Logger;

/**
 * 
 * 用于从Java Keystore文件里加载RSA私钥和X.509证书。
 * 
 * 用keystore产生KeyPair的例子：
 * keytool -genkey -keyalg "RSA" -keysize 1024 \
 *         -dname "CN=zxh, OU=qiji, O=cwa, L=bj, S=bj, C=CN" \
 *         -keystore cwa.keystore -alias cwa -validity 3650 \
 *         -storepass cwa100 -keypass cwa100
 * 
 * 导出证书：
 * keytool -export -keystore cwa.keystore -alias cwa -file cwa.cer
 * 
 */
public class RsaKeyHolder {
    
    private static final Logger logger = Logger.getLogger(RsaKeyHolder.class);
    
    // 单例
    private static final RsaKeyHolder INSTANCE = new RsaKeyHolder();
    public static RsaKeyHolder getInstance() {
        return INSTANCE;
    }
    
    private PrivateKey privateKey;
    private Certificate certificate;
    
    // 使用默认值加载私钥和证书：
    // keystore file: ./cwa.keystore
    // alias: cwa
    // password: cwa100
    public void loadKeyAndCert() {
        InputStream keystore = getClass().getClassLoader().getResourceAsStream("./cwa.keystore");
        loadKeyAndCert(keystore, "cwa", "cwa100");
    }
    
    // 加载私钥和证书
    public synchronized void loadKeyAndCert(InputStream is, String alias, String password) {
        logger.debug("load key and cert from:" + is);
        try {
            KeyStore ks = KeyStore.getInstance("jks");
            ks.load(is, password.toCharArray());
            privateKey = (PrivateKey) ks.getKey(alias, password.toCharArray());
            certificate = ks.getCertificate(alias);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load private key and certificate.", e);
        }
    }

    
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public Certificate getCertificate() {
        return certificate;
    }
    
    
    // 用公钥加密。
    public byte[] encrypt(byte[] data) {
        return RsaUtil.encrypt(data, certificate);
    }
    
    // 用私钥解密。
    public byte[] decrypt(byte[] data) {
        return RsaUtil.decrypt(data, privateKey);
    }
    
    // 用私钥对数据签名（SHA1withRSA）。
    public byte[] sign(byte[] data) {
        return RsaUtil.sign(data, privateKey);
    }
    
    // 用公钥验证签名。
    public boolean verify(byte[] data, byte[] sigBytes) {
        return RsaUtil.verify(data, sigBytes, certificate);
    }
    
}
