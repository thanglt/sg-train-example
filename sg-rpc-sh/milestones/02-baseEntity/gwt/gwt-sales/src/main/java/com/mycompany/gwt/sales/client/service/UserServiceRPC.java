package com.mycompany.gwt.sales.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mycompany.salesDomain.entity.User;

import java.util.List;

@RemoteServiceRelativePath("springGwtServices/userServiceRPCServlet")
public interface UserServiceRPC extends RemoteService {

    List<User> getAll();

}
