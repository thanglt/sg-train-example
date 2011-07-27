package com.m3958.firstgwt.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.m3958.firstgwt.client.types.ViewNameEnum;

public class LgbRelationGridEvent extends GwtEvent<LgbRelationEventHandler> {
	public static Type<LgbRelationEventHandler> TYPE = new Type<LgbRelationEventHandler>();
	
	public static enum EvType{
		ACTIVE,SELECT_CHANGE
	}
	
	private ViewNameEnum nextView;
	
	private EvType evType;
	
	private String[] paras;
	
	public LgbRelationGridEvent(ViewNameEnum nextView,EvType evType,String...paras){
		this.setNextView(nextView);
		this.setEvType(evType);
		this.setParas(paras);
	}
  
	@Override
	public Type<LgbRelationEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LgbRelationEventHandler handler) {
		handler.onLgbGridEvent(this);
	}

	public void setEvType(EvType evType) {
		this.evType = evType;
	}

	public EvType getEvType() {
		return evType;
	}

	public void setParas(String[] paras) {
		this.paras = paras;
	}

	public String[] getParas() {
		return paras;
	}

	public void setNextView(ViewNameEnum nextView) {
		this.nextView = nextView;
	}

	public ViewNameEnum getNextView() {
		return nextView;
	}

}
