package com.train.mvc.pingPong.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/12/11
 * Description: the event handler for Ping Event
 */
public interface PingEventHandler extends EventHandler{

    void onEvent(PingEvent event);

}
