package com.m3958.firstgwt.client.jso;

import com.google.gwt.core.client.JsArray;


public class ArticleJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected ArticleJso() { }
	  
	  public final native String getTitle()/*-{return this.title}-*/;
	  public final native String getExcerpt()/*-{return this.excerpt}-*/;
	  public final native String getContent()/*-{return this.content}-*/;
	  public final native String getFlag()/*-{return this.flag}-*/;
	  public final native AssetJso getTitleImg()/*-{return this.titleImg}-*/;
	  public final native JsArray<AssetJso> getContentImgs()/*-{return this.contentImgs}-*/;
	  public final native JsArray<AssetJso> getAttachments()/*-{return this.attachments}-*/;
}
