package com.train.mvc.pingPong.client.view;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;
import com.train.mvc.pingPong.client.event.PingEvent;
import com.train.mvc.pingPong.client.event.PingEventHandler;
import com.train.mvc.pingPong.client.event.PongEvent;

/**
 * Created by IntelliJ IDEA.
 * User: jingling
 * Date: 8/12/11
 * Description: the view for Ping
 */

public class PingView extends VLayout {

    public PingView(final SimpleEventBus eventBus) {

        setWidth(400);
        setHeight(400);

        Img img = new Img("ping.png");
        img.setWidth(260);
        img.setHeight(320);

        final Label label = new Label();

        eventBus.addHandler(PingEvent.TYPE, new PingEventHandler() {
            public void onEvent(final PingEvent event) {
                label.setContents("Ping");
                new Timer() {
                    @Override
                    public void run() {
                        label.setContents("");
                        eventBus.fireEvent(new PongEvent());
                    };
                }.schedule(1000);
            }
        });

        addMember(img);
        addMember(label);
    }
}


