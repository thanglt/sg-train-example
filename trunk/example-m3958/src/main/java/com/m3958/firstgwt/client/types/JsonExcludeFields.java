package com.m3958.firstgwt.client.types;


public class JsonExcludeFields {
	public final static String[] USER_EXCLUDES = new String[]{"creator","children","parent","roles","menuItems","groups","version","menuLevel","articles"};
	public final static String[] ROLE_EXCLUDES = new String[]{"creator","users","permissions","version"};
	public final static String[] LGB_EXCLUDES = new String[]{"creator","department","families","careers","stepCareers","addresses","rewards","attachments","houses","lgbs","folder","tags","shequ","zp","mainAddress","departmentIds","version","persistedShequId","persistedZpId","titleImg","attachments"};
	public final static String[] ASSET_EXCLUDES = new String[]{"creator","folders","tags","version","possibleOperations","possibleRoleNames","objectPermissions","objectRoles","stream"};
	public final static String[] DEPARTMENT_EXCLUDES = new String[]{"creator","parent","children","lgbs","shequs","version","possibleOperations","possibleRoleNames","objectPermissions","objectRoles"};
	public final static String[] GROUP_EXCLUDES = new String[]{"creator","parent","children","users","roles","version","possibleOperations","possibleRoleNames","objectPermissions","objectRoles","folder"};
	public final static String[] FOLDER_EXCLUDES = new String[]{"creator","parent","children","assets","version","possibleOperations","possibleRoleNames","objectPermissions","objectRoles","breadCrumb","childrenAssetCount","assetCount","folder"};
	public final static String[] SECTION_EXCLUDES = new String[]{"creator","parent","children","version","possibleOperations","possibleRoleNames","objectPermissions","objectRoles","webSite","articles","siblings","siblingsIncludeMe","defaultSection","tags","breadCrumb","articleCount","url","keyValueMap","folder","childrenArticleCount","isRoot"};
	public final static String[] WEBSITE_EXCLUDES = new String[]{"creator","parent","children","version","possibleOperations","possibleRoleNames","objectPermissions","objectRoles","webhosts","articles","articleCount","topSections","topLinks","topLinkCats","topXinjianCats","topAssetFolders","firstArticlePage","articleArchives","mistakes","visitNumber","dailyVisitNumber"};
	public final static String[] WEBHOST_EXCLUDES = new String[]{"version","webSite"};
	public final static String[] PAGE_MISTAKE_EXCLUDES = new String[]{"version","webSite"};
	public final static String[] ARTICLE_EXCLUDES = new String[]{"creator","parent","children","version","possibleOperations","possibleRoleNames","objectPermissions","objectRoles","sections","titleImg","contentImgs","attachments","defaultSection","tags","breadCrumb","content","url","keyValueMap"};
	public final static String[] FIELDENUM_EXCLUDES = new String[]{"creator","version"};
	public final static String[] OPERATION_EXCLUDES = new String[]{"creator","permission","version"};
	public final static String[] OBJECTCLASS_EXCLUDES = new String[]{"creator","operationCat","version"};
	public final static String[] PERMISSION_EXCLUDES = new String[]{"creator","roles","version","operation"};
	public final static String[] SHEQU_EXCLUDES = new String[]{"creator","department","lgbs","version"};
	public final static String[] ADDRESS_EXCLUDES = new String[]{"creator","lgb","department","version"};
	public final static String[] XINJIAN_CAT_EXCLUDES = new String[]{"creator","version","possibleOperations","possibleRoleNames","children","parent","objectPermissions","objectRoles","xinjians","url","breadCrumb","siblings","childrenXinjianCount","xinjianCount","folder","siblingsIncludeMe"};
	public final static String[] XINJIAN_EXCLUDES = new String[]{"creator","version","xjCats","xjCat","attachments","content","reply","url"};
	public final static String[] FAMILY_EXCLUDES = new String[]{"creator","lgb","version"};
	public final static String[] REWARD_EXCLUDES = new String[]{"creator","lgb","version"};
	public final static String[] HOUSE_EXCLUDES = new String[]{"creator","lgb","version"};
	public final static String[] STEPCAREER_EXCLUDES = new String[]{"creator","lgb","version"};
	public final static String[] CAREER_EXCLUDES = new String[]{"creator","lgb","version"};
	public final static String[] FEEDBACK_EXCLUDES = new String[]{"creator","version"};
	public final static String[] HMESSAGE_EXCLUDES = new String[]{"creator","roles","groups","menuLevel","attribute","version","sender","hmrCount"};
	public final static String[] HMESSAGE_RECEIVER_EXCLUDES = new String[]{"creator","roles","groups","menuLevel","version","receiver","sender","attachments","hMessage"};
	public final static String[] CALEVENT_EXCLUDES = new String[]{"creator","version"};
	public final static String[] FTL_EXCLUDES = new String[]{"creator","ftl","version"};
	public final static String[] SITE_CONFIG_EXCLUDES = new String[]{"creator","version"};
	public final static String[] TAB_EXCLUDES = new String[]{"creator","version"};
	public final static String[] JRXML_FILE_EXCLUDES = new String[]{"creator","report","children","parent","version","attachments","folder"};
	public final static String[] COMPONENT_PREFERENCE_EXCLUDES = new String[]{"creator","version"};
	public final static String[] SAVED_FORM_QUERY_EXCLUDES = new String[]{"creator","version"};
	public final static String[] MENUITEM_EXCLUDES = new String[]{"menuLevels","version"};
	public final static String[] MENULEVEL_EXCLUDES = new String[]{"menuitems","version"};
	public final static String[] OPERATIONCAT_EXCLUDES = new String[]{"version"};
	public final static String[] LINK_EXCLUDES = new String[]{"version","parent","children","keyValueMap","folder","visibleChildren"};
	public final static String[] VOTE_EXCLUDES = new String[]{"version","parent","children","voteHits","voteNum","childrenVoteNum","folder","creator"};
	public final static String[] COMMENT_EXCLUDES = new String[]{"version","parent","children","creator","creatorIds"};
	public final static String[] VOTE_HIT_EXCLUDES = new String[]{"version","vote"};
	public final static String[] HGLL_EXCLUDES = new String[]{"version"};
	public final static String[] HTMLCSS_EXCLUDES = new String[]{"version"};
}
