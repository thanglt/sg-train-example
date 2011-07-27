package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.service.WebContextFMCfg;
import com.m3958.firstgwt.session.SessionUser;
import com.m3958.firstgwt.utils.CookieUtils;
import com.m3958.firstgwt.utils.FriendConnectUtil;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Singleton
public class FreeMarkerServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Injector injector;
	
	@Inject
	private WebContextFMCfg fmCfg;
	
	private static String LOGOUT_ACTION = "logout";
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		if(action == null)return;
		if(LOGOUT_ACTION.equals(action)){
			doLogout(req,res);
		}
	}

	private void doLogout(HttpServletRequest req, HttpServletResponse res) throws IOException{

		
		SessionUser su = injector.getInstance(SessionUser.class);
		su.clearContent();
		
		if(CookieUtils.getCookieValue(req.getCookies(), FriendConnectUtil.FCCOOKIENAME) == null){
			if(req.getHeader("Host").contains("127.0.0.1")){
				res.sendRedirect("/Firstgwt.html?gwt.codesvr=127.0.0.1:9997");
			}else{
				String ul = req.getScheme() + "://" + req.getServerName();
				res.sendRedirect(ul);
			}
			return;
		}
		
		res.setContentType("text/html;charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		try {
			Template template = fmCfg.getTemplate("logout.html");
			Writer writer = res.getWriter();
			template.process(null, writer);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
	}

}
