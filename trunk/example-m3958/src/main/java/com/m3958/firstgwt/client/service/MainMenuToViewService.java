package com.m3958.firstgwt.client.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.layout.MainMenuEnum;
import com.m3958.firstgwt.client.types.ViewNameEnum;


@Singleton
public class MainMenuToViewService {
	
	@Inject
	private VblockService bservice;
	
	public void changeToArticleView(){
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.WEB_SITE.getValue());
		bservice.f1.setValues(ViewNameEnum.LEFT_STRIP, ViewAction.NO_ACTION);
		bservice.f2.setValues(ViewNameEnum.RIGHT_STRIP, ViewAction.NO_ACTION);
		bservice.g1.setValues(ViewNameEnum.SECTION, ViewAction.NO_ACTION);
		bservice.g2.setValues(ViewNameEnum.ARTICLE, ViewAction.NO_ACTION);
		bservice.addHistoryItemAndFireApplicationEvent();
	}
	
	public void changeToHmessageReceiveView(){
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.HMESSAGE.getValue());
		bservice.e1.setValues(ViewNameEnum.STRIP, ViewAction.NO_ACTION);
		bservice.e2.setValues(ViewNameEnum.HMESSAGE_RECEIVE, ViewAction.NO_ACTION);
		bservice.addHistoryItemAndFireApplicationEvent();
	}

	
	public void newViewState(MainMenuEnum mme){
		bservice.clearAllBlockContents();
		switch (mme) {
		case ASSET:
			changeToAssetView();
			break;
		case CHART:
			break;
		case DEPARTMENT:
			changeToDepartmentView();
			break;
		case FEED_BACK:
			changeToFeedBackView();
			break;
		case FIELD_ENUM:
			changeToFieldEnumView();
			break;
		case FTL:
			changeToFtlView();
			break;
		case HELP_MESSAGE:
			break;
		case JRXML:
			changeToJrxmlView();
			break;
		case LGB:
			break;
		case MENUITEM:
			changeToMenuItemView();
			break;
		case NO_VALUE:
			break;
		case OBJECT_CLASS:
			changeToObjectClassView();
			break;
		case OPERATION:
			break;
		case ROLE:
			changeToRoleView();
			break;
		case SITE_CONFIG:
			changeToSiteConfigView();
			break;
		case TOOLS:
			break;
		case USER:
			changeToUserView();
			break;
		case WEB_SITE:
			changeToWebSiteView();
			break;
		case WELCOME:
			break;
		case HGLL:
			changeHgllView();
			break;
		case HTMLCSS:
			changeHtmlCssView();
			break;
		case HMESSAGE:
			changeToHmessageReceiveView();
			return;
		default:
			break;
		}
		bservice.addHistoryItemAndFireApplicationEvent();
	}
	
	private void changeToFeedBackView() {
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.FEED_BACK.getValue());
		bservice.e1.setValues(ViewNameEnum.STRIP, ViewAction.NO_ACTION);
		bservice.e2.setValues(ViewNameEnum.FEED_BACK, ViewAction.NO_ACTION);
		
	}

	private void changeHgllView() {
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.HGLL.getValue());
		bservice.e1.setValues(ViewNameEnum.STRIP, ViewAction.NO_ACTION);
		bservice.e2.setValues(ViewNameEnum.HGLL, ViewAction.NO_ACTION);
	}
	
	private void changeHtmlCssView() {
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.HTMLCSS.getValue());
		bservice.e1.setValues(ViewNameEnum.STRIP, ViewAction.NO_ACTION);
		bservice.e2.setValues(ViewNameEnum.HTMLCSS, ViewAction.NO_ACTION);
	}

	private void changeToAssetView(){
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.ASSET.getValue());
		bservice.f1.setValues(ViewNameEnum.LEFT_STRIP, ViewAction.NO_ACTION);
		bservice.f2.setValues(ViewNameEnum.RIGHT_STRIP, ViewAction.NO_ACTION);
		bservice.g1.setValues(ViewNameEnum.ASSET_FOLDER, ViewAction.NO_ACTION);
		bservice.g2.setValues(ViewNameEnum.ASSET, ViewAction.NO_ACTION);
	}
	
	private void changeToDepartmentView(){
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.DEPARTMENT.getValue());
		bservice.f1.setValues(ViewNameEnum.LEFT_STRIP, ViewAction.NO_ACTION);
		bservice.f2.setValues(ViewNameEnum.RIGHT_STRIP, ViewAction.NO_ACTION);
		bservice.g1.setValues(ViewNameEnum.DEPARTMENT, ViewAction.NO_ACTION);
		bservice.g2.setValues(ViewNameEnum.LGB, ViewAction.NO_ACTION);
	}
	
	private void changeToFieldEnumView(){
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.FIELD_ENUM.getValue());
		bservice.e1.setValues(ViewNameEnum.STRIP, ViewAction.NO_ACTION);
		bservice.e2.setValues(ViewNameEnum.FIELD_ENUM, ViewAction.NO_ACTION);
	}
	
	private void changeToFtlView(){
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.FTL.getValue());
		bservice.e1.setValues(ViewNameEnum.STRIP, ViewAction.NO_ACTION);
		bservice.e2.setValues(ViewNameEnum.FTL, ViewAction.NO_ACTION);
	}
	
	private void changeToJrxmlView(){
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.JRXML.getValue());
		bservice.e1.setValues(ViewNameEnum.STRIP, ViewAction.NO_ACTION);
		bservice.e2.setValues(ViewNameEnum.JRXML, ViewAction.NO_ACTION);
		
	}
	
	
	
	private void changeToMenuItemView(){
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.MENUITEM.getValue());
		bservice.f1.setValues(ViewNameEnum.LEFT_STRIP, ViewAction.NO_ACTION);
		bservice.f2.setValues(ViewNameEnum.RIGHT_STRIP, ViewAction.NO_ACTION);
		bservice.g1.setValues(ViewNameEnum.MENULEVEL, ViewAction.NO_ACTION);
		bservice.g2.setValues(ViewNameEnum.MENUITEM, ViewAction.NO_ACTION);
		
	}
	
	private void changeToObjectClassView(){
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.OBJECT_CLASS.getValue());
		bservice.e1.setValues(ViewNameEnum.STRIP, ViewAction.NO_ACTION);
		bservice.e2.setValues(ViewNameEnum.OBJECT_CLASS, ViewAction.NO_ACTION);
		
	}
	
	private void changeToRoleView(){
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.ROLE.getValue());
		bservice.e1.setValues(ViewNameEnum.STRIP, ViewAction.NO_ACTION);
		bservice.e2.setValues(ViewNameEnum.ROLE, ViewAction.NO_ACTION);
	}
	
	private void changeToSiteConfigView(){
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.SITE_CONFIG.getValue());
		bservice.e1.setValues(ViewNameEnum.STRIP, ViewAction.NO_ACTION);
		bservice.e2.setValues(ViewNameEnum.SITE_CONFIG, ViewAction.NO_ACTION);
		
	}
	
	
	private void changeToUserView(){
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.USER.getValue());
		bservice.f1.setValues(ViewNameEnum.LEFT_STRIP, ViewAction.NO_ACTION);
		bservice.f2.setValues(ViewNameEnum.RIGHT_STRIP, ViewAction.NO_ACTION);
		bservice.g1.setValues(ViewNameEnum.GROUP, ViewAction.NO_ACTION);
		bservice.g2.setValues(ViewNameEnum.USER, ViewAction.NO_ACTION);
		
	}
	
	private void changeToWebSiteView(){
		bservice.c1.setValues(ViewNameEnum.TOP, ViewAction.NO_ACTION);
		bservice.d1.setValues(ViewNameEnum.MMENU, ViewAction.NO_ACTION,MainMenuEnum.WEB_SITE.getValue());
		bservice.e1.setValues(ViewNameEnum.STRIP, ViewAction.NO_ACTION);
		bservice.e2.setValues(ViewNameEnum.WEBSITE, ViewAction.NO_ACTION);
		
	}

}
