package com.mycompany.java.sales.dao.impl;

import com.mycompany.java.common.dao.impl.BaseDaoImpl;
import com.mycompany.java.sales.dao.UserDao;
import com.mycompany.java.sales.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao {

}
