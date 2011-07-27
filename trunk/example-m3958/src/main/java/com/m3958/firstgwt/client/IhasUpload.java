package com.m3958.firstgwt.client;

import com.m3958.firstgwt.client.jso.AssetJso;
import com.m3958.firstgwt.client.jso.UploadResponseJso;
import com.m3958.firstgwt.client.types.UploadFor;

public interface IhasUpload {
	void uploadSuccess(UploadResponseJso urj,AssetJso asset);
	void uploadFailure(UploadResponseJso urj);
	UploadFor[] getUploadFors();
	String[] getAllowedExts(UploadFor uploadFor);
	String getUpRelationModelName();
	String getUpRelationModelId();
	String getNextIframeName();
}
