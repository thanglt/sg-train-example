package com.m3958.firstgwt.client.jso;


public class MenuitemJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected MenuitemJso() { }
	  
	  public final native String getTitle()/*-{return this.title}-*/;
	  public final native String getUniqueName()/*-{return this.uniqueName}-*/;
	  public final native String getMenuItemCat()/*-{return this.menuItemCat}-*/;
}
