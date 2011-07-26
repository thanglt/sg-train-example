package com.spms.test.manager;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skynet.spms.service.PrinterEnum;
import com.skynet.spms.service.PrinterService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:base_Context.xml",
		"classpath:service_Context.xml",
		"classpath:util_Context.xml",
		"classpath:jbpm_Context.xml"})
public class TestPrintService {
	
	private Logger log=LoggerFactory.getLogger(TestPrintService.class);
	
	@Autowired
	private PrinterService printerService;
	
	@Test
	public void test(){
		
		try {
			Future<String> future=printerService.doPrint(PrinterEnum.A, "this is test");
			log.info("finish");
			log.info("is enable?:"+future.isDone());
			while(!future.isDone()){
				Thread.sleep(100);
			}
			log.info("result:"+future.get());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
