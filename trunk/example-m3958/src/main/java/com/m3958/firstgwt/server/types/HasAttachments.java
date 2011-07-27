package com.m3958.firstgwt.server.types;

import java.util.List;

import com.m3958.firstgwt.model.Asset;

public interface HasAttachments {
	
	boolean addAttachment(Asset at);
	
	boolean dropAttachment(Asset at);
	
	List<Asset> getAttachments();

}
