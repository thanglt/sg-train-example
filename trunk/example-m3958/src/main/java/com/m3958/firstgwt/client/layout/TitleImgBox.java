package com.m3958.firstgwt.client.layout;

import com.google.gwt.core.client.JsArray;
import com.m3958.firstgwt.client.jso.AssetJso;
import com.smartgwt.client.widgets.Label;

public abstract class TitleImgBox extends CattachmentWithControl{
	
	
	public TitleImgBox(CattachmentContainer cc) {
		super(cc);
	}

	@Override
	protected String getBoxType() {
		return TitleImgBox.class.getName();
	}

	@Override
	protected Label getBoxLabel() {
		Label l = new Label("标题图:");
		l.setHeight(20);
		return l;
	}
	
	
	@Override
	public void addCattachment(AssetJso aj){
		cc.clearCattachments();
		if(aj != null){
			cc.addCattachment(aj);
		}
	}
	
	@Override
	public void addCattachments(JsArray<AssetJso> ajs){
		if(ajs.length() > 0)
			addCattachment(ajs.get(0));
	}

}
