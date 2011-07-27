package com.m3958.firstgwt.client.datasource;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.HgllField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DateDisplayFormat;

public class HgllDatasource extends BaseDataSource{
	
	public static String className = "com.m3958.firstgwt.model.Hgll";
	
	
    @Inject
    public HgllDatasource(DsErrorHandler deh) {
    	setID("hgllDS");
    	
    	DataSourceTextField xmField = new DataSourceTextField(HgllField.XM.getEname(),HgllField.XM.getCname());
    	xmField.setRequired(true);
        DataSourceEnumField xbField = new DataSourceEnumField(HgllField.XB.getEname(),HgllField.XB.getCname());
        xbField.setValueMap(new String[]{"女","男"});
        xbField.setRequired(true);
        DataSourceDateField srField = new DataSourceDateField(HgllField.SR.getEname(),HgllField.SR.getCname());
        srField.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
        DataSourceTextField dhField = new DataSourceTextField(HgllField.DH.getEname(),HgllField.DH.getCname());
        DataSourceIntegerField jgField = new DataSourceIntegerField(HgllField.JG.getEname(),HgllField.JG.getCname());
        DataSourceTextField sfzField = new DataSourceTextField(HgllField.SFZ.getEname(),HgllField.SFZ.getCname());
        DataSourceTextField lxdzField = new DataSourceTextField(HgllField.LXDZ.getEname(),HgllField.LXDZ.getCname());
        DataSourceTextField gzdwField = new DataSourceTextField(HgllField.GZDW.getEname(),HgllField.GZDW.getCname());
        DataSourceTextField mzField = new DataSourceTextField(HgllField.MZ.getEname(),HgllField.MZ.getCname());
        DataSourceTextField ssxzField = new DataSourceTextField(HgllField.SSXZ.getEname(),HgllField.SSXZ.getCname());
        DataSourceTextField lyField = new DataSourceTextField(HgllField.LY.getEname(),HgllField.LY.getCname());
        
        DataSourceTextField sjField = new DataSourceTextField(HgllField.SJ.getEname(),HgllField.SJ.getCname());
        DataSourceTextField emailField = new DataSourceTextField(HgllField.EMAIL.getEname(),HgllField.EMAIL.getCname());
        DataSourceTextField cslbField = new DataSourceTextField(HgllField.CSLB.getEname(),HgllField.CSLB.getCname());
        DataSourceTextField csgqField = new DataSourceTextField(HgllField.CSGQ.getEname(),HgllField.CSGQ.getCname());
        DataSourceTextField whcdField = new DataSourceTextField(HgllField.WHCD.getEname(),HgllField.WHCD.getCname());
        DataSourceTextField zpsField = new DataSourceTextField(HgllField.ZPS.getEname(),HgllField.ZPS.getCname());
        
        lyField.setLength(1000);
        lyField.setDetail(false);
        addHandleErrorHandler(deh);
        
        setFields(getIdField(),getVersionField(),xmField,xbField,srField,dhField,jgField,sfzField,lxdzField,gzdwField,mzField,ssxzField,sjField,emailField,cslbField,csgqField,whcdField,lyField,getCreatedAtField(false),zpsField);
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}

}
