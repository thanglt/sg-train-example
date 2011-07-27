package com.m3958.firstgwt.dao;

import java.util.Date;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.model.CacheBreakerItem;
import com.m3958.firstgwt.model.CacheBreakerItem.ObAction;
import com.m3958.firstgwt.model.CacheBreakerItem.ObType;
import com.m3958.firstgwt.model.XinJianCat;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.service.SiteConfigService;
import com.m3958.firstgwt.session.SessionUser;

public class XinJianCatChangeStrategy extends TreeChangeStrategy implements ModelChangeStrategy<XinJianCat> {
	
	@Inject
	SessionUser su;
	
	@Inject
	protected SiteConfigService scs;

	@Override
	public boolean extraPersistTask(XinJianCat model){
		if(model.getPerpage() == 0)model.setPerpage(getPerpageFromSite());
		return true;
	}


	@Override
	public boolean extraRemoveTask(XinJianCat model) {
		if(model.getChildren().size() > 0){
			getErrors().addError(new Error("请先删除子目录！", ServerErrorCode.DEPENDENCY_ERROR));
			return false;
		}
		addCacheBreakerItem(getCbi(model, ObAction.DELETE));
		return true;
	}
	
	private CacheBreakerItem getCbi(XinJianCat xinJianCat,ObAction action){
		CacheBreakerItem cbi = new CacheBreakerItem();
		cbi.setAction(action);
		cbi.setCreatedAt(new Date());
		cbi.setDone(false);
		cbi.setObId(xinJianCat.getId());
		cbi.setObName("");
		cbi.setObType(ObType.XJ_CAT);
		return cbi;
	}
	
	
	@Override
	public boolean extraUpdateTask(XinJianCat model,XinJianCat newModel){
		addCacheBreakerItem(getCbi(model, ObAction.UPDATE));
		return true;
	}

	@Override
	public boolean afterPersist(XinJianCat model) {
		createPerRoleAsignToUser(model);
		addCacheBreakerItem(getCbi(model, ObAction.ADD));
		return true;
	}
}
