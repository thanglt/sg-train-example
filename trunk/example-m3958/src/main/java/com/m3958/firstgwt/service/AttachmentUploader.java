package com.m3958.firstgwt.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.UploadFormField;
import com.m3958.firstgwt.dao.BaseDao;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.server.types.HasAttachments;
import com.wideplay.warp.persist.Transactional;

public class AttachmentUploader extends FileUploader{
	
	@Inject
	public AttachmentUploader(HttpServletRequest req, HttpServletResponse res) {
		super(req, res);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	protected void afterUpload() {
//		String relationModelId = params.get(UploadFormField.RELATION_MODEL_ID.toString());
//		int id = Integer.parseInt(relationModelId);
//		String relationModelName = params.get(UploadFormField.RELATION_MODEL_NAME.toString());
//		
//		BaseDao<BaseModel> dao = modelConfig.getDaoInstance(relationModelName);
//		BaseModel model = dao.find(id);
//		for(Asset a:assets){
//			((HasAttachments)model).addAttachment(a);
//		}
//		dao.merge(model);
	}

	@Override
	protected JSONObject getOthersJsonObject() {
		return null;
	}

	@Override
	protected String getHtmlResponse() {
		return null;
	}

	@Override
	protected boolean isExceedMaxSize() {
		return false;
	}


}
