package com.mycompany.java.sales.service.impl;

import com.mycompany.java.common.service.impl.BaseServiceImpl;
import com.mycompany.java.sales.dao.UserDao;
import com.mycompany.java.sales.entity.User;
import com.mycompany.java.sales.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

    @Resource
    private UserDao userDao;

    /*  @Resource
    public void setBaseDao(UserDao userDao){
        super.setBaseDao(userDao);
    }*/

    public List<User> getAll() {
        return userDao.getAll();
    }

}
