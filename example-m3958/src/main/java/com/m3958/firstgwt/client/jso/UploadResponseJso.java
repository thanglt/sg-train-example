package com.m3958.firstgwt.client.jso;


import com.google.gwt.core.client.JavaScriptObject;


public class UploadResponseJso extends JavaScriptObject {
	  // Overlay types always have protected, zero-arg ctors
	  protected UploadResponseJso() { }
	  
	  public final native int getStatus()/*-{return this.STATUS}-*/;
	  
	  public final native JavaScriptObject getAssets()/*-{return this.DATA}-*/;
	  
	  public final native String getRelationModelName()/*-{return this.OTHERS.RELATIONMODELNAME}-*/;
	  public final native String getRelationModelId()/*-{return this.OTHERS.RELATIONMODELID}-*/;
	  public final native String getUploadFor()/*-{return this.OTHERS.UPLOADFOR}-*/;
	  public final native String getOperationType()/*-{return this.OTHERS.OPERATIONTYPE}-*/;
	  public final native String getErrorMsg()/*-{return this.OTHERS.ERRORMSG}-*/;
	  public final native String getMsg()/*-{return this.OTHERS.MSG}-*/;
	  public final native String getAskerTimeStamp()/*-{return this.OTHERS.ASKER_TIMESTAMP}-*/;
	  public final native String getFuTimeStamp()/*-{return this.OTHERS.FU_TIMESTAMP}-*/;
	  public final native String getViewName()/*-{return this.OTHERS.VIEWNAME}-*/;
	  
}
