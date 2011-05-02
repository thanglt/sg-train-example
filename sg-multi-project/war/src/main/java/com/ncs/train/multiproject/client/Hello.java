package com.ncs.train.multiproject.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.ncs.train.multiproject.domain.User;

public class Hello implements EntryPoint {
    final HelloServiceAsync service = HelloServiceAsync.Util.getInstance();

    public void onModuleLoad() {
        User user = new User();
        final Label label = new Label("GWT says : " + user.sayHello());
        RootPanel.get().add(label);

        Button button = new Button("click me !");
        RootPanel.get().add(button);
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                service.sayHello("hello", new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        label.setText("RPC failure " + caught.getMessage());
                        GWT.log("RPC failure", caught);
                    }

                    public void onSuccess(String result) {
                        label.setText(result);
                    }
                });
            }
        });

    }
}
