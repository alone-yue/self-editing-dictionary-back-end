package com.idictionary.util;

import javax.servlet.http.HttpServletRequest;


/**
 * 	用户登录工具
 * 	定义了三种用户的登录状�?�：未登录，token错误，已登录
 * 
 * 	定义了校验用户登录状态的方法
 * 	定义了获取正在登录用户的id方法
 * 	定义了获取用户token的方�?
 * */
public class UserVerificationUtil {
	
	public static final String USER_NOT_LOGGED_IN = "USER NOT LOGGED IN";
	public static final String USER_LOGGED_IN = "USER LOGGED IN";
	public static final String WRONG_TOKEN = "WRONG TOKEN";
	public static final String LOGIN_STATE = "VERIFICATION RESULT";
	public static final String USER_ID = "USER ID FROM TOKEN";
	public static final String AUTHORIZATION = "Authorization";
	
	public static boolean isUserLoggedIn(HttpServletRequest request) {
		return USER_LOGGED_IN.equals(request.getAttribute(LOGIN_STATE));
	}
	
	public static String getUserId(HttpServletRequest request) {
		if(isUserLoggedIn(request)) {
			return (String) request.getAttribute(USER_ID);
		}
		return null;
	}
	
	public static String getUserToken(HttpServletRequest request) {
		return request.getHeader(AUTHORIZATION);
	}
	
	public static String getUserState(HttpServletRequest request) {
		return (String) request.getAttribute(LOGIN_STATE);
	}
}
