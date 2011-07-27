package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.RewardField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class RewardDataSource extends BaseDataSource{

    
    public static String className = "com.m3958.firstgwt.model.Reward";
    
    @Inject
    public RewardDataSource(DsErrorHandler deh) {
    	setID("rewardDS");
    	addHandleErrorHandler(deh);
        DataSourceTextField bzField = new DataSourceTextField(RewardField.BZ.getEname());
        DataSourceTextField jlqcField = new DataSourceTextField(RewardField.JLQK.getEname());
        DataSourceTextField cfqcField = new DataSourceTextField(RewardField.CFQK.getEname());
        setFields(getIdField(),jlqcField,cfqcField,bzField,getCreatedAtField(false),getVersionField());
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}
