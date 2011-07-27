package com.m3958.firstgwt.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
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

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.utils.StringUtils;
import com.m3958.firstgwt.dao.AssetDao;
import com.m3958.firstgwt.dao.CachedContentDao;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.CachedContent;
import com.m3958.firstgwt.server.types.CharactSetName;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.service.RequestScopeObjectService;
import com.m3958.firstgwt.service.SiteConfigService;
import com.m3958.firstgwt.service.WebFrontUrlParser;

import freemarker.template.Template;
import freemarker.template.TemplateException;


@Singleton
public class WebFrontServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Injector injector;
	
	@Inject
	private AppUtilService autils;
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	@Inject
	private SiteConfigService scs;
	
	private boolean error404Inited = false;
	
	private String error404Content;
	
	private Pattern pattern = Pattern.compile("");
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		RequestScopeObjectService rso = injector.getInstance(RequestScopeObjectService.class);
		WebFrontUrlParser wfup = null;
		try {
			wfup = injector.getInstance(WebFrontUrlParser.class);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		
		
		
		try {
			if(!rso.getRequestWebHost().isAudit()){
				res.setContentType("text/html; charset=UTF-8");   
				res.setCharacterEncoding("UTF-8");
				Writer w = res.getWriter();
				w.write("此域名尚未经管理员审核，请联系管理员。奉化市诗篇网络科技有限公司，jianglibo@gmail.com");
				w.close();
			}
			
			if(rso.getRequestWebSite().isStop()){
				res.setContentType("text/html; charset=UTF-8");   
				res.setCharacterEncoding("UTF-8");
				Writer w = res.getWriter();
				if(wfup.getMaintenceFile() != null ){
					Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(wfup.getMaintenceFile()),CharactSetName.UTF_8));
			        int numRead=0;
			        char[] buf = new char[1024];
			        while((numRead=reader.read(buf)) != -1){
			        	w.write(buf, 0, numRead);
			        }
			        reader.close();
			        w.close();
			        return;
				}
				w.write("网站维护中.......，请稍候再来访问！");
				w.close();
				return;
			}
			
			
			
			File f = wfup.getNoneFtlFile();
			if(f != null && f.isFile()){
				autils.sendSiteFile(req, res, f, getServletContext().getMimeType(f.getAbsolutePath()));
			    return;
			}
		} catch (Exception e1) {
			res.setContentType("text/html; charset=UTF-8");   
			res.setCharacterEncoding("UTF-8");
			Writer w = res.getWriter();
			w.write("该域名没有对应的网站！");
			w.close();
			return;
		}
		
		if(rso.getRequestWebHost() == null){
			return;
		}
		
		if("asset".equals(wfup.getObName())){
			feedAsset(wfup,req,res);
			return;
		}
		
		Template template = wfup.getTpl();
		
		res.setContentType("text/html; charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		String content = null;
		String etag = UUID.randomUUID().toString();
		PrintWriter writer = res.getWriter();
		try {
			StringWriter sWriter = new StringWriter();
			Map<String, Object> rm = wfup.getRootModel();
			rm.put("tplname", template.getName());
			template.process(rm, sWriter);
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
		
		content = filterContent(content);
		
		res.setHeader("Etag", etag);
		writer.write(content);
		writer.close();
		if(rso.getRequestWebSite().isCacheEnable())
			saveCacheCopy(req,wfup,rso,content,etag);
	}

	private String filterContent(String content) {
		
		return content;
	}

	private void send404(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		res.setContentType("text/html; charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		res.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		Writer w = res.getWriter();
		if(scs.getError404File() == null || scs.getError404File().isEmpty()){
			w.write("404.请求的文件不存在！奉化市诗篇网络科技有限公司,jianglibo@gmail.com");
			w.close();
			return;
		}else{
			if(!error404Inited){
				StringBuffer sb = new StringBuffer();
				Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(scs.getError404File()),CharactSetName.UTF_8));
			    int numRead=0;
			    char[] buf = new char[1024];
			    while((numRead=reader.read(buf)) != -1){
			    	sb.append(buf,0,numRead);
			    }
			    reader.close();
			    error404Content = sb.toString();
			    error404Inited = true;
			}
			w.write(error404Content);
			w.close();
		}
	}
	
	private void feedAsset(WebFrontUrlParser wfup,HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		if(wfup.getObid() == SmartConstants.NONE_EXIST_MODEL_ID){
			send404(req,res);
			return;
		}
		AssetDao dao = injector.getInstance(AssetDao.class);
		Asset a = dao.find(wfup.getObid());
		
		String size = req.getParameter("size");
		
		Pattern p = Pattern.compile("/asset/\\d+\\.(\\d+x\\d+).*?");
		Matcher m = p.matcher(req.getRequestURI());
		if(m.matches()){
			size = m.group(1);
		}
		
		File bf = new File(scs.getAssetSavePath());
		File f;
		if(size == null || size.isEmpty()){
			f = new File(bf,a.getFilePath());
		}else{
			f = new File(bf,StringUtils.getFileNameAppend(a.getFilePath(), size));
			if(!f.exists()){
				f = new File(bf,a.getFilePath());
//				send404(req, res);
			}
		}
		if(!f.exists()){
			send404(req, res);
			return;
		}
		autils.sendSiteFile(req, res, f, getServletContext().getMimeType(f.getAbsolutePath()));
	}

	private void saveCacheCopy(HttpServletRequest req,WebFrontUrlParser wfup,RequestScopeObjectService rso,String content,String etag){
		
		Query q1 = emp.get().createNamedQuery(CachedContent.NamedQueries.FIND_BY_URL);
		q1.setParameter("urlPlusQueryString", req.getRequestURL().append(req.getQueryString()).toString());
		
		CachedContent cc = null;
		try {
			cc = (CachedContent) q1.getSingleResult();
		} catch (Exception e) {}
		
		CachedContentDao ccdao = injector.getInstance(CachedContentDao.class);
		
		if(cc == null){
			cc = new CachedContent();
			setCCContent(req, wfup,rso, content, etag, cc);
			ccdao.smartPersistBaseModel(cc);
		}else{
			setCCContent(req, wfup,rso, content, etag, cc);
			ccdao.merge(cc);
		}
	}

	private void setCCContent(HttpServletRequest req, WebFrontUrlParser wfup,RequestScopeObjectService rso,
			String content, String etag, CachedContent cc) {
		cc.setContent(content);
		cc.setHostName(rso.getRequestWebHost().getName());
		cc.setTheme(rso.getRequestWebHost().getTheme());
		cc.setTplName(wfup.getTplName());
		cc.setObName(wfup.getObName());
		cc.setObid(wfup.getObid());
		cc.setRequestUrl(req.getRequestURL().append(req.getQueryString()).toString());
		cc.setEtag(etag);
	}
}
