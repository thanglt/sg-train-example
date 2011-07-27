package com.m3958.firstgwt.utils;

import org.junit.Assert;
import org.junit.Test;

import com.m3958.firstgwt.client.types.TextMatchStyle;

public class EnumTest {
	
	@Test
	public void testMe(){
		String s = TextMatchStyle.EXACT.getValue();
		Assert.assertEquals("exact", s);
	}
	
}
