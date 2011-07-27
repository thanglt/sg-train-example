package com.m3958.firstgwt.server.types;

import java.util.List;

import com.m3958.firstgwt.model.Asset;

public interface IhasContentImg {
	void addContentImg(Asset at);
	
	void dropContentImg(Asset at);
	
	List<Asset> getContentImgs();
}
