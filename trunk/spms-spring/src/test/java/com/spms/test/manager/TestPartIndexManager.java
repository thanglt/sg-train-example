package com.spms.test.manager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.skynet.spms.manager.partCatalog.PartIndexManager;
import com.skynet.spms.persistence.entity.partCatalog.technicalCatalog.indexInfomation.PartIndex;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:base_Context.xml",
		"classpath:service_Context.xml",
		"classpath:util_Context.xml"})
@TransactionConfiguration(transactionManager="transManager", defaultRollback=false)
public class TestPartIndexManager {
	@Autowired
	private PartIndexManager partIndexManager ;
	private Logger log=LoggerFactory.getLogger(TestPartIndexManager.class);
	
	@Test
	public void testInsert() {

		PartIndex pi = new PartIndex();
		pi.setManufacturerPartNumber("1111");
		pi.setUniqueComponentIdentificationNumber("bbbbb");
		pi.setOverlengthPartNumber("aaaa");
		pi.setPartNumber("abc");
		partIndexManager.insertEntity(pi);
		
	}

}