package com.m3958.firstgwt.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.model.Article;
import com.m3958.firstgwt.model.Section;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.service.RequestScopeObjectService;


@Singleton
public class JavaScriptDataModelServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Injector injector;
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	@Inject
	private AppUtilService autils;
	
	//{obname:site,obid:-1,fn:findSection,params:["abc"]}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		RequestScopeObjectService rso = injector.getInstance(RequestScopeObjectService.class);
		String j = req.getParameter("j");
		JSONObject jo = JSONObject.fromObject(j);
		WebSite ws = rso.getRequestWebSite();
		String fn = jo.getString("fn");
		if(fn == null || fn.isEmpty() || fn.startsWith("find") || fn.startsWith("get")){
			;
		}else{
			fn = "get" + fn.substring(0, 1).toUpperCase() + fn.substring(1);
		}
		String obname = jo.getString("obname");
		if("site".equals(obname)){
			if(fn == null || fn.isEmpty() || "null".equals(fn)){
				autils.writeJsonResponse(res, autils.getObjectValueResponse(ws.toJson()));
				return;
			}else if("findSection".equals(fn)){
				Section section = ws.findSection(jo.getJSONArray("params").getString(0));
				autils.writeJsonResponse(res,autils.getObjectValueResponse(section.toJson()));
				return;				
			}
		}else if("section".equals(obname)){
			if("getArticles".equals(fn)){
				Section section = emp.get().find(Section.class, jo.getInt("obid"));
				autils.writeJsonResponse(res, autils.getListResponse(section.getArticles(), 0, section.getArticles().size()));
				return;
			}
		}else if("article".equals(obname)){
			if(fn == null || fn.isEmpty()){
				Article a = emp.get().find(Article.class, jo.getInt("obid"));
				autils.writeJsonResponse(res, autils.getObjectValueResponse(a.toJsonOne()));
				return;
			}
		}

	}
	
}
