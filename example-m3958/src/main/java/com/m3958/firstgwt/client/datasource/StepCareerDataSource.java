package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.utils.FormValidatorUtils;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class StepCareerDataSource extends BaseDataSource{

  
    public static String className = "com.m3958.firstgwt.model.StepCareer";

    @Inject
    public StepCareerDataSource(DsErrorHandler deh) {
    	setID("setpCareerDS");
    	
    	
        DataSourceTextField bzField = new DataSourceTextField("bz", "备注");
        DataSourceDateField startField = new DataSourceDateField("start","开始日期");
        DataSourceDateField endField = new DataSourceDateField("end","结束日期");
        
        DataSourceTextField zysjField = new DataSourceTextField("zysj", "主要事迹");
        zysjField.setRequired(true);
        zysjField.setValidators(FormValidatorUtils.getLengthRangeValidator(10, 10000));
       
        addHandleErrorHandler(deh);
        setFields(getIdField(),startField,endField,zysjField,bzField,getCreatedAtField(false),getVersionField());
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}
