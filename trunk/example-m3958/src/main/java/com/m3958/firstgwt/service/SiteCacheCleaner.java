package com.m3958.firstgwt.service;

import java.util.List;

import javax.persistence.EntityManager;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.dao.ArticleDao;
import com.m3958.firstgwt.dao.CacheBreakerItemDao;
import com.m3958.firstgwt.dao.CachedContentDao;
import com.m3958.firstgwt.dao.SectionDao;
import com.m3958.firstgwt.model.Article;
import com.m3958.firstgwt.model.CacheBreakerItem;
import com.m3958.firstgwt.model.Section;
import com.m3958.firstgwt.model.WebHost;
import com.m3958.firstgwt.model.WebSite;

@Singleton
public class SiteCacheCleaner {
	
	@Inject
	private CacheBreakerItemDao cbiDao;
	
	@Inject
	private CachedContentDao ccDao;

	@Inject
	private ArticleDao adao;
	
	@Inject
	private SectionDao sdao;
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	public void cleanCache(int siteId){
		List<CacheBreakerItem> bitems = cbiDao.getQuequedItem(siteId, false);
		for(CacheBreakerItem cbi : bitems){
			switch (cbi.getObType()) {
			case ARTICLE:
				clearArticleRelationCache(cbi);
				break;
			case SECTION:
				clearSectionRelationCache(cbi);
				break;
			case TEMPLATE:
				clearTemplateRelationCache(siteId,cbi);
				break;
			default:
				break;
			}
		}
	}
	
	private void clearArticleRelationCache(CacheBreakerItem cbi){
		//一篇文章的改变，就是将cachedcontent中obName = artilce，obid = cbi.obid的内容删除！
		ccDao.removeItemByOid("article",cbi.getObId());
		Article a = adao.find(cbi.getObId());
		if(a!=null){
			for(Section se : a.getSections()){
				Section tse = se;
				while(tse != null){
					ccDao.removeItemByOid("section", tse.getId());
					tse = tse.getParent();
				}
			}
		}
	}

	private void clearSectionRelationCache(CacheBreakerItem cbi){
		ccDao.removeItemByOid("section",cbi.getObId());
		Section s = sdao.find(cbi.getObId());
		if(s!=null){
			Section tse = s;
			while(tse != null){
				ccDao.removeItemByOid("section", tse.getId());
				tse = tse.getParent();
			}
		}
	}
	
	private void clearTemplateRelationCache(int siteId,CacheBreakerItem cbi){
		WebSite ws = emp.get().find(WebSite.class, siteId);
		if(ws != null){
			for(WebHost wh : ws.getWebhosts()){
				ccDao.removeItemByTpl(wh.getName(), cbi.getObName());
			}
		}
	}
	
}
