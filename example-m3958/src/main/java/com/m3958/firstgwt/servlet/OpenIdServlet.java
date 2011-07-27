package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.dao.UserDao;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.service.WebContextFMCfg;
import com.m3958.firstgwt.session.SessionUser;
import com.m3958.firstgwt.utils.Osdetecter;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Singleton
public class OpenIdServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AppUtilService apputils;
	
	@Inject
	private WebContextFMCfg wfmcfg;
	
	@Inject
	private Injector injector;
	
	@Inject
	public ConsumerManager manager;
	
	@Inject
	private com.google.inject.Provider<EntityManager> emp;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
	                               throws ServletException, IOException {
		doPost(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
	                               throws ServletException, IOException {
		res.setContentType("text/html; charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		
		SessionUser su = injector.getInstance(SessionUser.class);
			
		if(req.getParameter("loginstatus") != null){
			if(su.getLoginStatus()){
				User u = emp.get().find(User.class, su.getUserId());
				apputils.writeJsonResponse(res, apputils.getListResponse(u));
			}else{
				apputils.writeJsonResponse(res, apputils.getEmptyListResponse());
			}
			return;
		}
		
		if(req.getParameter("openid_identifier") != null){
			processRedirect(req,res);
		}else{
			processReturn(req,res);
		}
	}

	private void processReturn(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
        try
        {
            // extract the parameters from the authentication response
            // (which comes in as a HTTP request from the OpenID provider)
            ParameterList response =
                    new ParameterList(req.getParameterMap());

            // retrieve the previously stored discovery information
            DiscoveryInformation discovered = (DiscoveryInformation)
                    req.getSession().getAttribute("openid-disc");

            // extract the receiving URL from the HTTP request
            StringBuffer receivingURL = req.getRequestURL();
            System.out.println(receivingURL.toString());
            if(receivingURL.toString().indexOf("8888") == -1){
                try{
    	            URL urlReceifing = new URL(receivingURL.toString()); 
//    	            if (urlReceifing.getPort() == -1) { 
    	                    // no port! so add port 80! 
    	                    urlReceifing = new URL(urlReceifing.getProtocol(), 
    	                                    req.getParameter("hostname"), urlReceifing.getFile()); 
    	                    receivingURL = new StringBuffer(urlReceifing.toString()); 
    	                    System.out.println(urlReceifing.toString());
//    	            } 
    		    } catch (MalformedURLException e) {
    		            // TODO Auto-generated catch block 
    		            e.printStackTrace(); 
    		    } 
            }
    
            String queryString = req.getQueryString();
            if (queryString != null && queryString.length() > 0)
                receivingURL.append("?").append(req.getQueryString());

            // verify the response; ConsumerManager needs to be the same
            // (static) instance used to place the authentication request
            VerificationResult verification = manager.verify(
                    receivingURL.toString(),
                    response, discovered);

            // examine the verification result and extract the verified identifier
            Identifier verified = verification.getVerifiedId();
            if (verified != null)
            {
                AuthSuccess authSuccess =
                        (AuthSuccess) verification.getAuthResponse();
                
                String email = null;
                if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)){
                    FetchResponse fetchResp = (FetchResponse) authSuccess
                            .getExtension(AxMessage.OPENID_NS_AX);

                    List emails = fetchResp.getAttributeValues("email");
                    email = (String) emails.get(0);
                }
                UserDao udao = injector.getInstance(UserDao.class);
                
            	String v = verified.getIdentifier();
            	User u = udao.findByFcId(v);
            	if(u!=null){
        			SessionUser su = injector.getInstance(SessionUser.class);
        			su.setContent(u);
            	}else{
            		String uuid = UUID.randomUUID().toString();
            		u = new User();
            		u.setEmail(uuid);
            		u.setLoginName(uuid);
            		u.setMobile(uuid);
            		u.setFcId(v);
            		u.setFcUser(true);
            		u.setPassword(uuid);
            		udao.smartPersistBaseModel(u);
        			SessionUser su = injector.getInstance(SessionUser.class);
        			su.setContent(u);
            	}
//                }else{
//                	u = udao.findByLoginNameOrEmailOrMobile(email);
//                	if(u!=null){
//            			SessionUser su = injector.getInstance(SessionUser.class);
//            			su.setContent(u);
//                	}else{
//                		u = new User();
//                		u.setEmail(email);
//                		u.setLoginName(email);
//                		u.setMobile(email);
//                		u.setPassword(UUID.randomUUID().toString());
//                		udao.smartPersistBaseModel(u);
//                	}
//                }
                Object wheretogo = req.getSession().getAttribute("wheretogo");
                req.getSession().removeAttribute("wheretogo");
                String hostname = req.getParameter("hostname");
                if(hostname == null || hostname.isEmpty()){
                	hostname = "127.0.0.1:8888";
                }
                if(wheretogo != null && wheretogo.toString().length() > 0){
                	if(wheretogo.toString().startsWith("/")){
                		res.sendRedirect("http://" + hostname + wheretogo.toString());
                	}else{
                		res.sendRedirect("http://" + hostname + "/" + wheretogo.toString());
                	}
                	
                }else{
                	res.sendRedirect("http://" + hostname + "/openid.html");
                }
            }
        }
        catch (OpenIDException e)
        {
            e.printStackTrace();
        }
//        https://www.google.com/accounts/o8/id?id=AItOawkjMlZy1xSTNj-XP8NCbb4qANcwh2TiGy0        
//        https://www.myid.net/server?openid.identity=http%3A%2F%2Fjianglibo.myid.net%2F&openid.return_to=http%3A%2F%2F127.0.0.1%3A8888%2Fopenid%3Fopenid.rpnonce%3D2011-07-05T10%253A45%253A27Z0%26openid.rpsig%3DkawkUyjKMAqBkUOipEs9%252FZnanSYmh9w35f3RwJ1lkfE%253D&openid.trust_root=http%3A%2F%2F127.0.0.1%3A8888%2Fopenid&openid.assoc_handle=4f6577211b02aeac&openid.mode=checkid_setup&openid.ns.ext1=http%3A%2F%2Fopenid.net%2Fsrv%2Fax%2F1.0&openid.ext1.mode=fetch_request&openid.ext1.type.email=http%3A%2F%2Fschema.openid.net%2Fcontact%2Femail&openid.ext1.required=email
//        https://me.yahoo.com/a/CaZTXL8SuPg.sX1wjNABtcAwOajFAC4qz2jyjUpGK7A-#4be8e
	}

	private void processRedirect(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String userSuppliedString = req.getParameter("openid_identifier");
        try
        {
            // configure the return_to URL where your application will receive
            // the authentication responses from the OpenID provider
            String returnToUrl;
            
            if(Osdetecter.isWindows()){
            	returnToUrl = "http://127.0.0.1:8888/openid";
            }else{
            	returnToUrl = "http://www.m3958.com/openid";
            }

            // --- Forward proxy setup (only if needed) ---
            // ProxyProperties proxyProps = new ProxyProperties();
            // proxyProps.setProxyName("proxy.example.com");
            // proxyProps.setProxyPort(8080);
            // HttpClientFactory.setProxyProperties(proxyProps);

            // perform discovery on the user-supplied identifier
            List discoveries = manager.discover(userSuppliedString);

            // attempt to associate with the OpenID provider
            // and retrieve one service endpoint for authentication
            DiscoveryInformation discovered = manager.associate(discoveries);

            // store the discovery information in the user's session
            req.getSession().setAttribute("openid-disc", discovered);
            req.getSession().setAttribute("wheretogo", req.getParameter("wheretogo"));

            // obtain a AuthRequest message to be sent to the OpenID provider
            AuthRequest authReq = manager.authenticate(discovered, returnToUrl);

            // Attribute Exchange example: fetching the 'email' attribute
            FetchRequest fetch = FetchRequest.createFetchRequest();
            fetch.addAttribute("email",
                    // attribute alias
                    "http://schema.openid.net/contact/email",   // type URI
                    true);                                   // required

            // attach the extension to the authentication request
            authReq.addExtension(fetch);
            

    		
            if (! discovered.isVersion2() )
            {
                // Option 1: GET HTTP-redirect to the OpenID Provider endpoint
                // The only method supported in OpenID 1.x
                // redirect-URL usually limited ~2048 bytes
                res.sendRedirect(authReq.getDestinationUrl(true));
//            	apputils.writeJsonResponse(res, apputils.createJsonStr("status",0,"message",authReq.getDestinationUrl(true)));
            }
            else
            {
                // Option 2: HTML FORM Redirection (Allows payloads >2048 bytes)

//                RequestDispatcher dispatcher =
//                        getServletContext().getRequestDispatcher("formredirection.jsp");
        		res.setContentType("text/html;charset=UTF-8");   
        		res.setCharacterEncoding("UTF-8");
            	Map<String, Object> rm = new HashMap<String, Object>();
            	rm.put("parameterMap", authReq.getParameterMap());
            	rm.put("message", authReq);
            	
        		try {
        			Template template = wfmcfg.getTemplate("openidformredirect.html");
        			Writer writer = res.getWriter();
        			template.process(rm, writer);
        			writer.flush();
        		} catch (IOException e) {
        			e.printStackTrace();
        		} catch (TemplateException e) {
        			e.printStackTrace();
        		}

//                dispatcher.forward(httpReq, httpResp);
            }
        }
        catch (OpenIDException e)
        {
            e.printStackTrace();
            apputils.writeHtmlResponseWithTemplate(res, "openID登录失败！<a href=\"/openid.html#tab=1\">返回</a>");
//            if(userSuppliedString.indexOf("google") == -1){
//            	apputils.writeJsonResponse(res, apputils.createJsonStr("status",-1,"message","登录失败！请检查OpenId是否正确！"));
//            }else{
//            	apputils.writeJsonResponse(res, apputils.createJsonStr("status",-1,"message","google登录失败！请在网路畅通的时候再次尝试"));
//            }
        }
	}

}




//Map<String, Object> rm = new HashMap<String, Object>();
//
//if(req.getParameter("logout") != null){
//	req.getSession().removeAttribute("openid");
//	req.getSession().removeAttribute("openid-claimed");
//	rm.put("logined", false);
//	rm.put("logout",true);
//	rm.put("openid", "");
//}else{
//	if(req.getSession().getAttribute("openid")==null){
//		rm.put("logined", false);
//		rm.put("logout",false);
//		rm.put("openid", "");
//	}else{
//		rm.put("logined", true);
//		rm.put("logout",false);
//		rm.put("openid", req.getSession().getAttribute("openid"));
//	}
//}
//
//
//
//Template t = wfmcfg.getTemplate("openidhtml.ftl");
//try {
//	t.process(rm, res.getWriter());
//} catch (TemplateException e) {
//	e.printStackTrace();
//}

//private String CONSUMER_KEY = "www.m3958.com";
//private String CONSUMER_SECRET = "C7GDEIJbn-EJS5wcJS5y5cke";
//
//private String[] SCOPES = new String[]{"http://docs.google.com/feeds/","http://spreadsheets.google.com/feeds/","http://www-opensocial.googleusercontent.com/api/people/"};



//Map<String, String> openid_params = new HashMap<String, String>();
//openid_params.put("openid.ns", "http://specs.openid.net/auth/2.0");
//openid_params.put("openid.claimed_id", "http://specs.openid.net/auth/2.0/identifier_select");
//openid_params.put("openid.identity", "http://specs.openid.net/auth/2.0/identifier_select");
//openid_params.put("openid.return_to", "http://{$CONSUMER_KEY}{$_SERVER['PHP_SELF']}");
//openid_params.put("openid.realm", "http://{$CONSUMER_KEY}");
//openid_params.put("openid.mode", "@$_REQUEST['openid_mode']");
//openid_params.put("openid.ns.ui", "http://specs.openid.net/extensions/ui/1.0");
//openid_params.put("openid.ns.ext1", "http://openid.net/srv/ax/1.0");
//openid_params.put("openid.ext1.mode", "fetch_request");
//openid_params.put("openid.ext1.type.email", "http://axschema.org/contact/email");
//openid_params.put("openid.ext1.type.first", "http://axschema.org/namePerson/first");
//openid_params.put("openid.ext1.type.last", "http://axschema.org/namePerson/last");
//openid_params.put("openid.ext1.type.country", "http://axschema.org/contact/country/home");
//openid_params.put("openid.ext1.type.lang", "http://axschema.org/pref/language");
//
//openid_params.put("openid.ext1.required", "email,first,last,country,lang");
//openid_params.put("openid.ns.oauth", "http://specs.openid.net/extensions/oauth/1.0");
//openid_params.put("openid.oauth.consumer", CONSUMER_KEY);
//openid_params.put("openid.oauth.scope", StringUtils.join(SCOPES, " "));
//
//Map<String, String> openid_ext = new HashMap<String, String>();
//openid_ext.put("openid.ns.ext1", "http://openid.net/srv/ax/1.0");
//openid_ext.put("openid.ext1.mode", "fetch_request");
//openid_ext.put("openid.ext1.type.email", "http://axschema.org/contact/email");
//openid_ext.put("openid.ext1.type.first", "http://axschema.org/namePerson/first");
//openid_ext.put("openid.ext1.type.last", "http://axschema.org/namePerson/last");
//openid_ext.put("openid.ext1.type.country", "http://axschema.org/contact/country/home");
//openid_ext.put("openid.ext1.type.lang", "http://axschema.org/pref/language");
//openid_ext.put("openid.ext1.required", "email,first,last,country,lang");
//openid_ext.put("openid.ns.oauth", "http://specs.openid.net/extensions/oauth/1.0");
//openid_ext.put("openid.oauth.consumer", CONSUMER_KEY);
//openid_ext.put("openid.oauth.scope", StringUtils.join(SCOPES, " "));
//openid_ext.put("openid.ui.icon", "true");
