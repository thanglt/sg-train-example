package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.types.ViewNameEnum;

public class LgbTitleImgBox extends TitleImgBox{
	
	@Inject
	public LgbTitleImgBox(CattachmentContainer cc) {
		super(cc);
	}

	@Override
	protected ViewNameEnum getSrctViewName() {
		return ViewNameEnum.LGB_EDIT;
	}

}
