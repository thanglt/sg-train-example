package com.m3958.firstgwt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public class SmartDateConverter implements Converter {
	
	private static final String  dateFormat = "yyyy-MM-dd";
	private static final String  dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	
	
	private Date defaultValue = null;
	private boolean useDefault = true;
	
	public SmartDateConverter(){
		this.defaultValue = new Date();
		this.useDefault = false;
	}
	
	public SmartDateConverter(Date defaultValue){
		this.defaultValue = defaultValue;
		this.useDefault = true;
	}
	
	

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
       if (value == null) {
            if (useDefault) {
                return (defaultValue);
            } else {
                throw new ConversionException("No value specified");
            }
        }
       if (value instanceof Date) {
           return (value);
       }
       Date d = null;
       if (value instanceof String) {
           try {
               d = parseDate((String)value);
           }
           catch (Exception e) {
               if (useDefault) {
                   return (defaultValue);
               } else {
                   throw new ConversionException(e);
               }
           }
       } else {
           throw new ConversionException("Input value not of correct type");
       }

       return d; 
	}

	private Date parseDate(String value) {
		SimpleDateFormat sdf;
		if(value.trim().length() < 10){
			return defaultValue;
		}
		if(value.trim().length() < 11){
			sdf = new SimpleDateFormat(dateFormat);
		}else{
			value = value.replace("T", " ");
			sdf = new SimpleDateFormat(dateTimeFormat);
		}
		
		try {
			return sdf.parse(value);
		} catch (ParseException e) {
			return defaultValue;
		}
	}
}
