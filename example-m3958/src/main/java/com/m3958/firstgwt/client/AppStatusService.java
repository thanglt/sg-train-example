package com.m3958.firstgwt.client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.errorhandler.GwtRpcExceptionHandler;
import com.m3958.firstgwt.client.event.LoginEvent;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.jso.ObjectClassNameJso;
import com.m3958.firstgwt.client.jso.UserJso;
import com.m3958.firstgwt.client.jso.WebSiteJso;
import com.m3958.firstgwt.client.service.GwtRPCServiceAsync;
import com.m3958.firstgwt.client.types.ConfigKey;
import com.m3958.firstgwt.client.types.MenuItemCategory;
import com.m3958.firstgwt.client.types.MenuItemField;
import com.m3958.firstgwt.client.types.SiteConfigField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.utils.StringUtils;
import com.smartgwt.client.util.SC;

//這裡不要注入viewService，不然這個狀態就無法在View裡面使用，會引起循環注入。

@Singleton
public class AppStatusService {
	
	private String initToken;

	@Inject
	private MyEventBus eventBus;
	
	private JSONObject appvars;
	
	private boolean logined;
	
	private boolean appLoaded;

	private boolean superman;
	
	private boolean confirmInProgress;
	
	private UserJso su;
	
	private WebSiteJso webSite;
	
	private JsArray<ObjectClassNameJso> objectClassNameJsos;
	
	
	public int menuLevelId = SmartConstants.NONE_EXIST_MODEL_ID;
	

	private Map<MenuItemCategory, JSONArray> menuItemSections = new HashMap<MenuItemCategory, JSONArray>();
	
	
	private JSONArray clientConfigs;
	
	private String[] editableExts;
	
	@Inject
	private GwtRPCServiceAsync gwtRPCService;
	
	
	//已经登录
	public void start(){
		SC.showPrompt("程序初始化中......请稍候");
        gwtRPCService.initLgbApp(new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {
				GwtRpcExceptionHandler.handler(caught);
			}
			@Override
			public void onSuccess(String result) {
				JSONObject jo = JSONParser.parseStrict(result).isObject();
				init(jo);
			    if(initToken.length() > 0 && !initToken.contains("/LOGIN/")){
			    	String tk = initToken + ",random=" + new Date().getTime();
			    	History.newItem(tk);
			    }else{
			    	History.newItem(FirstGwt.defaultToken,true);//default
			    }
				SC.clearPrompt();
				eventBus.fireEvent(new LoginEvent(true));
			}
        });
	}
	
	public Map<MenuItemCategory, JSONArray> getMenuItemSections(){
		return menuItemSections;
	}
	
	public boolean allowEdit(String fn){
		String ext = StringUtils.getFileExtensionWithDot(fn);
		if(clientConfigs == null)return false;
		if(editableExts == null){
			for(int i=0;i<clientConfigs.size();i++){
				String ck = clientConfigs.get(i).isObject().get(SiteConfigField.CONFIG_KEY.getEname()).isString().stringValue();
				if(ck.equals((ConfigKey.ALLOW_EDIT_EXTS.getEname()))){
					String value = clientConfigs.get(i).isObject().get(SiteConfigField.CONFIG_VALUE.getEname()).isString().stringValue();
					editableExts = value.split("\\s");
				}
			}
			if(editableExts == null){
				editableExts = new String[]{};
			}
		}
		for(String ex:editableExts){
			if(ex.equalsIgnoreCase(ext)){
				return true;
			}
		}
		return false;
	}

	public void init(JSONObject jo){
		appvars = jo;
		logined = true;
		editableExts = null;
		superman = appvars.get(GlobalConstants.AppVarFields.IS_SUPERMAN).isBoolean().booleanValue();
		JSONObject userJson = appvars.get(GlobalConstants.AppVarFields.LOGIN_USER).isObject();
		su = (UserJso) userJson.isObject().getJavaScriptObject();
		if(appvars.containsKey(GlobalConstants.AppVarFields.CLIENT_SIDE_CONFIGS))
			clientConfigs = appvars.get(GlobalConstants.AppVarFields.CLIENT_SIDE_CONFIGS).isArray();
		if(appvars.containsKey(GlobalConstants.AppVarFields.CLIENT_SITE_CONFIGS))
			setWebSite((WebSiteJso) appvars.get(GlobalConstants.AppVarFields.CLIENT_SITE_CONFIGS).isObject().getJavaScriptObject());
		JSONValue mo = jo.get(GlobalConstants.AppVarFields.MENU_LEVEL_ID);
		//mo isNull只有在值是jsonnull的情况下才返回有效引用，否则是null。
		if(mo != null && mo.isNull() == null){
			menuLevelId = (int) mo.isNumber().doubleValue();
		}
		//{\"createdAt\":\"2010-10-08T09:36:47\",\"id\":5652,\"menuItemCat\":\"BASE\",\"title\":\"用户管理\",\"uniqueName\":\"BASE_USER_TAB\"}
		//分组可以在客户端写死，根据BASE，OA这样写死，如果有就显示，没有就不显示。
		JSONArray menuitems = appvars.get(GlobalConstants.AppVarFields.MAIN_MENU_ITEMS).isArray();
		
		//可以是一个hashmap，BASE-》【】，OA-》【】
		
		
		for(MenuItemCategory mic : MenuItemCategory.values()){
			menuItemSections.put(mic, new JSONArray());
		}
		
		Map<MenuItemCategory, Integer> mi = new HashMap<MenuItemCategory, Integer>();
		
		for(int i=0;i<menuitems.size();i++){
			MenuItemCategory mic = MenuItemCategory.valueOf(menuitems.get(i).isObject().get(MenuItemField.MENUITEMCAT.getEname()).isString().stringValue());
			int t = (mi.get(mic) == null ? 0 : mi.get(mic));
			menuItemSections.get(mic).set(t, menuitems.get(i));
			mi.put(mic, ++t);
		}
	}
	
	public boolean isSuperman() {
		return superman;
	}

	public void setSu(UserJso loginUser) {
		su = loginUser;
	}

	public UserJso getSu() {
		return su;
	}


	public  void setObjectClassNameJsos(JsArray<ObjectClassNameJso> objectClassNameJsos) {
		this.objectClassNameJsos = objectClassNameJsos;
	}


	@SuppressWarnings("unchecked")
	public JsArray<ObjectClassNameJso> getObjectClassNameJsos() {
		if(objectClassNameJsos == null){
//			objectClassNameJsos = (JsArray<ObjectClassNameJso>) JsoUtils.getJsArray((appvars.get(GlobalConstants.AppVarFields.OBJECT_CLASS_NAMES).toString()));
			objectClassNameJsos = (JsArray<ObjectClassNameJso>) appvars.get(GlobalConstants.AppVarFields.OBJECT_CLASS_NAMES).isArray().getJavaScriptObject();
		}
		return objectClassNameJsos;
	}
	

	
	public void setInitToken(String initToken) {
		this.initToken = initToken;
	}
	public String getInitToken() {
		return initToken;
	}
	public void setLogined(boolean logined) {
		this.logined = logined;
	}
	public boolean isLogined() {
		return logined;
	}
	public void setAppLoaded(boolean appLoaded) {
		this.appLoaded = appLoaded;
	}
	public boolean isAppLoaded() {
		return appLoaded;
	}
	


	public void setConfirmInProgress(boolean confirmInProgress) {
		this.confirmInProgress = confirmInProgress;
	}

	public boolean isConfirmInProgress() {
		return confirmInProgress;
	}






	public void setWebSite(WebSiteJso webSite) {
		this.webSite = webSite;
	}






	public WebSiteJso getWebSite() {
		return webSite;
	}

}
