package com.m3958.firstgwt.utils;

import java.util.Date;

import junit.framework.TestCase;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.junit.Test;

import com.m3958.firstgwt.model.User;

public class JsonLibTest extends TestCase {
	
	@Test
	public void testMe(){
//		JsonConfig jc = new JsonConfig();
//		jc.registerJsonValueProcessor(Date.class, new JsonlibDateValueProssor());
////		JsonValueProcessor jvp = jc.findJsonValueProcessor(Date.class);
////		assertNotNull(jvp);
//		User u = new User();
//		u.setCreatedAt(new Date());
//		u.setLoginName("abc");
//		JSONObject s = JSONObject.fromObject(u,jc);
//		System.out.println(s);
	}
	
	@Test
	public void testMe1(){
		JSONObject res = new JSONObject().element("response", new JSONObject());
		res.getJSONObject("response").element("a", "b");
		System.out.println(res);
	}
}
