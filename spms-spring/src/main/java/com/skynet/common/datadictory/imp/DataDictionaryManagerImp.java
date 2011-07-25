package com.skynet.common.datadictory.imp;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skynet.common.aop.cache.CacheSource;
import com.skynet.common.aop.cache.GroupBind;
import com.skynet.common.datadictory.DataDictEntity;
import com.skynet.common.datadictory.DataDictionaryManager;
import com.skynet.common.strtemplate.SimpleTmpTool;


@Component
public class DataDictionaryManagerImp implements DataDictionaryManager {

	private Logger log=LoggerFactory.getLogger(DataDictionaryManagerImp.class);
	
	@Autowired
	private DataDictionaryMapLoader ddMapLoader;
	
	private final static String XPATH_DISPFIELD="//vals/val[@name=\"${0}\"][@type=\"${1}-str\"]";
	
	@CacheSource("dd.dispMap")
	@Override
	public String[][] getDisplayDataDictoryByName(@GroupBind  String ddName,Locale locale){
		
		
		Element elem=ddMapLoader.getSourceByName(ddName);
		
		String type=elem.attributeValue("type");		
		
		
		if("flat".equals(type)){
			
			return generDispMapForFlat(ddName, locale, elem);			
			
		}else if("line".equals(type)){
			
			return generDispMapForLine(locale, elem);
			
		}else if("simple".equals(type)){
			return generDispMapForSimple(elem);
		}else{
			throw new IllegalArgumentException("unknow type:"+type);
		}
	}


	private String[][] generDispMapForLine(Locale locale, Element elem) {
		String displayName=elem.attributeValue("displayName");

		String keyList=elem.elementText("key");
		String[] keyArray=StringUtils.split(keyList, ',');
		
		
		String pathExp=SimpleTmpTool.generByTmp(XPATH_DISPFIELD,displayName,locale.toString().toLowerCase());
		List<?> fieldList=elem.selectNodes(pathExp);
		if(fieldList.size()!=1){
			throw new IllegalArgumentException(" mui or zero display field :"+displayName);
		}
		Element valElem=(Element)fieldList.get(0);
			
		String valStr=valElem.getText();
		String[] valArray=StringUtils.split(valStr,',');
		
		String[][] resultArray=new String[2][keyArray.length];
		for(int i=0;i<valArray.length;i++){
			resultArray[0][i]=StringUtils.trim(keyArray[i]);
			resultArray[1][i]=StringUtils.trim(valArray[i]);
		}
		return resultArray;
	}


	private String[][] generDispMapForFlat(String ddName, Locale locale,
			Element elem) {
		String displayName=elem.attributeValue("displayName");

		int idx=-1;
		List<?> elemList=elem.element("fields").elements("field");
		for(int i=0;i<elemList.size();i++){
						
			Element fieldElem=(Element)elemList.get(i);
			String locString=fieldElem.attributeValue("type");
			String loc=StringUtils.substringBefore(locString, "-");
			
			if(displayName.equals(fieldElem.attributeValue("name"))
					&&locale.toString().toLowerCase().equals(loc)){
				idx=i;
				break;
			}							
		}	
		if(idx==-1){
			throw new IllegalArgumentException("not found display name config:"+ddName);
		}
		List<Element> list =elem.element("entrys").elements("entry");
		
		String[][] resultArray=new String[2][list.size()];
		
		for(int i=0;i<list.size();i++){
			Element entryElem=list.get(i);
			String key=entryElem.attributeValue("key");
			String text=entryElem.getText();
			String[] valArray=StringUtils.split(text, ',');
			String val=valArray[idx];
			resultArray[0][i]=StringUtils.trim(key);
			resultArray[1][i]=StringUtils.trim(val);
		}
		return resultArray;
	}
	
	public String[][] generDispMapForSimple(Element elem) {
				
		String codeVal=elem.elementText("code");
		String[] codeArray=StringUtils.split(codeVal, ',');
		String[][] resultArray=new String[2][codeArray.length];
		
		for(int i=0;i<codeArray.length;i++){
			String clearStr=StringUtils.trim(codeArray[i]);
			resultArray[0][i]=clearStr;
			resultArray[1][i]=clearStr;
		}
		return resultArray;
	}


	@Override
	public DataDictEntity getDataDictoryByName(String ddName, String key,
			Locale locale) {
		return ddMapLoader.getDataDictoryByName(ddName, locale).get(key);
	}


	@Override
	public String[][] getDisplayDataDictoryByName(String ddName) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
