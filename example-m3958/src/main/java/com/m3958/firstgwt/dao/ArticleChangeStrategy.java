package com.m3958.firstgwt.dao;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.m3958.firstgwt.accesschecker.ArticleChecker;
import com.m3958.firstgwt.accesschecker.SectionChecker;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.model.Article;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.CacheBreakerItem;
import com.m3958.firstgwt.model.CacheBreakerItem.ObAction;
import com.m3958.firstgwt.model.CacheBreakerItem.ObType;
import com.m3958.firstgwt.model.Section;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.session.ErrorMessages;



public class ArticleChangeStrategy  extends BaseModelChangeStrategy implements ModelChangeStrategy<Article> {

	@Override
	public boolean extraPersistTask(Article model) {
		if(model.getPublishedAt() == null)model.setPublishedAt(new Date());
		model.setPublishedAt(afterAddHourMinutes(model.getPublishedAt()));
		if(getRso().isSiteEditor(model.getSiteId()) || getRso().isSiteOwner(model.getSiteId())){
			;
		}else{
			Section s = emp.get().find(Section.class, getReqPs().getRelationModelId());
			if(s!=null){
				SectionChecker ac = injector.getInstance(SectionChecker.class);
				if(!ac.hasTreeContainerPerms(Section.class.getName(), s.getId(), ObjectOperation.AUDIT_CONTENT)){
					model.setAudit(false);
				}
			}else{
				model.setAudit(false);
			}

		}
		return true;
	}
	
	private boolean setInitSection(Article article) {
		if(getReqPs() != null){
			Section s = emp.get().find(Section.class, getReqPs().getRelationModelId());
			if(s != null){
				s.manageRelation(article, true, "", injector.getInstance(ErrorMessages.class));
				article.setDefaultSectionId(s.getId());
				article.getSections().add(s);
				article.setSiteId(s.getSiteId());
				if(article.getProtectLevel() == null || article.getProtectLevel().isEmpty())
					article.setProtectLevel(s.getProtectLevel());
				SectionDao sdao = injector.getInstance(SectionDao.class);//必须用managerelation
				sdao.merge(s);
			}
			return true;
		}else{
			return false;
		}
	}


	@Override
	public boolean extraUpdateTask(Article oldModel,Article newModel) {
		attachAttachments(oldModel);
		addCacheBreakerItem(getCbi(oldModel, ObAction.UPDATE));
		if(oldModel.isAudit() != newModel.isAudit() && !canAudit(oldModel,newModel)){
			newModel.setAudit(oldModel.isAudit());
		}
		if(newModel.getPublishedAt() == null)newModel.setPublishedAt(new Date());
		return true;
	}
	
	private boolean canAudit(Article oldModel,Article newModel) {
		if(getRso().isSiteEditor(oldModel.getSiteId()) || getRso().isSiteOwner(oldModel.getSiteId()))return true;
		ArticleChecker ac = injector.getInstance(ArticleChecker.class);
		for(Section s : oldModel.getSections()){
			if(ac.hasTreeContainerPerms(Section.class.getName(), s.getId(), ObjectOperation.AUDIT_CONTENT)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean extraRemoveTask(Article model) {
		addCacheBreakerItem(getCbi(model, ObAction.DELETE));
		return true;
	}

	@Override
	public boolean afterPersist(Article model) {
		setInitSection(model);
		attachAttachments(model);
		addCacheBreakerItem(getCbi(model, ObAction.ADD));
		return false;
	}
	
	private CacheBreakerItem getCbi(Article article,ObAction action){
		CacheBreakerItem cbi = new CacheBreakerItem();
		cbi.setAction(action);
		cbi.setCreatedAt(new Date());
		cbi.setDone(false);
		cbi.setObId(article.getId());
		cbi.setObName("");
		cbi.setObType(ObType.ARTICLE);
		return cbi;
	}
	
	private void addAt(Article article,JSONObject jo,String usage){
		try {
			Integer oid = jo.getInt(CommonField.ID.getEname());
			Asset a = emp.get().find(Asset.class, oid);
			if("titleImg".equals(usage)){
				article.setTitleImg(a);
			}else if("contentImgs".equals(usage)){
				article.addContentImg(a);
			}else{
				article.addAttachment(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void attachAttachments(Article model) {
		model.setTitleImg(null);
		model.getContentImgs().clear();
		model.getAttachments().clear();
		String at = getReqPs().getStringValue(SmartParameterName.ATTACHMENT_JSON);
		if(at == null)return;
		JSONArray ja = null;
		JSONObject jo =JSONObject.fromObject(at);
		if(jo.containsKey("titleImg")){
			addAt(model,jo.getJSONObject("titleImg"),"titleImg");
		}
		
		if(jo.containsKey("contentImgs")){
			ja = jo.getJSONArray("contentImgs");
			for(int j = 0;j<ja.size();j++){
				addAt(model,ja.getJSONObject(j),"contentImgs");
			}
		}
		
		if(jo.containsKey("attachments")){
			ja = jo.getJSONArray("attachments");
			for(int j = 0;j<ja.size();j++){
				addAt(model,ja.getJSONObject(j),"attachments");
			}
		}

		
//		JSONArray ja = JSONArray.fromObject(at);
//		JSONArray jja;
//		Integer oid;
//		Asset a;
//		for(int i = 0;i < ja.size();i++){
//			JSONObject jo = (JSONObject) ja.get(i);
//			
//			if(jo.containsKey(TitleImgBox.class.getName())){
//				jja = jo.getJSONArray(TitleImgBox.class.getName());
//				if(jja.size() > 0){
//					oid = jja.getJSONObject(0).getInt(CommonField.ID.getEname());
//					a = emp.get().find(Asset.class, oid);
//					model.setTitleImg(a);
//					model.addContentImg(a);
//					model.addAttachment(a);
//				}
//			}
//			
//			if(jo.containsKey(ContentImgBox.class.getName())){
//				jja = jo.getJSONArray(ContentImgBox.class.getName());
//				for(int j = 0;j<jja.size();j++){
//					oid = jja.getJSONObject(j).getInt(CommonField.ID.getEname());
//					a = emp.get().find(Asset.class, oid);
//					model.addContentImg(a);
//					model.addAttachment(a);
//				}
//			}
//			
//			if(jo.containsKey(AttachmentBox.class.getName())){
//				jja = jo.getJSONArray(AttachmentBox.class.getName());
//				for(int j = 0;j<jja.size();j++){
//					oid = jja.getJSONObject(j).getInt(CommonField.ID.getEname());
//					a = emp.get().find(Asset.class, oid);
//					model.addAttachment(a);
//				}
//			}
//		}
	}
	
}
