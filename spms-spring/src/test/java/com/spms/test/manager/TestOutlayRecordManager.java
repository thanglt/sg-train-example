package com.spms.test.manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.skynet.spms.manager.organization.OutlayRecordManager;
import com.skynet.spms.persistence.entity.base.baseOutlayRecordEntity.BaseOutlayRecord;
import com.skynet.spms.persistence.entity.base.businessStatusEntity.BussinessStatusEntity;
import com.skynet.spms.persistence.entity.base.dataDictionary.EntityStatusMonitor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:base_Context.xml",
		"classpath:service_Context.xml",
		"classpath:util_Context.xml"})
@TransactionConfiguration(transactionManager="transManager", defaultRollback=false)
public class TestOutlayRecordManager {

	@Autowired
	private OutlayRecordManager outlayRecordManager ;
	
	
	private Logger log=LoggerFactory.getLogger(OutlayRecordManager.class);
	
	@Test
	public void instert(){
		BaseOutlayRecord record = new BaseOutlayRecord();
		record.setOrderNumber("ord001");
		record.setContractNumber("contr001");
		record.setAmount(234.0f);
		BussinessStatusEntity statusEntity = new BussinessStatusEntity();
		statusEntity.setStatus(EntityStatusMonitor.created);
		statusEntity.setActionDescription("新建");
		record.setM_BussinessStatusEntity(statusEntity);
		outlayRecordManager.insertEntity(record);
	}
}
