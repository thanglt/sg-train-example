package com.m3958.firstgwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;


public class PermissionJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected PermissionJso() { }
	  
	  public final native String getName()/*-{return this.name}-*/;
	  public final native String getDescription()/*-{return this.description}-*/;
	  public final native int getObjectIdentity()/*-{return this.objectIdentity}-*/;
	  public final native boolean isClassPermission()/*-{return this.classPermission ? this.classPermission : false}-*/;
	  public final native String getObjectClassName()/*-{return this.objectClassName}-*/;
	  public final native JavaScriptObject getOperation()/*-{return this.operation}-*/;

}
