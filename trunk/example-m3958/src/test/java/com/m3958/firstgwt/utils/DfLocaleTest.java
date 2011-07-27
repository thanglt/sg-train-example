package com.m3958.firstgwt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class DfLocaleTest {
	
	@Test
	public void dd() throws ParseException{
		String ds = "2011-05-05 15:30";
		Date d = new Date();
		String fs ="yyyy-MM-dd HH:mm";
		Locale l = Locale.US;
		SimpleDateFormat sdf = new SimpleDateFormat(fs, l);
		System.out.println(sdf.format(d));
		
		l = Locale.CHINA;
		sdf = new SimpleDateFormat(fs, l);
		System.out.println(sdf.format(d));
		
		l= Locale.US;
		sdf = new SimpleDateFormat(fs, l);
		System.out.println(sdf.parse(ds));
		
		l = Locale.CHINA;
		sdf = new SimpleDateFormat(fs, l);
		System.out.print(sdf.parse(ds));

	}
	
}
