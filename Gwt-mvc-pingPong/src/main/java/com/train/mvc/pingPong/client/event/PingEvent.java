package com.train.mvc.pingPong.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/12/11
 * Description: Ping Event
 */
public class PingEvent extends GwtEvent<PingEventHandler>{

    public static Type<PingEventHandler> TYPE = new Type<PingEventHandler>();

    @Override
    public Type<PingEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PingEventHandler handler) {
        handler.onEvent(this);
    }
}
