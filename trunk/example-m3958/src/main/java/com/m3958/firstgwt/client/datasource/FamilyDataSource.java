package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class FamilyDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.Family";
    
    @Inject
    public FamilyDataSource(DsErrorHandler deh) {
    	setID("familyDS");
    	
    	
        DataSourceTextField bzField = new DataSourceTextField("bz", "备注");
        DataSourceTextField xmField = new DataSourceTextField("xm","姓名");
        DataSourceTextField gxField = new DataSourceTextField("gx","关系");
        DataSourceTextField xbField = new DataSourceTextField("xb","性别");
        xbField.setValueMap("男","女","未知");
        
        DataSourceTextField zzmmField = new DataSourceTextField("zzmm","政治面貌");
        DataSourceDateField srField = new DataSourceDateField("birthday","生日");
        
        DataSourceTextField jkzkField = new DataSourceTextField("jkzk","健康状况");
        
        DataSourceTextField dizhiField = new DataSourceTextField("dizhi","地址");
        DataSourceTextField dianhuaField = new DataSourceTextField("dianhua","电话");
        DataSourceTextField shoujiField = new DataSourceTextField("shouji","手机");

        
        
        
        addHandleErrorHandler(deh);
        

        setFields(getIdField(),getVersionField(),xmField,gxField,dizhiField,shoujiField,dianhuaField,zzmmField,srField,jkzkField,xbField,bzField,getCreatedAtField(false));
        setClientOnly(false);
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}
