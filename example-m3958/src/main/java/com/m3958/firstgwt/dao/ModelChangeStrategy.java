package com.m3958.firstgwt.dao;



import com.m3958.firstgwt.model.BaseModel;


public interface ModelChangeStrategy<T extends BaseModel<T>> {
	boolean extraPersistTask(T model);
	boolean afterPersist(T model);
	boolean extraUpdateTask(T oldModel,T newModel);
	boolean extraRemoveTask(T model);
}
