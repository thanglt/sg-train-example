package com.ncs.train.multiproject.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath( "Hello" )
public interface HelloService

   extends RemoteService

{

   String sayHello(String message);

}