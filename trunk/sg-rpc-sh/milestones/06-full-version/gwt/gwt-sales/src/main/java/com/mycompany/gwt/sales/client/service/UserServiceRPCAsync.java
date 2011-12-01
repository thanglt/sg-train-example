package com.mycompany.gwt.sales.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mycompany.gwt.common.client.GenericGwtRpcServiceAsync;
import com.mycompany.gwt.sales.client.dto.RoleDto;
import com.mycompany.gwt.sales.client.dto.UserDto;

import java.util.List;

public interface UserServiceRPCAsync extends GenericGwtRpcServiceAsync<UserDto> {

    void getLoginUser(AsyncCallback<UserDto> async);

    void getRoleListByUser(String userId, AsyncCallback<List<RoleDto>> async);

    void getValidRoleListByUser(String userId, AsyncCallback<List<RoleDto>> async);

    void assignRoleToUser(String roleId, String userId, AsyncCallback<String> async);

    void removeRoleFromUser(String roleId, String userId, AsyncCallback<String> async);
}
