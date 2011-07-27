package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.AppStatusService;
import com.smartgwt.client.widgets.Label;


public abstract class AttachmentBox extends CattachmentWithControl{

	
	@Inject
	public AttachmentBox(CattachmentContainer cc) {
		super(cc);
	}

	@Override
	protected String getBoxType() {
		return AttachmentBox.class.getName();
	}

	@Override
	protected Label getBoxLabel() {
		Label l = new Label("普通附件:");
		l.setHeight(20);
		return l;
	}

	
}
