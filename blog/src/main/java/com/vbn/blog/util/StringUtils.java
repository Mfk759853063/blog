package com.vbn.blog.util;

import java.util.UUID;

public class StringUtils {

	/** 
	* 产生一个32位的GUID 
	* @return 
	*/ 
	public static String newGUID() { 
		UUID uuid = UUID.randomUUID(); 
		return uuid.toString(); 
	}
	
	public static Boolean isEmpty(String string) {
		if (string == null) {
			return true;
		}
		if (string.length() == 0) {
			return true;
		}
		return false;
	}
}
