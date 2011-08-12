package com.train.mvc.pingPong.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.train.mvc.pingPong.client.event.PingEvent;
import com.train.mvc.pingPong.client.view.PingView;
import com.train.mvc.pingPong.client.view.PongView;

public class PingPong implements EntryPoint {

    public void onModuleLoad() {

//        final SimpleEventBus eventBus = new SimpleEventBus();
        final SimpleEventBus eventBus = new SimpleEventBus();

        VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();
//        layout.setBackgroundColor("yellow");

        HLayout main = new HLayout();
        main.setWidth("100%");
        main.setHeight(400);
//        main.setBackgroundColor("blue");

        main.addMember(new PingView(eventBus));
        main.addMember(new PongView(eventBus));

        IButton startBtn = new IButton();
        startBtn.setTitle("Start Game");
        startBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                eventBus.fireEvent(new PingEvent());
            }
        });

        layout.addMember(main);
        layout.addMember(startBtn);

        layout.draw();
    }
}
