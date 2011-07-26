package com.spms.test.common;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skynet.common.strtemplate.TemplateGroup;
import com.skynet.common.strtemplate.TemplateTool;
import com.skynet.common.strtemplate.TemplateToolFactory;
import com.spms.test.common.entity.MesysUser;
import com.spms.test.common.entity.TaskMailEntity;
import com.spms.test.common.entity.TaskMailEntity.StepInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:util_Context.xml" })
public class TestTemplate {

	private Logger log = LoggerFactory.getLogger(TestTemplate.class);

	@Autowired
	private TemplateToolFactory templateFactory;

	@Test
	public void testSubject(){
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("param1", "one");
		paramMap.put("param2", 12.34f);
		paramMap.put("param3", new Date());
		
		TemplateGroup subjectTemplate=templateFactory.getTemplateGroup("subject");
		
		TemplateTool tool=subjectTemplate.getTemplateTool("demo_ii");
		
		String subject=tool.gener(paramMap);
		
		log.debug(subject);
		
	}
	
	@Test
	public void testSeq(){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("createDate", new Date());
		paramMap.put("type", "测试");
		paramMap.put("seq", 12345L);
		TemplateGroup subjectTemplate=templateFactory.getTemplateGroup("subject");

		TemplateTool tool=subjectTemplate.getTemplateTool("sequence");
		
		String seq=tool.gener(paramMap);
		log.debug(seq);
		
	}
	
	@Test
	public void testContext() throws FileNotFoundException, IOException {
		TaskMailEntity entity = new TaskMailEntity();
		MesysUser user = new MesysUser();
		user.setUserId("XXX-USER-ID");
		user.setName("中文");

		entity.setUserInfo(user);

		entity.setAdmin("superuser");
		entity.setCompletePre(90.34f);
		entity.setCostTime(123);
		entity.setTaskName("demo task");
		entity.setTaskUrl("http://www.demo.com/tasklist/abc");
		entity.setTime(new Date());
		

		List<StepInfo> stepList = new ArrayList<StepInfo>();
		for (int i = 0; i < 10; i++) {
			StepInfo info = new StepInfo();
			info.setStep("Step:" + i);
			info.setStatus("status:" + i % 3);
			stepList.add(info);
		}
		entity.setStepList(stepList);
		
		try {
			TemplateTool contextTemplate=templateFactory.getGlobeTemplateTool("demo");
			
			contextTemplate.addDateFormat("fulltime","yyyy-MM-dd:HH:mm:ss");
			
			String str = contextTemplate.gener(entity);

			log.debug("result:" + str);

			IOUtils.write(str, new FileOutputStream(
					"e:/workspace/mail.context.html"), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
}
