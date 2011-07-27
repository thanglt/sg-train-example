package com.m3958.firstgwt.command;

import java.io.IOException;

import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.exception.SmartJpaException;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.server.response.ErrorMessageResponse;

public class UpdateCommand extends BaseCommand implements Command{
	
	private String result = null;
	
	private boolean writeResponse = true;

	@Override
	public void execute() throws SmartJpaException, IOException{

		SmartSubOperationName sson = SmartSubOperationName.NO_SUB_OPERATION;
		
		try {
			sson = SmartSubOperationName.valueOf(reqPs.getSubOpType());
		} catch (Exception e) {}
		
		switch (sson) {
		default:
			result = smartUpdate();
			break;
		}
		
		if(!errors.isEmpty()){
			result = injector.getInstance(ErrorMessageResponse.class).toString();
		}
		if(writeResponse)
			autils.writeJsonResponse(res,result);
	}

	private String smartUpdate() throws SmartJpaException {
		BaseModel model = getBaseModelFromParams();
		BaseModel bm = getDao().smartUpdateBaseModel(model);
		return  autils.getListResponse(bm);
	}

	@Override
	public String getResult() {
		return result;
	}
	
	@Override
	public void execute(boolean writeResponse) throws SmartJpaException,
			IOException {
		this.writeResponse = writeResponse;
		execute();
	}

}
