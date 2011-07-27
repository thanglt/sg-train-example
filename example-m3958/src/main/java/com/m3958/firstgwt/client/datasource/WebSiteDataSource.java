package com.m3958.firstgwt.client.datasource;
import java.util.LinkedHashMap;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.WebSiteField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class WebSiteDataSource extends BaseDataSource{
   
    public static String className = "com.m3958.firstgwt.model.WebSite";

    @Inject
    public WebSiteDataSource(DsErrorHandler deh) {
    	setID("webSiteDS");
        DataSourceTextField cnameField = new DataSourceTextField(WebSiteField.CNAME.getEname(),WebSiteField.CNAME.getCname());
        cnameField.setRequired(true);
        
        DataSourceTextField enameField = new DataSourceTextField(WebSiteField.ENAME.getEname(),WebSiteField.ENAME.getCname());
        enameField.setRequired(true);
        DataSourceTextField titleField = new DataSourceTextField(WebSiteField.TITLE.getEname(),WebSiteField.TITLE.getCname());
        DataSourceTextField displayAuthorsField = new DataSourceTextField(WebSiteField.DISPLAY_AUTHORS.getEname(),WebSiteField.DISPLAY_AUTHORS.getCname());
        DataSourceTextField articleSourcesField = new DataSourceTextField(WebSiteField.ARTICLE_SOURCES.getEname(),WebSiteField.ARTICLE_SOURCES.getCname());
        DataSourceTextField articleFlagsField = new DataSourceTextField(WebSiteField.ARTICLE_FLAGS.getEname(),WebSiteField.ARTICLE_FLAGS.getCname());
        DataSourceTextField imgSizeField = new DataSourceTextField(WebSiteField.IMG_SIZES.getEname(),WebSiteField.IMG_SIZES.getCname());
        DataSourceTextField logoUrlField = new DataSourceTextField(WebSiteField.LOGO_URL.getEname(),WebSiteField.LOGO_URL.getCname());
        DataSourceTextField editorCssField = new DataSourceTextField(WebSiteField.EDITOR_CSS.getEname(),WebSiteField.EDITOR_CSS.getCname());
        DataSourceBooleanField cacheEnableField = new DataSourceBooleanField(WebSiteField.CACHE_ENABLE.getEname(),WebSiteField.CACHE_ENABLE.getCname());
        DataSourceBooleanField stopField = new DataSourceBooleanField(WebSiteField.STOP.getEname(),WebSiteField.STOP.getCname());
        DataSourceBooleanField sfurlField = new DataSourceBooleanField(WebSiteField.SEARCH_FRIEND_URL.getEname(),WebSiteField.SEARCH_FRIEND_URL.getCname());
        DataSourceEnumField commentLevelField = new DataSourceEnumField(CommonField.COMMENT_LEVEL.getEname(), CommonField.COMMENT_LEVEL.getCname());
        DataSourceTextField metakeywordField = new DataSourceTextField(CommonField.META_KEYWORDS.getEname(),CommonField.META_KEYWORDS.getCname());
        DataSourceTextField metadescriptionField = new DataSourceTextField(CommonField.META_DESCRIPTION.getEname(),CommonField.META_DESCRIPTION.getCname());
        DataSourceIntegerField rssDateNumField = new DataSourceIntegerField(WebSiteField.RSS_DATE_NUM.getEname(), WebSiteField.RSS_DATE_NUM.getCname());
        LinkedHashMap<String, String> vm = new LinkedHashMap<String, String>();
        vm.put("0", "不允许");
        vm.put("1", "无需审核");
        vm.put("2", "允许，审核");
        vm.put("3", "允许，登录");
        vm.put("4", "允许，登录，审核");
        commentLevelField.setValueMap(vm);
        
        DataSourceBooleanField commentClosedField  = new DataSourceBooleanField(WebSiteField.COMMENT_CLOSED.getEname(),WebSiteField.COMMENT_CLOSED.getCname());
        addHandleErrorHandler(deh);

        setFields(getIdField(),cnameField,enameField,getCreatedAtField(false),getVersionField(),titleField,logoUrlField,
        		imgSizeField,displayAuthorsField,articleSourcesField,
        		articleFlagsField,cacheEnableField,stopField,sfurlField,
        		getPerpageField(),commentLevelField,commentClosedField,
        		editorCssField,metakeywordField,metadescriptionField,rssDateNumField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}
