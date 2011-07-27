package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.exception.SmartJpaException;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Hgll;
import com.m3958.firstgwt.model.HtmlCss;
import com.m3958.firstgwt.model.Vote;
import com.m3958.firstgwt.model.VoteHit;
import com.m3958.firstgwt.model.XinJian;
import com.m3958.firstgwt.server.response.ErrorMessageResponse;
import com.m3958.firstgwt.server.types.CharactSetName;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.service.ReqParaService;
import com.m3958.firstgwt.service.RequestScopeObjectService;
import com.m3958.firstgwt.service.WebFrontUrlParser;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.session.SessionUser;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Singleton
public class FormServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Injector injector;
	
	@Inject
	private AppUtilService autils;
	
	
	private String[] allowedObjects = new String[]{Hgll.class.getName(),XinJian.class.getName(),HtmlCss.class.getName(),VoteHit.class.getName(),Vote.class.getName()};
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;

	/*
	 * 这个servlet返回的肯定是http方式的html
	 * 
	 */
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
		
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		ReqParaService reqps = injector.getInstance(ReqParaService.class);
		RequestScopeObjectService rso = injector.getInstance(RequestScopeObjectService.class);
		String obname = reqps.getStringValue(SmartParameterName.MODEL_NAME);
		ErrorMessages errors = injector.getInstance(ErrorMessages.class);
		BaseModel bm = null;
		if(!objectAllowed(obname) || !"add".equals(reqps.getStringValue(SmartParameterName.OPERATION_TYPE))){
			Error error = new Error("访问拒绝", ServerErrorCode.ACCESS_DENY);
			errors.addError(error);
		}
		if(errors.isEmpty()){
			if(VoteHit.class.getName().equals(obname)){
				;
			}else if(Vote.class.getName().equals(obname)){
				SessionUser su = injector.getInstance(SessionUser.class);
				if(!su.getLoginStatus()){
					Error error = new Error("访问拒绝", ServerErrorCode.ACCESS_DENY);
					errors.addError(error);
				}
			}else{
				checkCaptcha(req, reqps, errors,rso);
			}
			
			if(errors.isEmpty()){
				if(reqps.getStringValue("audit") != null)reqps.setKeyValue("audit", "false");
				try {
					bm = saveOb(req, res, reqps,rso);
				} catch (SmartJpaException e) {
					Error error = new Error("保存错误", ServerErrorCode.ACCESS_DENY);
					errors.addError(error);
				} catch (Exception e) {
					Error error = new Error(e.getMessage(),ServerErrorCode.ACCESS_DENY);
					errors.addError(error);
				}
			}
		}
		
		if(Vote.class.getName().equals(obname)){
			String rsstr;
			if(!errors.isEmpty()){
				rsstr = injector.getInstance(ErrorMessageResponse.class).toString();
			}else{
				rsstr = autils.getListResponse(bm);
			}
			String t = "<script type=\"text/javascript\">"+
				"window.parent.iframeCallbacks.voteCallback(" + rsstr + ");"+
			"</script>";
			
			autils.writeHtmlResponseWithTemplate(res, t);
			return;
		}
		
		String tplname = req.getParameter("tpl");
		if(tplname == null || tplname.isEmpty()){
			;
		}else{
			WebFrontUrlParser wfup = injector.getInstance(WebFrontUrlParser.class);
			Template template = wfup.getTpl();
			String content = null;
			Map<String, Object> model = wfup.getRootModel();
			model.put("errors", errors);
			try {
				StringWriter sWriter = new StringWriter();
				template.process(model, sWriter);
				content = sWriter.toString();
				res.setContentLength(content.getBytes("UTF8").length);
			} catch (TemplateException e) {
				e.printStackTrace(res.getWriter());
				return;
			}
			if(content == null || content.isEmpty()){
				res.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
			autils.writeHtmlResponse(res, content);
		}
	}

	private void checkCaptcha(HttpServletRequest req, ReqParaService reqps,
			ErrorMessages errors,RequestScopeObjectService rso) {
		Captcha captcha = (Captcha) req.getSession().getAttribute(Captcha.NAME);
		String answer = reqps.getStringValue("answer");
		if (captcha == null || !captcha.isCorrect(answer)){
			Error error = new Error("访问拒绝", ServerErrorCode.ACCESS_DENY);
			errors.addError(error);
		}
		req.getSession().removeAttribute(Captcha.NAME);
	}

	private BaseModel saveOb(HttpServletRequest req, HttpServletResponse res,
			ReqParaService reqps,RequestScopeObjectService rso) throws IOException, SmartJpaException {
		setExtraParamters(reqps,req,rso);
		BaseModel model = null;
		model = autils.getBaseModelFromParams();
		BaseModel bm  = autils.getDao().smartPersistBaseModel(model);
		return bm;
	}

	private void setExtraParamters(ReqParaService reqps,HttpServletRequest req,RequestScopeObjectService rso) {
		if(VoteHit.class.getName().equals(reqps.getModelName())){
			String referer = req.getHeader("referer");
			String ip = rso.getRemoteIp();
			reqps.setKeyValue("referer", referer);
			reqps.setKeyValue("voteIp", ip);
		}else if(Vote.class.getName().equals(reqps.getModelName())){
			String bianmachecker = req.getParameter("bianmachecker");
			if(!"我是utf-8".equals(bianmachecker)){
				String vname = reqps.getStringValue("name");
				try {
					vname = new String(vname.getBytes(CharactSetName.ISO_8859_1),CharactSetName.UTF_8);
					reqps.setKeyValue("name", vname);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean objectAllowed(String obname){
		for(String s:allowedObjects){
			if(s.equals(obname))return true;
		}
		return false;
	}
}
