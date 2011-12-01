package com.mycompany.gwt.sales.client.view;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.mycompany.gwt.sales.client.dto.UserDto;
import com.mycompany.gwt.sales.client.i18n.SalesConstants;
import com.mycompany.gwt.sales.client.i18n.SalesMessages;
import com.mycompany.gwt.sales.client.service.UserServiceRPCAsync;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class HomePage extends VLayout{

    @Inject
    public HomePage(final SalesMessages msg, SalesConstants constants, final UserServiceRPCAsync userService) {

        setBackgroundColor("black");
        setWidth100();
        setHeight100();

        IButton btn = new IButton();
        btn.setTitle(constants.clickMe());

        btn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                userService.getLoginUser(new AsyncCallback<UserDto>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        SC.warn(throwable.getMessage());
                    }
                    @Override
                    public void onSuccess(UserDto userDto) {
                        SC.say(msg.msg_welcome_back(userDto.getName()));
                    }
                });
            }
        });

        addChild(btn);
    }
}
