package com.m3958.firstgwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;


public class CareerJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected CareerJso() { }
	  
	  public final native JavaScriptObject getStart()/*-{return this.start}-*/;
	  public final native JavaScriptObject getEnd()/*-{return this.end}-*/;
	  public final native String getDanwei()/*-{return this.danwei}-*/;
	  public final native String getZhiwu()/*-{return this.zhiwu}-*/;
	  public final native String getBz()/*-{return this.bz}-*/;

}
