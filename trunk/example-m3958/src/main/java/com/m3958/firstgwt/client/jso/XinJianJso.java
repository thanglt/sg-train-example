package com.m3958.firstgwt.client.jso;

import com.google.gwt.core.client.JsArray;


public class XinJianJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected XinJianJso() { }
	  
	  public final native String getTitle()/*-{return this.title}-*/;
	  public final native String getContent()/*-{return this.content}-*/;
	  public final native JsArray<AssetJso> getAttachments()/*-{return this.attachments}-*/;
}
