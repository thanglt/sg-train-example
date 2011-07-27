package com.m3958.firstgwt.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 
 * 
 */
public class ClassUtils {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	@SuppressWarnings("unchecked")
	public final static void displayAttribute(Object obj) {
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			try {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				System.out.println(field.getName() + "=" + field.get(obj));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void setAttribute(Object obj, Field field,
			java.lang.String value) {
		if (StringUtils.isNull(value))
			return;
		try {
			Object tvalue = null;
			Class type = field.getType();
			if (type == Integer.class || type == int.class) {
				tvalue = Integer.parseInt(value);
			} else if (type == String.class) {
				tvalue = String.valueOf(value);
			} else if (type == Float.class || type == float.class) {
				tvalue = Float.parseFloat(value);
			} else if (type == Long.class || type == long.class) {
				tvalue = Long.parseLong(value);
			} else if (type == java.util.Date.class) {
				tvalue = dateFormat.parse(value);
			} else if (type == Double.class || type == double.class) {
				tvalue = Double.parseDouble(value);
			} else {
				tvalue = String.valueOf(value);
			}
			if (!field.isAccessible())
				field.setAccessible(true);
			field.set(obj, tvalue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 实例化
	 * 
	 * @param clazz
	 *            类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object newInstance(Class clazz) {
		if (clazz != null) {
			try {
				return clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 *实例化一个对象
	 * 
	 * @param classPath
	 *            类路径
	 * @return
	 */
	public static Object newInstance(String classPath) {
		try {
			return newInstance(Class.forName(classPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取得一个对象的所有属性
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getAttribute(Object obj) {
		Map<String, Object> atts = new HashMap<String, Object>();
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			try {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				atts.put(field.getName(), field.get(obj));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return atts;
	}
}