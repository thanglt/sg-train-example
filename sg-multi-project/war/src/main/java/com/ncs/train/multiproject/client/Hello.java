package com.ncs.train.multiproject.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class Hello implements EntryPoint {
    final HelloServiceAsync service = HelloServiceAsync.Util.getInstance();

    public void onModuleLoad() {

        VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();
        layout.setBackgroundColor("yellow");

        IButton btn = new IButton();
        btn.setTitle("click me");

        btn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                service.sayHello("hjl" , new AsyncCallback<String>(){
                    public void onFailure(Throwable e) {
                        System.out.println(e.toString());
                    }
                    public void onSuccess(String result) {
                        SC.say(result);
                    }
                });
            }
        });

        layout.addMember(btn);
        layout.draw();
    }
}
