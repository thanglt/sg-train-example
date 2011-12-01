package com.mycompany.gwt.sales.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.mycompany.gwt.sales.client.dto.UserDto;
import com.mycompany.gwt.sales.client.gin.SalesInjector;
import com.mycompany.gwt.sales.client.service.UserServiceRPC;
import com.mycompany.gwt.sales.client.service.UserServiceRPCAsync;
import com.mycompany.gwt.sales.client.tools.UserTools;
import com.smartgwt.client.util.SC;

public class Sales implements EntryPoint {

    public void onModuleLoad() {
        //removes child widgets in hostPage.
        DOM.getElementById("loadingMsg").removeFromParent();
        DOM.getElementById("loadingWrapper").removeFromParent();

        //加载当前登录用户信息
        UserServiceRPCAsync userService = GWT.create(UserServiceRPC.class);
        userService.getLoginUser(new AsyncCallback<UserDto>() {
            public void onFailure(Throwable caught) {
                SC.warn(caught.getMessage());
            }
            public void onSuccess(UserDto result) {
                UserTools.loginUser = result;
                //add homepage
                SalesInjector injector = GWT.create(SalesInjector.class);
                RootPanel.get().add(injector.getHomePage());
            }
        });
    }
}
