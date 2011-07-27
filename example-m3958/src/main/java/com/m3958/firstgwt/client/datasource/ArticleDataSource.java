package com.m3958.firstgwt.client.datasource;
import java.util.LinkedHashMap;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.ArticleField;
import com.m3958.firstgwt.client.types.CommonField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class ArticleDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.Article";
    
    @Inject
    public ArticleDataSource(DsErrorHandler deh) {
    	setID("articleDS");

        DataSourceTextField titleField = new DataSourceTextField(ArticleField.TITLE.getEname());
        DataSourceTextField excerptField = new DataSourceTextField(ArticleField.EXCERPT.getEname());
        DataSourceTextField contentField = new DataSourceTextField(ArticleField.CONTENT.getEname());
        DataSourceTextField flagField = new DataSourceTextField(ArticleField.FLAG.getEname());
        DataSourceTextField displayAuthorField = new DataSourceTextField(ArticleField.DISPLAY_AUTHOR.getEname());
        DataSourceTextField sourceField = new DataSourceTextField(ArticleField.SOURCE.getEname());
        
        DataSourceIntegerField sectionField = new DataSourceIntegerField(ArticleField.DEFAULT_SECTION_ID.getEname());
        DataSourceTextField kvField = new DataSourceTextField(CommonField.KEY_VALUES.getEname(),CommonField.KEY_VALUES.getEname());
        DataSourceDateTimeField publishedAtField = new DataSourceDateTimeField(ArticleField.PUBLISHED_AT.getEname());
        publishedAtField.setCanFilter(false);
        
        DataSourceEnumField commentLevelField = new DataSourceEnumField(CommonField.COMMENT_LEVEL.getEname(), CommonField.COMMENT_LEVEL.getCname());
        LinkedHashMap<String, String> vm = new LinkedHashMap<String, String>();
        vm.put("0", "继承");
        vm.put("1", "允许");
        vm.put("2", "禁止");
        commentLevelField.setValueMap(vm);

        
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),titleField,contentField,excerptField,kvField,getTagNamesField(),flagField,publishedAtField,getCreatedAtField(false),sectionField,getAuditField(),getTplNameField(),displayAuthorField,sourceField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}

}
