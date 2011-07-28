package com.train.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.train.client.HelloService;

public class HelloServiceImpl extends RemoteServiceServlet implements HelloService{

    public String sayHello(String username) {
        return "Hello : " + username;
    }
}
