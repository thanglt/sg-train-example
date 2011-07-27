package com.m3958.firstgwt.dao;


import java.util.List;

import javax.persistence.Query;

import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Token;

public class TokenDao  extends BaseDao<Token>{

	public TokenDao() {
		super(Token.class);
	}

	@Override
	public Integer smartCustomCount() {
		return null;
	}
	
	public Token findTokenByUuid(String uuid){
		String qs = "SELECT t FROM Token AS t WHERE t.tokenUuid = :uuid";
		Query q = emp.get().createQuery(qs);
		q.setParameter("uuid", uuid);
		return (Token) q.getSingleResult();
	}

	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}

	@Override
	public List<Token> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}





}
