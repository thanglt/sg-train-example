package com.m3958.firstgwt.dao;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.m3958.firstgwt.accesschecker.XinJianChecker;
import com.m3958.firstgwt.client.layout.AttachmentBox;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.CacheBreakerItem;
import com.m3958.firstgwt.model.CacheBreakerItem.ObAction;
import com.m3958.firstgwt.model.CacheBreakerItem.ObType;
import com.m3958.firstgwt.model.Section;
import com.m3958.firstgwt.model.XinJian;
import com.m3958.firstgwt.model.XinJianCat;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.session.ErrorMessages;


public class XinJianChangeStrategy  extends BaseModelChangeStrategy implements ModelChangeStrategy<XinJian> {

	@Override
	public boolean extraPersistTask(XinJian model) {
		Section s = emp.get().find(Section.class, getReqPs().getRelationModelId());
//		model.setRepliedAt(afterAddHourMinutes(model.getRepliedAt()));
		if(s!=null){
			XinJianChecker ac = injector.getInstance(XinJianChecker.class);
			if(!ac.hasTreeContainerPerms(Section.class.getName(), s.getId(), ObjectOperation.AUDIT_CONTENT)){
				model.setAudit(false);
			}
		}else{
			model.setAudit(false);
		}
		return true;
	}

	private boolean setInitCat(XinJian model) {
		if(getReqPs() != null && getReqPs().getRelationModelId() != SmartConstants.NONE_EXIST_MODEL_ID){
			XinJianCat s = emp.get().find(XinJianCat.class, getReqPs().getRelationModelId());
			s.manageRelation(model, true, "", injector.getInstance(ErrorMessages.class));
			model.getXjCats().add(s);
			model.setSiteId(s.getSiteId());
			return true;
		}else{
			return false;
		}
	}


	@Override
	public boolean extraUpdateTask(XinJian oldModel,XinJian newModel) {
		if(newModel.getRepliedAt() == null)newModel.setRepliedAt(new Date());
		newModel.setRepliedAt(afterAddHourMinutes(newModel.getRepliedAt()));
		attachAttachments(oldModel);
		addCacheBreakerItem(getCbi(oldModel, ObAction.UPDATE));
		if(oldModel.isAudit() != newModel.isAudit() && !canAudit(oldModel,newModel)){
			newModel.setAudit(oldModel.isAudit());
		}
		return true;
	}
	
	private boolean canAudit(XinJian oldModel,XinJian newModel) {
		if(getRso().isSiteEditor(oldModel.getSiteId()) || getRso().isSiteOwner(oldModel.getSiteId()))return true;
		XinJianChecker ac = injector.getInstance(XinJianChecker.class);
		for(XinJianCat s : oldModel.getXjCats()){
			if(ac.hasTreeContainerPerms(XinJianCat.class.getName(), s.getId(), ObjectOperation.AUDIT_CONTENT)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean extraRemoveTask(XinJian model) {
		addCacheBreakerItem(getCbi(model, ObAction.DELETE));
		return true;
	}

	@Override
	public boolean afterPersist(XinJian model) {
		setInitCat(model);
		attachAttachments(model);
		addCacheBreakerItem(getCbi(model, ObAction.ADD));
		return false;
	}
	
	private CacheBreakerItem getCbi(XinJian article,ObAction action){
		CacheBreakerItem cbi = new CacheBreakerItem();
		cbi.setAction(action);
		cbi.setCreatedAt(new Date());
		cbi.setDone(false);
		cbi.setObId(article.getId());
		cbi.setObName("");
		cbi.setObType(ObType.ARTICLE);
		return cbi;
	}
	
	private void attachAttachments(XinJian model) {
		model.getAttachments().clear();
		String at = getReqPs().getStringValue(SmartParameterName.ATTACHMENT_JSON);
		if(at == null)return;
		JSONArray ja = JSONArray.fromObject(at);
		JSONArray jja;
		Integer oid;
		Asset a;
		for(int i = 0;i < ja.size();i++){
			JSONObject jo = (JSONObject) ja.get(i);
			if(jo.containsKey(AttachmentBox.class.getName())){
				jja = jo.getJSONArray(AttachmentBox.class.getName());
				for(int j = 0;j<jja.size();j++){
					oid = jja.getJSONObject(j).getInt(CommonField.ID.getEname());
					a = emp.get().find(Asset.class, oid);
					model.addAttachment(a);
				}
			}
		}
	}
	
}
