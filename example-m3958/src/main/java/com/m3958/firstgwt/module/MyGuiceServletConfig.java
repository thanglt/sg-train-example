package com.m3958.firstgwt.module;

import java.util.HashMap;
import java.util.Map;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.filter.ContentCacheFilter;
import com.m3958.firstgwt.filter.GZIPFilter;
import com.m3958.firstgwt.filter.MonitorFilter;
import com.m3958.firstgwt.server.GwtRPCServiceImpl;
import com.m3958.firstgwt.servlet.AdminToolsServlet;
import com.m3958.firstgwt.servlet.AdvancedExampleServlet2;
import com.m3958.firstgwt.servlet.AssetFeedServlet;
import com.m3958.firstgwt.servlet.CmsDemoServlet;
import com.m3958.firstgwt.servlet.CsvDataSourceServlet;
import com.m3958.firstgwt.servlet.EnvServlet;
import com.m3958.firstgwt.servlet.ExportCVSServlet;
import com.m3958.firstgwt.servlet.FileUploadServlet;
import com.m3958.firstgwt.servlet.FormServlet;
import com.m3958.firstgwt.servlet.FreeMarkerServlet;
import com.m3958.firstgwt.servlet.InitAppServlet;
import com.m3958.firstgwt.servlet.JavaScriptDataModelServlet;
import com.m3958.firstgwt.servlet.JqformServlet;
import com.m3958.firstgwt.servlet.JsonpServlet;
import com.m3958.firstgwt.servlet.LgbVisualDataSourceServlet;
import com.m3958.firstgwt.servlet.MonitorsServlet;
import com.m3958.firstgwt.servlet.MySimpleCaptchaServlet;
import com.m3958.firstgwt.servlet.NotRpcLogin;
import com.m3958.firstgwt.servlet.OpenIdServlet;
import com.m3958.firstgwt.servlet.PageHitServlet;
import com.m3958.firstgwt.servlet.QqAuthLoginServlet;
import com.m3958.firstgwt.servlet.RssFeedServlet;
import com.m3958.firstgwt.servlet.SearchServlet;
import com.m3958.firstgwt.servlet.SimpleExampleServlet;
import com.m3958.firstgwt.servlet.SmartCFUDServiceImpl;
import com.m3958.firstgwt.servlet.SqlDataSourceServlet;
import com.m3958.firstgwt.servlet.StaticFileServlet;
import com.m3958.firstgwt.servlet.TokenConsumeServlet;
import com.m3958.firstgwt.servlet.UploadProgressServlet;
import com.m3958.firstgwt.servlet.ViewSourceServlet;
import com.m3958.firstgwt.servlet.WebFrontServlet;
import com.wideplay.warp.persist.PersistenceService;
import com.wideplay.warp.persist.UnitOfWork;

public class MyGuiceServletConfig extends GuiceServletContextListener {


	@Override
	protected Injector getInjector() {
		
		Injector ij = Guice.createInjector(PersistenceService.usingJpa()
				.across(UnitOfWork.REQUEST).buildModule(),
		new ServletModule() {
			@Override
			protected void configureServlets() {
				 Map<String, String>  captchaParams = new HashMap<String, String>();
				 captchaParams.put("captcha-width", "150");
				 captchaParams.put("captcha-height", "35");
				 
//				 •The filter is mapped to a matching path
//				 •The response status code is >=200 and <300 
//				 •The content length is unknown or more than the minGzipSize initParameter or the minGzipSize is 0(default)
//				 •The content-type is in the comma separated list of mimeTypes set in the mimeTypes initParameter or if no mimeTypes are defined the content-type is not "application/gzip"
//				 •No content-encoding is specified by the resource
				filter("/*").through(MonitorFilter.class);
				filterRegex("/.*\\.js$", "/.*\\.html$").through(GZIPFilter.class);
				filterRegex("^/(?!pagehit|viewsource|formprocessor|asset|smartcfud|simpleImg|initApp|uploadprogress|firstgwt|fileUploadHandler|exportcvs|cmsdemo|monitor|jsonp|javascriptdm|env|search|rss|openid|tokenconsume|qqauthlogin)[^.]*").through(ContentCacheFilter.class);
				serve("/rss").with(RssFeedServlet.class);
				serve("/jsonp").with(JsonpServlet.class);
				serve("/javascriptdm").with(JavaScriptDataModelServlet.class);
				serve("/monitor").with(MonitorsServlet.class);
				serve("/pagehit").with(PageHitServlet.class);
				serve("/env").with(EnvServlet.class);
				serve("/viewsource").with(ViewSourceServlet.class);
				serve("/search").with(SearchServlet.class);
				serve("/smartcfud").with(SmartCFUDServiceImpl.class);
				serve(SmartConstants.UPLOAD_URL + "/*").with(FileUploadServlet.class);
				serve("/simpleImg").with(MySimpleCaptchaServlet.class,captchaParams);
				serve("/initApp").with(InitAppServlet.class);
				serve("/simpleexample").with(SimpleExampleServlet.class);
				serve("/csv").with(CsvDataSourceServlet.class);
				serve("/advanced").with(AdvancedExampleServlet2.class);
				serve("/sqlvds").with(SqlDataSourceServlet.class);
				serve("/lgbVisual").with(LgbVisualDataSourceServlet.class);
				serve("/freemarker").with(FreeMarkerServlet.class);
				serve("/assetfeed/*").with(AssetFeedServlet.class);
				serve("/admintools").with(AdminToolsServlet.class);
				serve("/cmsdemo").with(CmsDemoServlet.class);
				serve("/uploadprogress/*").with(UploadProgressServlet.class);
				serveRegex(".*/gwtrpcservice$").with(GwtRPCServiceImpl.class);
				serve("/notrpclogin").with(NotRpcLogin.class);
				serve("/openid").with(OpenIdServlet.class);
				serve("/qqauthlogin").with(QqAuthLoginServlet.class);
				serve("/tokenconsume").with(TokenConsumeServlet.class);
				serve("/exportcvs").with(ExportCVSServlet.class);
				serve("/jqfprocessor").with(JqformServlet.class);
				serve("/formprocessor").with(FormServlet.class);
				serve("/webfront").with(WebFrontServlet.class);
				serve("/*").with(StaticFileServlet.class);
			}
		}, new JpaModel(),new HowManyMilliSecModule());
		return ij;
	}

}
