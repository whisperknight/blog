package com.mycompany.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;

public class CryptographyUtil {
	/**
	 * Base64加密
	 * @param str
	 * @return
	 */
	public static String enBase64(String str) {
		return Base64.encodeToString(str.getBytes());
	}

	/**
	 * Base64解密
	 * @param str
	 * @return
	 */
	public static String deBase64(String str) {
		return Base64.decodeToString(str);
	}
	
	/**
	 * MD5加密（无法破解）
	 * @param str 需要加密的字符串
	 * @param salt 盐在用的时候要放在配置文件里面然后读取
	 * @return
	 */
	public static String md5(String str, String salt) {
		return new Md5Hash(str, salt).toString();
	}
	
	public static void main(String[] args) {
		String password = "123456";
		
		System.out.println(CryptographyUtil.enBase64(password));
		System.out.println(CryptographyUtil.deBase64(CryptographyUtil.enBase64(password)));
		
		System.out.println(CryptographyUtil.md5(password, "mycompany"));
	}
}
