package com.m3958.firstgwt.server.types;

import java.util.List;

import com.m3958.firstgwt.model.BaseModel;


public interface TreeModel<T extends BaseModel<T>> {
	public TreeModel<T> getParent();
	
	public void setParent(T parent);
	
	public List<T> getChildren();
	
	public void setChildren(List<T> children);
	
	public boolean addChildren(T bm);
	
	public boolean removeChildren(T bm);
	
	public int getParentId();
	
	public boolean isFolder();
	
	public int getId();
}
