package com.mycompany.webservice.sample.server.dao;


import com.mycompany.webservice.sample.server.entity.User;

import java.util.List;

public interface UserDao {

    public List<User> getList();

    public void create(User user);

    public void update(User user);

    public User getById(String id);

    public void deleteById(User user);
}
