package com.m3958.firstgwt.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

import net.sf.json.JSONObject;

public class SimpleUploader extends FileUploader{
	
	@Inject
	public SimpleUploader(HttpServletRequest req, HttpServletResponse res) {
		super(req, res);
	}

	@Override
	protected void afterUpload() {
	}


	@Override
	protected JSONObject getOthersJsonObject() {
		return null;
	}

	@Override
	protected String getHtmlResponse() {
		return null;
	}

	@Override
	protected boolean isExceedMaxSize() {
		return false;
	}

}
