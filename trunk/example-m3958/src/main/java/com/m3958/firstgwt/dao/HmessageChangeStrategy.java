package com.m3958.firstgwt.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.HmessageField;
import com.m3958.firstgwt.client.types.HmessageStatus;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.model.Group;
import com.m3958.firstgwt.model.Hmessage;
import com.m3958.firstgwt.model.HmessageReceiver;
import com.m3958.firstgwt.model.Token;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.server.types.Error;


public class HmessageChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<Hmessage> {
	
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	private Set<Integer> sended = new HashSet<Integer>();

	@Override
	public boolean extraPersistTask(Hmessage model){
		User sender = emp.get().find(User.class, getSu().getUserId());
		model.setSender(sender);
		String purpose = model.getPurpose(); 
		if(purpose != null && purpose.startsWith("inviteGroup,")){
			String m = model.getMessage() + "<br/><a class=\"m3958-confirm-token\" href=\"#\" id=\""  + createToken(purpose) +   "\">同意</a><br/>";
			model.setMessage(m);
		}
		return true;
	}
	
	private String createToken(String purpose){
		String[] ss = purpose.split(",");
		Token t = new Token();
		t.setCreatedAt(new Date());
		String uid = UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString(); 
		t.setTokenUuid(uid);
		t.setPurpose(ss[0]);
		t.setDetail(ss[1]);
		TokenDao tdao = injector.getInstance(TokenDao.class);
		tdao.smartPersistBaseModel(t);
		return t.getTokenUuid();
	}


	private boolean sendMessage(Hmessage hm) {
		
		String guids = getReqPs().getStringValue(SmartParameterName.GUIDS);
		JSONObject jguids = JSONObject.fromObject(guids);
		
		JSONArray gids = jguids.getJSONArray(SmartParameterName.JGIDS);
		
		for(int i =0; i< gids.size();i++){
			JSONObject jo = gids.getJSONObject(i); 
			int gid = jo.getInt(SmartParameterName.JGID);
			boolean ic = jo.getBoolean(SmartParameterName.JGINCLUDE);
			sendtoGroup(hm,gid,ic);
		}
		
		JSONArray uids = jguids.getJSONArray(SmartParameterName.JUIDS);
		
		for(int i=0;i<uids.size();i++){
			if(!sended.contains(uids.getInt(i))){
				User u = emp.get().find(User.class, uids.getInt(i));
				if(u != null){
					sendToUser(hm, u);
				}
			}
		}
		return true;
	}


	@SuppressWarnings("unchecked")
	private boolean sendtoGroup(Hmessage hm, int gid,boolean ic) {
		if(ic){
			String qs = "SELECT g FROM Group g WHERE g.parentIds LIKE %," + gid + ",%";
			Query q = emp.get().createQuery(qs);
			List<Group> gs = q.getResultList(); 
			for(Group g:gs){
				sendToOneGroup(sended,hm, g);
			}
		}else{
			Group g = emp.get().find(Group.class, gid);
			sendToOneGroup(sended, hm, g);
		}
		return true;
	}


	@SuppressWarnings("unchecked")
	private boolean sendToOneGroup(Set<Integer> sended, Hmessage hm, Group g) {
//		List<User> users = emp.get().createNamedQuery(User.UserNamedQuerys.FIND_USERS_BY_GROUP).setParameter(1, g).getResultList();
//		for(User u: users){
//			sendToUser(sended, hm, u);
//		}
		return true;
	}
	
	private boolean sendToUser(Hmessage hm, User u){
		if(!sended.contains(u.getId())){
			HmessageReceiver hr = new HmessageReceiver();
			hr.sethMessage(hm);
			hr.setReceiver(u);
			hr.setStatus(HmessageStatus.UNREAD);
			HmessageReceiverDao hrdao = injector.getInstance(HmessageReceiverDao.class);
			hrdao.smartPersistBaseModel(hr);
			sended.add(u.getId());
		}
		return true;
	}
	
	private void mergeModel(Hmessage model){
		HmessageDao hdao = injector.getInstance(HmessageDao.class);
		hdao.merge(model);
	}


	@Override
	public boolean extraRemoveTask(Hmessage model) {
		if(model.getStatus() == null){
			model.setStatus(HmessageStatus.TRASHED);
			mergeModel(model);
			return false;
		}else if(model.getStatus() == HmessageStatus.TRASHED){
			if(model.getHmrCount() > 0){
				model.setStatus(HmessageStatus.DELETED);
				mergeModel(model);
				return false;
			}else{
				return true;
			}
		}
		return true;
	}

	@Override
	public boolean extraUpdateTask(Hmessage model,Hmessage newModel){
		return true;
	}


	@Override
	public boolean afterPersist(Hmessage model) {
		if(getReqPs().getStringValue(HmessageField.RECEIVER_IDENTITY.getEname()) != null){
			String rid = getReqPs().getStringValue(HmessageField.RECEIVER_IDENTITY.getEname());
			User u = User.findByLoginNameOrEmailOrMobile(rid);
			if(u == null){
				getErrors().addError(new Error("用户不存在！", 0));
				return false;
			}
			sendToUser(model, u);
		}else{
			sendMessage(model);
		}
		
		return true;
	}
	

}
