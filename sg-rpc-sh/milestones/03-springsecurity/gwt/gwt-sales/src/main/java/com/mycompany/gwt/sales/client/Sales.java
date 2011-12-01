package com.mycompany.gwt.sales.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mycompany.gwt.sales.client.dto.UserDto;
import com.mycompany.gwt.sales.client.service.UserServiceRPC;
import com.mycompany.gwt.sales.client.service.UserServiceRPCAsync;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class Sales implements EntryPoint {

    private final UserServiceRPCAsync userService = GWT.create(UserServiceRPC.class);

    public void onModuleLoad() {

        VLayout layout = new VLayout();
        layout.setBackgroundColor("black");
        layout.setWidth100();
        layout.setHeight100();

        IButton btn = new IButton();
        btn.setTitle("click me");

        btn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                userService.getLoginUser(new AsyncCallback<UserDto>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        SC.warn(throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(UserDto userDto) {
                        SC.say(userDto.getName());
                    }
                });
            }
        });

        layout.addChild(btn);
        layout.draw();
        layout.show();
    }

}
