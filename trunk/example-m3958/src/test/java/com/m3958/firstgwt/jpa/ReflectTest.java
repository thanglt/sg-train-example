package com.m3958.firstgwt.jpa;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.User;

public class ReflectTest {
	private BaseModel descObj;
	private BaseModel srcObj;
	
	
	@Before
	public void setup(){
		descObj = new User();
		srcObj = new Role();
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testReflect() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class[] cs = {Role.class};
		Method m = descObj.getClass().getMethod("addRole", cs);
		Assert.assertEquals("addRole", m.getName());
		m.invoke(descObj, srcObj);
		Assert.assertEquals(1, ((User) descObj).getRoles().size());
	}
	
	
	@After
	public void tearDown(){
		descObj = null;
		srcObj = null;
	}

}
