package com.m3958.firstgwt.client.utils;

import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

public class FormValidatorUtils {
	public static RegExpValidator getEmailValidator(){
        RegExpValidator emailValidator = new RegExpValidator();
        emailValidator.setExpression("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");
        return emailValidator;
	}
	
	public static RegExpValidator getDigitalValidator(){
        RegExpValidator emailValidator = new RegExpValidator();
        emailValidator.setExpression("^([0-9]){6,20}$");
        return emailValidator;
	}
	
	public static LengthRangeValidator getLengthRangeValidator(int min,int max){
	    LengthRangeValidator lv = new LengthRangeValidator();
	    lv.setMin(min);
	    lv.setMax(max);
	    return lv;
	}

}
