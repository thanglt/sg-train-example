package com.m3958.firstgwt.dao;

import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.model.Vote;
import com.m3958.firstgwt.model.VoteHit;

public class VoteHitChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<VoteHit> {

	@Override
	public boolean extraPersistTask(VoteHit model){//初始状态下以appdomian结尾的必须是通过审核的
		setVote(model);
		return true;
	}



	private void setVote(VoteHit model) {
		if(getReqPs().getStringValue("_vote_has_processed") == null){
			Vote v = emp.get().find(Vote.class, getReqPs().getIdValue(SmartParameterName.RELATION_MODEL_ID));
			v.addVoteHits(model);
		}
	}



	@Override
	public boolean extraRemoveTask(VoteHit model) {
		
		return true;
	}

	@Override
	public boolean extraUpdateTask(VoteHit model,VoteHit newModel){
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
//		if(!getSu().isSuperman()){
//			newModel.setAudit(model.isAudit());
//		}
		return true;
	}

	@Override
	public boolean afterPersist(VoteHit model) {
		return false;
	}
}
