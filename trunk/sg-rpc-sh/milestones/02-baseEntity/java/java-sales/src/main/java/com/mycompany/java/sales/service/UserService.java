package com.mycompany.java.sales.service;

import com.mycompany.java.common.service.BaseService;
import com.mycompany.java.sales.entity.User;

import java.util.List;

public interface UserService extends BaseService<User, String> {

    public List<User> getAll();

}
