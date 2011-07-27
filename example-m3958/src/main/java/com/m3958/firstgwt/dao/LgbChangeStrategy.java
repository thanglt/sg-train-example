package com.m3958.firstgwt.dao;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.layout.AttachmentBox;
import com.m3958.firstgwt.client.layout.TitleImgBox;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.Lgb;
import com.m3958.firstgwt.service.MemcachedService;


public class LgbChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<Lgb> {
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	@Inject
	private MemcachedService memched;

	@Override
	public boolean extraPersistTask(Lgb model) {
//		clearCache(model.getDepartment());
		attachAttachments(model);
		return true;
	}

	@Override
	public boolean extraUpdateTask(Lgb model,Lgb newModel) {
//		clearCache(model.getDepartment());
		model.setTitleImg(null);
		model.setAttachments(new ArrayList<Asset>());
		attachAttachments(model);
		return true;
	}
	
	@Override
	public boolean extraRemoveTask(Lgb model) {
//		clearCache(model.getDepartment());
		return true;
	}
	
//	private boolean clearCache(Department department){
//		Department d = department;
//		while( d!= null){
//			String key = "," + d.getId() + ",";
//			memched.clear(key);
//			d = d.getParent();
//		}
//		return true;
//	}


	@Override
	public boolean afterPersist(Lgb model) {
		return false;
	}
	
	private void attachAttachments(Lgb model) {
		String at = getReqPs().getStringValue(SmartParameterName.ATTACHMENT_JSON);
		if(at == null)return;
		JSONArray ja = JSONArray.fromObject(at);
		JSONArray jja;
		Integer oid;
		Asset a;
		for(int i = 0;i < ja.size();i++){
			JSONObject jo = (JSONObject) ja.get(i);
			
			if(jo.containsKey(TitleImgBox.class.getName())){
				jja = jo.getJSONArray(TitleImgBox.class.getName());
				if(jja.size() > 0){
					oid = jja.getJSONObject(0).getInt(CommonField.ID.getEname());
					a = emp.get().find(Asset.class, oid);
					model.setTitleImg(a);
				}
			}
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
