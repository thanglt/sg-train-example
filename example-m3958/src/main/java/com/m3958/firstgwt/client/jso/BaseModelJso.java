package com.m3958.firstgwt.client.jso;


import com.google.gwt.core.client.JavaScriptObject;

public class BaseModelJso extends JavaScriptObject {
	  // Overlay types always have protected, zero-arg ctors
	  protected BaseModelJso() { }
	  
	  public final native int getId()/*-{return (this.id ? this.id : 0)}-*/;
	  
	  public final native int getVersion()/*-{return (this.version ? this.version : 0)}-*/;
  
	  public final native JavaScriptObject getCreatedAt()/*-{return this.createdAt}-*/;
	  

}
