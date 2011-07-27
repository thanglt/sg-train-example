package com.m3958.firstgwt.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.junit.Test;

import com.m3958.firstgwt.model.User;

public class ConverterTest extends TestCase {
	
	@Test
	public void testMe(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("loginName", "loginName");
		params.put("createdAt", "2010-05-06T05:06:07");
		SmartDateConverter dc = new SmartDateConverter();
		ConvertUtils.register(dc, Date.class);
		try {
			User u = new User();
			BeanUtils.populate(u, params);
			assertEquals("loginName", u.getLoginName());
			assertNotNull(u.getCreatedAt());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
