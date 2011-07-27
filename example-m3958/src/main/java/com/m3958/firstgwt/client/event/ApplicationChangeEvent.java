package com.m3958.firstgwt.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ApplicationChangeEvent extends GwtEvent<ApplicationChangeEventHandler> {
	public static Type<ApplicationChangeEventHandler> TYPE = new Type<ApplicationChangeEventHandler>();

  
	@Override
	public Type<ApplicationChangeEventHandler> getAssociatedType() {
		return TYPE;
	}


	@Override
	protected void dispatch(ApplicationChangeEventHandler handler) {
		handler.onApplicationChange(this);
	}
}
