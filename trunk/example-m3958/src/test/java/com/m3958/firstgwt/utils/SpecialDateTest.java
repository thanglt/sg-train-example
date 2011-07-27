package com.m3958.firstgwt.utils;

import org.junit.Test;

public class SpecialDateTest {

	@Test
	public void t(){
		SpecialDate sd = new SpecialDate();
		System.out.print(sd.startOfDay());
		System.out.print(sd.startOfWeek());
		System.out.print(sd.startOfMonth());
		System.out.print(sd.startOfYear());
	}
}
