package com.m3958.firstgwt.utils;

import java.io.InputStream;

import com.m3958.firstgwt.client.types.FileSaveTo;
import com.m3958.firstgwt.model.Asset;

public class ReportImgFeed{
	
	public InputStream getStream(Asset asset){
		return asset.getStream("e:\\abc");
	}
	
	public static void main(String[] args) {
		Asset a = new Asset();
		a.setSaveTo(FileSaveTo.FILE_SYSTEM);
		a.setFilePath("2010/10/30/f7540f6b-8c6f-4f52-bfd2-2cc356071ad4.jpg");
		InputStream is =  a.getStream("e:\\abc");
		System.out.print(is);
	}
	
	

}
