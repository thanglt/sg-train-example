package com.train.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class HelloDynamicForm3 extends DynamicForm{

    @Inject
    HelloServiceAsync helloService;

    public HelloDynamicForm3() {
        setWidth(300);
        setHeight(300);
        setBackgroundColor("silver");

        final TextItem textItem = new TextItem();
        textItem.setTitle("Your name:");

        ButtonItem btnItem = new ButtonItem();
        btnItem.setTitle("click me");

        btnItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent clickEvent) {
                String name = textItem.getValue().toString();
                helloService.sayHello(name, new AsyncCallback<String>() {
                    public void onFailure(Throwable throwable) {
                        GWT.log(throwable.toString());
                    }

                    public void onSuccess(String s) {
                        SC.say(s);
                    }
                });
            }
        });

        setItems(textItem, btnItem);
    }

}
