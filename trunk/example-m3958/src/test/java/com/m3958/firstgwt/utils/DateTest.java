package com.m3958.firstgwt.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

public class DateTest {
	
	@Test
	public void testMe(){
		String s = null;
		s.isEmpty();
		Date d = new Date();
		long t = d.getTime();
		long myt = 100L*365*24*60*60*1000L;
		
		GregorianCalendar firstFlight = new GregorianCalendar(1903, Calendar.DECEMBER, 17);
		Date d1 = firstFlight.getTime();
		long gl = firstFlight.getTime().getTime();
		Assert.assertTrue(t < myt);
//		Date d = new Date(new Date().getTime() - 100*365*24*60*60*1000);
//		calen
//		Assert.assertEquals("1910", actual)
	}
}
