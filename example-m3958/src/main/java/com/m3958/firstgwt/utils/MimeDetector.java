package com.m3958.firstgwt.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;


public class MimeDetector {
	
	
	
	@SuppressWarnings("unchecked")
	public String getMimeType(File file) throws UnsupportedEncodingException{
//		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
//		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.WindowsRegistryMimeDetector");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
//		List<String> es = new ArrayList<String>();
//		es.add("GB18030");
//		es.add("UTF-8");
//		EncodingGuesser.setSupportedEncodings(es);
//		Collection<Object> mm = EncodingGuesser.getPossibleEncodings("你好".getBytes("UTF-8"));
		Collection<MimeType> mimeTypes = MimeUtil.getMimeTypes(file);
		for (MimeType mt : mimeTypes) {
			System.out.print(mt);
		}
		return mimeTypes.toString();
	}
}
