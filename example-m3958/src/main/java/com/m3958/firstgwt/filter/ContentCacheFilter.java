package com.m3958.firstgwt.filter;

import java.io.IOException;
import java.io.Writer;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.model.CachedContent;
import com.m3958.firstgwt.server.types.AppConstants;
import com.m3958.firstgwt.service.RequestScopeObjectService;
import com.m3958.firstgwt.service.WebFrontUrlParser;

@Singleton
public class ContentCacheFilter implements Filter {
	
	private ServletContext sc;
	private FilterConfig fc;
  
	@Inject
	private Injector injector;
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
                       FilterChain chain)
                       throws IOException, ServletException {
	    HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
	    RequestScopeObjectService rso = injector.getInstance(RequestScopeObjectService.class);
	    String uri = request.getRequestURI();
	    String tplName = request.getParameter(AppConstants.TPL_PARAMETER_NAME);
	    if((tplName == null || tplName.isEmpty()) && "/".equals(uri)){
	    	tplName = "index";
	    }
	    
	    if(tplName == null || tplName.isEmpty()){
	    	chain.doFilter(request, response);
	    	return;
	    }else{
    		WebFrontUrlParser wfu = injector.getInstance(WebFrontUrlParser.class);
    		if(rso.getRequestWebSite() == null || !rso.getRequestWebSite().isCacheEnable()){
    	    	chain.doFilter(request, response);
    	    	return;
    		}
	    	String etag = request.getHeader("If-None-Match");
	    	Query q = emp.get().createNamedQuery(CachedContent.NamedQueries.FIND_BY_ETAG);
	    	q.setParameter("etag", etag);
	    	CachedContent cc = null;
	    	try {
				cc = (CachedContent) q.getSingleResult();
			} catch (Exception e) {}
	    	if(cc != null){//回头客，并且缓存还是存在
	    		response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
	    		return;
	    	}else{//如果是新的客户，并且缓存已经存在呢？
//	    		Query q1 = emp.get().createNamedQuery(CachedContent.NamedQueries.FIND_BY_FIELDS);
//	    		q1.setParameter("hostName", wfu.getHostName());
//	    		q1.setParameter("theme", wfu.getThemeName());
//	    		q1.setParameter("tplName", wfu.getTplName());
//	    		q1.setParameter("obName", wfu.getObName());
//	    		q1.setParameter("obid", wfu.getObid());
	    		
	    		Query q1 = emp.get().createNamedQuery(CachedContent.NamedQueries.FIND_BY_URL);
	    		q1.setParameter("urlPlusQueryString", request.getRequestURL().append(request.getQueryString()).toString());
//	    		q1.setParameter("theme", wfu.getThemeName());
//	    		q1.setParameter("tplName", wfu.getTplName());
//	    		q1.setParameter("obName", wfu.getObName());
//	    		q1.setParameter("obid", wfu.getObid());
	    		
	    		try {
					cc = (CachedContent) q1.getSingleResult();
				} catch (Exception e) {				}
	    		if(cc != null){//新客户，但不是第一个请求者
	    			response.setContentType("text/html; charset=UTF-8");   
	    			response.setCharacterEncoding("UTF-8");
	    			response.setHeader("ETag", cc.getEtag());
	    			response.setContentLength(cc.getContent().getBytes("UTF8").length);
	    			response.addDateHeader("Last-Modified", cc.getCreatedAt().getTime());
	    			Writer writer = response.getWriter();
	    			writer.write(cc.getContent());
	    			writer.close();
	    		}else{
	    			chain.doFilter(request, response);//继续，由servlet来产生新的内容，并且设置etag
	    		}
	    	}
	    }
	}
  
  @Override
  public void init(FilterConfig filterConfig) {
    this.fc = filterConfig;
    this.sc = filterConfig.getServletContext();
  }

  public void destroy() {
    this.sc = null;
    this.fc = null;
  }
}
