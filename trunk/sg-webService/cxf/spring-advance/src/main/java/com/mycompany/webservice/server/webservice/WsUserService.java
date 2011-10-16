package com.mycompany.webservice.server.webservice;

import com.mycompany.webservice.server.entity.User;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface WsUserService {

    public List<User> getList();

    public void create(String name , String email);

    public void update(String id , String name , String email);

    public void delete(String id);
}
