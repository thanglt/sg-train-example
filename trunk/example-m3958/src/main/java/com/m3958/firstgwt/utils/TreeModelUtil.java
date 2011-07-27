package com.m3958.firstgwt.utils;

import java.util.ArrayList;
import java.util.List;

import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.server.types.TreeModel;

public class TreeModelUtil {

	public <T extends BaseModel<T>> List<T> getSiblings(TreeModel<T> tm){
		if(tm.getParent()!=null){
			return tm.getParent().getChildren();
		}
		return new ArrayList<T>();
	}
}
