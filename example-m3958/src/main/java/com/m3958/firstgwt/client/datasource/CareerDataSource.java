package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class CareerDataSource extends BaseDataSource{
    
    public static String className = "com.m3958.firstgwt.model.Career";

    @Inject
    public CareerDataSource(DsErrorHandler deh) {
    	setID("careerDS");
        DataSourceTextField bzField = new DataSourceTextField("bz", "备注");
        DataSourceDateField startField = new DataSourceDateField("start","开始日期");
        DataSourceDateField endField = new DataSourceDateField("end","结束日期");
        DataSourceTextField danweiField = new DataSourceTextField("danwei","单位");
        DataSourceTextField zhiwuField = new DataSourceTextField("zhiwu","职务");
        addHandleErrorHandler(deh);

        setFields(getIdField(),getVersionField(),startField,endField,danweiField,zhiwuField,bzField,getCreatedAtField(false));
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}
