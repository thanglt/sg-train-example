package com.m3958.firstgwt.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.m3958.firstgwt.client.errorhandler.LgbRpcException;


public class LgbPortTest {
	
	@Test
	public void testMe() throws FileNotFoundException, UnsupportedEncodingException, LgbRpcException{
		File srcFile = new File("E:/gwt/firstgwt/war/staticdss/fh.txt");
		FileInputStream fis = new FileInputStream(srcFile);
		InputStreamReader isr = new InputStreamReader(fis,"utf-8");
		LgbPort lp = new LgbPort();
		lp.startPort(isr);
	}
}
