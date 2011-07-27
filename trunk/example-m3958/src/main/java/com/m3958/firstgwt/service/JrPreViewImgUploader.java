package com.m3958.firstgwt.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.UploadFormField;
import com.m3958.firstgwt.dao.JrxmlFileDao;
import com.m3958.firstgwt.model.JrxmlFile;
import com.wideplay.warp.persist.Transactional;

public class JrPreViewImgUploader extends FileUploader{
	
	@Inject
	public JrPreViewImgUploader(HttpServletRequest req, HttpServletResponse res) {
		super(req, res);
	}

	@Override
	@Transactional
	protected void afterUpload() {
		String relationModelId = params.get(UploadFormField.RELATION_MODEL_ID.toString());
		int jrxmlFileId = Integer.parseInt(relationModelId);
		JrxmlFileDao dao = injector.getInstance(JrxmlFileDao.class);
		JrxmlFile jrxmlFile = dao.find(jrxmlFileId);
		jrxmlFile.setPreviewImg(assets.get(0));
		jrxmlFile.addAttachment(assets.get(0));
		dao.merge(jrxmlFile);
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
