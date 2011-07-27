package com.m3958.firstgwt.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.ViewNameEnum;

public class StripEvent extends GwtEvent<StripEventHandler> {
	
	public static Type<StripEventHandler> TYPE = new Type<StripEventHandler>();
	
	private ViewNameEnum vname;
	
	private Btname sourceBt;
	
	
	public StripEvent(ViewNameEnum vname,Btname sourceBt){
		this.vname = vname;
		this.setSourceBt(sourceBt);
	}
  
	public ViewNameEnum getVname() {
		return vname;
	}



	public void setVname(ViewNameEnum vname) {
		this.vname = vname;
	}


	@Override
	public Type<StripEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(StripEventHandler handler) {
		handler.onViewEvent(this);
	}

	public void setSourceBt(Btname sourceBt) {
		this.sourceBt = sourceBt;
	}

	public Btname getSourceBt() {
		return sourceBt;
	}
}
