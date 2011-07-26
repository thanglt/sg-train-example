package com.spms.test.common;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skynet.spms.service.FileService;
import com.skynet.spms.service.UUIDGeneral;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:base_Context.xml",
	"classpath:service_Context.xml",
	"classpath:jbpm_Context.xml",
"classpath:util_Context.xml"})
public class TestSeq {
	private Logger log = LoggerFactory.getLogger(TestSeq.class);

	@Autowired
	private UUIDGeneral general;
	
//	@Autowired
//	private FileService fileService;
	
	
	@Test
	public void testSimpleSeq() throws IOException{
		ScheduledThreadPoolExecutor threadPool=new ScheduledThreadPoolExecutor(10);
		
		long start=System.currentTimeMillis();
		for(int i=0;i<20;i++){
			threadPool.scheduleAtFixedRate(new Task(i), (System.currentTimeMillis()-start), 10, TimeUnit.MILLISECONDS);			
		}
		
		while(true){
			char ch=(char)System.in.read();
			
			if(ch=='q'){
				System.exit(0);
			}
		}
	}
	
	private class Task implements Runnable{

		
		private final String type;
		
		public Task(int idx){

			type="type"+(idx%10);
		}
		
		@Override
		public void run() {
			try{
				for(int i=0;i<10;i++){
					String seq=general.getSequence(type);
					log.info(seq);
				}
				
				Thread.sleep(100);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}
