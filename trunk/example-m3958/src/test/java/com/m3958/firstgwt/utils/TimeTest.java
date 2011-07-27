package com.m3958.firstgwt.utils;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.junit.Test;



public class TimeTest {
	
	
	
	public String getFileNameAppend(String fname,String append){
		int p = fname.lastIndexOf('.'); 
		if(p != -1){
			return fname.substring(0,p) + append + fname.substring(p);
		}else{
			return fname + append;
		}
	}
	
	@Test
	public void Ft(){
		System.out.print(getFileNameAppend("abc.cc.gif", "48x48"));
	}

	
	@Test
	public void testMe() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(df.format(d));
		df.setTimeZone(TimeZone.getDefault());
		System.out.println(df.format(d));
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		System.out.println(df.format(d));
		System.out.println(d.getTime());
		//所以最好用GMT时间，这样服务器保存的就是GMT时间。
		System.out.println(d.toString());
		System.out.println(d.toGMTString());
		
		String[] tzids = TimeZone.getAvailableIDs();
		for (int i = 0; i < tzids.length; i++) {
			System.out.println(tzids[i] + "|");
		}
		
		Locale l = Locale.CHINA;
		BeanUtilsBean bub = new BeanUtilsBean();
		Map<String, Object> mp = bub.describe(l);
		System.out.println(mp);
		
		TimeZone tz = TimeZone.getDefault();
		System.out.println(bub.describe(tz));
		
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTimeZone(TimeZone.getTimeZone("GMT+8:55"));
		System.out.println(rightNow);
//		SimpleTimeZone tz1 = new SimpleTimeZone(rightNow.getTimeZone())
//		System.out.println(tz1.getID());
		System.out.println((rightNow.get(Calendar.ZONE_OFFSET) + rightNow.get(Calendar.DST_OFFSET))/(60*1000));
		System.out.println(getGMTStr((rightNow.get(Calendar.ZONE_OFFSET) + rightNow.get(Calendar.DST_OFFSET))));
	}
	
	private String getGMTStr(int offsetMillis){
		int hours = offsetMillis/1000/60/60;
		int minuts = (offsetMillis - hours*1000*60*60)/1000/60;
		String h = "" + hours;
		String m = "" + minuts;
		if(hours<10){
			h = "0" + h;
		}
		if(minuts<10){
			m = "0" + minuts;
		}
		return "GMT" + h + ":" + m;
	}
}
