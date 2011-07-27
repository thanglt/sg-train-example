package com.m3958.firstgwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;


public class UserJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected UserJso() { }
	  
	  public final native String getLoginName()/*-{return this.loginName}-*/;
	  public final native String getEmail()/*-{return this.email}-*/;
	  public final native String getPassword()/*-{return this.password}-*/;
	  public final native String getMobile()/*-{return this.mobile}-*/;
	  public final native String getNickname()/*-{return this.nickname}-*/;
	  public final native String getAvatar()/*-{return this.avatar}-*/;
	  public final native JavaScriptObject getBirthday()/*-{return this.birthday}-*/;

}
