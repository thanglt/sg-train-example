package com.mycompany.webservice.server.webservice;

import com.mycompany.webservice.server.entity.User;
import com.mycompany.webservice.server.webservice.type.OperationOutputType;
import com.mycompany.webservice.server.webservice.type.UserListOutputType;
import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * The interface exposed as soap and rest
 * @ WebService is for soap
 * @ Path is for the rest top path, ,should be the same as cxf-config.xml
 */

@WebService
@Path("/userService/")
//@Produces("application/xml")
public interface WsUserService {


    /**
	 * @ WebMethod is for SOAP
     * @ WebParam is for SOAP parameter
     *
	 * @ GET/POST/PUT/DELETE is for REST
	 * @ Path is for the REST service path
     * @ PathParam is for REST parameter in url
     * @ FormParam is for REST parameter in form
	 */
	@WebMethod
	@GET
    @Path("/users/")
    public UserListOutputType getList();

    @WebMethod
	@POST
    @Path("/users/")
    @Consumes("application/x-www-form-urlencoded")
    public Response addUser(
            //arg name
            @WebParam(name = "name")
            @FormParam("name")
            String name,
            @WebParam(name = "email")
            @FormParam("email")
            String email);

    @WebMethod
	@POST
    @Path("/users2/")
    public Response addUser2(User user);

    @WebMethod
    @PUT
    @Path("/users/")
    public Response update(User user);

    @WebMethod
    @DELETE
    @Path("/users/{id}/")
    public Response delete(
            @PathParam("id")
            @WebParam(name = "id")
            String id);
}
