package com.m3958.firstgwt.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.model.Asset;

public interface AssetRepository {
	Asset save(String siteId,UploadFor ufor,String fname,String contentType,InputStream stream) throws FileNotFoundException, IOException;
	InputStream get(Asset asset);
}
