package com.m3958.firstgwt.dao;

import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ProtectLevel;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.model.CacheBreakerItem;
import com.m3958.firstgwt.model.Section;
import com.m3958.firstgwt.model.CacheBreakerItem.ObAction;
import com.m3958.firstgwt.model.CacheBreakerItem.ObType;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.service.SiteConfigService;

public class SectionChangeStrategy extends TreeChangeStrategy implements ModelChangeStrategy<Section> {
	
	@Inject
	protected SiteConfigService scs;

	@Override
	public boolean extraPersistTask(Section model){
		if(model.getPerpage() == 0)model.setPerpage(getPerpageFromSite());
		if(model.getProtectLevel() == null || model.getProtectLevel().isEmpty())model.setProtectLevel(ProtectLevel.PERMISSION);
		return checkDuplication(model);
	}


	private boolean checkDuplication(Section model) {
		if(model.getParent() == null){
			SectionDao sdao = injector.getInstance(SectionDao.class);
			List<Section> ss = sdao.getTopSections(getReqPs().getIdValue(CommonField.SITE_ID.getEname()));
			for(Section s : ss){
				if(s.getName().equals(model.getName())){
					getErrors().addError(new Error("同级目录不能重名！", ServerErrorCode.DUPLICATE_RECORD));
					return false;
				}
			}
		}else{
			int i=0;
			for(Section s :model.getParent().getChildren()){
				if(s.getName().equals(model.getName())){
					i++;
				}
			}
			if(i>1){
				getErrors().addError(new Error("同级目录不能重名！", ServerErrorCode.DUPLICATE_RECORD));
				model.getParent().removeChildren(model);
				return false;
			}
		}
		return true;
		
	}


	@Override
	public boolean extraRemoveTask(Section model) {
		if(model.getChildren().size() > 0){
			getErrors().addError(new Error("请先删除子目录！", ServerErrorCode.DEPENDENCY_ERROR));
			return false;
		}
		addCacheBreakerItem(getCbi(model, ObAction.DELETE));
		return true;
	}
	
	private CacheBreakerItem getCbi(Section section,ObAction action){
		CacheBreakerItem cbi = new CacheBreakerItem();
		cbi.setAction(action);
		cbi.setCreatedAt(new Date());
		cbi.setDone(false);
		cbi.setObId(section.getId());
		cbi.setObName("");
		cbi.setObType(ObType.SECTION);
		return cbi;
	}
	
	
	@Override
	public boolean extraUpdateTask(Section model,Section newModel){
		addCacheBreakerItem(getCbi(model, ObAction.UPDATE));
		return true;
	}

	@Override
	public boolean afterPersist(Section model) {
		createPerRoleAsignToUser(model);
		addCacheBreakerItem(getCbi(model, ObAction.ADD));
		return true;
	}
}
