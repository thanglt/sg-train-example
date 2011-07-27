package com.m3958.firstgwt.command;

import java.io.IOException;

import com.m3958.firstgwt.exception.SmartJpaException;

public class NoOperationCommand extends BaseCommand implements Command {

	@Override
	public void execute() throws SmartJpaException, IOException{
		autils.writeJsonResponse(res,"不支持的操作类型！");
	}

	@Override
	public String getResult() {
		return null;
	}

	@Override
	public void execute(boolean writeResponse) throws SmartJpaException,
			IOException {
		// TODO Auto-generated method stub
		
	}

}
