package mytest.client.service;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import mytest.client.lib.GenericGwtRpcService;
import mytest.shared.UserDto;

import java.util.List;

@RemoteServiceRelativePath("springGwtServices/userServiceRpcServlet")
public interface UserServiceRPC extends GenericGwtRpcService<UserDto> {

}
