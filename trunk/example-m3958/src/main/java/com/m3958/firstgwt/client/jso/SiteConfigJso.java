package com.m3958.firstgwt.client.jso;


public class SiteConfigJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected SiteConfigJso() { }
	  
	  public final native String getConfigKey()/*-{return this.configKey}-*/;
	  public final native String getConfigValue()/*-{return this.configValue}-*/;

}
