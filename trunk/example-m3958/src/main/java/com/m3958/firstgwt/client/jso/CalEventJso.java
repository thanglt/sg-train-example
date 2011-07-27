package com.m3958.firstgwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;


public class CalEventJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected CalEventJso() { }
	  
	  public final native JavaScriptObject getStartDate()/*-{return this.startDate}-*/;
	  public final native JavaScriptObject getEndDate()/*-{return this.endDate}-*/;
	  public final native String getCalEventType()/*-{return this.calEventType}-*/;
	  public final native String getName()/*-{return this.name}-*/;
	  public final native String getDescription()/*-{return this.description}-*/;
}
