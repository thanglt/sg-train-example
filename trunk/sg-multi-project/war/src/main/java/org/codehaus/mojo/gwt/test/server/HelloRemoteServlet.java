package org.codehaus.mojo.gwt.test.server;

import org.codehaus.mojo.gwt.test.client.HelloService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class HelloRemoteServlet extends RemoteServiceServlet implements HelloService
{
   public String sayHello( String message )
   {
       return message + " from server";
   }

}