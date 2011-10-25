package com.mycompany.webservice.server.webservice;

import com.mycompany.webservice.server.webservice.type.OperationOutputType;
import com.mycompany.webservice.server.webservice.type.UserListOutputType;
import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

/**
 * The interface exposed as soap and rest
 * @ WebService is for soap
 * @ Path is for the rest top path, ,should be the same as cxf-config.xml
 */

@WebService
@Path("/wsUserServiceImpl/")
public interface WsUserService {


    /**
	 * Simple methods that get user List
	 * @ WebMethod is for soap
	 * @ GET is for REST
	 * @ Path is for the REST service path
	 */
	@WebMethod
	@GET @Path("/")
    public UserListOutputType getList();

    @WebMethod
	@POST @Path("create")
    public OperationOutputType create(
            //arg name
            @WebParam(name = "name")
            String name,
            @WebParam(name = "email")
            String email);

    @WebMethod
    @POST @Path("update")
    public OperationOutputType update(
            @WebParam(name = "id")
            String id,
            @WebParam(name = "name")
            String name,
            @WebParam(name = "email")
            String email);

    @WebMethod
    @POST @Path("delete")
    public OperationOutputType delete(
            @WebParam(name = "id")
            String id);
}
