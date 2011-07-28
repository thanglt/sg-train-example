package com.train.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

public interface HelloServiceAsync {
    void sayHello(String username , AsyncCallback<String> async);
}
