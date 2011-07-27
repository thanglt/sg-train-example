package com.m3958.firstgwt.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.model.HtmlCss;
import com.m3958.firstgwt.server.types.Error;

public class HtmlCssChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<HtmlCss> {
	
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;

	@Override
	public boolean extraPersistTask(HtmlCss model){
		if(!getSu().isSuperman()){
			model.setAudit(false);
		}
		return checkDuplication(model);
	}
	
	private boolean checkDuplication(HtmlCss model) {
		if(model.getContentVersion() == null)model.setContentVersion("");
		Query q = emp.get().createNamedQuery(HtmlCss.NamedQueries.FIND_UNIQUE);
		q.setParameter("name", model.getName());
		q.setParameter("contentType", model.getContentType());
		q.setParameter("contentVersion", model.getContentVersion());

		HtmlCss t = null;
		try {
			t = (HtmlCss) q.getSingleResult();
		} catch (Exception e) {}
		if(t != null){
			getErrors().addError(new Error("条目已经有啦！再加上去就重複了！",  ServerErrorCode.DUPLICATE_RECORD));
			return false;
		}
		return true;
	}

	@Override
	public boolean extraRemoveTask(HtmlCss model) {
		return true;
		
	}

	@Override
	public boolean extraUpdateTask(HtmlCss oldModel,HtmlCss newModel){
		if(oldModel.isAudit() != newModel.isAudit() && !getSu().isSuperman()){
			newModel.setAudit(oldModel.isAudit());
		}
		return true;
	}


	@Override
	public boolean afterPersist(HtmlCss model) {
		return false;
	}
	

}
