package com.m3958.firstgwt.command;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.ActiveMqSubject;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.dao.AssetDao;
import com.m3958.firstgwt.dao.GroupDao;
import com.m3958.firstgwt.exception.SmartJpaException;
import com.m3958.firstgwt.model.Article;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Group;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.response.ErrorMessageResponse;
import com.m3958.firstgwt.service.MyActiveMqProducerService;
import com.m3958.firstgwt.service.SiteCacheCleaner;
import com.m3958.firstgwt.service.SiteConfigService;
import com.m3958.firstgwt.session.SessionUser;

public class CustomCommand extends BaseCommand implements Command{
	
	private String result = null;
	
	private boolean writeResponse = true;
	
	@Inject
	private SiteConfigService scs;
	
	@Inject
	private SiteCacheCleaner siteCacheCleaner;

	@Override
	public void execute() throws SmartJpaException, IOException{
		SmartSubOperationName sson = SmartSubOperationName.NO_SUB_OPERATION;
		if(reqPs.getSubOpType() != null)
			sson = SmartSubOperationName.valueOf(reqPs.getSubOpType());
		
		switch (sson) {
		case MANAGE_RELATION:
			result = manageRelation();
			break;
		case FETCH_RELATION_COUNT:
			result = fetchRelationCount();
			break;
		case SHARE_TO_USER:
			result = shareToUser();
			break;
		case SHARE_TO_GROUP:
			result = shareToGroup();
			break;
		case ADD_USER_TO_GROUP:
			result = addUserToGroup();
			break;
		case MQ:
			result = mqit();
			break;
		case ASSET_TO_DISK:
			result = assetToDisk();
			break;
		case REFRESH_SITE_CACHE:
			result = refreshSiteCache();
			break;
		case REBUILD_SOLR_INDEX:
			result = rebuildSolrIndex();
			break;
		case AUDIT_ONE:
			result = auditOne();
			break;
		case REMOTE_ASSET:
			result = processRemoteAsset();
			break;
		default:
			;
		}
		
		if(!errors.isEmpty()){
			result = injector.getInstance(ErrorMessageResponse.class).toString();
		}
		if(writeResponse)
			autils.writeJsonResponse(res,result);
	}
	
	private String processRemoteAsset() throws ClientProtocolException, IOException {
		Asset a = autils.saveRemoteAsset(reqPs);
		return autils.getListResponse(a);
	}

	private String auditOne() {
		return null;
	}

	private String refreshSiteCache() {
		siteCacheCleaner.cleanCache(reqPs.getIntegerValue(CommonField.SITE_ID.getEname()));
		return autils.getObjectValueResponse(true);
	}
	
	private String rebuildSolrIndex() {
		int siteId = Integer.parseInt(req.getParameter(CommonField.SITE_ID.getEname()));
		Article.writeAllSolrCamel(siteId);
		return autils.getObjectValueResponse(true);
	}

	private String assetToDisk() {
		AssetDao adao = injector.getInstance(AssetDao.class);
		return autils.getObjectValueResponse(adao.assetToDisk());
	}
	
	
	private String getClonePageMessage(){
		WebSite ws = emp.get().find(WebSite.class, reqPs.getIdValue("siteId"));
		String path = reqPs.getStringValue("path","");
		if(path.isEmpty()){
			path = "default";
		}else{
			String[] ss = path.trim().split("/", 2);
			if(ss.length < 2){
				path = "default";
			}else{
				if(ss[1].trim().isEmpty()){
					path = "default";
				}else{
					path = ss[1].trim();
				}
			}
		}
		File themeroot = ws.getThemeRoot(scs.getSiteRoot(),path); 
		JSONObject jo = new JSONObject();
		jo.element("dowhat", reqPs.getStringValue("dowhat"));
		jo.element("url", reqPs.getStringValue("url"));
		jo.element("fn", reqPs.getStringValue("fn"));
		jo.element("siteId", reqPs.getStringValue("siteId"));
		jo.element("path", themeroot.getAbsolutePath());
		return jo.toString();
	}

	
//	surlRegex,pageNumber,pageStep,aurlRegex,titleRegex,contentRegex,publishTimeRegex
	private String getImportArticleMessage(){
		SessionUser su = injector.getInstance(SessionUser.class);
		if(!su.isLogined())return null;
		JSONObject jo = new JSONObject();
		jo.element("dowhat", reqPs.getStringValue("dowhat"));
		
		jo.element("surlRegex", reqPs.getStringValue("surlRegex"));
		jo.element("pageNumber", reqPs.getIntegerValue("pageNumber"));
		jo.element("pageStep", reqPs.getIntegerValue("pageStep"));
		
		jo.element("aurlRegex", reqPs.getStringValue("aurlRegex"));
		jo.element("titleRegex", reqPs.getStringValue("titleRegex"));
		jo.element("contentRegex", reqPs.getStringValue("contentRegex"));
		jo.element("publishTimeRegex", reqPs.getStringValue("publishTimeRegex"));
		
		jo.element("sectionId", reqPs.getIdValue("sectionId"));
		
		jo.element("userId", su.getUserId());
		
		return jo.toString();
	}

	//http://127.0.0.1:8888/smartcfud?_modelName=com.m3958.firstgwt.model.Group&_operationType=custom&_subOperationType=MQ
	private String mqit() {
		String dowhat = reqPs.getStringValue("dowhat","");
		MyActiveMqProducerService mm = injector.getInstance(MyActiveMqProducerService.class);
		if("clonepage".equals(dowhat)){
			mm.sendTextMessage(ActiveMqSubject.CLONE_PAGE, getClonePageMessage());
			return autils.getObjectValueResponse(true); 
		}else if("importArticle".equals(dowhat)){
			String s = getImportArticleMessage();
			if(s == null)return "";
			mm.sendTextMessage(ActiveMqSubject.IMPORT_ARTICLE, s);
			return autils.getObjectValueResponse(true); 
		}
		return "";
//		
//		Asset a = new Asset();
//		mm.sendMessage(new AssetVo(a));
//		return autils.getObjectValueResponse(true);
	}

	private String addUserToGroup() {
		GroupDao gdao = injector.getInstance(GroupDao.class);
		List<User> users = gdao.addUserToGroup();
		return autils.getListResponse(users,reqPs.getStartRow(),users.size());
	}

	private String shareToUser() {
		User u = getDao().shareToUser();
		return autils.getListResponse(u);
	}
	
	private String shareToGroup() {
		Group g = getDao().shareToGroup();
		return autils.getListResponse(g);
	}

	private String fetchRelationCount() {
		Integer num = getDao().getRelationCount();
		return autils.getObjectValueResponse(num);
	}


	private String manageRelation() {
		List<BaseModel> results = getDao().manageRelation();
		if(results == null){
			return "null";
		}
		return autils.getListResponse(results,reqPs.getStartRow(),results.size());
	}

	@Override
	public String getResult() {
		return result;
	}
	
	@Override
	public void execute(boolean writeResponse) throws SmartJpaException,
			IOException {
		this.writeResponse = writeResponse;
		execute();
	}


}
