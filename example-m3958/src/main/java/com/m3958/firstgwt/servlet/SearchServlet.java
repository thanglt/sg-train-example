package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.model.Article;
import com.m3958.firstgwt.model.Section;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.service.ReqParaService;
import com.m3958.firstgwt.service.WebFrontUrlParser;
import com.m3958.firstgwt.utils.SimplePaginator;

import freemarker.template.Template;
import freemarker.template.TemplateException;


@Singleton
public class SearchServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Injector injector;
	
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	/*
	 * 查找，bytitle，bycontent，bytag，bysection，obname
	 * 参数：searchfor
	 */
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		ReqParaService reqps = injector.getInstance(ReqParaService.class);
		WebFrontUrlParser wfup = null;
		try {
			wfup = injector.getInstance(WebFrontUrlParser.class);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		
		String searchFor = req.getParameter("searchfor");
		Map<String, Object> rootDataModel = wfup.getRootModel();
		if("article".equals(searchFor)){
			rootDataModel = prepairArticleDataModel(reqps,rootDataModel);
		}else{
			rootDataModel = prepairArticleDataModel(reqps,rootDataModel);
		}
		
		Template template = wfup.getTpl();
		
		res.setContentType("text/html; charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		String content = null;
		String etag = UUID.randomUUID().toString();
		PrintWriter writer = res.getWriter();
		try {
			StringWriter sWriter = new StringWriter();
			rootDataModel.put("tplname", template.getName());
			template.process(rootDataModel, sWriter);
			content = sWriter.toString();
			res.setContentLength(content.getBytes("UTF8").length);
		} catch (TemplateException e) {
			e.printStackTrace(writer);
			return;
		}
		if(content == null || content.isEmpty()){
			res.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		}
	
		res.setHeader("Etag", etag);
		writer.write(content);
		writer.close();
	}
	/*
	 * 如果有sw参数，那么只要提供q参数即可。
	 * 如果没有sw参数，那么需要提供qtitle和qcontent参数。
	 * searchfor
	 */
	
	private Map<String, Object> prepairArticleDataModel(ReqParaService reqps,Map<String, Object> datamodel){
		String searchwhat = reqps.getStringValue("sw", "");
		boolean isand = reqps.getBooleanValue("isand", false);
		String q = reqps.getStringValue("q", "");
		String qtitle = reqps.getStringValue("qtitle", "");
		String qcontent = reqps.getStringValue("qcontent", "");
		int cid = reqps.getIdValue("cid");
		WebSite ws = (WebSite)datamodel.get("site");
		int currentPage = (Integer) datamodel.get("currentPage");
		if(cid == SmartConstants.NONE_EXIST_MODEL_ID){
			if("title".equals(searchwhat)){//有searchwhat，只要提供q即可。
				List<Article> ars = ws.getSearchPage(q,"",isand, currentPage);
				SimplePaginator sp = ws.getSearchPaginator(q,"",isand, currentPage);
				datamodel.put("articles", ars);
				datamodel.put("paginator", sp);
				datamodel.put("q", q);
				datamodel.put("qtitle", q);
				datamodel.put("qcontent", "");
			}else if("content".equals(searchwhat)){
				List<Article> ars = ws.getSearchPage("",q,isand, currentPage);
				SimplePaginator sp = ws.getSearchPaginator("",q,isand, currentPage);
				datamodel.put("articles", ars);
				datamodel.put("paginator", sp);
				datamodel.put("q", q);
				datamodel.put("qtitle", "");
				datamodel.put("qcontent", q);
			}else{//没有searchWhat的话，应该提供qtitle和qcontent
				List<Article> ars = ws.getSearchPage(qtitle,qcontent,isand,currentPage);
				SimplePaginator sp = ws.getSearchPaginator(qtitle,qcontent,isand, currentPage);
				datamodel.put("articles", ars);
				datamodel.put("paginator", sp);
				datamodel.put("q", "");
				datamodel.put("qtitle", qtitle);
				datamodel.put("qcontent", qcontent);
			}
			datamodel.put("cid", SmartConstants.NONE_EXIST_MODEL_ID);
		}else{
			Section section = emp.get().find(Section.class, cid);
			if("title".equals(searchwhat)){
				List<Article> ars = section.getSearchPage(q,"",isand, currentPage);
				SimplePaginator sp = section.getSearchPaginator(q,"",isand, currentPage);
				datamodel.put("articles", ars);
				datamodel.put("paginator", sp);
				datamodel.put("q", q);
				datamodel.put("qtitle", q);
				datamodel.put("qcontent", "");
			}else if("content".equals(searchwhat)){
				List<Article> ars = section.getSearchPage("",q,isand, currentPage);
				SimplePaginator sp = section.getSearchPaginator("",q,isand, currentPage);
				datamodel.put("articles", ars);
				datamodel.put("paginator", sp);
				datamodel.put("q", q);
				datamodel.put("qtitle", "");
				datamodel.put("qcontent", q);
			}else{
				List<Article> ars = section.getSearchPage(qtitle,qcontent,isand,currentPage);
				SimplePaginator sp = section.getSearchPaginator(qtitle,qcontent,isand, currentPage);
				datamodel.put("articles", ars);
				datamodel.put("paginator", sp);
				datamodel.put("q", "");
				datamodel.put("qtitle", qtitle);
				datamodel.put("qcontent", qcontent);
			}
			datamodel.put("cid", cid);
		}

		return datamodel;
	}
}
