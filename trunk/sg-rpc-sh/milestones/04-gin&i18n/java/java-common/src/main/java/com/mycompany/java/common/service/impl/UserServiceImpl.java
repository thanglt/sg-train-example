package com.mycompany.java.common.service.impl;

import com.mycompany.java.common.dao.UserDao;
import com.mycompany.java.common.entity.User;
import com.mycompany.java.common.service.RoleService;
import com.mycompany.java.common.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

    @Resource
    public void setBaseDao(UserDao userDao) {
        super.setBaseDao(userDao);
    }

    @Resource
    private UserDao userDao;

    @Resource
    private RoleService roleService;

    private static Logger log = Logger.getLogger(UserServiceImpl.class);


    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public String changePassword(String oldPassword, String newPassword) {
        String username = "unknown";
        String flg = "OK";
        username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals("unknown")) {
            try {
                User user = userDao.getUserByUsername(username);
                if (user != null && user.getPassword().equals(oldPassword)) {
                    user.setPassword(newPassword);
                    user.setLastUpdateDate(new Timestamp(new Date().getTime()));
                    userDao.update(user);
                } else {
                    flg = "旧密码不正确";
                }
            } catch (Exception e) {
                flg = "密码修改失败，请与管理员联系!";
            }
        }
        return flg;
    }

    public void logoutPrincipal() {
        SecurityContextHolder.clearContext();
    }

    public User getUserSessionInfo() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
