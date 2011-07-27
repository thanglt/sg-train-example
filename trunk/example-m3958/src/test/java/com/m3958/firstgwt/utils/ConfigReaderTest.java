package com.m3958.firstgwt.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfigReaderTest {

	@Test
	public void testMultiply() {
		ConfigReader cfg = new ConfigReader();
		assertEquals("Results",50,cfg.multiply(5, 10));
	}

}
