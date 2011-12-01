package mytest.client.service;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import mytest.client.lib.GenericGwtRpcService;
import mytest.shared.MenuNodeDto;

@RemoteServiceRelativePath("springGwtServices/menuNodeServiceRpcServlet")
public interface MenuNodeServiceRPC extends GenericGwtRpcService<MenuNodeDto> {
}
