package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;
import nl.captcha.Captcha;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.exception.SmartJpaException;
import com.m3958.firstgwt.model.Article;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Comment;
import com.m3958.firstgwt.model.Hgll;
import com.m3958.firstgwt.model.HtmlCss;
import com.m3958.firstgwt.model.PageMistake;
import com.m3958.firstgwt.model.VoteHit;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.model.XinJian;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.service.AllSitesFMCfg;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.service.ReqParaService;
import com.m3958.firstgwt.service.RequestScopeObjectService;
import com.m3958.firstgwt.service.WebFrontUrlParser;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.session.SessionUser;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Singleton
public class ViewSourceServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Injector injector;
	
	@Inject
	private AppUtilService autils;
	
	@Inject
	private AllSitesFMCfg fmCfg;
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	Pattern p = Pattern.compile("<#include\\s+\"([^\"]+)\"", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);

	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String tplname = req.getParameter("tplname");
		Template t = fmCfg.getTemplate(tplname);
		String s = t.toString();
		Matcher m = p.matcher(s);
		List<String> includes = new ArrayList<String>();
		String[] baseName = StringUtils.split(tplname,"/");
		while(m.find()){
			String tn = m.group(1);
			baseName[baseName.length - 1] = tn;
			includes.add(StringUtils.join(baseName,"/"));
		}
		
		String es = StringEscapeUtils.escapeHtml(s);
		
		Map<String, Object> rootModel = new HashMap<String, Object>();
		rootModel.put("tplsource", es);
		rootModel.put("includes", includes);
		rootModel.put("tplname", tplname);
		Template myt = fmCfg.getTemplate("viewsourcetpl.ftl");
		res.setContentType("text/html; charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		Writer out = res.getWriter();
		try {
			myt.process(rootModel, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	
}
