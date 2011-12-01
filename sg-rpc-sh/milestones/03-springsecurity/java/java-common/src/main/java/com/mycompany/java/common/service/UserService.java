package com.mycompany.java.common.service;


import com.mycompany.java.common.entity.User;

public interface UserService extends BaseService<User, String> {

    public User getUserByUsername(String username);

    public String changePassword(String oldPassword, String newPassword);

    public void logoutPrincipal();

    public User getUserSessionInfo();

}
