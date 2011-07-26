package com.spms.test.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skynet.spms.service.FileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:base_Context.xml",
	"classpath:service_Context.xml",
	"classpath:util_Context.xml"
	})
public class TestFile {
	
	private Logger log=LoggerFactory.getLogger(TestFile.class);
	
	@Autowired
	private FileService fileService;
	
	@Test
	public void testSave() throws FileNotFoundException{
		
		File basePath=new File("E:/workspace");
		for(File file:basePath.listFiles()){
			if(file.isFile()){
//				String fileName=fileService.saveFile(file.getName(), new FileInputStream(file));
//				log.info(fileName);
				
			}
		}
	}

}
