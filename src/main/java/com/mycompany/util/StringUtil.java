package com.mycompany.util;

public class StringUtil {
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().equals(""))
			return true;
		else
			return false;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (str != null && !str.trim().equals(""))
			return true;
		else
			return false;
	}
}
