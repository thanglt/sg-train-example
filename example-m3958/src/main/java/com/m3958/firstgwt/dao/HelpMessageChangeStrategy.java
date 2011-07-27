package com.m3958.firstgwt.dao;

import com.m3958.firstgwt.client.types.HelpId;
import com.m3958.firstgwt.client.types.HelpMessageField;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.HelpMessage;

public class HelpMessageChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<HelpMessage> {

	@Override
	public boolean extraPersistTask(HelpMessage model){
		setHelpId(model);
		return true;
	}

	private boolean setHelpId(HelpMessage model) {
		String hid = getReqPs().getStringValue(HelpMessageField.HELP_ID.getEname());
		if(hid != null){
			((HelpMessage)model).setHelpId(HelpId.valueOf(hid));
		}
		return true;
	}

	@Override
	public boolean extraRemoveTask(HelpMessage model) {
		return true;
	}

	@Override
	public boolean extraUpdateTask(HelpMessage model,HelpMessage newModel){
		setHelpId(model);
		return true;
	}

	@Override
	public boolean afterPersist(HelpMessage model) {
		return false;
	}
	

}
