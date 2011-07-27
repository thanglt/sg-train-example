package com.m3958.firstgwt.utils;

import javax.servlet.http.Cookie;

public class CookieUtils {
	public static String getCookieValue(Cookie[] cookies,String cookieName){
		if(cookies == null)return null;
		for (int i = 0; i < cookies.length; i++) {
			if(cookieName.equals(cookies[i].getName())){
				return cookies[i].getValue();
			}
		}
		return null;
	}

}
