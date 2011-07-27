package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.types.ViewNameEnum;

public class XinJianAttachmentBox extends AttachmentBox{
	
	@Inject
	public XinJianAttachmentBox(CattachmentContainer cc) {
		super(cc);
	}

	@Override
	protected ViewNameEnum getSrctViewName() {
		return ViewNameEnum.XINJIAN_EDIT;
	}
}
