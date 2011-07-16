package com.mycompany.sales.service;

import com.mycompany.sales.dao.UserDao;
import com.mycompany.sales.entity.User;
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
