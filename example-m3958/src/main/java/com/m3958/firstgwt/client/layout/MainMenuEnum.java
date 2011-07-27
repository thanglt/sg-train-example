package com.m3958.firstgwt.client.layout;

public enum MainMenuEnum {
	USER("user"),
	ROLE("role"),
	DEPARTMENT("department"),
	FIELD_ENUM("fieldenum"),
	HELP_MESSAGE("helpmessage"),
	HMESSAGE("hmessage"),
	OPERATION("operation"),
	OBJECT_CLASS("objectclass"),
	SITE_CONFIG("siteconfig"),
	FEED_BACK("feedback"),
	CHART("chart"),
	FTL("ftl"),
	JRXML("jrxml"),
	TOOLS("tools"),
	MENUITEM("menuitem"),
	ASSET("asset"),
	LGB("lgb"),
	WEB_SITE("website"),
	WELCOME("welcome"),
	HGLL("hgll"),
	NO_VALUE("novalue"),
	HTMLCSS("htmlcss");
	
	public static MainMenuEnum getEnumByValue(String vvalue){
		for(MainMenuEnum ft: MainMenuEnum.values()){
			if(ft.getValue().equals(vvalue)){
				return ft;
			}
		}
		return NO_VALUE;
	}
	
	private String value;
	
	public String getValue(){
		return value;
	}
	
	MainMenuEnum(String value){
		this.value = value;
	}
}
