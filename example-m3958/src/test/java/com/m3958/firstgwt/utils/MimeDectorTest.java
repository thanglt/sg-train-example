package com.m3958.firstgwt.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class MimeDectorTest {
	
	@Test
	public void testMe() throws UnsupportedEncodingException{
		MimeDetector md = new MimeDetector();
		String s = md.getMimeType(new File("d:/a3640bfa-92e2-483f-8f0b-4a22fd51445a.flv"));
	}

}
