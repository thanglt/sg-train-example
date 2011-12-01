package com.mycompany.java.common.service;


import com.mycompany.java.common.entity.Role;
import com.mycompany.java.common.entity.User;

import java.util.List;

public interface UserService extends BaseService<User, String> {

    public User getUserByUsername(String username);

    public String changePassword(String oldPassword, String newPassword);

    public void logoutPrincipal();

    public User getUserSessionInfo();

    public List<Role> getRoleListByUser(String userId);

    public List<Role> getValidRoleListByUser(String userId);
}
