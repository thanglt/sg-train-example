package com.mycompany.gwt.sales.client.service;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mycompany.gwt.common.client.GenericGwtRpcService;
import com.mycompany.gwt.sales.client.dto.MenuNodeDto;

@RemoteServiceRelativePath("springGwtServices/menuNodeServiceRPCServlet")
public interface MenuNodeServiceRPC extends GenericGwtRpcService<MenuNodeDto> {
}
