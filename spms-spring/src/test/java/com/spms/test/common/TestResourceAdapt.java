//package com.spms.test.common;
//
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.skynet.common.prop.PropertyEntity;
//import com.skynet.spms.aop.i18nconvert.I18nAdapter;
//import com.skynet.spms.datasource.EntityInfoManager;
//import com.skynet.spms.datasource.entity.EntityMetaInfo;
//import com.skynet.spms.gwt.homepage.client.entity.DataInfo;
//import com.skynet.spms.gwt.homepage.client.entity.FeatureInfo;
//import com.skynet.spms.gwt.homepage.client.entity.ModuleDetail;
//import com.skynet.spms.gwt.homepage.client.entity.ModuleItem;
//import com.skynet.spms.gwt.homepage.client.entity.ViewModuleTree;
//import com.skynet.spms.gwt.homepage.client.util.Needi18n;
//import com.skynet.spms.modules.RuleManager;
//import com.skynet.spms.modules.common.DataElement;
//import com.skynet.spms.modules.common.ModuleTree;
//import com.skynet.spms.modules.common.RootModuleInfo;
//import com.skynet.spms.modules.entity.ModuleList;
//import com.skynet.spms.modules.entity.ModulesElement;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({
//		"classpath:dataSource_Context.xml",
//		"classpath:service_Context.xml",
//		"classpath:util_Context.xml"})
//public class TestResourceAdapt {
//
//	private Logger log=LoggerFactory.getLogger(TestResourceAdapt.class);
//	
//	@Autowired
//	private I18nAdapter i18nAdapter;
//		
//	@Autowired
//	private EntityInfoManager entityMang;
//
//
//	@Autowired
//	private RuleManager ruleManager;
//	
//	@Test
//	public void resourceConvert(){
//		String locale="zh_cn";
//		
//		ModuleList mod=ruleManager.getRootModules("admin");
//		
//		List<ModuleItem> itemList=new ArrayList<ModuleItem>();
//		for(RootModuleInfo info:mod.getRootModules()){
//			ModuleItem item=info.getModuleItem();
//			itemList.add(item);
//		}
//		
//		i18nAdapter.doResultConvert(itemList , locale);
//		
//		for(ModuleItem root:itemList){
//			log.info(root.getDesc());
//			assertFalse(root.getDesc().contains("."));
//
//			ModuleTree tree=mod.getTreeByName(root.getName());
//			ViewModuleTree viewTree=tree.getViewModuleTree();
//			i18nAdapter.doResultConvert(viewTree, locale);	
//			
//		}
//				
//	}
//	
//	public ModuleDetail getModuleDetail(ModulesElement elem) {
//		ModuleDetail detail=new ModuleDetail();
//		detail.setModuleDetail(elem.getDesc());
//		
//		ModuleItem item=new ModuleItem();
//		item.setDesc(elem.getDesc());
//		item.setName(elem.getName());
//		detail.setModuleItem(item);
//		
//		DataInfo dataInfo=new DataInfo();
//		if(!elem.getDatas().isEmpty()){
//			DataElement dataElem=elem.getDatas().iterator().next();
//			dataInfo.setTableName(dataElem.getClassName());
//			
//			EntityMetaInfo entityInfo=entityMang.getEntityInfoByClsName(dataElem.getClassName());
//			
////			dataInfo.setFieldSet(dataElem.getFieldInfoSet(entityInfo));
//		}
//		detail.setDataInfo(dataInfo);
//		
//		for(String feature:elem.getFeatures()){
//			FeatureInfo info=new FeatureInfo();
//			info.setName(feature);
//			info.setDesc(feature);
//			detail.addFeature(info);
//		}
//		return detail;
//	}
//
//}
