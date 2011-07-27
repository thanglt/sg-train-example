package com.m3958.firstgwt.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.UploadFormField;
import com.m3958.firstgwt.dao.AssetFolderDao;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.AssetFolder;
import com.m3958.firstgwt.session.ErrorMessages;

public class UploaderInFolder extends FileUploader{
	
	@Inject
	public UploaderInFolder(HttpServletRequest req, HttpServletResponse res) {
		super(req, res);
	}

	@Override
	protected void afterUpload() {
		String relationModelId = params.get(UploadFormField.RELATION_MODEL_ID.toString());
		try {
			int fid = Integer.parseInt(relationModelId);
			AssetFolderDao dao = injector.getInstance(AssetFolderDao.class);
			AssetFolder fo = dao.find(fid);
			for(Asset a : assets){
				fo.manageRelation(a, true, "", injector.getInstance(ErrorMessages.class));
				
			}
			dao.merge(fo);
		} catch (NumberFormatException e) {
			
		}
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
