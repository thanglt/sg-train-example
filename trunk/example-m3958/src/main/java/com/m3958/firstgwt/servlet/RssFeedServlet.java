package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.model.Article;
import com.m3958.firstgwt.model.WebHost;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.service.RequestScopeObjectService;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;


@Singleton
public class RssFeedServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Injector injector;
	
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;

	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		RequestScopeObjectService rso = injector.getInstance(RequestScopeObjectService.class);
		WebSite s = rso.getRequestWebSite();
		String before = req.getParameter("before");
		int beforedays = -1;
		if(before == null || before.isEmpty()){
			;
		}else{
			try {
				beforedays = Integer.parseInt(before);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	    try {
	        String feedType = "rss_2.0";
//	        String fileName = "rss.xml";
	        
	        SyndFeed feed = new SyndFeedImpl();
	        feed.setFeedType(feedType);
	        String hostname = null;
	        if(s.getWebhosts().size() == 1){
	        	hostname = "http://" + s.getWebhosts().get(0).getName();
	        }else{
	        	for(WebHost wh : s.getWebhosts()){
	        		if(wh.getName().indexOf(".m3958.") == -1){
	        			hostname = "http://" + wh.getName();
	        			break;
	        		}
	        	}
	        	if(hostname == null)hostname = "http://" + s.getWebhosts().get(0).getName();
	        }
	        String ftitle = s.getTitle();
	        if(ftitle == null || ftitle.isEmpty()){
	        	ftitle = "请输入feed标题。";
	        }
	        feed.setTitle(ftitle);
	        feed.setLink(hostname);
	        String fdesc = s.getMetaDescription();
	        if(fdesc == null || fdesc.isEmpty()){
	        	fdesc=s.getTitle();
	        	if(fdesc == null || fdesc.isEmpty()){
	        		fdesc = "请输入feed的描述。";
	        	}
	        }
	        feed.setDescription(fdesc);
	        
	        Calendar c = Calendar.getInstance();
	        if(beforedays == -1){
	        	if(s.getRssDateNum() == 0){
	        		c.add(Calendar.DAY_OF_MONTH, -30);//默认一个月
	        	}else{
	        		c.add(Calendar.DAY_OF_MONTH, -s.getRssDateNum());
	        	}
	        }else{
	        	c.add(Calendar.DAY_OF_MONTH, -beforedays);
	        }
		    
//	        c.add(Calendar.DAY_OF_MONTH, -1000);
	        Query qa = emp.get().createQuery("SELECT a FROM Article AS a WHERE a.siteId = :siteId AND a.audit = true AND a.updatedAt > :updatedAt");
	        qa.setParameter("updatedAt", c.getTime());
	        qa.setParameter("siteId", s.getId());
	        List<Article> ars = qa.getResultList();
	        
	        List<SyndEntry> entries = new ArrayList<SyndEntry>();
	        
	        for(Article a :ars){
		        SyndEntry entry;
		        SyndContent description;
		        entry = new SyndEntryImpl();
		        entry.setTitle(a.getTitle());
		        entry.setLink(hostname + a.getUrl());
		        entry.setPublishedDate(a.getPublishedAt());
		        description = new SyndContentImpl();
		        description.setType("text/plain");
		        description.setValue(a.getExcerpt());
		        entry.setDescription(description);
		        entry.setUpdatedDate(a.getUpdatedAt());
		        entries.add(entry);
	        }
	        feed.setEntries(entries);
	
//		        Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), Charset.forName("UTF-8"));
			res.setContentType("application/rss+xml;charset=utf-8");   
			res.setCharacterEncoding("UTF-8");
	        Writer writer = res.getWriter();
	        SyndFeedOutput output = new SyndFeedOutput();
	        output.output(feed,writer);
	        writer.close();
	      }
	      catch (Exception ex) {
	          ex.printStackTrace();
	      }
	}

}
