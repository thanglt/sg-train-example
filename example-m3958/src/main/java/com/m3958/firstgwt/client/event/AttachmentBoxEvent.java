package com.m3958.firstgwt.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.m3958.firstgwt.client.jso.AssetJso;
import com.m3958.firstgwt.client.types.ViewNameEnum;

public class AttachmentBoxEvent extends GwtEvent<AttachmentBoxEventHandler> {
	
	public static enum AttachmentBoxEventType{
		INSERT_IMG,INSERT_LINK
	}
	
	public static Type<AttachmentBoxEventHandler> TYPE = new Type<AttachmentBoxEventHandler>();
	
	private List<AssetJso> assetJsos;
	
	private ViewNameEnum srcViewName;
	
	private String hint;
	
	private AttachmentBoxEventType atype;
	
	public AttachmentBoxEvent(ViewNameEnum srcViewName,AttachmentBoxEventType atype,List<AssetJso> assetJsos,String hint){
		this.srcViewName = srcViewName;
		this.setAssetJsos(assetJsos);
		this.atype = atype;
		this.hint = hint;
	}
  


	@Override
	public Type<AttachmentBoxEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AttachmentBoxEventHandler handler) {
		handler.onAttachmentsEvent(this);
	}


	public ViewNameEnum getSrcViewName() {
		return srcViewName;
	}



	public void setSrcViewName(ViewNameEnum srcViewName) {
		this.srcViewName = srcViewName;
	}



	public String getHint() {
		return hint;
	}



	public void setHint(String hint) {
		this.hint = hint;
	}



	public AttachmentBoxEventType getAtype() {
		return atype;
	}



	public void setAtype(AttachmentBoxEventType atype) {
		this.atype = atype;
	}



	public void setAssetJsos(List<AssetJso> assetJsos) {
		this.assetJsos = assetJsos;
	}



	public List<AssetJso> getAssetJsos() {
		return assetJsos;
	}
}
