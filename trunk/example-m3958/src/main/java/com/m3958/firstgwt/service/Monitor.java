package com.m3958.firstgwt.service;

import java.util.Date;

import com.google.inject.Singleton;

@Singleton
public class Monitor {
	
	private Date startDate;
	
	private long requestCounter;
	
	public Monitor(){
		startDate = new Date();
		requestCounter = 0L;
	}
	
	public void increaseRequestCounter(){
		requestCounter ++;
	}
	
	public long getRequestCounter() {
		return requestCounter;
	}

	public Date getStartDate() {
		return startDate;
	}
}
