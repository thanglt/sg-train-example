package com.m3958.firstgwt.dao;


import com.m3958.firstgwt.model.Token;


public class TokenChangeStrategy extends BaseModelChangeStrategy  implements ModelChangeStrategy<Token> {


	@Override
	public boolean extraPersistTask(Token model){
		return true;
	}

	@Override
	public boolean extraRemoveTask(Token model) {
		return true;
	}

	@Override
	public boolean extraUpdateTask(Token model,Token newModel){
		return true;
	}
	

	@Override
	public boolean afterPersist(Token model) {
		return true;
	}
}
