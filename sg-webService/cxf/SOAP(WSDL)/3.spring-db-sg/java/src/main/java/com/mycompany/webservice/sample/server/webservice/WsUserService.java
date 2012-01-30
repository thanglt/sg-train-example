package com.mycompany.webservice.sample.server.webservice;

import com.mycompany.webservice.sample.server.entity.User;
import com.mycompany.webservice.sample.server.webservice.type.OperationOutputType;
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

    @WebResult(name = "OperationOutputType")
    @WebMethod(operationName = "Create")
    public OperationOutputType create(
            //arg name
            @WebParam(name = "Name")
            String name,
            @WebParam(name = "Email")
            String email);

    @WebResult(name = "OperationOutputType")
    @WebMethod(operationName = "Update")
    public OperationOutputType update(
            @WebParam(name = "Id")
            String id,
            @WebParam(name = "Name")
            String name,
            @WebParam(name = "Email")
            String email);

    @WebResult(name = "OperationOutputType")
    @WebMethod(operationName = "Delete")
    public OperationOutputType delete(
            @WebParam(name = "Id")
            String id);
}
