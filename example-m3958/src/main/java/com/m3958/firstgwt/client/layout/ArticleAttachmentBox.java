package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.types.ViewNameEnum;

public class ArticleAttachmentBox extends AttachmentBox{
	
	@Inject
	public ArticleAttachmentBox(CattachmentContainer cc) {
		super(cc);
	}

	@Override
	protected ViewNameEnum getSrctViewName() {
		return ViewNameEnum.ARTICLE_EDIT;
	}

}
