package com.mycompany.webservice.server.webservice;

import com.mycompany.webservice.server.webservice.type.OperationOutputType;
import com.mycompany.webservice.server.webservice.type.UserListOutputType;
import javax.jws.WebParam;
import javax.jws.WebService;

//exposed as a Web Service
@WebService
public interface WsUserService {


    public UserListOutputType getList();

    public OperationOutputType create(
            //arg name
            @WebParam(name = "name")
            String name,
            @WebParam(name = "email")
            String email);

    public OperationOutputType update(
            @WebParam(name = "id")
            String id,
            @WebParam(name = "name")
            String name,
            @WebParam(name = "email")
            String email);

    public OperationOutputType delete(
            @WebParam(name = "id")
            String id);
}
