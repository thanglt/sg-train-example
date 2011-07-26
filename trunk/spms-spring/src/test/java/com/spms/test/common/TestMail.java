package com.spms.test.common;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skynet.common.network.MailMessage;
import com.skynet.common.network.MailTool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:util_Context.xml" })
public class TestMail {

	
	@Autowired
	private MailTool sender;
	
	@Test
	public void sendSimpleMail(){
		
		MailMessage message=new MailMessage();
		message.setFrom("jiangyx@skynetsoft.org");
		message.setPwd("12345678");
		message.setDisplayName("admin");
		
		message.setSubject("test");
		message.setMessage("this is just a test and pic <img src=\"./arrow_right.png\"> ");
		message.setTo("oldoldwatch@sina.com");
		message.addAttachment(new File("E:/workspace/arrow_right.png"));
		sender.sendMail(message);
	}
	
	@Test
	public void sendMailWithAttr(){
		
	}
}
