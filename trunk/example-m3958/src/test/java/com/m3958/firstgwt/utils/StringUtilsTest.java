package com.m3958.firstgwt.utils;



import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {
	
	public boolean isDateString(String ds){
		return ds.matches("^[0-9]{4}-[01]?[0-9]{1}-[0-3]?[0-9]{1}$");
	}
	
	public boolean isTimeStampString(String ts){
		return ts.matches("^[0-9]{4}-[01]?[0-9]{1}-[0-3]?[0-9]{1}T[0-2]?[0-9]{1}:[0-6]?[0-9]{1}:[0-6]?[0-9]{1}$");
	}
	
	@Test
	public void isDateStringTest(){
		Assert.assertTrue(isDateString("2010-5-6"));
		Assert.assertTrue(isDateString("2010-05-06"));
		Assert.assertTrue(!isDateString("20120-5-6"));
		Assert.assertTrue(isDateString("2010-15-6"));
		Assert.assertTrue(isTimeStampString("2010-15-6T15:13:18"));
	}
	
	@Test
	public void splitTest(){
		String s = "/";
		String[] ss = s.split("/");
		Assert.assertEquals(0, ss.length);
		
		s = "/a";
		ss = s.split("/");
		Assert.assertEquals(2, s.split("/").length);
		Assert.assertEquals("",ss[0]);
		Assert.assertEquals("a",ss[1]);
		
		
		s = "a";
		ss = s.split("/");
		Assert.assertEquals(1, s.split("/").length);
		Assert.assertEquals("a",ss[0]);
		
	}
}
