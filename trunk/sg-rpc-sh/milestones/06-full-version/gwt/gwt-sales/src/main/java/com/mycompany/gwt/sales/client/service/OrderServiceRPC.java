package com.mycompany.gwt.sales.client.service;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mycompany.gwt.common.client.GenericGwtRpcService;
import com.mycompany.gwt.sales.client.dto.OrderDto;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: put description for the type here...
 */

@RemoteServiceRelativePath("springGwtServices/orderServiceRPCServlet")
public interface OrderServiceRPC extends GenericGwtRpcService<OrderDto> {
}
