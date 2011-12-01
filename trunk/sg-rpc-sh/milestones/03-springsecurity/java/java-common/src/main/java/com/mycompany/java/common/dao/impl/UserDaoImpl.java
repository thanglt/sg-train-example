package com.mycompany.java.common.dao.impl;

import com.mycompany.java.common.dao.UserDao;
import com.mycompany.java.common.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User,String> implements UserDao {


    public boolean isExistByUsername(String username) {
        String hql = "from User user where lower(user.username) = lower(?)";
		User user = (User) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
		if (user != null) {
			return true;
		} else {
			return false;
		}
    }

    public User getUserByUsername(String username) {
        String hql = "from User user where lower(user.username) = lower(?)";
        return (User)getSession().createQuery(hql).setParameter(0, username).uniqueResult();
    }
}
