package com.m3958.firstgwt.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.inject.Singleton;

import sun.misc.BASE64Encoder;

@Singleton
public class MySHAService {
	
	  public String encrypt(String plaintext){
		  
	    MessageDigest md = null;
	    try
	    {
	      md = MessageDigest.getInstance("SHA"); //step 2
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	      e.printStackTrace();
	    }
	    try
	    {
	      md.update(plaintext.getBytes("UTF-8")); //step 3
	    }
	    catch(UnsupportedEncodingException e)
	    {
	      e.printStackTrace();
	    }
	    byte raw[] = md.digest(); //step 4
	    String hash = (new BASE64Encoder()).encode(raw); //step 5
	    return hash; //step 6
	  }
	  
	  public boolean isEqual(String sha,String plain){
		  if(plain == null || plain.length() < 1)return false;
		  if(encrypt(plain).equals(sha)){
			  return true;
		  }
		  return false;
	  }
}
