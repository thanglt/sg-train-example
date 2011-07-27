package com.m3958.firstgwt.client.rpc;

import com.m3958.firstgwt.client.types.SmartResponseFieldName;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.SC;

public abstract class  MyDsCallback implements DSCallback{
	
	@Override
	public void execute(DSResponse response, Object rawData, DSRequest request) {
		if(response.getStatus() != RPCResponse.STATUS_SUCCESS){
			String es = response.getData()[0].getAttribute(SmartResponseFieldName.ERROR_MESSAGE);
			if(es.contains("Duplicate entry")){
				SC.warn("重复的记录！");
			}else{
				SC.warn(es);
			}
		}else{
			afterSuccess(response, rawData, request);
		}
	}
	public abstract void afterSuccess(DSResponse response, Object rawData, DSRequest request);

}
