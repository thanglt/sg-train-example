package com.m3958.firstgwt.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;



public class UrlTest {
	
	@Test
	public void tt(){
		String s = "http://sfj.m3958.com/search";
		Pattern p = Pattern.compile("(p=\\d+)");
		Matcher m = p.matcher(s);
		if(m.find()){
			s = s.replace(m.group(1), "p=55");
			System.out.println(m.group(1));
		}else{
			if(s.indexOf('?') != -1){
				s += "&p=55";
			}else{
				s += "?p=55";
			}
		}
		s="abcd";
		System.out.println(StringUtils.removeEnd(s, "?"));
		
		s = "hostname=sfj.m3958.com&remoteip=2.101.188.234&searchfor=article&q=&sw=title";
		s = s.replaceAll("hostname=[^&]+", "");
		s = s.replaceAll("remoteip=[^&]+", "");
		s = s.replaceAll("&&", "&");
		s = StringUtils.removeStart(s, "&");
		System.out.println(s);
	}
	
}
