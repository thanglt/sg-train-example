package com.m3958.firstgwt.dao;

import com.m3958.firstgwt.model.Comment;


public class CommentChangeStrategy  extends TreeChangeStrategy implements ModelChangeStrategy<Comment> {
	
	
	@Override
	public boolean extraPersistTask(Comment model) {
		return true;
	}


	@Override
	public boolean extraUpdateTask(Comment model,Comment newModel) {
		return true;
	}
	


	@Override
	public boolean extraRemoveTask(Comment model) {
		return true;
	}
	

	@Override
	public boolean afterPersist(Comment model) {
		return false;
	}
}
