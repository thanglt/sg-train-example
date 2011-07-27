package com.m3958.firstgwt.session;

import java.util.HashMap;
import java.util.Map;


import com.google.inject.servlet.SessionScoped;
import com.m3958.firstgwt.service.UploadProgressListener;

@SessionScoped
public class UploadProgressSession {
	
	private Map<String, UploadProgressListener> listenerMap = new HashMap<String, UploadProgressListener>();
	
	
	public Map<String, UploadProgressListener> getListenerMap(){
		return listenerMap;
	}
	
	public void addListener(String key,UploadProgressListener listener){
		listenerMap.put(key, listener);
	}
	
	public UploadProgressListener getListener(String key){
		return listenerMap.get(key);
	}
	
	public void removeListener(String key){
		listenerMap.remove(key);
	}
	
}
