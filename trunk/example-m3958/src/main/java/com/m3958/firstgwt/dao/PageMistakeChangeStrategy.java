package com.m3958.firstgwt.dao;

import com.m3958.firstgwt.model.PageMistake;
import com.m3958.firstgwt.model.WebSite;

public class PageMistakeChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<PageMistake> {

	@Override
	public boolean extraPersistTask(PageMistake model){//初始状态下以appdomian结尾的必须是通过审核的
		setInitSite(model);
		return true;
	}

	private boolean setInitSite(PageMistake model) {
		int sid = getReqPs().getRelationModelId();
		WebSite ws = emp.get().find(WebSite.class, sid);
		model.setWebSite(ws);
		return true;
	}


	@Override
	public boolean extraRemoveTask(PageMistake model) {
		return true;
	}

	@Override
	public boolean extraUpdateTask(PageMistake model,PageMistake newModel){
		return true;
	}

	@Override
	public boolean afterPersist(PageMistake model) {
		return false;
	}
}
