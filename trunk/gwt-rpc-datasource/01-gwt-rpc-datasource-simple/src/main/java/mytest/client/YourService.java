package mytest.client;
import mytest.client.lib.GenericGwtRpcService;
import mytest.shared.YourDataObject;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("yourservice")
public interface YourService extends GenericGwtRpcService<YourDataObject> { 

}