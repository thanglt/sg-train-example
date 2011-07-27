package com.m3958.firstgwt.dao;

import com.m3958.firstgwt.model.WebHost;
import com.m3958.firstgwt.model.WebSite;

public class WebHostChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<WebHost> {

	@Override
	public boolean extraPersistTask(WebHost model){//初始状态下以appdomian结尾的必须是通过审核的
		if(!model.getName().endsWith(getScs().getAppDomain())){
			if(!getSu().isSuperman()){
				model.setAudit(false);
			}
		}else{
			model.setAudit(true);
		}
		setInitSite(model);
		return true;
	}

	private boolean setInitSite(WebHost model) {
		int sid = getReqPs().getRelationModelId();
		WebSite ws = emp.get().find(WebSite.class, sid);
		model.setWebSite(ws);
		return true;
	}


	@Override
	public boolean extraRemoveTask(WebHost model) {
		
		return true;
	}

	@Override
	public boolean extraUpdateTask(WebHost model,WebHost newModel){
		//更新的情况下，不能修改审核状态
//		if(!newModel.getName().equals(model.getName())){
//			if(!newModel.getName().endsWith(getScs().getAppDomain())){
//				if(!getSu().isSuperman()){
//					newModel.setAudit(false);
//				}else{
//					//没关系，是超级用户，知道自己在做什么
//				}
//			}else{
//				//没关系，以m3958结尾。
//			}
//		}else{//相等的情况下，不改变。
//			if(!getSu().isSuperman())
//				newModel.setAudit(model.isAudit());
//		}
		if(!getSu().isSuperman()){
			newModel.setAudit(model.isAudit());
		}
		return true;
	}

	@Override
	public boolean afterPersist(WebHost model) {
		return false;
	}
}
