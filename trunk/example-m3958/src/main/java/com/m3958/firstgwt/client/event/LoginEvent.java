package com.m3958.firstgwt.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoginEvent extends GwtEvent<LoginEventHandler> {
	public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
  
	private boolean login;
	
	public LoginEvent(boolean login){
		this.login = login;
	}

	@Override
	public Type<LoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginEventHandler handler) {
		handler.onLoginEvent(this);
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public boolean isLogin() {
		return login;
	}


}
