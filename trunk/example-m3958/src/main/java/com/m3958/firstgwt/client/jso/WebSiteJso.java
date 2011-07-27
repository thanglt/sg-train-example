package com.m3958.firstgwt.client.jso;



public class WebSiteJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected WebSiteJso() { }
	  
	  public final native String getLogoUrl()/*-{return this.logoUrl}-*/;
	  
	  public final native String getDisplayAuthors()/*-{return this.displayAuthors}-*/;
	  public final native String getArticleFlags()/*-{return this.articleFlags}-*/;
	  public final native String getCname()/*-{return this.cname}-*/;
	  public final native String getArticleSources()/*-{return this.articleSources}-*/;
	  
	  public final native String isSearchFriendUrl()/*-{return this.searchFriendUrl}-*/;
	  
	  public final native String getImgSizes()/*-{return this.imgSizes ? this.imgSizes : "48x48"}-*/;
	  
	  public final native String getRichEditorCssPath()/*-{return this.richEditorCssPath ? this.richEditorCssPath : ""}-*/;
	  
	  public final native boolean isSiteOwner()/*-{return this.siteOwner == null ? false : this.siteOwner}-*/;
	  
	  public final native boolean isSiteEditor()/*-{return this.siteEditor == null ? false : this.siteEditor}-*/;
}
