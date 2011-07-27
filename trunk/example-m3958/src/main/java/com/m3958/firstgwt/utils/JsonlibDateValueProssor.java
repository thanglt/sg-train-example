package com.m3958.firstgwt.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.m3958.firstgwt.client.types.CommonField;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonlibDateValueProssor implements JsonValueProcessor {

	
	@Inject
	private Injector injector;
	
	
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value,jsonConfig);
	}



	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return process(value,jsonConfig);
	}
	
	private Object process(Object value, JsonConfig jsonConfig){
		if(value == null){
			return null;
		}
		HttpServletRequest req = injector.getInstance(HttpServletRequest.class);
		Locale l = req.getLocale();
		String tz = req.getParameter(CommonField.TIMEZONE.getEname());
		TimeZone timezone;
		if(tz == null){
			timezone = TimeZone.getDefault();
		}else{
			timezone = TimeZone.getTimeZone(tz);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",l);
		sdf.setTimeZone(timezone);
		String s = sdf.format((Date)value);
		s = s.replace(" ", "T");
		return s;
	}

}
