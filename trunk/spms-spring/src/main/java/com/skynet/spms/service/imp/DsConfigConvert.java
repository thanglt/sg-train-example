package com.skynet.spms.service.imp;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skynet.common.datadictory.DataDictionaryManager;
import com.skynet.common.gwt.GwtActionHelper;
import com.skynet.common.prop.PropEnum;
import com.skynet.common.prop.PropManager;
import com.skynet.common.prop.PropertyEntity;
import com.skynet.spms.datasource.BeanPropUtil;

@Component
public class DsConfigConvert {
	
	@Autowired
	private DataDictionaryManager dataDictMang;
	
	private String start="<isomorphicXML xmlns:xsi=\"nativeType\">";
	private String end="</isomorphicXML>";
		
	public String convertToDsXml(String dsTmpXml) throws DocumentException, IOException{
		
		Document doc=DocumentHelper.parseText(start+dsTmpXml+end);		
		Element dsElem=doc.getRootElement().element("DataSource");
		
		Document newDoc=DocumentHelper.createDocument();
		QName qname=new QName("xmlns:xsi");
		Element newElem=newDoc.addElement("isomorphicXML").addAttribute(qname,"nativeType");
		
		newElem.add(dsElem.attribute("id"));
		newElem.addAttribute("serverType", "generic");
		Element fieldsElem=newElem.addElement("fields");
			
		for(Object val:dsElem.element("fields").elements("field")){
			Element fieldElem=(Element)val;
			String type=fieldElem.attributeValue("type");
			
			Element newFieldElem=null;
			if("enumClass".equals(type)){
				newFieldElem=operEnumMap(fieldElem);
			}else if("dataDict".equals(type)){
				newFieldElem=operDataDict(fieldElem);
			}else if("enum".equals(type)){
				newFieldElem=operValueMap(fieldElem);
			}else{
				newFieldElem=operTopic(fieldElem);
			}
			newFieldElem.add(fieldElem.element("validators"));
			fieldsElem.add(newFieldElem);
		}	
		//<serverObject lookupStyle="spring" bean="simpleDataSourceService"/>
		newElem.addElement("serverObject")
		.addAttribute("lookupStyle", "spring")
		.addAttribute("bean","simpleDataSourceService");
				
		newElem.add(dsElem.element("operationBindings"));
		
		StringWriter strWriter=new StringWriter();
		XMLWriter writer=new XMLWriter(strWriter);
		writer.write(newDoc);		
		return strWriter.toString();
	}
	
	private Element operValidate(Element validatesElem){
		
		List<Element> list=validatesElem.elements("validator");
		for(Element valiElem:list){
			String topic=valiElem.attributeValue("topic");			
			valiElem.addAttribute("topic",combinTopic(validatesElem,"valiate",topic));
		}
		return validatesElem;
	}
	
	private Element operDataDict(Element fieldElem){
		Element newElem=DocumentHelper.createElement("field");
		newElem.setAttributes(fieldElem.attributes());
		newElem.addAttribute("type", "enum");
		
		String dictName=fieldElem.getText();
		
		String[][] value=dataDictMang.getDisplayDataDictoryByName(dictName);
		Element valMapElem=newElem.addElement("valueMap");		
		for(String[] valArray:value){
			String name=valArray[0];
			String val=valArray[1];
			
			Element valElement=DocumentHelper.createElement("value");
			
			valElement.addAttribute("ID", name);
			valElement.setText(combinTopic(fieldElem,name));
			
			valMapElem.add(valElement);
		}
		newElem.add(valMapElem);
		
		return newElem;
	}
	
	private Element operEnumMap(Element fieldElem) {
		String clsName=fieldElem.attributeValue("class");
		Class<Enum> cls=null;
		try{
			cls=(Class<Enum>) Class.forName(clsName).asSubclass(Enum.class);
		}catch(Exception e){
			throw new IllegalArgumentException(e);
		}
		Enum[] enumArray=cls.getEnumConstants();
		
		Element newElem=DocumentHelper.createElement("field");
		newElem.setAttributes(fieldElem.attributes());
		newElem.addAttribute("type", "enum");
		
		Element valMapElem=newElem.addElement("valueMap");		
		for(Enum enumIns:enumArray){
			String name=enumIns.name();
			Element valElement=DocumentHelper.createElement("value");
			
			valElement.addAttribute("ID", name);
			valElement.setText(combinTopic(fieldElem,cls.getSimpleName(),name));
			valMapElem.add(valElement);
		}
		
		return newElem;
	}
	
	
	
	private  Element operValueMap(Element fieldElem){
		Element valMapElem=fieldElem.element("valueMap");

		String valMapName=fieldElem.attributeValue("name");
		
		for(Object val:valMapElem.elements("value")){
			Element valElem=(Element)val;
			String id=valElem.attributeValue("ID");
			
			String topic=valElem.getText();
			if(StringUtils.isBlank(topic)){
				topic=combinTopic(fieldElem,valMapName,id);
				valElem.addText(topic);
			}			
		}
		return fieldElem;
	}


	private Element operTopic(Element fieldElem) {
		
		String topic=fieldElem.attributeValue("topic");
		String name=fieldElem.attributeValue("name");
		
		if(StringUtils.isBlank(topic)){
			topic=combinTopic(fieldElem,name);
			fieldElem.addAttribute("topic", topic);			
		}	
		return fieldElem;
	}
	

	
	private String combinTopic(Element elem,String... params){
		String dsName = getDsName(elem);
		StringBuilder build=new StringBuilder();
		build.append("${").append(dsName);
		for(String str:params){
			build.append(".").append(str);
		}
		build.append("}");
		return build.toString();
	}

	private String getDsName(Element fieldElem) {
		String dsName=fieldElem.getDocument().getRootElement().attributeValue("ID");
		return dsName;
	}
}
