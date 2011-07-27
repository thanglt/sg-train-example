package com.m3958.firstgwt.utils;

import java.io.File;

import org.junit.Test;

public class ResourceTest {

	@Test
	public void Tm(){
		File f = new File(this.getClass().getResource("/").getFile());
		File warf = f.getParentFile().getParentFile();
		f = new File(warf,"resize.pl");
		System.out.print(f.getAbsolutePath());
		
	}
}
