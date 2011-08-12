package com.train.mvc.pingPong.client.view;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;
import com.train.mvc.pingPong.client.event.PingEvent;
import com.train.mvc.pingPong.client.event.PongEvent;
import com.train.mvc.pingPong.client.event.PongEventHandler;

/**
 * Created by IntelliJ IDEA.
 * User: jingling
 * Date: 8/12/11
 * Description: the view for Pong
 */
public class PongView extends VLayout {

    public PongView(final SimpleEventBus eventBus) {
        setWidth(400);
        setHeight(400);

        Img img = new Img("pong.png");
        img.setWidth(360);
        img.setHeight(310);

        final Label label = new Label();

        eventBus.addHandler(PongEvent.TYPE, new PongEventHandler() {
            public void onEvent(PongEvent event) {
                label.setContents("Pong");
                new Timer() {
                    public void run() {
                        label.setContents("");
                        eventBus.fireEvent(new PingEvent());
                    }

                    ;
                }.schedule(1000);
            }
        });

        addMember(img);
        addMember(label);
    }
}
