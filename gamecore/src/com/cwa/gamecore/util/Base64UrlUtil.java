package com.cwa.gamecore.util;

import org.apache.commons.codec.binary.Base64;

/**
 * base64编码后在url中的应用处理类
 * 
 * @author landu
 * 
 */
public class Base64UrlUtil {

	/*
	 * 加密并屏蔽掉url中传递时的非法字符
	 */
	public static String encryptBASE64(String key) throws Exception {
		if (key == null || key.length() < 1) {
			return "";
		}
		return new String(Base64.encodeBase64URLSafe(key.getBytes()));
	}

	/*
	 * 解密
	 */
	public static String decryptBASE64(String key) throws Exception {
		if (key == null || key.length() < 1) {
			return "";
		}
		return new String(Base64.decodeBase64(key.getBytes()));

	}
}
