//package com.spms.test.common;
//
//import static org.junit.Assert.*;
//
//import java.util.Locale;
//import java.util.Map;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.skynet.common.datadictory.DataDictEntity;
//import com.skynet.common.datadictory.DataDictEnum;
//import com.skynet.common.datadictory.DataDictionaryManager;
//import com.skynet.common.datadictory.entity.CurrencyCode;
//import com.skynet.common.datadictory.entity.TraceDataIndicator;
//import com.skynet.common.prop.PropEnum;
//import com.skynet.common.prop.PropManager;
//import com.skynet.common.prop.PropertyEntity;
//import com.skynet.spms.persistence.type.EnterpriseCategory;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({ "classpath:util_Context.xml" })
//public class TestPropCache {
//	
//	@Autowired
//	private DataDictionaryManager ddMang;
//	
//	
//	@Autowired
//	private PropManager propTool;
//	
//	@Test
//	public void checkDict2(){
//		Map<String, DataDictEntity> dispMap=ddMang.getDataDictoryByName(DataDictEnum.CurrencyCode, Locale.SIMPLIFIED_CHINESE);
//		
//		assertEquals(((CurrencyCode)dispMap.get("GRD")).getName(),"希腊德拉克马");
//		assertEquals(((CurrencyCode)dispMap.get("GRD")).getUnit(),100);
//				
//		dispMap=ddMang.getDataDictoryByName(DataDictEnum.CurrencyCode, Locale.ENGLISH);
//		
//		assertEquals(((CurrencyCode)dispMap.get("GRD")).getName(),"Greek Drachma");
//		
//		dispMap=ddMang.getDataDictoryByName(DataDictEnum.TraceAblityDataIndicator, Locale.SIMPLIFIED_CHINESE);
//		assertEquals(((TraceDataIndicator)dispMap.get("NAP")).getDesc(),"追溯不适用");
//
//		dispMap=ddMang.getDataDictoryByName(DataDictEnum.TraceAblityDataIndicator, Locale.ENGLISH);
//		assertEquals(((TraceDataIndicator)dispMap.get("NAP")).getDesc(),"NAP");
//	}
//	
//	@Test
//	public void checkDict1(){
//		
//		Map<String,String> dispMap=ddMang.getDisplayDataDictoryByName(DataDictEnum.CurrencyCode, Locale.SIMPLIFIED_CHINESE);
//		
//		assertEquals(dispMap.get("GRD"),"希腊德拉克马");
//		
//		dispMap=ddMang.getDisplayDataDictoryByName(DataDictEnum.CurrencyCode, Locale.ENGLISH);
//		
//		assertEquals(dispMap.get("GRD"),"Greek Drachma");
//		
//		dispMap=ddMang.getDisplayDataDictoryByName(DataDictEnum.TraceAblityDataIndicator, Locale.SIMPLIFIED_CHINESE);
//		assertEquals(dispMap.get("NAP"),"追溯不适用");
//
//		dispMap=ddMang.getDisplayDataDictoryByName(DataDictEnum.TraceAblityDataIndicator, Locale.ENGLISH);
//		assertEquals(dispMap.get("NAP"),"NAP");
//	}
//	
//	@Test
//	public void doEnum(){
//		
//		Map<String,String> map=propTool.getDisplayMapByEnum(EnterpriseCategory.class, Locale.SIMPLIFIED_CHINESE);
//		
//		assertEquals(map.get(EnterpriseCategory.carrier.name()),"运代商");
//		
//	}
//	
//	@Test
//	public void doRead(){
//		
//		PropertyEntity prop=propTool.getPropEntity(Locale.SIMPLIFIED_CHINESE,PropEnum.Feature);
//		
//		assertEquals(prop.getProperty("mesys.user.fp"),"用户函数");
//				
//		for(int i=0;i<10;i++){
//			propTool.getPropEntity(Locale.SIMPLIFIED_CHINESE,PropEnum.Feature);
//		}
//		
//		prop=propTool.getPropEntity(Locale.ENGLISH,PropEnum.Feature);
//		assertEquals(prop.getProperty("mesys.role"),"Role");
//	}
//
//}
