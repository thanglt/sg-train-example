package com.mycompany.gwt.sales.client.service;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mycompany.gwt.common.client.GenericGwtRpcService;
import com.mycompany.gwt.sales.client.dto.RoleDto;
import com.mycompany.gwt.sales.client.dto.UserDto;

import java.util.List;

@RemoteServiceRelativePath("springGwtServices/userServiceRPCServlet")
public interface UserServiceRPC extends GenericGwtRpcService<UserDto> {

    UserDto getLoginUser();

    List<RoleDto> getRoleListByUser(String userId);

    List<RoleDto> getValidRoleListByUser(String userId);

    String assignRoleToUser(String roleId, String userId);

    String removeRoleFromUser(String roleId, String userId);
}
