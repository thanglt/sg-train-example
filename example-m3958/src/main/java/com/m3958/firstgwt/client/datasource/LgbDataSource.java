package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.LgbField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class LgbDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.Lgb";
    
    @Inject
    public LgbDataSource(DsErrorHandler deh) {
    	setID("lgbDS");
        
        DataSourceTextField xmField = new DataSourceTextField(LgbField.XM.getEname());
        xmField.setRequired(true);
        
        DataSourceEnumField xbField = new DataSourceEnumField(LgbField.XB.getEname());
        xbField.setValueMap("男","女","未知");
        
        DataSourceTextField sszbField = new DataSourceTextField(LgbField.SSZB.getEname());
        
        DataSourceDateField srField = new DataSourceDateField(LgbField.SR.getEname());
        
        DataSourceTextField sfzField = new DataSourceTextField(LgbField.SFZ.getEname());
        
        DataSourceTextField minzuField = new DataSourceTextField(LgbField.MINZU.getEname());
        
      
        DataSourceTextField jgField = new DataSourceTextField(LgbField.JG.getEname());
        DataSourceTextField csdField = new DataSourceTextField(LgbField.CSD.getEname());
        
        DataSourceTextField jyField = new DataSourceTextField(LgbField.JY.getEname());
        
        DataSourceDateField rdsjField = new DataSourceDateField(LgbField.RDSJ.getEname());
        DataSourceDateField rwsjField = new DataSourceDateField(LgbField.RWSJ.getEname());
        DataSourceDateField ltxsjField = new DataSourceDateField(LgbField.LTXSJ.getEname());
        DataSourceTextField ygzdwField = new DataSourceTextField(LgbField.YGZDW.getEname());
        DataSourceTextField yzwField = new DataSourceTextField(LgbField.YZW.getEname());
        DataSourceTextField xzgdwField = new DataSourceTextField(LgbField.XZGDW.getEname());
        
        DataSourceTextField dwxzField = new DataSourceTextField(LgbField.DWXZ.getEname());
        
        DataSourceTextField xzjbField = new DataSourceTextField(LgbField.XZJB.getEname());
        
        DataSourceTextField xsdyField = new DataSourceTextField(LgbField.XSDY.getEname());
        
        DataSourceTextField gblxField = new DataSourceTextField(LgbField.GBLX.getEname());
        
        DataSourceTextField jkzkField = new DataSourceTextField(LgbField.JKZK.getEname());
        
        DataSourceTextField hyzkField = new DataSourceTextField(LgbField.HYZK.getEname());
       
        DataSourceTextField jjzkField = new DataSourceTextField(LgbField.JJZK.getEname());
        
        DataSourceTextField pogwField = new DataSourceTextField(LgbField.POGZ.getEname());
        DataSourceTextField hjszdField = new DataSourceTextField(LgbField.HJSZD.getEname());
        DataSourceDateField swsjField = new DataSourceDateField(LgbField.SWSJ.getEname());
        DataSourceTextField bzField = new DataSourceTextField(LgbField.BZ.getEname());
        DataSourceBooleanField passAwayField = new DataSourceBooleanField(LgbField.PASSAWAY.getEname());
        
        DataSourceTextField jbmsField = new DataSourceTextField(LgbField.JBMS.getEname(),LgbField.JBMS.getCname());
        
        addHandleErrorHandler(deh);
        
        DataSourceIntegerField paixuField = new DataSourceIntegerField(LgbField.PAIXU.getEname());
        
        DataSourceIntegerField shequIdField = new DataSourceIntegerField(LgbField.SHEQU_ID.getEname());
        shequIdField.setHidden(true);
        
        DataSourceIntegerField zpIdField = new DataSourceIntegerField(LgbField.ZP_ID.getEname());
        zpIdField.setHidden(true);
        DataSourceIntegerField departmentIdField = new DataSourceIntegerField(LgbField.DEPARTMENT_ID.getEname());
        departmentIdField.setHidden(true);
        

        setFields(getIdField(),getVersionField(),xmField,xbField,srField,sfzField,minzuField,jgField,csdField,jyField,rdsjField,rwsjField,ltxsjField,ygzdwField,
        		yzwField,xzgdwField,dwxzField,xzjbField,xsdyField,sszbField,gblxField,jkzkField,hyzkField,jjzkField,pogwField,hjszdField,swsjField,bzField,passAwayField,getCreatedAtField(false),paixuField,jbmsField,shequIdField,departmentIdField,zpIdField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}   
}
