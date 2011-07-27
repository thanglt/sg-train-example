package com.m3958.firstgwt.server;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.annotation.ProtectMe;
import com.m3958.firstgwt.client.GlobalConstants;
import com.m3958.firstgwt.client.errorhandler.LgbExceptionType;
import com.m3958.firstgwt.client.errorhandler.LgbRpcException;
import com.m3958.firstgwt.client.errorhandler.SmartDuplicateRecordException;
import com.m3958.firstgwt.client.service.GwtRPCService;
import com.m3958.firstgwt.client.types.GwtResponseStatus;
import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SortDirection;
import com.m3958.firstgwt.dao.AssetDao;
import com.m3958.firstgwt.dao.BaseDao;
import com.m3958.firstgwt.dao.ComponentPreferenceDao;
import com.m3958.firstgwt.dao.ObjectClassNameDao;
import com.m3958.firstgwt.dao.UserDao;
import com.m3958.firstgwt.dao.WebHostDao;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.ComponentPreference;
import com.m3958.firstgwt.model.MenuItem;
import com.m3958.firstgwt.model.MenuLevel;
import com.m3958.firstgwt.model.ObjectClassName;
import com.m3958.firstgwt.model.SiteConfig;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.model.WebHost;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.response.GwtRpcResponse;
import com.m3958.firstgwt.server.types.AppConstants;
import com.m3958.firstgwt.server.types.HasToJson;
import com.m3958.firstgwt.service.ModelAndDao;
import com.m3958.firstgwt.service.MySHAService;
import com.m3958.firstgwt.service.RequestScopeObjectService;
import com.m3958.firstgwt.session.SessionUser;
import com.m3958.firstgwt.utils.FriendConnectUtil;
import com.m3958.firstgwt.utils.JsonUtils;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;
import com.wideplay.warp.persist.Transactional;


@Singleton
public class GwtRPCServiceImpl  extends RemoteServiceServlet implements GwtRPCService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7550793684989207986L;
	
	@Inject 
	private Injector injector;
	
	@Inject
	private ModelAndDao mc;
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
//	public String fetch(BaseModel emptyModel, int startRow, int num,String sortField,
//			SortDirection sortDir,String[] excludes)  throws LgbRpcException{
//		BaseDao<BaseModel> dao = mc.getDaoInstance(emptyModel.getClass().getName());
//		List<BaseModel> results = dao.gwtQuery(emptyModel, startRow, num, sortField, sortDir);
//		GwtRpcResponse response = new GwtRpcResponse(GwtResponseStatus.SUCCESS,
//				JSONArray.fromObject(results,JsonUtils.getJsonConfig(excludes)));
//		return response.toString();
//
//	}
//	
	
//	public String fetchAll(BaseModel emptyModel, String[] excludes)  throws LgbRpcException{
//		BaseDao<BaseModel> dao = mc.getDaoInstance(emptyModel.getClass().getName());
//		List<BaseModel> results = dao.fetchAll(emptyModel);
//		GwtRpcResponse response = new GwtRpcResponse(GwtResponseStatus.SUCCESS,
//				JSONArray.fromObject(results,JsonUtils.getJsonConfig(excludes)));
//		return response.toString();
//	}
	
//	public String fetchHasRelation(BaseModel emptyModel, int startRow,
//			int num,String sortField, SortDirection sortDir, Integer otherObjId, String myProperty,String[] excludes)  throws LgbRpcException {
//		BaseDao<BaseModel> dao = mc.getDaoInstance(emptyModel.getClass().getName());
//		List<BaseModel> results = dao.gwtQuery(emptyModel,startRow,num,sortField,sortDir,otherObjId,myProperty);
//		GwtRpcResponse response = new GwtRpcResponse(GwtResponseStatus.SUCCESS,
//				JSONArray.fromObject(results,JsonUtils.getJsonConfig(excludes)));
//		return response.toString();
//
//	}
//	

	
//	public String manageRelation(BaseModel destObj, String methodName,
//			List<BaseModel> srcObjs,String[] excludes)  throws LgbRpcException{
//		BaseDao<BaseModel> dao = mc.getDaoInstance(destObj.getClass().getName());
//		List<BaseModel> results = dao.manageRelation(destObj, methodName, srcObjs);
//		GwtRpcResponse response = new GwtRpcResponse(GwtResponseStatus.SUCCESS,
//				JSONArray.fromObject(results,JsonUtils.getJsonConfig(excludes)));
//		return response.toString();
//	}
	

//	public String fetchChildren(BaseModel model, String[] excludes)  throws LgbRpcException{
//		BaseDao<BaseModel> dao = mc.getDaoInstance(model.getClass().getName());
//		List<BaseModel> results = dao.fetchChildren(model);
//		BaseModel parent = null;
//		if(model.getId() != 0){
//			parent = model;
//		}
//		GwtRpcResponse response = new GwtRpcResponse(GwtResponseStatus.SUCCESS,
//				JSONArray.fromObject(results,JsonUtils.getJsonConfig(excludes)),
//				new JSONObject().element("parent", JSONObject.fromObject(parent,JsonUtils.getJsonConfig(excludes))));
//		return response.toString();
//	}
	
//	public String addModel(BaseModel model,String[] excludes)  throws LgbRpcException, SmartDuplicateRecordException{
//		BaseDao<BaseModel> dao = mc.getDaoInstance(model.getClass().getName());	
//		model = dao.smartPersistBaseModel(model);
//		GwtRpcResponse response = new GwtRpcResponse(GwtResponseStatus.SUCCESS,
//				JSONArray.fromObject(model,JsonUtils.getJsonConfig(excludes)));
//		return response.toString();
//	}
//	
//	@ProtectMe
//	public String removeModel(BaseModel model,String[] excludes) throws LgbRpcException{
//		try {
//			BaseDao<BaseModel> dao = mc.getDaoInstance(model.getClass().getName());
//			model = dao.remove(model);
//			GwtRpcResponse response = new GwtRpcResponse(GwtResponseStatus.SUCCESS,
//					JSONObject.fromObject(model,JsonUtils.getJsonConfig(excludes)));
//			return response.toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//			if(e.getMessage().indexOf("foreign") != -1){
//				throw new LgbRpcException(LgbExceptionType.REMOVE_CONSTRAINT_VIOLATION);
//			}
//		}
//		return null;
//	}
	
//	@ProtectMe
//	public String removeAsset(Asset asset,String relationModelName,String relationModelId,String[] excludes) throws LgbRpcException{
//		try {
//			AssetDao dao = injector.getInstance(AssetDao.class);
//			asset = dao.removeAsset(asset,relationModelName,relationModelId);
//			GwtRpcResponse response = new GwtRpcResponse(GwtResponseStatus.SUCCESS,
//					JSONObject.fromObject(asset,JsonUtils.getJsonConfig(excludes)));
//			return response.toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//			if(e.getMessage().indexOf("foreign") != -1){
//				throw new LgbRpcException(LgbExceptionType.REMOVE_CONSTRAINT_VIOLATION);
//			}
//		}
//		return null;
//	}

	
//	public String updateModel(BaseModel model,String[] excludes)  throws LgbRpcException, SmartDuplicateRecordException{
//		BaseDao<BaseModel> dao = mc.getDaoInstance(model.getClass().getName());	
//		model = dao.smartUpdateBaseModel(model);
//		GwtRpcResponse response = new GwtRpcResponse(GwtResponseStatus.SUCCESS,
//				JSONObject.fromObject(model,JsonUtils.getJsonConfig(excludes)));
//		return response.toString();
//	}
	
	/**
	 * 返回登录的用户，限定字段常量，树的入口点，是否超级用户（控制UI）等等。
	 * {login:{user},isSuperman:boolean,entries:[{department,department}],
	 * limitFieldValues:[],objectClassNames:[{},{}]}
	 * 
	 * 
	 */
	@Override
	public String initLgbApp() throws LgbRpcException{
		SessionUser su = injector.getInstance(SessionUser.class);
		UserDao udao = injector.getInstance(UserDao.class);
		RequestScopeObjectService rso = injector.getInstance(RequestScopeObjectService.class);
		User u = null;
		
		if(su.getUserId() == null){
				throw new LgbRpcException(LgbExceptionType.LOGIN_REQUIRED);
		}else{
			u = udao.find(su.getUserId());
		}
		
		if(u == null){
			throw new LgbRpcException(LgbExceptionType.LOGIN_REQUIRED);
		}
		if(u.getFcUser()){
			u.setLoginName(su.getLoginName());
		}
		

		JSONObject jo = new JSONObject();
		
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.USER_EXCLUDES);
	
		jo.element(GlobalConstants.AppVarFields.LOGIN_USER, JSONObject.fromObject(u,jc));
		if(su.isSuperman()){
			jo.element(GlobalConstants.AppVarFields.IS_SUPERMAN, true);
		}else{
			jo.element(GlobalConstants.AppVarFields.IS_SUPERMAN, false);
		}
		
		MenuLevel ml = u.getMenuLevel();
		
		List<MenuItem> menus = null;
		
		if(ml != null){
			jo.element(GlobalConstants.AppVarFields.MENU_LEVEL_ID, ml.getId());
			menus = ml.getMenuitems();
		}else{
			jo.element(GlobalConstants.AppVarFields.MENU_LEVEL_ID, SmartConstants.NONE_EXIST_MODEL_ID);
		}
		
		if(menus == null)menus = new ArrayList<MenuItem>();
		JSONArray ja = new JSONArray();
		for(MenuItem mi : menus){
			ja.add(mi.toJson());
		}
		
		jo.element(GlobalConstants.AppVarFields.CLIENT_SIDE_CONFIGS, ja);
		
		jo.element(GlobalConstants.AppVarFields.MAIN_MENU_ITEMS, ja);
		
		Query q = emp.get().createNamedQuery(SiteConfig.NamedQueries.FIND_CLIENT_SIDE_CONFIG);
		q.setParameter(1, true);
		
		ja = new JSONArray();
		for(Object o :q.getResultList()){
			ja.add(((HasToJson)o).toJson());
		}
		jo.element(GlobalConstants.AppVarFields.CLIENT_SIDE_CONFIGS, ja);
		jo.element("status", 0);
		
		HttpServletRequest req = injector.getInstance(HttpServletRequest.class);
		WebHostDao whdao = injector.getInstance(WebHostDao.class);
		
		String hostName = req.getParameter(AppConstants.HOST_PARAMETER_NAME);
		if(hostName == null || hostName.isEmpty()){
			hostName = req.getRemoteHost();
		}
		
		WebHost wh = whdao.findByName(hostName);
		if(wh != null){
			WebSite ws = wh.getWebSite();
			ws.setSiteEditor(rso.isSiteEditor(ws));
			ws.setSiteOwner(rso.isSiteOwner(ws));
			jo.element(GlobalConstants.AppVarFields.CLIENT_SITE_CONFIGS, wh.getWebSite().toJsonOne());
		}
			
		
		ObjectClassNameDao odao = injector.getInstance(ObjectClassNameDao.class);
		List<ObjectClassName> ocns = odao.fetchAll();
		
		JsonConfig jc1 = new JsonConfig();
		jc1.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc1.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc1.setExcludes(JsonExcludeFields.OBJECTCLASS_EXCLUDES);
		
		jo.element(GlobalConstants.AppVarFields.OBJECT_CLASS_NAMES, JSONArray.fromObject(ocns,jc1));
		
		return jo.toString();
	}


	@Override
	public void logout() {
		SessionUser su = injector.getInstance(SessionUser.class);
		su.clearContent();
	}

	@Override
	public boolean loginStatus(){
		try{
		//放在这里。
//		HttpServletResponse res = injector.getInstance(HttpServletResponse.class);
//		Cookie c = new Cookie(FriendConnectUtil.FCCOOKIENAME,"ALhR-_sLBJx2fh3s9hQMOzuwHD2Tbk8Z5QqF6YNwl7qzWfiYc8uMuhQgO13UbrsOJirzctLO4_VAG7Jvfoh7vIw4jApERyA3_A~08347242695057973357~jlH8tsmfmLTq");
//		c.setPath("/");
//		res.addCookie(c);
		SessionUser su = injector.getInstance(SessionUser.class);
		if(su.getLoginStatus()){
			return true;
		}else{
			FriendConnectUtil fcutils = injector.getInstance(FriendConnectUtil.class);
			return fcutils.fcLogin("");
//			HttpServletRequest req = getThreadLocalRequest();
//			FriendConnectUtil fcutils = injector.getInstance(FriendConnectUtil.class);
//			String token = fcutils.getFauthCookie(req.getCookies());
//			if(token != null){
//				User u = fcutils.getUserFromFC(token, null);
//				if(u == null)return false;
//				su.setContent(u);
//				return true;
//			}
		}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean login(String userIdentity, String password) {
		//如果已经登录，就不需要再次登陆
		//如果是post登录，这里有点不一样，因为我们的是ajax登录不是普通的page登录，要作另外的处理。
		//如果有fcauth，就开始osp登录
		//FriendConnectUtil::getViewer($fcAuthToken)
		//设置session。
		
		UserDao dao = injector.getInstance(UserDao.class);
		User user = dao.findByLoginNameOrEmailOrMobile(userIdentity);
		if(user == null)return false;
		MySHAService shas = injector.getInstance(MySHAService.class);
//		if(user.getPassword().equals(password)){
		if(shas.isEqual(user.getPassword(), password)){
			SessionUser su = injector.getInstance(SessionUser.class);
			su.setContent(user);
			return true;
		}
		return false;
	}

//	@Override
//	@Transactional
//	public void setLastPreference(Integer lastPreferenceId) {
//		ComponentPreferenceDao dao = injector.getInstance(ComponentPreferenceDao.class);
//		List<BaseModel> cc = dao.getMyModel(injector.getInstance(SessionUser.class).getUserId());
//		for (BaseModel baseModel : cc) {
//			if(baseModel.getId() == lastPreferenceId){
//				((ComponentPreference)baseModel).setLastRequest(true);
//			}else{
//				((ComponentPreference)baseModel).setLastRequest(false);
//			}
//		}
//	}

}
