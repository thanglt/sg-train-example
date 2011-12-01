package com.mycompany.gwt.sales.client.service;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mycompany.gwt.common.client.GenericGwtRpcService;
import com.mycompany.gwt.sales.client.dto.RoleDto;

import java.util.List;

@RemoteServiceRelativePath("springGwtServices/roleServiceRPCServlet")
public interface RoleServiceRPC extends GenericGwtRpcService<RoleDto> {

}
