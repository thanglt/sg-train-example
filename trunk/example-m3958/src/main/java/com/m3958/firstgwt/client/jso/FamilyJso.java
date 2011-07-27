package com.m3958.firstgwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;


public class FamilyJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected FamilyJso() { }
	  
	  public final native String getXm()/*-{return this.xm}-*/;
	  public final native String getGx()/*-{return this.gx}-*/;
	  public final native String getXb()/*-{return this.xb}-*/;
	  public final native JavaScriptObject getBirthday()/*-{return this.birthday}-*/;
	  
	  
	  public final native String getZzmm()/*-{return this.zzmm}-*/;
	  
	  public final native String getJkzk()/*-{return this.jkzk}-*/;
	  public final native String getDizhi()/*-{return this.dizhi}-*/;
	  public final native String getDianhua()/*-{return this.dianhua}-*/;
	  public final native String getShouji()/*-{return this.shouji}-*/;
	  public final native String getBz()/*-{return this.bz}-*/;

}
