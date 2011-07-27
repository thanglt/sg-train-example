package com.m3958.firstgwt.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.dao.WebHostDao;
import com.m3958.firstgwt.model.WebHost;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.types.CharactSetName;
import com.m3958.firstgwt.service.FilePathService;
import com.m3958.firstgwt.service.SiteConfigService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Singleton
public class CmsDemoServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Injector injector;
	
	@Inject
	private SiteConfigService scs;
	
	private Pattern p = Pattern.compile("\\s+(\\d+)\\s*\\.\\.\\s*(\\d+)\\s+",Pattern.DOTALL | Pattern.MULTILINE);
	
	@Inject
	private FilePathService fps;
	
	private WebSite demoSite;
	
	private File demoFtlRoot;
	
	private Map<String, Object> rootModel = new HashMap<String, Object>();
	
	private Configuration cf = new Configuration();
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		if(demoSite == null)initDemoSite();
		
		
		String ftlName = req.getParameter("ftlname");
		
		List<String> ftlNames = new ArrayList<String>();
		
		res.setContentType("text/html; charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		PrintWriter writer = res.getWriter();
		
		if(ftlName == null || ftlName.isEmpty()){
			for(String s : demoFtlRoot.list()){
				if(s.endsWith(".ftl") && !s.matches(".*_\\d{4}_.*")){
					ftlNames.add(s.substring(0,s.length()-4));
				}
			}
			String s = JSONArray.fromObject(ftlNames).toString();
			writer.write(s);
			writer.flush();
			writer.close();
			return;
		}
		File f = new File(demoFtlRoot,ftlName + ".ftl");
		if(!f.getAbsolutePath().startsWith(demoFtlRoot.getAbsolutePath())){return;}
		String templateStr = fps.getContents(f, CharactSetName.UTF_8);

		writer.write(templateStr);
		writer.flush();
		writer.close();
		
	}
	
	private void initDemoSite(){
		WebHostDao whdao = injector.getInstance(WebHostDao.class);
		WebHost wh = whdao.findByName(scs.getDemoHostName());
		demoSite = wh.getWebSite();
		demoFtlRoot = new File(new File(new File(scs.getSiteRoot(), demoSite.getId()+""),wh.getTheme()),"demoftl");
		if(!demoFtlRoot.exists()){
			demoFtlRoot.mkdir();
		}
		rootModel.put("site", demoSite);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		if(demoSite == null)initDemoSite();
		String templateStr = req.getParameter("ftlTpl");
		String bianmachecker = req.getParameter("bianmachecker");
		if(!"我是utf8".equals(bianmachecker)){
			templateStr = new String(templateStr.getBytes(CharactSetName.ISO_8859_1),CharactSetName.UTF_8);
		}
		Matcher m = p.matcher(templateStr);
		boolean b = m.find();
		while(b){
			Integer i1 = Integer.parseInt(m.group(1));
			Integer i2 = Integer.parseInt(m.group(2));
			int looptimes;
			if(i1 > i2){
				looptimes = i1 -i2;
			}else{
				looptimes = i2 - i1;
			}
			if(looptimes > 1000)return;
			b = m.find();
		}
		
		
		Template t = new Template("name", new StringReader(templateStr),cf);
		
		res.setContentType("text/html; charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		String content = null;
		PrintWriter writer = res.getWriter();
		try {
			StringWriter sWriter = new StringWriter();
			t.process(rootModel, sWriter);
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
		writer.write(content);
		writer.close();
	}
}
