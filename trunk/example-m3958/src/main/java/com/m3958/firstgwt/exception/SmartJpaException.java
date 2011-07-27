package com.m3958.firstgwt.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.m3958.firstgwt.client.errorhandler.SmartBaseException;

@SuppressWarnings("serial")
public class SmartJpaException extends SmartBaseException {
	
	private final static Pattern nullErrorCodePattern = Pattern.compile("Error\\s+Code:\\s+(\\d+)\\D");
	private final static Pattern nullColumnPattern = Pattern.compile("Column\\s+'(\\S+)'\\s+[a-zA-Z\\s]+null");
	
	private String errorCode = null;
	private String errorMessage = null;
	

	public SmartJpaException(Exception exp) {
		super(exp);
	}

	/*
	 * (non-Javadoc)
	 * @see com.m3958.firstgwt.exception.BaseException#createNewMessage(java.lang.Exception)
	 * 对我们来说主要面对的是：违反字段唯一性，违反字段不可null两种情况。
	 */
	@Override
	protected void createNewMessage(Exception exp) {
		exp.printStackTrace();
		String ori = exp.getMessage();
		Matcher m = nullErrorCodePattern.matcher(ori);
		if(m.find()){
			errorCode = m.group(1);
		}
		
		if("1048".equals(errorCode)){
			m = nullColumnPattern.matcher(ori);
			if(m.find()){
				errorMessage = "表单的'" + m.group(1) + "'字段不能是空白！";
			}
		}
		
		if(errorMessage != null){
			message = errorMessage;
		}else{
			message = exp.getMessage();
		}
	}

}
