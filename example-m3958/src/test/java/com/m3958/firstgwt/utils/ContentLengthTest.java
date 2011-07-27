package com.m3958.firstgwt.utils;

import org.junit.Test;

public class ContentLengthTest {
	
	@Test
	public void Tl(){
		String s="你好";
		int l = s.length();
		System.out.print(l);
		System.out.print(",");
		l = s.getBytes().length;
		System.out.print(l);
		System.out.print(",");
//		l = s.getBytes("GB2312").length;
	}
}
