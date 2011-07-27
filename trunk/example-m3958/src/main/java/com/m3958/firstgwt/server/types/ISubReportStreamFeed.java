package com.m3958.firstgwt.server.types;

import java.io.InputStream;

public interface ISubReportStreamFeed {
	public InputStream getStream(String subReportName);
}
