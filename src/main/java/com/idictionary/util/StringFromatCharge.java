package com.idictionary.util;

public class StringFromatCharge {

	/**
	 * 判断字符串是否为何法电话号码
	 * */
	public static boolean isLegalPhoneNumber(String s) {
		if(s.length() != 11) {
			return false;
		}
		
		for(int i = 0;i < s.length();i++) {
			char c = s.charAt(i);
			if(c < '0' || c > '9') {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 判断若字段中是否存在空字段
	 * */
	public static boolean hasNullField(String ... fields) {
		
		for(String string : fields) {
			if(string == null || string.length() == 0) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 *	将null替换为空字符串
	 * */
	public static void nullToBlank(String ...strings) {
		for(String string : strings) {
			if(string == null) {
				string = "";
			}
		}
	}
}
