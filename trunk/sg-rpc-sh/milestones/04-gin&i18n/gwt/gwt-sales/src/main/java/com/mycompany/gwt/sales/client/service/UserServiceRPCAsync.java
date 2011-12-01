package com.mycompany.gwt.sales.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mycompany.gwt.sales.client.dto.UserDto;

import java.util.List;

public interface UserServiceRPCAsync {

    void getAll(AsyncCallback<List<UserDto>> async);

    void getLoginUser(AsyncCallback<UserDto> async);
}
