package com.m3958.firstgwt.client.utils;

import java.util.Date;

import com.google.gwt.user.client.Cookies;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.util.DOMUtil;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemKeyPressEvent;
import com.smartgwt.client.widgets.form.events.ItemKeyPressHandler;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.SubmitItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;


public class LoginUtils {
	public static final Dialog loginDialog = new Dialog();
	
	private final static DynamicForm loginForm = new DynamicForm();
	
	private final static SubmitItem submitItem = new SubmitItem("submitLogin","登录");
	
	private static String loginCookieName = "lgbLoginName";
	
	static{
		loginDialog.setAutoCenter(true);
		loginDialog.setShowCloseButton(false);
		loginDialog.setWidth(300);
		loginDialog.setHeight(160);
		loginDialog.setTitle("登录系统");
		loginDialog.setShowToolbar(false);
		loginDialog.setShowShadow(true);
		VLayout sqlayout = initLoginFormLayout();
		loginDialog.addItem(sqlayout);
		focusItem();
	}
	
	public static void showLoginDialog(){
		loginDialog.show();
		loginDialog.moveTo(Page.getWidth()/2 - 150, 80);
	}
	
	private static void focusItem() {
		String loginName = Cookies.getCookie(loginCookieName);
		if(loginName != null){
			loginForm.setValue("userIdentity",Cookies.getCookie(loginCookieName));
			loginForm.setValue("rememberMe",true);
			loginForm.focusInItem("password");
		}else{
			loginForm.focusInItem("userIdentity");
		}
	}

	public static void hideLoginDialog(){
		loginDialog.hide();
	}
	
	private static void login(){
		if(loginForm.validate(false)){
//			submitItem.setDisabled(true);
//			FirstGwt.gwtRPCService.login(loginForm.getValueAsString("userIdentity"),loginForm.getValueAsString("password"),new AsyncCallback<Boolean>(){
//
//				@Override
//				public void onFailure(Throwable caught) {
//					submitItem.setDisabled(false);
//				}
//
//				@Override
//				public void onSuccess(Boolean result) {
//					if(result){
//						FirstGwt.initApp();
//						loginForm.clearValues();
//						loginDialog.hide();
//					}else{
//						submitItem.setDisabled(false);
//						GlobalStaffs.showTmpPrompt("用户名或者密码错误！", 2000);
//						loginForm.setValue("password", "");
//						loginForm.focusInItem("password");
//					}
//					
//				}});
		}		
	}
	
	@SuppressWarnings("deprecation")
	private static void setupCookie() {
		if(loginForm.getValue("rememberMe") != null && (Boolean)loginForm.getValue("rememberMe")){
			Date d = new Date();
			Date de = new Date(d.getYear() + 1,d.getMonth(),d.getDate());
			Cookies.setCookie(loginCookieName, loginForm.getValueAsString("userIdentity"),de);
		}else{
			Cookies.removeCookie(loginCookieName);
		}
		
	}

	private static VLayout initLoginFormLayout() {
		VLayout layout = new VLayout();
		layout.setMargin(5);
		layout.setMembersMargin(5);
		layout.setWidth100();
		layout.setHeight100();
		
		
		loginForm.setWidth100();
		loginForm.setHeight100();
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
		
		
		submitItem.setWidth(50);
		submitItem.setColSpan(2);
		submitItem.setAlign(Alignment.CENTER);
		
		submitItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler(){
			@Override
			public void onClick(
					com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				setupCookie();
				login();
			}
		});
		
		loginForm.addItemKeyPressHandler(new ItemKeyPressHandler(){
			@Override
			public void onItemKeyPress(ItemKeyPressEvent event) {
				if(KeyNames.ENTER.equals(event.getKeyName())){
					setupCookie();
					login();
				}
				
			}});
		loginForm.setFields(loginNameItem,hintItem,passwordItem,rememberItem,submitItem);
		
		
		HLayout formLayout = new HLayout();
		formLayout.addMember(loginForm);
		layout.addMember(formLayout);
		return layout;
	}

	@SuppressWarnings("unused")
	private static HLayout initFcLayout() {
		HLayout btContainer = new HLayout();
		Label fcbtLabel = new Label();
		fcbtLabel.setContents("<div id=\"login-fcbutton\"></div>");
		DOMUtil.setID(btContainer,"login-fcbutton");
		btContainer.addMember(fcbtLabel);
		return btContainer;
	}
}
