package com.mycompany.gwt.sales.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mycompany.salesDomain.entity.User;

import java.util.List;

public interface UserServiceRPCAsync {

    void getAll(AsyncCallback<List<User>> async);

}
