package com.mycompany.webservice.sample.server.service;

import com.mycompany.webservice.sample.server.dao.UserDao;
import com.mycompany.webservice.sample.server.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao userDao;

    public List<User> getList() {
        return userDao.getList();
    }

    public User getById(String id) {
        return userDao.getById(id);
    }

    public void create(User user) {
        userDao.create(user);
    }

    @Transactional
    public void update(User user){
        User u = userDao.getById(user.getId());
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        userDao.update(user);
    }

    @Transactional
    public void deleteById(String id) {
        User user = userDao.getById(id);
        userDao.deleteById(user);
    }
}
