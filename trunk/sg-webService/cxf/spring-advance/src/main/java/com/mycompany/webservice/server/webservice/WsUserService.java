package com.mycompany.webservice.server.webservice;

import com.mycompany.webservice.server.entity.User;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface WsUserService {

    public List<User> getList();

    public void create(
            @WebParam(name="name")
            String name ,
            @WebParam(name="email")
            String email);

    public void update(
            @WebParam(name="id")
            String id ,
            @WebParam(name="name")
            String name ,
            @WebParam(name="email")
            String email);

    public void delete(
            @WebParam(name="id")
            String id);
}
