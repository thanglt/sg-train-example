package com.m3958.firstgwt.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.dao.TokenDao;
import com.m3958.firstgwt.model.Group;
import com.m3958.firstgwt.model.Token;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.session.SessionUser;
import com.wideplay.warp.persist.Transactional;


@Singleton
public class TokenConsumeServlet extends HttpServlet{

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
	
	
	/*
	 * 1、不需要任何输出
	 * 2、输出document.write
	 * 3、输出callback
	 */
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
	                               throws ServletException, IOException {
		
		doPost(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
	                               throws ServletException, IOException {
		String uuid = req.getParameter("uuid");
		String want = req.getParameter("want");
		TokenDao tdao = injector.getInstance(TokenDao.class);
		Token t = tdao.findTokenByUuid(uuid);
		ErrorMessages errors = injector.getInstance(ErrorMessages.class);
		
		if(t == null || t.isUsed()){
			errors.addError(new Error("该值已经消费！", ServerErrorCode.ACCESS_DENY));
			autils.writeJsonErrorResponse(res);
			return;
		}else{
			if("inviteGroup".equals(t.getPurpose())){
				SessionUser su = injector.getInstance(SessionUser.class);
				if(su.getLoginStatus()){
					User u = emp.get().find(User.class, su.getUserId());
					Group g = emp.get().find(Group.class, Integer.parseInt(t.getDetail()));
					boolean success = addToGroup(g, u,errors,t);
					
					if(!errors.isEmpty()){
						autils.writeJsonErrorResponse(res);
						return;
					}
					
					if(success){
						autils.writeJsonResponse(res, autils.getListResponse(g));
					}else{
						autils.writeJsonResponse(res, autils.getEmptyListResponse());
					}
					
				}else{
					errors.addError(new Error("该值已经消费！", ServerErrorCode.ACCESS_DENY));
					autils.writeJsonErrorResponse(res);
					return;
				}
			}
		}
	}
	
	@Transactional
	public boolean addToGroup(Group g,User u,ErrorMessages errors,Token t){
		t.setUsed(true);
		return g.manageRelation(u, true, "", errors);
	}
}
