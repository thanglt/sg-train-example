package com.m3958.firstgwt.dao;


import com.m3958.firstgwt.model.BaseModel;

public abstract class HasRelationDao<T extends BaseModel<T>> extends BaseDao<T>{

	public HasRelationDao(Class<T> persistentClass) {
		super(persistentClass);
	}
}
