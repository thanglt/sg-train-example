package com.m3958.firstgwt.command;



import java.io.IOException;

import com.m3958.firstgwt.exception.SmartJpaException;

public interface Command {
	public void execute() throws SmartJpaException, IOException;
	public void execute(boolean writeResponse) throws SmartJpaException, IOException;
	public String getResult();
}
