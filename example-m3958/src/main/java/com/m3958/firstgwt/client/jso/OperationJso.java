package com.m3958.firstgwt.client.jso;


public class OperationJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected OperationJso() { }
	  
	  public final native String getName()/*-{return this.name}-*/;
	  public final native String getOpCode()/*-{return this.opCode}-*/;

}
