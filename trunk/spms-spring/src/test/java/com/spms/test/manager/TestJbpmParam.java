package com.spms.test.manager;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skynet.spms.jbpm.dao.WfPersistenceParamDao;
import com.skynet.spms.persistence.entity.jbpm.WfParamID;
import com.skynet.spms.persistence.entity.jbpm.WfPersistenceParam;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:base_Context.xml",
		"classpath:service_Context.xml",
		"classpath:util_Context.xml",
		"classpath:jbpm_Context.xml"})
@TransactionConfiguration(transactionManager="transManager", defaultRollback=true)
@Transactional
public class TestJbpmParam {

	@Autowired
	private WfPersistenceParamDao paramDao;
	
	@Test
	public void testParam(){
		
		for(int i=0;i<10;i++){
			WfPersistenceParam param=new WfPersistenceParam();
			WfParamID id=new WfParamID();
//			id.setProcID("procID");
			id.setTaskID("taskID:"+i%3);
			id.setDefFormName("form:"+i%4);
			param.setWfParamID(id);
			param.setRefBusinessKey("busKey:"+i%5);
			param.setRefFormName("ref form:"+i%4);
			
			paramDao.saveOrUpdateParam(param);
		}
		
		
		List<WfPersistenceParam> paramList=paramDao.getParamsByProcInstID("procID", "taskID:"+1);
		assertEquals(paramList.size(),3);
		
		
		
	}
	
}
