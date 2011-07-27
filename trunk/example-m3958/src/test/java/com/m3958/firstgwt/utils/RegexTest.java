package com.m3958.firstgwt.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.junit.Assert;
import org.junit.Test;

public class RegexTest {
	
	@Test
	public void dateTimePairTest(){
		String content = "sss<img src=\"/asset/18244?size=\" title=\"宁波人大代表视察奉化市sss<img src=\"/asset/1826664?size=\"法律服务中心3.jpg\"";
		Pattern p = Pattern.compile("\"/asset/(\\d+)\\?.*?\"", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(content);
		String s = m.replaceAll("\"/asset/$1\"");
		System.out.print(s);
	}
	
	
	@Test
	public void AssetUrlTest(){
		Pattern p = Pattern.compile("^/assetFeed/(\\d+)\\.(\\d+x\\d+)?\\.?.*");
		String s = "/assetFeed/1.48x48.gif";
		Matcher m = p.matcher(s);
		boolean b = m.matches();
		Assert.assertTrue(b);
		Assert.assertEquals("1", m.group(1));
		Assert.assertEquals("48x48", m.group(2));
		
		s = "/assetFeed/1.gif";
		m = p.matcher(s);
		b = m.matches();
		Assert.assertTrue(b);
		Assert.assertEquals("1", m.group(1));
		Assert.assertEquals(null, m.group(2));
	}
	
	
//	They do not consume characters in the string, but only assert whether a match is possible or not. 
	@Test
	public void aheadTest(){
		Pattern p = Pattern.compile("^/(?!pagehit|smartcfud|simpleImg|initApp|uploadprogress|firstgwt)[^.]*");
		String s = "/pagehit?lsl=uu";
		Matcher m = p.matcher(s);
		Assert.assertFalse(m.matches());
		
		s = "/sbcuu.";
		m = p.matcher(s);
		Assert.assertTrue(m.matches());
		
		p = Pattern.compile("^/[^.]*");//look behind
		
		s = "/agc.js";
		m = p.matcher(s);
		Assert.assertFalse(m.matches());
//		m = p1.matcher(s);
//		Assert.assertTrue(m.matches());

	}
	
	
}
