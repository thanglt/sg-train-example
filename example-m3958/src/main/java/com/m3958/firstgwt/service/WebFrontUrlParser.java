package com.m3958.firstgwt.service;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.RequestScoped;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.dao.ArticleDao;
import com.m3958.firstgwt.dao.SectionDao;
import com.m3958.firstgwt.dao.VoteDao;
import com.m3958.firstgwt.dao.XinJianCatDao;
import com.m3958.firstgwt.dao.XinJianDao;
import com.m3958.firstgwt.model.Article;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Section;
import com.m3958.firstgwt.model.Tag;
import com.m3958.firstgwt.model.Vote;
import com.m3958.firstgwt.model.XinJian;
import com.m3958.firstgwt.model.XinJianCat;
import com.m3958.firstgwt.server.types.AppConstants;
import com.m3958.firstgwt.session.SessionUser;
import com.m3958.firstgwt.utils.FtlUtil;

import freemarker.template.Template;

@RequestScoped
public class WebFrontUrlParser {
	
	@Inject
	private AllSitesFMCfg fmCfg;
	
	@Inject
	private SiteConfigService scs;
	
	@Inject
	private Injector injector;
	
	@Inject
	private HttpServletRequest req;
	
	@Inject
	private RequestScopeObjectService rso;

	
	@Inject
	private SessionUser su;

	private Tag tag;
	private int page;
	private int numPerPage;
	
	private String obName = "";
	private int obid = SmartConstants.NONE_EXIST_MODEL_ID;
	private String tplName;
	private boolean obInited = false;
	
	
	private File thisThemeRoot;
	private boolean themeRootInited = false;
	
	private File maintenceFile;
	private boolean maintenceFileInited = false;
	
	@Inject
	public WebFrontUrlParser(Injector injector,HttpServletRequest req,SiteConfigService scs){
		this.injector = injector;
		this.scs = scs;
		this.req = req;
	}
	
	
	public File getThisThemeRoot(){
		if(!themeRootInited){
			thisThemeRoot = new File(new File(scs.getSiteRoot(), rso.getRequestWebSite().getId()+""),rso.getRequestWebHost().getTheme());
			themeRootInited = true;
		}
		return thisThemeRoot;
	}
	
	public File getMaintenceFile(){
		if(!maintenceFileInited){
			maintenceFile = new File(new File(scs.getSiteRoot(), rso.getRequestWebSite().getId()+""),"maintence.html");
			if(!maintenceFile.exists()){
				maintenceFile = null;
			}
			maintenceFileInited = true;
		}
		return maintenceFile;
	}
	
	private int getCurrentPage(){
		String p = req.getParameter("p");
		if(p == null || p.isEmpty())return 1;
		int pi = 1;
		try {
			pi = Integer.parseInt(p);
		} catch (NumberFormatException e) {}
		return pi;
	}
	
	public Map<String, Object> getRootModel(){
		Map<String,Object> rootDataModel = new HashMap<String, Object>();
		rootDataModel.put("site", rso.getRequestWebSite());
		rootDataModel.put("currentPage", getCurrentPage());
		rootDataModel.put(getObName(), getOb());
		rootDataModel.put("requestParameters", getParameterMap());
		rootDataModel.put("uri", req.getRequestURI());
		rootDataModel.put("qs", req.getQueryString());
		rootDataModel.put("referer", req.getHeader("referer"));
		rootDataModel.put("remoteip",rso.getRemoteIp());
		if(req.getQueryString() == null){
			rootDataModel.put("uriAndQs", req.getRequestURI());
		}else{
			String s = req.getQueryString();
			s = s.replaceAll("hostname=[^&]+", "");
			s = s.replaceAll("remoteip=[^&]+", "");
			s = s.replaceAll("&&", "&");
			s = StringUtils.removeStart(s, "&");
			rootDataModel.put("uriAndQs", req.getRequestURI() + "?" + s);
		}
		
		if(su.isLogined())
			rootDataModel.put("user", su);
		FtlUtil ftlUtil = injector.getInstance(FtlUtil.class);
		ftlUtil.setTplName(getTplName());
		rootDataModel.put("util", ftlUtil);
		rootDataModel.put("uris", rso.getUriSegments());
		return rootDataModel;
	}
	
	private Map<String, String>  getParameterMap() {
		Map<String, String> maps = new HashMap<String, String>();
		Map<String, String[]> m = req.getParameterMap();
		for(String s : m.keySet()){
			if(m.get(s).length == 1){
				maps.put(s, m.get(s)[0]);
			}
		}
		return maps;
	}


	private void parseId(String intstr){
		try {
			if(intstr.contains(".")){
				String[] ss = intstr.split("\\.");
				intstr = ss[0];
			}
			obid = Integer.parseInt(intstr);
		} catch (Exception e) {}
	}
	
	
	/*
	 * url 规则：
	 * 1、uri 长度 0 /
	 * 2、uri长度1 /articles
	 * 3、uri长度2 /article/1
	 */
	private void InitObNameIdTpl(){
		String[] ss = req.getRequestURI().split("/");
		if(ss.length == 0){ //根目录的情况，参数只能从queryString里面获取。
			if(req.getParameter(AppConstants.OBNAME_PARAMETER_NAME) != null){
				obName = req.getParameter(AppConstants.OBNAME_PARAMETER_NAME);
				parseId(req.getParameter(AppConstants.OBID_PARAMETER_NAME));
			}
			tplName="index";
		}else if(ss.length == 2){ ///articles，只有一个的情况下，这个是模板名称。
			if(req.getParameter(AppConstants.OBNAME_PARAMETER_NAME) != null){
				obName = req.getParameter(AppConstants.OBNAME_PARAMETER_NAME);
				parseId(req.getParameter(AppConstants.OBID_PARAMETER_NAME));
			}
			tplName = ss[1];
		}else if(ss.length == 3){ ///article/1
			obName = ss[1];
			parseId(ss[2]);
			tplName=ss[1];
		}else{//
			tplName = ss[1];
		}
		String utplname = req.getParameter(AppConstants.TPL_PARAMETER_NAME);
		if(utplname == null || utplname.isEmpty()){
			;
		}else{
			tplName = utplname;
		}
		int tt = tplName.indexOf("."); 
		if( tt != -1)tplName = tplName.substring(0, tt);
		obInited = true;
	}
	
	
	public String getTplName(){
		if(!obInited){
			getObid();
		}
		return tplName;
	}
	
	public String getObName(){
		if(!obInited){
			InitObNameIdTpl();
		}
		return obName;
	}
	
	public int getObid(){
		if(!obInited){
			getObName();
		}
		return obid;
	}

	public File getNoneFtlFile(){
		String uri = req.getRequestURI();
		if(uri.toLowerCase().endsWith(AppConstants.TPL_FILE_EXTENSION))return null;
		if(uri.startsWith("/webfront"))
			uri = uri.substring(9);
		File f = new File(getThisThemeRoot(),uri);
		if(f.exists()){
			return f;
		}
		return null;
	}
	
	public Tag getTag(String value) {
		return null;
	}
		
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public int getNumPerPage() {
			return numPerPage;
		}
		public void setNumPerPage(int numPerPage) {
			this.numPerPage = numPerPage;
		}
		
		public Template getTpl() throws IOException {
			Template t = fmCfg.getTemplate(rso.getRequestWebSite().getId() + "/" + rso.getRequestWebHost().getTheme() + "/" +  getTplName() + AppConstants.TPL_FILE_EXTENSION,req.getLocale());
			return t;
		}
		
		public Template getTpl(String tplName) throws IOException {
			Template t = fmCfg.getTemplate(rso.getRequestWebSite().getId() + "/" + rso.getRequestWebHost().getTheme() + "/" +  tplName + AppConstants.TPL_FILE_EXTENSION,req.getLocale());
			return t;
		}


		@SuppressWarnings("rawtypes")
		public BaseModel getOb() {
			if(getObName() == null || getObName().isEmpty())return null;
			if("section".equals(getObName())){
				SectionDao sdao = injector.getInstance(SectionDao.class);
				Section section = sdao.find(getObid());
				return section;
			}else if("article".equals(getObName())){
				ArticleDao sdao = injector.getInstance(ArticleDao.class);
				Article article = sdao.find(getObid());
				return article;
			}else if("xjcat".equals(getObName())){
				XinJianCatDao xjcDao = injector.getInstance(XinJianCatDao.class);
				XinJianCat xjc = xjcDao.find(getObid());
				return xjc;
			}else if("xinjian".equals(getObName())){
				XinJianDao xjDao = injector.getInstance(XinJianDao.class);
				XinJian xj = xjDao.find(getObid());
				return xj;
			}else if("vote".equals(getObName())){
				VoteDao vdao = injector.getInstance(VoteDao.class);
				Vote v = vdao.find(getObid());
				return v;
			}
			return null;
		}
		
		public Tag getTag() {
			return tag;
		}
		public void setTag(Tag tag) {
			this.tag = tag;
		}

}
