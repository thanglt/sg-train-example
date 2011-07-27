package com.m3958.firstgwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;


public class FtlJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected FtlJso() { }
	  
	  public final native JavaScriptObject getUpdatedAt()/*-{return this.updatedAt}-*/;
	  public final native String getFtl()/*-{return this.ftl}-*/;
	  public final native String getName()/*-{return this.name}-*/;
	  public final native String getDescription()/*-{return this.description}-*/;
}
