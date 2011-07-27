package com.m3958.firstgwt.client.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.http.client.URL;
import com.m3958.firstgwt.client.jso.AssetJso;

public class StringUtils {
	
	
//	public static String getImgUrlFromAssetJso(AssetJso aj,String size){
//		String html = "";
//		if("原图".equals(size)){
//			html = "/asset/" + aj.getId() + "?size=o"; 
//		}else if("800x600".equals(size)){
//			html = "/asset/" + aj.getId() + "?size=m";
//		}else if("128x128".equals(size)){
//			html = "/asset/" + aj.getId() + "?size=s";
//		}else if("48x48".equals(size)){
//			html = "/asset/" + aj.getId() + "?size=t";
//		}else{
//			html = "/asset/" + aj.getId();
//		}
//		return html;
//	}
	
	public static String addOrUpdateParemeter(String url,String key,String value){
		String[] ss = url.trim().split("\\?");
		String baseUrl = ss[0];
		String us = "";
		if(ss.length < 2){
			us =  key + "=" + value;
		}else{
			String[] pairs = ss[1].split("&");
			boolean find = false;
			for (int i = 0; i < pairs.length; i++) {
				if(pairs[i].startsWith(key + "=")){
					pairs[i] = key + "=" + value;
					find = true;
				}
				us += pairs[i] + "&";
			}
			if(!find){
				us += key + "=" + value;
			}
			
			if(us.endsWith("&")){
				us = us.substring(0, us.length() - 1);
			}
		}
		return baseUrl + "?" + us;
	}
	
	
	public static String removeParemeter(String url,String key){
		String[] ss = url.trim().split("\\?");
		if(ss.length < 2)return url;
		
		String baseUrl = ss[0];
		String us = "";
		
		String[] pairs = ss[1].split("&");
		for (int i = 0; i < pairs.length; i++) {
			if(pairs[i].startsWith(key + "=")){
				;
			}else{
				us += pairs[i] + "&";
			}
		}
		
		if(us.endsWith("&")){
			us = us.substring(0, us.length() - 1);
		}
		return baseUrl + "?" + us;
	}
	
	public final static String urlFromStringPairs(String... pairs ){
		if(pairs.length % 2 != 0){
			return "";
		}
		String url = "";
		for(int i =0;i<pairs.length;){
			url += "&" + pairs[i++] + "=" + pairs[i++];  
		}
		return url.substring(1);
	}
	
	public final static String getListUrlStr(String paramName, String... fields){
		String s = "";
		for (int i = 0; i < fields.length; i++) {
			s += paramName + "=" + fields[i] + "&"; 
		}
		if(s.endsWith("&"))s = s.substring(0, s.length()-1);
		return s;
	}

	
	public static String mapToJsonStr(Map<String, String> m){
		String s = "{";
		for (String key : m.keySet()) {
			s += "\"" + key + "\":" + "\"" + m.get(key) +  "\",";
		}
		
		if(s.endsWith(",")){
			s = s.substring(0,s.length() -1);
		}
		
		s += "}";
		return s;
	}
	
	public static String join(String[] sary,String sperator){
		String result = "";
		for(String s : sary){
			if(s != null && s.length() > 0)
				result += s + sperator; 
		}
		if(result.endsWith(sperator))result = result.substring(0, result.length()-1);
		return result;
	}
	
	public static String join(List<String> sary,String sperator){
		String result = "";
		for(String s : sary){
			if(s != null && s.length() > 0)
				result += s + sperator; 
		}
		if(result.endsWith(sperator))result = result.substring(0, result.length()-1);
		return result;
	}
	
	
	public static String addUrlParam(String url,String key, String value){
		url = url + "&" + key + "=" + URL.decode(value + "");
		return url;
	}
	
	public static String addUrlParams(String url,Map<String, String> params){
		url += "&";
		for(String key:params.keySet()){
			try {
				url += key + "=" + URL.encode(params.get(key)) + "&";
			} catch (Exception e) {
				url += key + "=" + URL.encode(String.valueOf(params.get(key))) + "&";
			}
		}
		if(url.endsWith("&"))url = url.substring(0, url.length() - 1);
		if(url.startsWith("&"))url = url.substring(1);
		return url;
	}
	
	public static String criteriaToJsonString(){
		
		return null;
	}
	
	
	@SuppressWarnings("deprecation")
	public static final String getDateAdd(int a){
		Date date = new Date();
		int y = date.getYear()+ 1900 - a + 1;
		int m = date.getMonth() + 1;
		int d = date.getDate();
		String s;
		if(m<10){
			if(d<10){
				s = y + "-0" + m + "-0" + d;
			}else{
				s = y + "-0" + m + "-" + d;
			}
		}else{
			if(d<10){
				s = y + "-" + m + "-0" + d;
			}else{
				s = y + "-" + m + "-" + d;
			}
		}
		return s;  
	}
	
	@SuppressWarnings("deprecation")
	public static final String getDateAdd(String b){
		if(b == null || b.length() == 0)return "";
		int a = Integer.parseInt(b);
		Date date = new Date();
		int y = date.getYear()+ 1900 - a + 1;
		int m = date.getMonth() + 1;
		int d = date.getDate();
		String s;
		if(m<10){
			if(d<10){
				s = y + "-0" + m + "-0" + d;
			}else{
				s = y + "-0" + m + "-" + d;
			}
		}else{
			if(d<10){
				s = y + "-" + m + "-0" + d;
			}else{
				s = y + "-" + m + "-" + d;
			}
		}
		return s;  
	}
	
	@SuppressWarnings("deprecation")
	public static final String getDateFromAge(int a){
		Date date = new Date();
		int y = date.getYear()+ 1900 - a + 1;
		String s = y + "-01-01";
		return s;
	}
	
	@SuppressWarnings("deprecation")
	public static final String getDateFromAge(String b){
		if(b == null || b.length() == 0)return "";
		int a = Integer.parseInt(b);
		Date date = new Date();
		int y = date.getYear()+ 1900 - a + 1;
		String s = y + "-01-01";
		return s;
	}
	public static String getFileExtensionWithDot(String fname){
		int p = fname.lastIndexOf('.'); 
		if(p != -1){
			return fname.substring(p);
		}else{
			return "";
		}
	}
	
	public static String basenameWithoutDot(String filename) {
		int i = filename.lastIndexOf('.');
		String bn = filename.substring(0, i);
		return bn;
	}
	
	
	public static String getFileNameAppend(String fname,String append){
		int p = fname.lastIndexOf('.'); 
		if(p != -1){
			return fname.substring(0,p) + "." +  append + fname.substring(p);
		}else{
			return fname + append;
		}
	}
	
	public static String[] IMG_EXTS = new String[]{".JPG",".PNG",".GIF",".JPEG"}; 
	
	public static boolean isImageExt(String fname){
		String ext = getFileExtensionWithDot(fname);
		if(ext.isEmpty())return false;
		for(String s:IMG_EXTS){
			if(s.equalsIgnoreCase(ext)){
				return true;
			}
		}
		return false;
	}
}
