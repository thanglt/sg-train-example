package com.m3958.firstgwt.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.UploadFormField;
import com.m3958.firstgwt.dao.BaseDao;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.server.types.HasAttachments;
import com.m3958.firstgwt.server.types.IhasContentImg;


public class ContentImgUploader extends FileUploader{
	
	
	@Inject
	public ContentImgUploader(HttpServletRequest req, HttpServletResponse res) {
		super(req, res);
	}
	
	@Inject
	private ModelAndDao modelAndDao;

	@SuppressWarnings("unchecked")
	@Override
	protected void afterUpload() {
//		String relationModelName = params.get(UploadFormField.RELATION_MODEL_NAME.toString());
//		String relationModelId = params.get(UploadFormField.RELATION_MODEL_ID.toString());
//		try {
//			int id = Integer.parseInt(relationModelId);
//			BaseDao<BaseModel> dao = modelAndDao.getDaoInstance(relationModelName);
//			BaseModel bm = dao.find(id);
//			IhasContentImg hasConentImg = (IhasContentImg) bm;
//			hasConentImg.addContentImg(assets.get(0));
//			((HasAttachments)bm).addAttachment(assets.get(0));
//			dao.merge(bm);
//		} catch (NumberFormatException e) {
//			
//		}
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
		return req.getContentLength() > 800 * 1024L;
	}


}
