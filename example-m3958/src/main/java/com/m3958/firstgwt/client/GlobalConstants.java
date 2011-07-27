package com.m3958.firstgwt.client;

import com.google.inject.Singleton;

@Singleton
public class GlobalConstants {
	public static enum LimitFieldName{
		MINZU("民族"),JIAOYU("教育"),DWXZ("单位性质"),XZJB("行政级别"),XSDY("享受待遇"),	GBLX("干部类型"),JKZK("健康状况"),JJZK("家庭经济状况"),
		HYZK("婚姻状况"),ZFXZ("住房性质"),ZFJG("住房结构"),FKLX("反馈类型"),FKZT("反馈状态"),LMC("类名称"),SSZB("支部名称"),
		LINK_TO("链接对象");
		
		private String name;
		
		private LimitFieldName(String name) {
			this.name = name;
		}
		public String getName(){
			return name;
		}
	}
	
	public String[] getAllLimitFieldNames(){
		String[] ss = new String[LimitFieldName.values().length];
		int i = 0;
		for(LimitFieldName lfn:LimitFieldName.values()){
			ss[i++] = lfn.getName();
		}
		return ss;
	}
	
	public LimitFieldName getLimitFieldNameByName(String name){
		LimitFieldName result = null;
		for(LimitFieldName lfn : LimitFieldName.values()){
			if(name.equals(lfn.getName())){
				result =  lfn;
				break;
			}
		}
		return result;
	}
	
	public static class AppVarFields{
		public static String MENU_LEVEL_ID = "menuLevelId";
		public static String LOGIN_USER = "loginUser";
		public static String IS_SUPERMAN = "isSuperman";
		public static String DEPARTMENTS = "departments";
		public static String OBJECT_CLASS_NAMES = "objectClassNames";
		public static String MAIN_MENU_ITEMS = "mainMenuItems";
		public static String CLIENT_SIDE_CONFIGS = "cconfigs";
		public static String CLIENT_SITE_CONFIGS = "csiteconfigs";

	}
}
