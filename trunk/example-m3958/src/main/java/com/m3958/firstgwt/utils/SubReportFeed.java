package com.m3958.firstgwt.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import com.m3958.firstgwt.server.types.ISubReportStreamFeed;

public class SubReportFeed implements ISubReportStreamFeed{
	
	public InputStream getStream(String nn){
		try {
			File f = new File(nn);
			InputStream i1 = null;
			i1 = new FileInputStream(f);
			int l = ((Long)f.length()).intValue();
			byte[] b = new byte[l];
				i1.read(b, 0, l);
			InputStream ii = new ByteArrayInputStream(b);
			return ii;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		SubReportFeed sf = new SubReportFeed();
		System.out.println(sf.getStream("sub1"));
	}
}
