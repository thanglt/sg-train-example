package com.spms.test.manager;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skynet.spms.manager.CommonDao;
import com.skynet.spms.persistence.entity.organization.userGroup.UserGroup;
import com.skynet.spms.persistence.entity.organization.userInformation.User;
import com.skynet.spms.persistence.entity.organization.userInformation.UserQuota;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:base_Context.xml",
		"classpath:service_Context.xml",
		"classpath:util_Context.xml",
		"classpath:jbpm_Context.xml"})
@TransactionConfiguration(transactionManager="transManager", defaultRollback=true)
@Transactional
public class UserManager {
	
	@Autowired
	private CommonDao dao;
	
	@Autowired
	private SessionFactory factory;
	
	private Session getSession(){
		return factory.getCurrentSession();
	}
	
	@Test
	public void addUser(){
		
		
		
		List<com.skynet.spms.persistence.entity.organization.userInformation.User>
		   list=getSession().createQuery(
				   " select user " +
				   " from UserGroup m_group join m_group.m_User user " +
				   " where m_group.roleNames like :role" )	  
			.setParameter("role", "%"+"admin"+"%")
			.list();
		
		
		List<UserGroup> 
		   glist=getSession().createQuery(
				   " select m_group from User user join user.m_UserGroup m_group" +
		   		" where user.username = :username" )
			.setParameter("username", "abc")
			.list();
		
		UserQuota quota=(UserQuota) getSession().createQuery("select quota from User user " +
				" join user.m_UserQuota quota " +
				" where user.username = :name " )
				.setParameter("name", "userID").uniqueResult();
				
	
	}

}
