package com.m3958.firstgwt.client.jso;


public class AddressJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected AddressJso() { }
	  
	  public final native String getDizhi()/*-{return this.dizhi}-*/;
	  public final native boolean isMainAddress()/*-{return this.mainAddress ? this.mainAddress : false}-*/;
	  public final native String getDianhua()/*-{return this.dianhua}-*/;
	  public final native String getShouji()/*-{return this.shouji}-*/;
	  public final native String getBz()/*-{return this.bz}-*/;
	  public final native ShequJso getShequ()/*-{return this.shequ}-*/;
}
