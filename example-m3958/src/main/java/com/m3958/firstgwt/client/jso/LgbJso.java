package com.m3958.firstgwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;



public class LgbJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected LgbJso() { }
	  
	  /*record返回的是string，int可能出错。*/
	  public final native int getPaixu()/*-{return (this.paixu ? this.paixu : 0)}-*/;
	  public final native JavaScriptObject getShequ()/*-{return this.shequ}-*/;
	  public final native String getDepartmentIds()/*-{return this.departmentIds}-*/;
	  public final native String getXm()/*-{return this.xm}-*/;
	  public final native String getXb()/*-{return this.xb}-*/;
	  //注意，这里获取的值和浏览器有关。
	  public final native JavaScriptObject getSr()/*-{return this.sr}-*/;
	  public final native AssetJso getZp()/*-{return this.zp}-*/;
	  public final native String getSfz()/*-{return this.sfz}-*/;
	  public final native String getMinzu()/*-{return this.minzu}-*/;
	  public final native String getJg()/*-{return this.jg}-*/;
	  public final native String getCsd()/*-{return this.csd}-*/;
	  public final native String getJy()/*-{return this.jy}-*/;
	  public final native JavaScriptObject getRdsj()/*-{return this.rdsj}-*/;
	  public final native JavaScriptObject getRwsj()/*-{return this.rwsj}-*/;
	  public final native String getYgzdw()/*-{return this.ygzdw}-*/;
	  public final native String getYzw()/*-{return this.yzw}-*/;
	  public final native String getXzgdw()/*-{return this.xzgdw}-*/;
	  public final native String getDwxz()/*-{return this.dwxz}-*/;
	  public final native String getXzjb()/*-{return this.xzjb}-*/;
	  public final native String getXsdy()/*-{return this.xsdy}-*/;
	  public final native String getGblx()/*-{return this.gblx}-*/;
	  public final native String getHyzk()/*-{return this.hyzk}-*/;
	  public final native String getJjzk()/*-{return this.jjzk}-*/;
	  public final native String getJkzk()/*-{return this.jkzk}-*/;
	  public final native JavaScriptObject getLtxsj()/*-{return this.ltxsj}-*/;
	  public final native String getPogz()/*-{return this.pogz}-*/;
	  public final native String getHjszd()/*-{return this.hjszd}-*/;
	  public final native JavaScriptObject getSwsj()/*-{return this.swsj}-*/;
	  public final native boolean getPassAway()/*-{return this.passAway ? this.passAway : false}-*/;
	  public final native String getBz()/*-{return this.bz}-*/;
	  public final native String getSszb()/*-{return this.sszb}-*/;
	  public final native JsArray<AssetJso> getAttachments()/*-{return this.attachments}-*/;
}
