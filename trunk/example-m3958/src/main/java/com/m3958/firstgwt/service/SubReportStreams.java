package com.m3958.firstgwt.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.m3958.firstgwt.model.JrxmlFile;
import com.m3958.firstgwt.server.types.ISubReportStreamFeed;

public class SubReportStreams implements ISubReportStreamFeed{
	
	private JrxmlFile master;
	
	public SubReportStreams(JrxmlFile master){
		this.master = master;
	}
	
	
	public InputStream getStream(String subReportName){
		for(JrxmlFile sub:master.getChildren()){
			if(sub.getName().equals(subReportName)){
				return new ByteArrayInputStream(sub.getReport());
			}
		}
		return null;
	}
}
