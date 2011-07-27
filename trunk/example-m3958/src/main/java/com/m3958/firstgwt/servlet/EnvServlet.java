package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.session.SessionUser;

@Singleton
public class EnvServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Injector injector;
	
	@Inject
	private AppUtilService autils;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		SessionUser su = injector.getInstance(SessionUser.class);
//		if(su.isSuperman()){
			Locale l = Locale.getDefault();
			String lstr = "Locale:<br/>";
			lstr += "display country:" + l.getDisplayCountry() + "<br/>";
			lstr += "display language:" + l.getDisplayLanguage() + "<br/>";
			lstr += "display name:" + l.getDisplayName() + "<br/>";
			lstr += "display Variant:" + l.getDisplayVariant() + "<br/>";
			lstr += "ISO3Country:" + l.getISO3Country() + "<br/>";
			lstr += "ISO3Language:" + l.getISO3Language() + "<br/>";
			lstr += "language:" + l.getLanguage() + "<br/>";
			lstr += "<br/><br/>";
			lstr += "TimeZone:<br/>";
			TimeZone tz = TimeZone.getDefault();
			lstr += "DisplayName:" + tz.getDisplayName() + "<br/>";
			lstr += "DSTSavings:" + tz.getDSTSavings() + "<br/>";
			lstr += "id:" + tz.getID() + "<br/>";
			
			lstr += "<br/>可用IDS：<br/>";
			
			String[] ss = tz.getAvailableIDs();
			
			for(String s : ss){
				lstr += s + "<br/>";
			}
			
			lstr += "RequestUrli:<br/>";
			
			lstr += req.getRequestURL() + "<br/>";
			lstr += req.getRequestURI() + "<br/>";
			lstr += req.getQueryString() + "<br/>";
			
			autils.writeHtmlResponseWithTemplate(res, lstr);
//		}
	}
	

}
