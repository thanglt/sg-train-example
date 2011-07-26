package com.spms.test.manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skynet.spms.webservice.entity.FaultResponse;
import com.skynet.spms.webservice.entity.VisualLocationInputParameters;
import com.skynet.spms.webservice.service.WsRFIDService;
import com.skynet.spms.webservice.service.WsTaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:base_Context.xml",
		"classpath:service_Context.xml",
		"classpath:util_Context.xml",
		"classpath:jbpm_Context.xml"})
@TransactionConfiguration(transactionManager="transManager", defaultRollback=true)
@Transactional
public class TestLocation {

	@Autowired
	private WsTaskService wsTaskService;

	@Autowired
	private WsRFIDService wsRFIDService;
	
//	@Test
//	public void testGetLocationInfo(){
//		QueryLocationParameters request = new QueryLocationParameters();
//		
//		try {
//			wsRFIDService.getLocationInfo(request);
//		} catch (FaultResponse e) {
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void testGetTaskListByRFIDTag(){
//		QueryTasklistByTAGInputParameters request = new QueryTasklistByTAGInputParameters();
//		
//		try {
//			request.setTagIdentifierCode("test0001");
//			wsTaskService.getTaskListByRFIDTag(request);
//		} catch (FaultResponse e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void testGetVirtualLocaltion(){
		VisualLocationInputParameters request = new VisualLocationInputParameters();
		
		try {
			request.setLocationNumber("SHA-3A-01B-02B-01");
			wsRFIDService.getVirtualLocaltion(request);
		} catch (FaultResponse e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void testGetTasklist(){
//		QueryTasklistInputParameters request = new QueryTasklistInputParameters();
//		
//		try {
//			wsTaskService.getTasklist(request);
//		} catch (FaultResponse e) {
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void testGetTaskDetails(){
//		GetTaskDetailsInputParameters request = new GetTaskDetailsInputParameters();
//		request.setTaskNumber("PIK-1106-00002");
//		
//		try {
//			wsTaskService.getTaskDetails(request);
//		} catch (FaultResponse e) {
//			e.printStackTrace();
//		}
//	}
}
