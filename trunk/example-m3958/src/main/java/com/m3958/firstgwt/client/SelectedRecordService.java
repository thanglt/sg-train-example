package com.m3958.firstgwt.client;

import java.util.HashMap;

import com.google.inject.Singleton;
import com.smartgwt.client.data.Record;

@Singleton
public class SelectedRecordService {
	
	private HashMap<String, Record> selectedRecords = new HashMap<String, Record>();
	
	public Record getRecord(String id){
		return selectedRecords.get(id);
	}
	
	public void putRecord(String id,Record r){
		selectedRecords.put(id, r);
	}
	
	public void removeRecord(String id){
		selectedRecords.remove(id);
	}
}
