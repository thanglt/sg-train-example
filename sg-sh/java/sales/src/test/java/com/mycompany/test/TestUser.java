package com.mycompany.test;

import com.mycompany.salesDomain.dao.UserDao;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.List;

@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestUser extends AbstractTransactionalJUnit4SpringContextTests{

	@Resource
	private UserDao userDao;
	
	@Test
	public void simple(){
		
	} 
	
	@Test
	public void save(){
		List list = userDao.getAll();
		Assert.assertEquals(0, list.size());
	}
	
	
	
}
