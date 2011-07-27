package com.m3958.firstgwt.dao;

import java.util.List;

import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.model.Vote;
import com.m3958.firstgwt.server.types.Error;


public class VoteChangeStrategy  extends TreeChangeStrategy implements ModelChangeStrategy<Vote> {
	
	
	@Override
	public boolean extraPersistTask(Vote model) {
		if(model.getMinSelect() == 0){
			model.setMinSelect(1);
		}
		if(model.getMaxSelect() == 0){
			model.setMaxSelect(1);
		}
		if(model.getMinSelect() > model.getMaxSelect()){
			int max = model.getMinSelect();
			model.setMinSelect(model.getMaxSelect());
			model.setMaxSelect(max);
		}
		return checkDuplication(model);
	}
	
	private boolean checkDuplication(Vote model) {
		if(model.getParent() == null){
			VoteDao vdao = injector.getInstance(VoteDao.class);
			List<Vote> ss = vdao.getTopVotes(getReqPs().getIdValue(CommonField.SITE_ID.getEname()));
			for(Vote s : ss){
				if(s.getName().equals(model.getName())){
					getErrors().addError(new Error("同级目录不能重名！", ServerErrorCode.DUPLICATE_RECORD));
					return false;
				}
			}
		}else{
			int i=0;
			for(Vote s :model.getParent().getChildren()){
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
	public boolean extraUpdateTask(Vote model,Vote newModel) {
		if(model.getCreator() == null && getSu().getLoginStatus()){
			User u = emp.get().find(User.class, getSu().getUserId());
			model.setCreator(u);
		}
		return true;
	}
	


	@Override
	public boolean extraRemoveTask(Vote model) {
		return true;
	}
	

	@Override
	public boolean afterPersist(Vote model) {
		return false;
	}
}
