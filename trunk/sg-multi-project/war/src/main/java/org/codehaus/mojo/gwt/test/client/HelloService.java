package org.codehaus.mojo.gwt.test.client;

import java.util.Collection;
import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath( "Hello" )

public interface HelloService

   extends RemoteService

{

   String sayHello( String message );

}