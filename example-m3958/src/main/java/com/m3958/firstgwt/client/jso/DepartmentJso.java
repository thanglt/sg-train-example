package com.m3958.firstgwt.client.jso;


public class DepartmentJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected DepartmentJso() { }
	  
	  public final native String getBz()/*-{return this.bz}-*/;
	  public final native String getCname()/*-{return this.cname}-*/;
	  public final native boolean isShequContainer()/*-{return this.shequContainer ? this.shequContainer : false}-*/;

}
