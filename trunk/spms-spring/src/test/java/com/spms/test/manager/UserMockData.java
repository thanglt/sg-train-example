package com.spms.test.manager;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

//import com.skynet.spms.persistence.entity.organization.userGroup.UserGroup;
import com.skynet.spms.persistence.entity.organization.userInformation.User;
//import com.skynet.spms.persistence.entity.organization.userInformation.UserQuota;
import com.skynet.spms.persistence.entity.organization.userRole.Role;
import com.skynet.spms.persistence.entity.spmsdd.EnableStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:base_Context.xml",
		"classpath:service_Context.xml",
		"classpath:util_Context.xml",
		"classpath:jbpm_Context.xml"})
@TransactionConfiguration(transactionManager="transManager", defaultRollback=false)
@Transactional
public class UserMockData {
	
	@Autowired
	private SessionFactory factory;
	
	private Session getSession(){
		return factory.getCurrentSession();
	}
	
	@Test
	public void addUser(){
		
//		List<UserGroup> groupList=new ArrayList<UserGroup>();
		Role group=getRole("Salesman");
//		group.setRoleName();
//		group.setApprovalQuota(1000);
//		
//		getSession().save(group);
		
		for(int i=0;i<10;i++){
			User user=new User();
			user.setUsername("sale_"+i);
			user.setPassword("qwerty");
			user.setM_EnableStatus(EnableStatus.enable);
			
//			UserQuota quota=new UserQuota();
//			quota.setApprovalQuota(1000);
//			user.setM_UserQuota(quota);
			
			List<Role> groupList=new ArrayList<Role>();
			groupList.add(group);
			user.setM_Role(groupList);
			
			getSession().save(user);
		}
	}

	@Test
	public void addMang(){
		
		Role group=getRole("SectionHeadApprover");
//		group.setRoleName("SectionHeadApprover");
//		group.setApprovalQuota(10000);
//		getSession().save(group);

		User user=new User();
		user.setUsername("head_1");
		user.setPassword("qwerty");
		user.setM_EnableStatus(EnableStatus.enable);

//		UserQuota quota=new UserQuota();
//		quota.setApprovalQuota(10000);
//		user.setM_UserQuota(quota);
		
		List<Role> groupList=new ArrayList<Role>();
		groupList.add(group);
		user.setM_Role(groupList);
		
		getSession().save(user);
	}
	
	@Test
	public void addOther(){
		
		String[] nameArray={"vp_1","sale_sv_1","financ_sv_1"};
		String[] roleArray={"ViceGeneralApprover","SalesSupervisor","FinancialSupervisor"};
		
		for(int i=0;i<nameArray.length;i++){
			Role group=getRole(roleArray[i]);
//			group.setRoleName(roleArray[i]);
//			group.setApprovalQuota(50000);
//			getSession().save(group);
			
			User user=new User();
			user.setUsername(nameArray[i]);
			user.setPassword("qwerty");
			user.setM_EnableStatus(EnableStatus.enable);

//			UserQuota quota=new UserQuota();
//			quota.setApprovalQuota(50000);
//			user.setM_UserQuota(quota);
			
			List<Role> groupList=new ArrayList<Role>();
			
			groupList.add(group);
			user.setM_Role(groupList);
			
			getSession().save(user);
		}
	}

	private Role getRole(String name){
		Role role=(Role) getSession()
		.createQuery("from Role where roleName=:name")
		.setString("name", name)
		.uniqueResult();
		
		return role;
	}

}
