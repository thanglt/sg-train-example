package com.m3958.firstgwt.client;


import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.m3958.firstgwt.client.layout.AppToolStrip;
import com.m3958.firstgwt.client.layout.Iview;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.Layout;


@Singleton
public class ViewService {
	
	@Inject @Named("HGLL")
	private Iview hgll;
	
	@Inject @Named("HGLL_EDIT")
	private Iview hgll_edit;
	
	@Inject @Named("HTMLCSS")
	private Iview htmlcss;
	
	@Inject @Named("HTMLCSS_EDIT")
	private Iview htmlcss_edit;
	
	@Inject @Named("LOGIN")
	private Iview login;
	
	@Inject @Named("WELCOME")
	private Iview welcome;
	
	@Inject @Named("REGISTE")
	private Iview registe;
	
	@Inject @Named("MMENU")
	private Iview mmenu;
	
	@Inject @Named("TOP")
	private Iview top;
	
	@Inject @Named("MENULEVEL")
	private Iview menulevel;
	
	@Inject @Named("MENULEVEL_EDIT")
	private Iview menuleveledit;
	
	@Inject @Named("MENUITEM_EDIT")
	private Iview menuitemedit;
	
	@Inject @Named("MENUITEM")
	private Iview menuitem;
	
	@Inject @Named("DEPARTMENT")
	private Iview department;
	
	@Inject @Named("DEPARTMENT_EDIT")
	private Iview department_edit;
	
	@Inject @Named("SHEQU")
	private Iview shequ;
	
	@Inject @Named("SHEQU_EDIT")
	private Iview shequ_edit;
	
	@Inject @Named("LGB")
	private Iview lgb;
	
	@Inject @Named("LGB_EDIT")
	private Iview lgb_edit;
	
	@Inject @Named("SITE_CONFIG")
	private Iview site_config;
	
	@Inject @Named("SITECONFIG_EDIT")
	private Iview siteconfig_edit;
	
	@Inject @Named("ADDRESS_EDIT")
	private Iview address_edit;
	@Inject @Named("FAMILY_EDIT")
	private Iview family_edit;
	@Inject @Named("HOUSE_EDIT")
	private Iview house_edit;
	@Inject @Named("CAREER_EDIT")
	private Iview career_edit;
	@Inject @Named("STEP_CAREER_EDIT")
	private Iview step_career_edit;
	@Inject @Named("REWARD_EDIT")
	private Iview reward_edit;
	
	@Inject @Named("ASSET")
	private Iview asset;
	
	@Inject @Named("ASSET_EDIT")
	private Iview asset_edit;
	
	@Inject @Named("ASSET_FOLDER")
	private Iview folder;
	
	@Inject @Named("SECTION")
	private Iview section;
	
	@Inject @Named("SECTION_EDIT")
	private Iview section_edit;
	
	@Inject @Named("ARTICLE")
	private Iview article;
	
	@Inject @Named("ARTICLE_EDIT")
	private Iview article_edit;
	
	@Inject @Named("ASSET_FOLDER_EDIT")
	private Iview folder_edit;
	
	@Inject @Named("WEBSITE")
	private Iview website;
	
	@Inject @Named("WEBSITE_EDIT")
	private Iview website_edit;
	
	@Inject @Named("DISKFILE")
	private Iview diskfile;
	
	@Inject @Named("DISKFILE_EDIT")
	private Iview diskfile_edit;
	
	@Inject @Named("USER")
	private Iview user;
	@Inject @Named("USER_EDIT")
	private Iview user_edit;
	
	@Inject @Named("GROUP")
	private Iview group;
	@Inject @Named("GROUP_EDIT")
	private Iview group_edit;
	
	@Inject @Named("ROLE")
	private Iview role;
	
	@Inject @Named("ROLE_EDIT")
	private Iview role_edit;
	
	@Inject @Named("FIELD_ENUM")
	private Iview fieldenum;
	
	@Inject @Named("FIELD_ENUM_EIDT")
	private Iview fieldenum_edit;
	
	@Inject @Named("OBJECT_CLASS")
	private Iview ocl;
	
	@Inject @Named("OBJECT_CLASS_EDIT")
	private Iview ocl_edit;
	
	@Inject @Named("JRXML")
	private Iview jrxml;
	
	@Inject @Named("JRXML_EDIT")
	private Iview jrxml_edit;
	
	@Inject @Named("FEED_BACK")
	private Iview feedback;
	
	@Inject @Named("FEEDBACK_EDIT")
	private Iview feedback_edit;
	
	@Inject @Named("WEBHOST")
	private Iview webhost;
	
	@Inject @Named("WEBHOST_EDIT")
	private Iview webhost_edit;
	
	@Inject @Named("FTL")
	private Iview ftl;
	
	@Inject @Named("FTL_EDIT")
	private Iview ftl_edit;
	
	@Inject @Named("TOOLS")
	private Iview tools;
	
	@Inject @Named("strip")
	private AppToolStrip strip;
	
	@Inject @Named("rightStrip")
	private AppToolStrip rstrip;
	
	@Inject @Named("leftStrip")
	private AppToolStrip lstrip;
	
	@Inject @Named("LINK")
	private Iview link;
	
	@Inject @Named("LINK_EDIT")
	private Iview link_eidt;
	
	@Inject @Named("VOTE")
	private Iview vote;

	@Inject @Named("VOTEHIT")
	private Iview voteHit;

	
	@Inject @Named("VOTE_EDIT")
	private Iview vote_edit;

	
	@Inject @Named("XINJIAN")
	private Iview xinjian;
	
	@Inject @Named("XINJIAN_EDIT")
	private Iview xinjian_edit;
	
	@Inject @Named("XJ_CAT")
	private Iview xinjiancat;
	
	@Inject @Named("XJ_CAT_EDIT")
	private Iview xinjiancat_edit;
	
	@Inject @Named("COMMENT")
	private Iview comment;
	
	@Inject @Named("COMMENT_EDIT")
	private Iview comment_edit;
	
	@Inject @Named("PAGE_MISTAKE")
	private Iview page_mistake;
	
	@Inject @Named("PAGE_MISTAKE_EDIT")
	private Iview page_mistake_edit;
	
	@Inject @Named("HMESSAGE_RECEIVE")
	private Iview hmessage_receive;
	
	public List<Iview> allViews;
	
	public List<Iview> getAllViews(){
		if(allViews == null || allViews.isEmpty()){
			allViews = new ArrayList<Iview>();
			allViews.add(mmenu);
			allViews.add(top);
			allViews.add(menuitem);
			allViews.add(menulevel);
			allViews.add(menuitemedit);
			allViews.add(strip);
			allViews.add(lstrip);
			allViews.add(rstrip);
			allViews.add(menuleveledit);
			allViews.add(department);
			allViews.add(lgb);
			allViews.add(department_edit);
			allViews.add(lgb_edit);
			allViews.add(site_config);
			allViews.add(siteconfig_edit);
			
			allViews.add(shequ);
			allViews.add(shequ_edit);
			allViews.add(address_edit);
			allViews.add(reward_edit);
			allViews.add(family_edit);
			allViews.add(house_edit);
			allViews.add(asset);
			allViews.add(asset_edit);
			allViews.add(user);
			allViews.add(group);
			
			allViews.add(user_edit);
			allViews.add(group_edit);
			allViews.add(role);
			allViews.add(role_edit);
			allViews.add(fieldenum);
			allViews.add(fieldenum_edit);
			allViews.add(ocl);
			
			allViews.add(ocl_edit);
			allViews.add(jrxml);
			allViews.add(jrxml_edit);
			allViews.add(feedback);
			allViews.add(feedback_edit);
			allViews.add(ftl);
			allViews.add(ftl_edit);
			
			allViews.add(website);
			allViews.add(website_edit);
			allViews.add(folder);
			allViews.add(folder_edit);
			allViews.add(diskfile);
			allViews.add(diskfile_edit);
			
			
			allViews.add(webhost);
			allViews.add(webhost_edit);
			allViews.add(tools);
			allViews.add(section);
			allViews.add(article);
			allViews.add(article_edit);
			allViews.add(link);
			allViews.add(link_eidt);
			allViews.add(welcome);
			allViews.add(login);
			
			allViews.add(registe);
			allViews.add(hgll);
			allViews.add(hgll_edit);
			allViews.add(xinjian);
			allViews.add(xinjian_edit);
			allViews.add(xinjiancat);
			allViews.add(xinjiancat_edit);
			
			allViews.add(htmlcss);
			allViews.add(htmlcss_edit);
			allViews.add(vote);
			allViews.add(vote_edit);
			allViews.add(voteHit);
			
			allViews.add(comment);
			allViews.add(comment_edit);
			
			allViews.add(page_mistake);
			allViews.add(page_mistake_edit);
			allViews.add(hmessage_receive);
			
		}
		return allViews;
	}
	
	
	public AppToolStrip getLeftStrip(){
		return lstrip;
	}
	
	public AppToolStrip getRightStrip(){
		return rstrip;
	}
	
	public AppToolStrip getStrip(){
		return strip;
	}
	
	public Iview getView(ViewNameEnum vname){
		if(vname == null)return null;
		switch (vname) {
		case MMENU:
			return mmenu;
		case TOP:
			return top;
		case MENUITEM:
			return menuitem;
		case MENULEVEL:
			return menulevel;
		case MENUITEM_EDIT:
			return menuitemedit;
		case STRIP:
			return strip;
		case LEFT_STRIP:
			return lstrip;
		case RIGHT_STRIP:
			return rstrip;
		case MENULEVEL_EDIT:
			return menuleveledit;
		case DEPARTMENT:
			return department;
		case LGB:
			return lgb;
		case DEPARTMENT_EDIT:
			return department_edit;
		case LGB_EDIT:
			return lgb_edit;
		case SITE_CONFIG:
			return site_config;
		case SITECONFIG_EDIT:
			return siteconfig_edit;
		case SHEQU:
			return shequ;
		case SHEQU_EDIT:
			return shequ_edit;
		case ADDRESS_EDIT:
			return address_edit;
		case CAREER_EDIT:
			return career_edit;
		case STEP_CAREER_EDIT:
			return step_career_edit;
		case REWARD_EDIT:
			return reward_edit;
		case FAMILY_EDIT:
			return family_edit;
		case HOUSE_EDIT:
			return house_edit;
		case ASSET:
			return asset;
		case ASSET_EDIT:
			return asset_edit;
		case USER:
			return user;
		case GROUP:
			return group;
		case USER_EDIT:
			return user_edit;
		case GROUP_EDIT:
			return group_edit;
		case ROLE:
			return role;
		case ROLE_EDIT:
			return role_edit;
		case FIELD_ENUM:
			return fieldenum;
		case FIELD_ENUM_EIDT:
			return fieldenum_edit;
		case OBJECT_CLASS:
			return ocl;
		case OBJECT_CLASS_EDIT:
			return ocl_edit;
		case JRXML:
			return jrxml;
		case JRXML_EDIT:
			return jrxml_edit;
		case FEED_BACK:
			return feedback;
		case FEEDBACK_EDIT:
			return feedback_edit;
		case FTL:
			return ftl;
		case FTL_EDIT:
			return ftl_edit;
		case WEBSITE:
			return website;
		case WEBSITE_EDIT:
			return website_edit;
		case ASSET_FOLDER:
			return folder;
		case ASSET_FOLDER_EDIT:
			return folder_edit;
		case DISKFILE:
			return diskfile;
		case DISKFILE_EDIT:
			return diskfile_edit;
		case WEBHOST:
			return webhost;
		case WEBHOST_EDIT:
			return webhost_edit;
		case TOOLS:
			return tools;
		case SECTION:
			return section;
		case SECTION_EDIT:
			return section_edit;
		case ARTICLE:
			return article;
		case ARTICLE_EDIT:
			return article_edit;
		case LINK:
			return link;
		case LINK_EDIT:
			return link_eidt;
		case WELCOME:
			return welcome;
		case LOGIN:
			return login;
		case REGISTE:
			return registe;
		case HGLL:
			return hgll;
		case HGLL_EDIT:
			return hgll_edit;
		case XINJIAN:
			return xinjian;
		case XINJIAN_EDIT:
			return xinjian_edit;
		case XJ_CAT:
			return xinjiancat;
		case XJ_CAT_EDIT:
			return xinjiancat_edit;
		case HTMLCSS:
			return htmlcss;
		case HTMLCSS_EDIT:
			return htmlcss_edit;
		case VOTE:
			return vote;
		case VOTE_EDIT:
			return vote_edit;
		case VOTEHIT:
			return voteHit;
		case COMMENT:
			return comment;
		case COMMENT_EDIT:
			return comment_edit;
		case PAGE_MISTAKE:
			return page_mistake;
		case PAGE_MISTAKE_EDIT:
			return page_mistake_edit;
		case HMESSAGE_RECEIVE:
			return hmessage_receive;
		default:
			break;
		}
		return null;
	}
	
	public void hideAllMembers(Layout l){
		for(Canvas c : l.getMembers()){
			c.hide();
		}
	}
}
