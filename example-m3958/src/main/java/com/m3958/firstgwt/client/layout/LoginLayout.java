package com.m3958.firstgwt.client.layout;

import java.util.Date;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.FirstGwt;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.service.AppUiService;
import com.m3958.firstgwt.client.service.GwtRPCServiceAsync;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.FormValidatorUtils;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.util.DOMUtil;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemKeyPressEvent;
import com.smartgwt.client.widgets.form.events.ItemKeyPressHandler;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class LoginLayout extends VLayout implements Iview{

	@Inject
	private AppUiService auiService;
	
	@Inject
	private AppStatusService aservice;
	
	@Inject
	private VblockService bservice;
	
	@Inject
	private GwtRPCServiceAsync gwtRPCService;
	
	private final DynamicForm loginForm = new DynamicForm();
	
	private final IButton loginBt = new IButton("登录");
	
	private final IButton registeBt = new IButton("注册");
	
	private String loginCookieName = "lgbLoginName";
	
	public LoginLayout(){
		setWidth100();
		setHeight100();
		setMargin(5);
		setMembersMargin(5);
		addMember(initLoginFormLayout());
		focusItem();
	}

	private Canvas initOpenIdIcon() {
		Canvas c = new Canvas();
		c.setContents("<a href=\"/openid.html?wheretogo=/FirstGwt.html\"><img src=\"/images/logo/openid-icon-100x100.png\" border=\"0\" width=\"50\" height=\"50\"/></a>");
		return c;
	}

	public void enableLoginButton(){
		loginBt.setDisabled(false);
	}
	
	private void focusItem() {
		String loginName = Cookies.getCookie(loginCookieName);
		if(loginName != null){
			loginForm.setValue("userIdentity",Cookies.getCookie(loginCookieName));
			loginForm.setValue("rememberMe",true);
			loginForm.focusInItem("password");
		}else{
			loginForm.focusInItem("userIdentity");
		}
	}

	private void login(){
		if(loginForm.validate(false)){
			loginBt.setDisabled(true);
			gwtRPCService.login(loginForm.getValueAsString("userIdentity"),loginForm.getValueAsString("password"),new AsyncCallback<Boolean>(){

				@Override
				public void onFailure(Throwable caught) {
					loginBt.setDisabled(false);
				}

				@Override
				public void onSuccess(Boolean result) {
					loginBt.setDisabled(false);
					if(result){
						loginForm.setValue("password", "");
						aservice.start();
					}else{
						auiService.showTmpPrompt("用户名或者密码错误！", 2000);
						loginForm.setValue("password", "");
						loginForm.focusInItem("password");
					}
				}});
		}		
	}
	
	@SuppressWarnings("deprecation")
	private void setupCookie() {
		if(loginForm.getValue("rememberMe") != null && (Boolean)loginForm.getValue("rememberMe")){
			Date d = new Date();
			Date de = new Date(d.getYear() + 1,d.getMonth(),d.getDate());
			Cookies.setCookie(loginCookieName, loginForm.getValueAsString("userIdentity"),de);
		}else{
			Cookies.removeCookie(loginCookieName);
		}
		
	}

	private Canvas initLoginFormLayout() {
		Layout vlayout = new VLayout(5);
		vlayout.setWidth(600);
		vlayout.setHeight(200);
		vlayout.setBorder("1px solid gray");
		vlayout.setTop(50);
		vlayout.setLayoutAlign(Alignment.CENTER);
		loginForm.setWidth100();
		loginForm.setHeight(150);
		loginForm.setNumCols(2);
		loginForm.setAutoFocus(true);
		
		TextItem loginNameItem = new TextItem("userIdentity","用户名称");
		loginNameItem.setValidators(FormValidatorUtils.getLengthRangeValidator(2, 50));
		loginNameItem.setRequired(true);

		
		StaticTextItem hintItem = new StaticTextItem("提示");
		hintItem.setDefaultValue("也可输入电子邮件或者手机号码");
		PasswordItem passwordItem = new PasswordItem("password","用户密码");
		passwordItem.setRequired(true);
		passwordItem.setValidators(FormValidatorUtils.getLengthRangeValidator(4, 40));
		
		CheckboxItem rememberItem = new CheckboxItem("rememberMe","记住登陆名");
		
		loginForm.addItemKeyPressHandler(new ItemKeyPressHandler(){
			@Override
			public void onItemKeyPress(ItemKeyPressEvent event) {
				if(KeyNames.ENTER.equals(event.getKeyName())){
					setupCookie();
					login();
				}
				
			}});
		loginForm.setFields(loginNameItem,hintItem,passwordItem,rememberItem);
		
		HLayout hlayout = new HLayout(10);
		loginBt.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				setupCookie();
				login();
			}
		});
		
		loginBt.setLeft(30);
		
		registeBt.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				bservice.clearAllBlockContents();
				bservice.b.setValues(ViewNameEnum.REGISTE, ViewAction.ADD);
				bservice.addHistoryItemAndFireApplicationEvent();
			}
		});
		
		hlayout.addMember(loginBt);
		hlayout.addMember(registeBt);
		hlayout.addMember(initOpenIdIcon());
		vlayout.addMember(loginForm);
		vlayout.addMember(hlayout);
		return vlayout;
	}

	@SuppressWarnings("unused")
	private HLayout initFcLayout() {
		HLayout btContainer = new HLayout();
		Label fcbtLabel = new Label();
		fcbtLabel.setContents("<div id=\"login-fcbutton\"></div>");
		DOMUtil.setID(btContainer,"login-fcbutton");
		btContainer.addMember(fcbtLabel);
		return btContainer;
	}

	@Override
	public void changeDisplay(ViewAction va, String... paras) {
		if(aservice.isLogined()){
			History.newItem(FirstGwt.defaultToken,true);
		}
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.LOGIN;
	}

	@Override
	public Layout asLayout() {
		return this;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}

	@Override
	public Btname[] getStripStatus() {
		return null;
	}

	@Override
	public String[] getParas() {
		return null;
	}

	@Override
	public void setUnInitialized() {
	}
}
