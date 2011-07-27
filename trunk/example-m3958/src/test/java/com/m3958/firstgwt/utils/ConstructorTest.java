package com.m3958.firstgwt.utils;

import junit.framework.TestCase;

import org.junit.Test;

public class ConstructorTest extends TestCase{
	
	@Test
	public void testCC(){
		B b = new B(2);
		assertEquals(1, b.i);
	}
}
