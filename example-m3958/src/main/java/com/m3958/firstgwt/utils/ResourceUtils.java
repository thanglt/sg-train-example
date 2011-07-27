package com.m3958.firstgwt.utils;

import java.util.ResourceBundle;

/**
 * 
 * 
 * @author
 * 
 */
public class ResourceUtils {
	final static ResourceBundle Bundle;
	static {
		/**
		 * 
		 */
		Bundle = ResourceBundle.getBundle("com.m3958.firstgwt.config.p");
	}

	public final static String getParameter(String name) {
		String v = null;
		try {
			v = Bundle.getString(name);
		} catch (Exception e) {
		}
		return v;
	}
}