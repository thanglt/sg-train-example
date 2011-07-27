package com.m3958.firstgwt.client.jso;


public class RoleJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected RoleJso() { }
	  
	  public final native String getEname()/*-{return this.ename}-*/;
	  public final native String getCname()/*-{return this.cname}-*/;

}
