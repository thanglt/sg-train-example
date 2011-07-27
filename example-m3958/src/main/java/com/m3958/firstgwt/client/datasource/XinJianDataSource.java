package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.XinJianField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class XinJianDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.XinJian";

	
    @Inject
    public XinJianDataSource(DsErrorHandler deh) {
    	setID("xinJianDS");

        DataSourceTextField titleField = new DataSourceTextField(XinJianField.TITLE.getEname(),XinJianField.TITLE.getCname());
        DataSourceTextField contentField = new DataSourceTextField(XinJianField.CONTENT.getEname(),XinJianField.CONTENT.getCname());
        DataSourceTextField replyField = new DataSourceTextField(XinJianField.REPLY.getEname(),XinJianField.REPLY.getCname());
        DataSourceTextField xingmingField = new DataSourceTextField(XinJianField.XINGMING.getEname(),XinJianField.XINGMING.getCname());
        DataSourceTextField xingbieField = new DataSourceTextField(XinJianField.XINGBIE.getEname(),XinJianField.XINGBIE.getCname());
        DataSourceDateField shengriField = new DataSourceDateField(XinJianField.SHENGRI.getEname(),XinJianField.SHENGRI.getCname());
        DataSourceDateTimeField publishedAtField = new DataSourceDateTimeField(XinJianField.PUBLISHEDAT.getEname(),XinJianField.PUBLISHEDAT.getCname());
        DataSourceDateTimeField repliedAtField = new DataSourceDateTimeField(XinJianField.REPLIEDAT.getEname(),XinJianField.REPLIEDAT.getCname());
        DataSourceTextField shoujiField = new DataSourceTextField(XinJianField.SHOUJI.getEname(),XinJianField.SHOUJI.getCname());
        DataSourceTextField emailField = new DataSourceTextField(XinJianField.EMAIL.getEname(),XinJianField.EMAIL.getCname());
        DataSourceTextField dianhuaField = new DataSourceTextField(XinJianField.DIANHUA.getEname(),XinJianField.DIANHUA.getCname());
        DataSourceTextField dizhiField = new DataSourceTextField(XinJianField.DIZHI.getEname(),XinJianField.DIZHI.getCname());
        DataSourceTextField leixingField = new DataSourceTextField(XinJianField.LEIXING.getEname(),XinJianField.LEIXING.getCname());
        DataSourceTextField youbianField = new DataSourceTextField(XinJianField.YOUBIAN.getEname(),XinJianField.YOUBIAN.getCname());
        DataSourceTextField baomimaField = new DataSourceTextField(XinJianField.BAOMIMA.getEname(),XinJianField.BAOMIMA.getCname());
        
        DataSourceBooleanField gongkaiField = new DataSourceBooleanField(XinJianField.GONGKAI.getEname(),XinJianField.GONGKAI.getCname());
        DataSourceBooleanField auditField = new DataSourceBooleanField(CommonField.AUDIT.getEname(),CommonField.AUDIT.getCname());
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),titleField,contentField,replyField,getCreatedAtField(false),xingmingField,xingbieField,
        		shengriField,shoujiField,emailField,dianhuaField,dizhiField,leixingField,youbianField,baomimaField,
        		gongkaiField,auditField,publishedAtField,repliedAtField,getTplNameField());
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}

}
