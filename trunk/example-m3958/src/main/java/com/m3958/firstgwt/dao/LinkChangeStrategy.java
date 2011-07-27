package com.m3958.firstgwt.dao;

import java.util.List;

import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.model.Link;
import com.m3958.firstgwt.server.types.Error;


public class LinkChangeStrategy  extends TreeChangeStrategy implements ModelChangeStrategy<Link> {
	
	
	@Override
	public boolean extraPersistTask(Link model) {
		return checkDuplication(model);
	}
	
	private boolean checkDuplication(Link model) {
		if(model.getParent() == null){
			LinkDao ldao = injector.getInstance(LinkDao.class);
			List<Link> ss = ldao.getTopLinks(getReqPs().getIdValue(CommonField.SITE_ID.getEname()));
			for(Link s : ss){
				if(s.getName().equals(model.getName())){
					getErrors().addError(new Error("同级目录不能重名！",  ServerErrorCode.DUPLICATE_RECORD));
					return false;
				}
			}
		}else{
			int i=0;
			for(Link s :model.getParent().getChildren()){
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
	public boolean extraUpdateTask(Link model,Link newModel) {
		return true;
	}
	


	@Override
	public boolean extraRemoveTask(Link model) {
		return true;
	}
	

	@Override
	public boolean afterPersist(Link model) {
		return false;
	}
}
