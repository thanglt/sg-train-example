package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import net.sf.json.JSONObject;
import nl.captcha.Captcha;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.UserField;
import com.m3958.firstgwt.client.types.XinJianField;
import com.m3958.firstgwt.dao.UserDao;
import com.m3958.firstgwt.dao.VoteHitDao;
import com.m3958.firstgwt.exception.SmartJpaException;
import com.m3958.firstgwt.model.Article;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Comment;
import com.m3958.firstgwt.model.Hgll;
import com.m3958.firstgwt.model.HtmlCss;
import com.m3958.firstgwt.model.PageMistake;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.model.Vote;
import com.m3958.firstgwt.model.VoteHit;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.model.XinJian;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.service.MySHAService;
import com.m3958.firstgwt.service.ReqParaService;
import com.m3958.firstgwt.service.RequestScopeObjectService;
import com.m3958.firstgwt.service.WebFrontUrlParser;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.session.SessionUser;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Singleton
public class JqformServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Injector injector;
	
	@Inject
	private AppUtilService autils;
	
	Pattern p = Pattern.compile(".*[&<>].*", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	
	
	private String[] allowedObjects = new String[]{Hgll.class.getName(),XinJian.class.getName(),HtmlCss.class.getName(),VoteHit.class.getName(),Comment.class.getName(),PageMistake.class.getName(),User.class.getName()};
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;

	
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
		
		boolean needCaptcha = true;
		//用戶更新是唯一的update
		if(User.class.getName().equals(obname) && "update".equals(reqps.getOpType())){
			checkCaptcha(req, reqps, errors,rso);
			if(!autils.hasErrors()){
				SessionUser su = injector.getInstance(SessionUser.class);
				if(!su.getLoginStatus()){
					errors.addError(new Error("您尚未登录！", ServerErrorCode.LOGIN_FAILURE));
				}else{
					UserDao udao = injector.getInstance(UserDao.class);
					User u = emp.get().find(User.class, su.getUserId());//当前登录用户
					User u1 = udao.findByLoginNameOrEmailOrMobile(reqps.getStringValue("loginName"));
					MySHAService shas = injector.getInstance(MySHAService.class);
					
					if(u.getFcUser() && u1 != null && !u1.getFcUser() && u != u1){//登录用户和修改用户不一致的情况下，可能是用户合并。
						if(shas.isEqual(u1.getPassword(), reqps.getStringValue("prePassword",""))){
							//开始合并用户
							u1.setFcUser(true);
							u1.setFcId(u.getFcId());
							su.clearContent();
							su.setContent(u1);
							for(Role r:u.getRoles()){//角色转移
								u1.addRole(r);
							}
							udao.remove(u);
							udao.smartUpdateBaseModel(u1);
							autils.writeJsonResponse(res, autils.getListResponse(u));
							return;
						}else{
							errors.addError(new Error("原密码错误", ServerErrorCode.ACCESS_DENY));
						}
					}else{
						if(shas.isEqual(u.getPassword(), reqps.getStringValue("prePassword",""))){
							String pas = reqps.getStringValue("password","");
							String repas = reqps.getStringValue("confirmPassword","");
							if(!pas.equals(repas)){
								errors.addError(new Error("两次密码不相等！", 0));
							}else{
								if(!pas.isEmpty()){
									u.setPassword(pas);
								}
								u.setLoginName(reqps.getStringValue(UserField.LOGIN_NAME.getEname()));
								u.setEmail(reqps.getStringValue(UserField.EMAIL.getEname()));
								u.setMobile(reqps.getStringValue(UserField.MOBILE.getEname()));
								udao.smartUpdateBaseModel(u);
							}
							
							if(!autils.hasErrors()){
								autils.writeJsonResponse(res, autils.getListResponse(u));
								return;
							}
						}else{
							errors.addError(new Error("原密码错误", ServerErrorCode.ACCESS_DENY));
						}
					}
				}
			}
			if(autils.hasErrors()){
				autils.writeJsonErrorResponse(res);
			}
			return;
		}
		
		if(!objectAllowed(obname) || !"add".equals(reqps.getStringValue(SmartParameterName.OPERATION_TYPE))){
			errors.addError(new Error("需要登录之后操作！", ServerErrorCode.LOGIN_REQUIRED));
			autils.writeJsonErrorResponse(res);
			return;
		}
		if(reqps.getStringValue("audit") != null)reqps.setKeyValue("audit", "false");
		
		if(VoteHit.class.getName().equals(obname)){
			if("html".equals(reqps.getStringValue("type"))){
				processVoteHitHtml(req,res,reqps,errors,rso);
				return;
			}else{
				provessVote(req,res,reqps,errors,rso);
			}
		}
		

		if(Comment.class.getName().equals(obname)){
			processComment(req,res,reqps,errors,rso);
			needCaptcha = false;
		}
		
		if(PageMistake.class.getName().equals(obname)){
			processPageMistake(req,res,reqps,errors,rso);
			needCaptcha = false;
		}
		
		if(needCaptcha){
			checkCaptcha(req, reqps, errors,rso);
		}

		if(autils.hasErrors()){
			autils.writeJsonErrorResponse(res);
			return;
		}
			
		BaseModel bm;
		try {
			bm = saveOb(req, res, reqps,rso);
		} catch (Exception e) {
			e.printStackTrace();
			errors.addError(new Error(e.getMessage(), ServerErrorCode.JPA_ERROR));
			autils.writeJsonErrorResponse(res);
			return;
		}
		if(autils.hasErrors()){
			autils.writeJsonErrorResponse(res);
		}else{
			String callback = reqps.getStringValue("callback", ""); 
			if(callback.isEmpty()){
				autils.writeJsonResponse(res, autils.getListResponse(getJsonObject(bm)));
			}else{
				autils.writeJsonpResponse(res, autils.getListResponse(getJsonObject(bm)),callback);
			}
		}
		
	}
	
	private void provessVote(HttpServletRequest req, HttpServletResponse res,
			ReqParaService reqps, ErrorMessages errors,
			RequestScopeObjectService rso) throws IOException {
		String referer = req.getHeader("referer");
		reqps.setKeyValue("_vote_has_processed", "true");
		VoteHitDao vhdao = injector.getInstance(VoteHitDao.class);
		String[] vids = reqps.getMvParaValues(SmartParameterName.RELATION_MODEL_ID);
		List<VoteHit> vhits = new ArrayList<VoteHit>();
		for(String vid : vids){
			VoteHit vh = new VoteHit();
			vh.setReferer(referer);
			vh.setVoteIp(rso.getRemoteIp());
			Vote v = emp.get().find(Vote.class, Integer.parseInt(vid));
			v.addVoteHits(vh);
			vhdao.smartPersistBaseModel(vh);
			vhits.add(vh);
		}
		
		String callback = reqps.getStringValue("callback", ""); 
		if(callback.isEmpty()){
			autils.writeJsonResponse(res, autils.getListResponse(vhits,0,vhits.size()));
		}else{
			autils.writeJsonpResponse(res, autils.getListResponse(vhits,0,vhits.size()),callback);
		}
	}

	private void processPageMistake(HttpServletRequest req,
			HttpServletResponse res, ReqParaService reqps, ErrorMessages errors,RequestScopeObjectService rso) {
		//同一个ip，两次发贴时间不能少于5分钟
		String ip = reqps.getStringValue("ip");
		Query q = emp.get().createNamedQuery(PageMistake.NamedQueries.GET_LATEST_BY_IP);
		q.setParameter("ip", ip);
		q.setFirstResult(0);
		q.setMaxResults(1);
		List<PageMistake> pms = q.getResultList();
		if(pms.size() >0){
			PageMistake pm = pms.get(0);
			if(new Date().getTime() - pm.getCreatedAt().getTime() < 5 * 60 * 1000){
				errors.addError(new Error("5分钟之内只能提交一次！", ServerErrorCode.INTERVAL_TOO_SMALL));
				return;
			}
		}
	}

	private void checkCaptcha(HttpServletRequest req, ReqParaService reqps,ErrorMessages errors,RequestScopeObjectService rso){
		Captcha captcha = (Captcha) req.getSession().getAttribute(Captcha.NAME);
		String answer = reqps.getStringValue("answer");
		if (captcha == null || !captcha.isCorrect(answer)){
			errors.addError(new Error("验证码错误！", ServerErrorCode.CAPTCHA_ERROR));
			return;
		}
		req.getSession().removeAttribute(Captcha.NAME);
	}

	private void processComment(HttpServletRequest req,
			HttpServletResponse res, ReqParaService reqps,ErrorMessages errors,RequestScopeObjectService rso) {
		int obid = reqps.getIdValue("obid");
		String obname = req.getParameter("obname");
		int siteId = reqps.getIdValue(CommonField.SITE_ID.getEname());
		if(siteId == SmartConstants.NONE_EXIST_MODEL_ID){
			errors.addError(new Error("必须提供siteId", ServerErrorCode.OBJECT_NOT_EXIST));
			return;
		}
		
		String message = reqps.getStringValue("message");
		Matcher m = p.matcher(message);
		if(m.matches()){
			errors.addError(new Error("内容体内不可以有&<>等字符！", ServerErrorCode.OBJECT_NOT_EXIST));
			return;
		}
		
		WebSite ws = emp.get().find(WebSite.class, siteId);
//        vm.put("0", "不允许");
//        vm.put("1", "无需审核");
//        vm.put("2", "允许，审核");
//        vm.put("3", "允许，登录");
//        vm.put("4", "允许，登录，审核");
		if(ws.isCommentClosed()){
			errors.addError(new Error("站点评论已关闭！", ServerErrorCode.OBJECT_NOT_EXIST));
			return;
		}
		if(ws.getCommentLevel() == 1){
			reqps.setKeyValue("audit", String.valueOf(true));
		}else if(ws.getCommentLevel() == 3 || ws.getCommentLevel() == 4){
			SessionUser su = injector.getInstance(SessionUser.class);
			if(!su.isLogined()){
				errors.addError(new Error("本站点需要登录后评论！", ServerErrorCode.OBJECT_NOT_EXIST));
				return;
			}else{
				if(ws.getCommentLevel() == 3){
					reqps.setKeyValue("audit", String.valueOf(true));
				}else{
					reqps.setKeyValue("audit", String.valueOf(false));
				}
				
			}
		}else{
			reqps.setKeyValue("audit", String.valueOf(false));
		}
		String ip = rso.getRemoteIp();
		Query q = emp.get().createNamedQuery(Comment.NamedQueries.FIND_COMMENT_BY_SITEID_OBJECTID_IP);
		q.setParameter("siteId", siteId);
		q.setParameter("obid", obid+"");
		q.setParameter("ip", ip);
		q.setFirstResult(0);
		q.setMaxResults(1);
		List<Comment> cs = q.getResultList();
		if(cs.size() >0){
			Comment c = cs.get(0);
			if(new Date().getTime() - c.getCreatedAt().getTime() < 5 * 60 * 1000){
				errors.addError(new Error("同一个对象，5分钟之内只能评论一次！", ServerErrorCode.INTERVAL_TOO_SMALL));
				return;
			}
		}
		
		if("article".equals(obname)){
			if(obid == SmartConstants.NONE_EXIST_MODEL_ID){
				errors.addError(new Error("对象不存在！", ServerErrorCode.OBJECT_NOT_EXIST));
				return;
			}
			Article a = emp.get().find(Article.class, obid);
			if(a == null){
				errors.addError(new Error("对象不存在！", ServerErrorCode.OBJECT_NOT_EXIST));
				return;
			}
			if(!a.canComment()){
				errors.addError(new Error("不允许评论！", ServerErrorCode.ACCESS_DENY));
				return;
			}
			reqps.setKeyValue(CommonField.SITE_ID.getEname(), a.getSiteId()+"");
		}else if("page".equals(obname)){
			;
		}else{
			errors.addError(new Error("对象不可评论！", ServerErrorCode.OBJECT_NOT_EXIST));
			return;
		}
		
	}

	private BaseModel saveOb(HttpServletRequest req, HttpServletResponse res,
			ReqParaService reqps,RequestScopeObjectService rso) throws IOException, SmartJpaException {
		setExtraParamters(reqps,req,rso);
		BaseModel model = null;
		model = autils.getBaseModelFromParams();
		BaseModel bm  = autils.getDao().smartPersistBaseModel(model);
		return bm;
	}
	
	private void processVoteHitHtml(HttpServletRequest req,
			HttpServletResponse res, ReqParaService reqps,ErrorMessages errors,RequestScopeObjectService rso) throws IOException {
		boolean votesuccess = true;
		try {
			if(reqps.getRelationModelId() != SmartConstants.NONE_EXIST_MODEL_ID)
				saveOb(req, res,reqps,rso);
		} catch (SmartJpaException e1) {
			votesuccess = false;
		}
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
			Map<String, Object> model = wfup.getRootModel();
			if(!votesuccess){
				model.put("hasError", true);
			}
			try {
				StringWriter sWriter = new StringWriter();
				template.process(model, sWriter);
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

	private JSONObject getJsonObject(BaseModel bm) {
		return bm.toJson();
	}
	


	private void setExtraParamters(ReqParaService reqps,HttpServletRequest req,RequestScopeObjectService rso) {
		String name = reqps.getModelName();
		if(VoteHit.class.getName().equals(name)){
			String referer = req.getHeader("referer");
			reqps.setKeyValue("referer", referer);
			reqps.setKeyValue("voteIp", rso.getRemoteIp());
		}else if(Comment.class.getName().equals(name)){
			reqps.setKeyValue("ip", rso.getRemoteIp());
		}else if(PageMistake.class.getName().equals(name)){
//			String referer = req.getHeader("referer");
//			reqps.setKeyValue("pageUrl", referer);
//			reqps.setKeyValue("ip", rso.getRemoteIp());
			;
		}else if(XinJian.class.getName().equals(name)){
			try {
				String sr = reqps.getStringValue(XinJianField.SHENGRI.getEname(),"");
				if(!sr.isEmpty()){
					if(sr.length()<4){
						Calendar c = Calendar.getInstance();
						c.add(Calendar.YEAR, -Integer.parseInt(sr));
						Date d = c.getTime();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						reqps.setKeyValue(XinJianField.SHENGRI.getEname(), sdf.format(d));
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
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
