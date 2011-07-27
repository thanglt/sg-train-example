package com.m3958.firstgwt.utils;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.m3958.firstgwt.service.FilePathService;

public class ZipTest {
	
	@Test
	public void tt() throws IOException{
		FilePathService fps = new FilePathService();
		File f = new File("e:/conf");
		File f1 = new File("e:/abc/abc.zip");
		fps.zip(f, f1);
	}
}
