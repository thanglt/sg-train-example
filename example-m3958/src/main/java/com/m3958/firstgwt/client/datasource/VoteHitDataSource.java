package com.m3958.firstgwt.client.datasource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.VoteHitField;
import com.smartgwt.client.data.fields.DataSourceTextField;

@Singleton
public class VoteHitDataSource extends BaseDataSource{

    public static String className = "com.m3958.firstgwt.model.VoteHit";
    
    @Inject
    public VoteHitDataSource(DsErrorHandler deh) {
    	setID("voteHitDS");
        DataSourceTextField nameField = new DataSourceTextField(CommonField.NAME.getEname(),CommonField.NAME.getCname());
        DataSourceTextField refererField = new DataSourceTextField(VoteHitField.REFERER.getEname(),VoteHitField.REFERER.getCname());
        DataSourceTextField ipField = new DataSourceTextField(VoteHitField.VOTE_IP.getEname(),VoteHitField.VOTE_IP.getCname());

        addHandleErrorHandler(deh);

        setFields(getIdField(),nameField,refererField,ipField,getCreatedAtField(false),getVersionField());
        setClientOnly(false);
       
    }

	@Override
	protected String getModelClassName() {
		return className;
	}
}
