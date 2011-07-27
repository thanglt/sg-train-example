package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.HouseField;
import com.smartgwt.client.data.fields.DataSourceTextField;


@Singleton
public class HouseDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.House";
    
    @Inject
    public HouseDataSource(DsErrorHandler deh) {
    	setID("houseDS");
    	
    	
        DataSourceTextField bzField = new DataSourceTextField(HouseField.BZ.getEname());
        DataSourceTextField zfxzField = new DataSourceTextField(HouseField.ZFXZ.getEname());
        DataSourceTextField areaField = new DataSourceTextField(HouseField.AREA.getEname());
        DataSourceTextField structureField = new DataSourceTextField(HouseField.STRUCTURE.getEname());
        addHandleErrorHandler(deh);
        setFields(getIdField(),zfxzField,areaField,structureField,bzField,getCreatedAtField(false),getVersionField());
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}  
}
