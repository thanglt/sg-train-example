package com.m3958.firstgwt.utils;

import org.junit.Assert;
import org.junit.Test;

public class AddTwoArray {
	
	@Test
	public void testMe(){
		String[] s1 = {"1","2","3"};
		String[] s2 = {"4","5","6"};
		String[] s = StringUtils.addTwoArray(s1, s2);
		Assert.assertArrayEquals(new String[]{"1","2","3","4","5","6"}, s);
	}
	
	@Test
	public void testMe1(){
		String[] s1 = {};
		String[] s2 = {"4","5","6"};
		String[] s = StringUtils.addTwoArray(s1, s2);
		Assert.assertArrayEquals(new String[]{"4","5","6"}, s);
	}

	@Test
	public void testMe2(){
		String[] s1 = {};
		String[] s2 = {};
		String[] s = StringUtils.addTwoArray(s1, s2);
		Assert.assertArrayEquals(new String[]{}, s);
	}

}
