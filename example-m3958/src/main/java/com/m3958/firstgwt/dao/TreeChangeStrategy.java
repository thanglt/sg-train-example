package com.m3958.firstgwt.dao;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.types.HasObjectPermission;
import com.m3958.firstgwt.server.types.TreeModel;
import com.m3958.firstgwt.service.ModelAndDao;
import com.m3958.firstgwt.session.SessionUser;

public class TreeChangeStrategy extends BaseModelChangeStrategy{
	

	@Inject
	SessionUser su;
	
	@Inject
	ModelAndDao mc;
	
	protected int getPerpageFromSite(){
		int siteid = getReqPs().getIdValue(CommonField.SITE_ID.getEname());
		if(siteid != SmartConstants.NONE_EXIST_MODEL_ID){
			WebSite ws = emp.get().find(WebSite.class, siteid);
			if(ws.getPerpage() > 0)return ws.getPerpage();
		}
		return 20;
	}
	
	@Override
	protected void createPerRoleAsignToUser(BaseModel bm){
		TreeModel t = (TreeModel) bm;
		HasObjectPermission hp = (HasObjectPermission) t;
		//folde如果是顶级目录，则必须产生对象permissions，同时将这些permissions赋值给role，这个role同时保存。
		if(t.getParent() ==  null){
			hp.createObjectPermissions();//hp已经persiste过了,但是当时role还没有加上去.
			hp.createObjectRole(ObjectRoleName.OWNER);
			hp.createObjectRole(ObjectRoleName.CONTENT_READER);
			UserDao udao = injector.getInstance(UserDao.class);
			User cu = udao.find(getSu().getUserId());
			RoleDao rdao = injector.getInstance(RoleDao.class);
			for(Role r:hp.getObjectRoles()){
				r.setCreator(cu);
				rdao.smartPersistBaseModel(r);
				cu.addRole(r);
			}
			udao.merge(cu);
		}
	}
}
