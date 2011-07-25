package com.skynet.common.datadictory;

import java.util.Locale;

public interface DataDictionaryManager {
	
	String[][] getDisplayDataDictoryByName(String ddName,Locale locale);
	
	DataDictEntity getDataDictoryByName(String ddName,String key,Locale locale);
	
	String[][] getDisplayDataDictoryByName(String ddName);
	
}
