package com.m3958.firstgwt.client.jso;


public class HouseJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected HouseJso() { }
	  
	  public final native String getZfxz()/*-{return this.zfxz}-*/;
	  public final native float getArea()/*-{return this.area ? parseFloat(this.area) : 0}-*/;
	  public final native String getStructure()/*-{return this.structure}-*/;
	  public final native String getBz()/*-{return this.bz}-*/;

}
