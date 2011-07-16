package com.mycompany.sales.dao;

import com.mycompany.sales.entity.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao{

	public List<User> getAll() {
		String hql =  "from User";
		return this.getHibernateTemplate().find(hql);
	}

	
	
}
