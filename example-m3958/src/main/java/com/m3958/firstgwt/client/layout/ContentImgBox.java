package com.m3958.firstgwt.client.layout;

import com.m3958.firstgwt.client.AppStatusService;
import com.smartgwt.client.widgets.Label;


public abstract class ContentImgBox extends CattachmentWithControl{
	
	
	public ContentImgBox(CattachmentContainer cc) {
		super(cc);
	}


	@Override
	protected Label getBoxLabel() {
		Label l = new Label("内嵌图:");
		l.setHeight(20);
		return l;
	}


	@Override
	protected String getBoxType() {
		return ContentImgBox.class.getName();
	}
}
