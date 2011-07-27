package com.m3958.firstgwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;


public class StepCareerJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected StepCareerJso() { }
	  
	  public final native JavaScriptObject getStart()/*-{return this.start}-*/;
	  public final native JavaScriptObject getEnd()/*-{return this.end}-*/;
	  public final native String getZysj()/*-{return this.zysj}-*/;
	  public final native String getBz()/*-{return this.bz}-*/;

}
