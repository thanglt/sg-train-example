package com.ncs.train.multiproject.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ncs.train.multiproject.client.HelloService;

public class HelloRemoteServlet extends RemoteServiceServlet implements HelloService
{
   public String sayHello( String message )
   {
       return message + " from server";
   }

}