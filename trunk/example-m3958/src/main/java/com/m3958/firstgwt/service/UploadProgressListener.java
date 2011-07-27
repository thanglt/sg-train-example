package com.m3958.firstgwt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.ProgressListener;

import com.m3958.firstgwt.client.types.UploadProgressFields;

public class UploadProgressListener implements ProgressListener{

	private long pBytesRead;
	private long pContentLength;
	private int pItems;
	
	private List<String> fnames = new ArrayList<String>();
	
	private Date createdAt = new Date();
	
//	pBytesRead - The total number of bytes, which have been read so far.
//	pContentLength - The total number of bytes, which are being read. May be -1, if this number is unknown.
//	pItems - The number of the field, which is currently being read. (0 = no item so far, 1 = first item is being read, ...)
	
	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		this.pBytesRead = pBytesRead;
		this.pContentLength = pContentLength;
		this.pItems = pItems;
	}
	
	public String toJsonString(){
		JSONObject jo = new JSONObject();
		jo.element(UploadProgressFields.BYTES_READ, pBytesRead);
		jo.element(UploadProgressFields.TOTAL_BYTES, pContentLength);
		jo.element(UploadProgressFields.ITEM_NUM, pItems);
		jo.element(UploadProgressFields.FILE_NAMES, JSONArray.fromObject(fnames));
		return jo.toString();
	}

	public List<String> getFnames() {
		return fnames;
	}

	public void addFnames(String fname) {
		this.fnames.add(fname);
	}
	

}
