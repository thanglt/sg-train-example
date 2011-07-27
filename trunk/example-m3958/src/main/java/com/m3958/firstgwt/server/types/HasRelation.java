package com.m3958.firstgwt.server.types;

import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.session.ErrorMessages;

public interface HasRelation {
	boolean manageRelation(BaseModel bm,boolean isAdd,String hint,ErrorMessages errors);
}
