package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.util.Date;

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
import com.m3958.firstgwt.dao.PageHitDao;
import com.m3958.firstgwt.model.PageHit;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.service.ReqParaService;
import com.m3958.firstgwt.utils.SpecialDate;


@Singleton
public class PageHitServlet extends HttpServlet{

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
		
		ReqParaService reqps = injector.getInstance(ReqParaService.class);
		int siteId = reqps.getIdValue("siteid");
		if(siteId == SmartConstants.NONE_EXIST_MODEL_ID)return;
		
		String obname = reqps.getStringValue("obname","");//article,xinjian等等
		String obId = reqps.getStringValue("obid","");//对象id
		String callback = reqps.getStringValue("callback","");//"none"(document.write),没有callback参数就没有输出，但是统计还是进行
		String askfor = reqps.getStringValue("askfor","");//sitestat,objectstat(default)
		String detail = reqps.getStringValue("detail","");//v,vv,vvv,vvvv
		
		String outStr = null;
		
		if(askfor.isEmpty() || "objectstat".equals(askfor)){
			if(obname.isEmpty())obname="page";
			if(obId.isEmpty()){
				;
			}else{
				outStr = getObjectStat(siteId, obname, obId, callback, detail);
			}
		}else if("sitestat".equals(askfor)){
			outStr = getSitestat(siteId, callback, detail);
		}
		
		if(outStr == null || outStr.isEmpty()){
			res.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		}
		autils.writeJsonResponse(res, outStr);
	}
	
	private String getSitestat(int siteId,String callback,String detail){
		SpecialDate startDate = new SpecialDate();
//		Query q = emp.get().createNamedQuery(PageHit.NamedQueries.FIND_PAGE_HIT_BY_SITE);
//		q.setParameter("siteId", siteId);
//		q.setParameter("startDate", startDate.Y1700());
//		Long l = (Long) q.getSingleResult();
		Long l = getSitePh(siteId, startDate.Y1700());
		if("none".equals(callback)){
			return "document.write('" + l + "');";//直接输出
		}else{
			if(detail.isEmpty()){//回调输出
				return callback + "(" + l + ");";
			}else if(detail.length() > 3){
				String s = "{" + "total:" + l + 
								 "year:" + getSitePh(siteId, startDate.startOfYear()) + "," +
								 "month:" + getSitePh(siteId, startDate.startOfMonth()) + "," +
								 "week:" + getSitePh(siteId, startDate.startOfWeek()) + "," +
								 "day:" + getSitePh(siteId, startDate.startOfDay()) + 
								 "}";
				return callback + "(" + s + ")";
				//今日访问，本周访问，本月访问，年度访问，{total:xx,year:xx,month:xx,week:xx,day:xx}
			}else if(detail.length() > 2){
				String s = "{" + "total:" + l + 
//						"year:" + getSitePh(siteId, startDate.startOfYear()) + 
						"month:" + getSitePh(siteId, startDate.startOfMonth()) + "," +
						"week:" + getSitePh(siteId, startDate.startOfWeek()) + "," +
						"day:" + getSitePh(siteId, startDate.startOfDay()) +
						"}";
				return callback + "(" + s + ")";
				//今日访问，本周访问，本月访问 {total:xx,month:xx,week:xx,day:xx}
			}else if(detail.length() > 1){
				String s = "{" + "total:" + l + 
//				 		"year:" + getSitePh(siteId, startDate.startOfYear()) + 
//				 		"month:" + getSitePh(siteId, startDate.startOfMonth()) +
				 		"week:" + getSitePh(siteId, startDate.startOfWeek()) + "," +
				 		"day:" + getSitePh(siteId, startDate.startOfDay()) +
				 	"}";
					return callback + "(" + s + ")";
				//今日访问，加本周访问 {total:xx,week:xx,day:xx}
			}else{
				String s = "{" + "total:" + l + 
//				 	"year:" + getSitePh(siteId, startDate.startOfYear()) + 
//				 	"month:" + getSitePh(siteId, startDate.startOfMonth()) +
//				 	"week:" + getSitePh(siteId, startDate.startOfWeek()) +
				 	"day:" + getSitePh(siteId, startDate.startOfDay()) +
				 	"}";
						return callback + "(" + s + ")";
				//今日访问 {total:xx,day:xx}
			}
		}
//		return "";
	}
	
	private Long getSitePh(int siteId,Date startDate){
		Query q = emp.get().createNamedQuery(PageHit.NamedQueries.FIND_PAGE_HIT_BY_SITE);
		q.setParameter("siteId", siteId);
		q.setParameter("startDate", startDate);
		Long l = (Long) q.getSingleResult();
		if(l == null)return 0L;
		return l;
	}
	
	private String getObjectStat(int siteId,String obname,String obId,String callback,String detail){
		addPageHit(siteId, obname, obId);
		SpecialDate startDate = new SpecialDate();
		if(callback.isEmpty()){
			return ";";//隐藏的统计
		}else{
			Query q = emp.get().createNamedQuery(PageHit.NamedQueries.FIND_PAGE_HIT_BY_OBJECT);
			q.setParameter("siteId", siteId);
			q.setParameter("obId", obId);
			q.setParameter("startDate", startDate.Y1700());
			Long l = (Long) q.getSingleResult();
			if("none".equals(callback)){
				return "document.write('" + l + "');";//直接输出
			}else{
				if(detail.isEmpty()){//回调输出
					return callback + "(" + l + ");";
				}else if(detail.length() > 3){
					//今日访问，本周访问，本月访问，年度访问，{total:xx,year:xx,month:xx,week:xx,day:xx}
				}else if(detail.length() > 2){
					//今日访问，本周访问，本月访问 {total:xx,month:xx,week:xx,day:xx}
				}else if(detail.length() > 1){
					//今日访问，加本周访问 {total:xx,week:xx,day:xx}
				}else{
					;//今日访问 {total:xx,day:xx}
				}
			}
		}
		return "";
	}
	
	private void addPageHit(int siteId,String obname,String obId){
		PageHitDao phDao = injector.getInstance(PageHitDao.class);
		phDao.addPageHit(siteId, obname, obId);
	}
}
