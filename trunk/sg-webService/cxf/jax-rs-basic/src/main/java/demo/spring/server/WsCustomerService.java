package demo.spring.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

//for REST
@Path("/customerservice/")
//for SOAP
@WebService(serviceName = "Customer" , name = "CustomerService" , targetNamespace = "http://services.mycompany.com")
public interface WsCustomerService {

    //for SOAP
    @WebMethod
    //for REST
    @GET
    @Path("/customers/{id}/")
    public Customer getCustomer(
            @PathParam("id")
            @WebParam(name="id")
            String id);

    @WebMethod
    @PUT
    @Path("/customers/")
    public Response updateCustomer(Customer customer);

    @WebMethod
    @POST
    @Path("/customers/")
    public Response addCustomer(Customer customer);

    @WebMethod
    @DELETE
    @Path("/customers/{id}/")
    public Response deleteCustomer(@PathParam("id") String id);

    @WebMethod
    @Path("/orders/{orderId}/")
    public Order getOrder(@PathParam("orderId") String orderId);

}
