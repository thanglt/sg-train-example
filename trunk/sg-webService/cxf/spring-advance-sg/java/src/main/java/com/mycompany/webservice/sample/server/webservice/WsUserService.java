package com.mycompany.webservice.sample.server.webservice;

import com.mycompany.webservice.sample.server.entity.User;
import com.mycompany.webservice.sample.server.webservice.type.UserListOutputType;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

//exposed as a Web Service
@WebService
public interface WsUserService {


    //element name
    @WebResult(name = "UserListOutputType")
    //operation name & complexType name
    @WebMethod(operationName = "GetList")
    public UserListOutputType getList();

    @WebMethod(operationName = "Create")
    public void create(
            //arg name
            @WebParam(name = "Name")
            String name,
            @WebParam(name = "Email")
            String email);

    @WebMethod(operationName = "Update")
    public void update(
            @WebParam(name = "Id")
            String id,
            @WebParam(name = "Name")
            String name,
            @WebParam(name = "Email")
            String email);

    @WebMethod(operationName = "Delete")
    public void delete(
            @WebParam(name = "Id")
            String id);
}
