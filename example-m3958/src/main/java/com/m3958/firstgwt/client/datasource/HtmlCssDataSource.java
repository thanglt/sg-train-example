package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.GlobalConstants;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.HtmlCssField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class HtmlCssDataSource extends BaseDataSource{
    
    public static String className = "com.m3958.firstgwt.model.HtmlCss";
    
    @Inject
    public HtmlCssDataSource(GlobalConstants gcontants,DsErrorHandler deh) {
    	setID("htmlCssDS");
    	DataSourceTextField nameField = new DataSourceTextField(CommonField.NAME.getEname(),CommonField.NAME.getCname());

        DataSourceEnumField contentTypeField = new DataSourceEnumField(HtmlCssField.CONTENT_TYPE.getEname(),HtmlCssField.CONTENT_TYPE.getCname());
        contentTypeField.setValueMap("html","css");
        DataSourceTextField contentField = new DataSourceTextField(HtmlCssField.CONTENT.getEname(),HtmlCssField.CONTENT.getCname());
        DataSourceTextField contentVersionField = new DataSourceTextField(HtmlCssField.CONTENT_VERSION.getEname(),HtmlCssField.CONTENT_VERSION.getCname());
        DataSourceTextField contributorField = new DataSourceTextField(HtmlCssField.CONTRIBUTOR.getEname(),HtmlCssField.CONTRIBUTOR.getCname());
        DataSourceTextField descriptionField = new DataSourceTextField(HtmlCssField.DESCRIPTION.getEname(),HtmlCssField.DESCRIPTION.getCname());
        
        addHandleErrorHandler(deh);

        setFields(getIdField(),nameField,contentTypeField,contentField,descriptionField,contentVersionField,contributorField,getAuditField(),getCreatedAtField(false),getVersionField());
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}   
}
