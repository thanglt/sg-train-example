package com.mycompany.salesDomain.service;

import com.mycompany.salesDomain.dao.UserDao;
import com.mycompany.salesDomain.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;
	
	public List<User> getAll() {
		return userDao.getAll();
	}

}
