package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.User;
import com.wideplay.warp.persist.Transactional;

public class UserDao  extends BaseDao<User> {
	
	@Inject
	public UserDao(UserChangeStrategy task) {
		super(User.class);
		extraStrategyTask = task;
	}
	

	public User findByLoginNameOrEmailOrMobile(String identity) {
		User user = null;
		try {
			if(identity.indexOf('@') != -1){
				user = findByEmail(identity);
			}else if(identity.matches("\\d+")){
				user = findByMobile(identity);
			}else{
				user = findByLoginName(identity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	private User findByLoginName(String identity) {
		return (User) emp.get().createQuery("SELECT u FROM User u WHERE u.loginName = :loginName").
			setParameter("loginName", identity).getSingleResult();
	}

	private User findByMobile(String identity) {
		return (User) emp.get().createQuery("SELECT u FROM User u WHERE u.mobile = :mobile").
		setParameter("mobile", identity).getSingleResult();
	}

	private User findByEmail(String identity) {
		return (User) emp.get().createQuery("SELECT u FROM User u WHERE u.email = :email").
		setParameter("email", identity).getSingleResult();
	}
	
	@Transactional
	public User remove(User user) {
		user = emp.get().find(User.class, user.getId());
		user.setRoles(null);
		emp.get().remove(user);
		return user;
	}

	public User findByFcId(String id) {
		String qs = "SELECT u FROM User u WHERE u.fcId = :fcId";
		Query q = emp.get().createQuery(qs);
		q.setParameter("fcId", id);
		User u = null;
		try {
			u = (User) q.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return u;
	}

	@Override
	public Integer smartCustomCount() {
		return null;
	}

	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}

	@Override
	public List<User> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}
