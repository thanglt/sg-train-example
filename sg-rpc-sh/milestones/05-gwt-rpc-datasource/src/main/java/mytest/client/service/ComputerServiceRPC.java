package mytest.client.service;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import mytest.client.lib.GenericGwtRpcService;
import mytest.shared.ComputerDto;

@RemoteServiceRelativePath("springGwtServices/computerServiceRpcServlet")
public interface ComputerServiceRPC extends GenericGwtRpcService<ComputerDto> {
}
