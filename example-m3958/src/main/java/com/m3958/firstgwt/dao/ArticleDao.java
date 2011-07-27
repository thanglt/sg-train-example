package com.m3958.firstgwt.dao;

import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.Article;
import com.m3958.firstgwt.model.BaseModel;

public class ArticleDao  extends BaseDao<Article>{
	
	@Inject
	public ArticleDao(ArticleChangeStrategy task) {
		super(Article.class);
		extraStrategyTask = task;
	}
	
	@Override
	public Integer smartCustomCount() {
		return null;
	}

	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}

	@Override
	public List<Article> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}
