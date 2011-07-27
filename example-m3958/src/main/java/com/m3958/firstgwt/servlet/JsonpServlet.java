package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.VoteField;
import com.m3958.firstgwt.command.FetchCommand;
import com.m3958.firstgwt.dao.VoteDao;
import com.m3958.firstgwt.dao.WebSiteDao;
import com.m3958.firstgwt.exception.SmartJpaException;
import com.m3958.firstgwt.model.Comment;
import com.m3958.firstgwt.model.HtmlCss;
import com.m3958.firstgwt.model.Vote;
import com.m3958.firstgwt.model.WebSite;
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
public class JsonpServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	@Inject
	private AppUtilService autils;
	
	@Inject
	private Injector injector;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
    			throws ServletException, IOException {
			doGet(req, res);
	}
	
	/*
	 * 1、callback
	 * 2、_modelName
	 * callback	jQuery15102818758148683965_1303615181172
		featureClass	P
		maxRows	12
		name_startsWith	shan
		style	full
	 */
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
	                               throws ServletException, IOException {
		ErrorMessages errors = injector.getInstance(ErrorMessages.class);
		if("html".equals(req.getParameter("type"))){
			processHTML(req, res,errors);
		}else{
			ReqParaService reqps = injector.getInstance(ReqParaService.class);
			RequestScopeObjectService rso = injector.getInstance(RequestScopeObjectService.class);
			
			String modelName = reqps.getModelName();
			if(modelName == null || modelName.isEmpty()){
				;
			}else{
				if(HtmlCss.class.getName().equals(modelName)){
					processHtmlCss(req,res, reqps,errors);
				}else if(Vote.class.getName().equals(modelName)){
					processVote(req,res,reqps,errors);
				}else if(WebSite.class.getName().equals(modelName)){
					processWebSite(req,res,reqps,rso,errors);
				}else if(Comment.class.getName().equals(modelName)){
					processComment(req,res,reqps,rso,errors);
				}else{
					;
				}
			}
		}
	}


	private void processComment(HttpServletRequest req,
			HttpServletResponse res, ReqParaService reqps,
			RequestScopeObjectService rso,ErrorMessages errors) throws IOException {
		int siteId = reqps.getIdValue(CommonField.SITE_ID.getEname());
		String obid = reqps.getStringValue("obid","");
		if(siteId == SmartConstants.NONE_EXIST_MODEL_ID || obid.isEmpty()){
			return;
		}else{
			Query q = emp.get().createNamedQuery(Comment.NamedQueries.FIND_FLAT_COMMENT_BY_SITE_ID_OBJECT_ID);
			q.setParameter("siteId", siteId);
			q.setParameter("obid", obid);
			q.setParameter("audit", true);
			List<Comment> cc = q.getResultList();
			autils.writeJsonResponse(res, autils.getListResponse(cc, 0, cc.size()));
		}
	}


	private void processWebSite(HttpServletRequest req,
			HttpServletResponse res, ReqParaService reqps,RequestScopeObjectService rso,ErrorMessages errors) throws IOException {
			int sid = reqps.getModelId();
			WebSite ws;
			if(sid == SmartConstants.NONE_EXIST_MODEL_ID){
				ws = rso.getRequestWebSite();
			}else{
				ws = emp.get().find(WebSite.class, reqps.getModelId());
			}
			
			if(rso.isSiteEditor(ws)){
				ws.setSiteEditor(true);
			}
			if(rso.isSiteOwner(ws)){
				ws.setSiteOwner(true);
			}
			autils.writeJsonResponse(res, autils.getListResponse(ws));
	}


	private void processHTML(HttpServletRequest req, HttpServletResponse res,ErrorMessages errors) throws IOException {
		String tplname = req.getParameter("tpl");
		if(tplname == null || tplname.isEmpty()){
			;
		}else{
			WebFrontUrlParser wfup = injector.getInstance(WebFrontUrlParser.class);
			Template template = wfup.getTpl();
			res.setContentType("text/html; charset=UTF-8");   
			res.setCharacterEncoding("UTF-8");
			String content = null;
			String etag = UUID.randomUUID().toString();
			PrintWriter writer = res.getWriter();
			try {
				StringWriter sWriter = new StringWriter();
				template.process(wfup.getRootModel(), sWriter);
				content = sWriter.toString();
				res.setContentLength(content.getBytes("UTF8").length);
			} catch (TemplateException e) {
				e.printStackTrace(writer);
			}
			if(content == null || content.isEmpty()){
				res.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
			res.setHeader("Etag", etag);
			writer.write(content);
			writer.close();
		}
		
	}


	private void processVote(HttpServletRequest req, HttpServletResponse res,ReqParaService reqps,ErrorMessages errors) throws IOException {
		VoteDao vdao = injector.getInstance(VoteDao.class);
		String what = reqps.getStringValue("what", "");
		String callback = reqps.getStringValue("callback", "");
		if(what.equals("myvote")){
//			SessionUser su = injector.getInstance(SessionUser.class);
//			if(!su.isLogined()){
//				errors.addError(new Error("您尚未登录！", 0));
//				if(callback.isEmpty()){
//					autils.writeJsonErrorResponse(res);
//				}else{
//					autils.writeJsonpErrorResponse(res, callback);
//				}
//				return;
//			}else{
				int userId = reqps.getIntegerValue("userId");
				if(SmartConstants.NONE_EXIST_MODEL_ID == userId){
					errors.addError(new Error("请指定用户ID！", ServerErrorCode.NO_ERROR));
					if(callback.isEmpty()){
						autils.writeJsonErrorResponse(res);
					}else{
						autils.writeJsonpErrorResponse(res, callback);
					}
					return;
				}
				List<Vote> myvotes = vdao.getUserVotes(userId);
				writeResult(req, res, autils.getListResponse(myvotes, 0, myvotes.size()));
//			}
		}else{
			int mid = reqps.getModelId();
			Vote v = null;
			if(mid == SmartConstants.NONE_EXIST_MODEL_ID){
				 return;
			}else{
				v = vdao.find(mid);
			}
			if(v == null){
				errors.addError(new Error("投票不存在，请核对voteId值！", ServerErrorCode.OBJECT_NOT_EXIST));
				autils.writeJsonpErrorResponse(res, callback);
				return;
			}
			String result = "";
			if("jsonb".equals(reqps.getStringValue("want"))){
				result =  autils.getObjectValueResponse(v.toJsonb());
			}else{
				result = autils.getObjectValueResponse(v.toJsona());
			}
			writeResult(req, res, result);
		}
	}


	private void processHtmlCss(HttpServletRequest req, HttpServletResponse res,ReqParaService reqps,ErrorMessages errors) throws IOException {
		String referrer = req.getHeader("referer");
		String result = "";
		if(referrer == null || referrer.isEmpty() || !(referrer.startsWith("http://127.0") || referrer.startsWith("http://www.m3958"))){
			;
		}else{
			reqps.setKeyValue("audit", String.valueOf(true));
			FetchCommand fc = injector.getInstance(FetchCommand.class);
			try {
				fc.execute(false);
				result =  fc.getResult();
			} catch (SmartJpaException e) {
				e.printStackTrace();
			}
		}
		writeResult(req, res, result);
	}
	
	private void writeResult(HttpServletRequest req,HttpServletResponse res,String result) throws IOException{
		String callback = req.getParameter("callback");
		if(callback == null || callback.isEmpty()){
			autils.writeJsonResponse(res,result);
		}else{
			autils.writeJsonpResponse(res, result, callback);
		}
	}

}
