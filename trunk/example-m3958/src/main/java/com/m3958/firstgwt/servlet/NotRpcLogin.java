package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.dao.UserDao;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.service.MySHAService;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.session.SessionUser;

@Singleton
public class NotRpcLogin extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AppUtilService apputils;
	
	@Inject
	private Injector injector;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
	                               throws ServletException, IOException {
		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		String want = req.getParameter("want");
		String answer = req.getParameter("answer");
		String lo = req.getParameter("logout");
		
		if(lo != null && lo.length() > 0){
			SessionUser su = injector.getInstance(SessionUser.class);
			su.clearContent();
			if(req.getParameter("hostname") == null || req.getParameter("hostname").isEmpty()){
				res.sendRedirect("http://127.0.0.1:8888/openid.html");
			}else{
				res.sendRedirect("http://" +  req.getParameter("hostname") + "/openid.html");
			}
			
			return;
		}
		
		
		Captcha captcha = (Captcha) req.getSession().getAttribute(Captcha.NAME);
		
		if(captcha == null || !captcha.isCorrect(answer)){
			ErrorMessages ems = injector.getInstance(ErrorMessages.class);
			ems.addError(new Error("验证码错误！", ServerErrorCode.CAPTCHA_ERROR));
			apputils.writeJsonErrorResponse(res);
			return;
		}
		req.getSession().removeAttribute(Captcha.NAME);
		UserDao dao = injector.getInstance(UserDao.class);
		User user = dao.findByLoginNameOrEmailOrMobile(userName);
		boolean logined = false;
		if(user != null){
			MySHAService shas = injector.getInstance(MySHAService.class);
			if(shas.isEqual(user.getPassword(), password)){
				SessionUser su = injector.getInstance(SessionUser.class);
				su.setContent(user);
//				res.sendRedirect("http://127.0.0.1:8888/Firstgwt.html?gwt.codesvr=127.0.0.1:9997");
				logined = true;
			}
		}
		
		if(logined){
			if("json".equals(want)){
				apputils.writeJsonResponse(res, apputils.getListResponse(user));
			}
		}else{
			ErrorMessages ems = injector.getInstance(ErrorMessages.class);
			ems.addError(new Error("用户名或密码错误！", ServerErrorCode.LOGIN_FAILURE));
			apputils.writeJsonErrorResponse(res);
		}
	}

}
