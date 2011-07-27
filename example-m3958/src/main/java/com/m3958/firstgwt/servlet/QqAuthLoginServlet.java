package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.dao.UserDao;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.session.SessionUser;
import com.qq.config.Config;
import com.qq.connect.AccessToken;
import com.qq.connect.InfoToken;
import com.qq.connect.RequestToken;
import com.qq.util.ParseString;
import com.qq.util.Verify;
import com.qq.util.XMLHelper;

@Singleton
public class QqAuthLoginServlet extends HttpServlet{

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
		doPost(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
	                               throws ServletException, IOException {

		

		if(req.getParameter("openid_identifier") == null){
	        String oauth_token = req.getParameter("oauth_token");
	        String openid = req.getParameter("openid");
	        String oauth_signature = req.getParameter("oauth_signature");
	        String oauth_vericode = req.getParameter("oauth_vericode");
	        String timestamp = req.getParameter("timestamp");

	        PrintWriter out = res.getWriter();

	        /**
	         * QQ互联登录，授权成功后会回调此地址 必须要用授权的req token换取access token
	         * 访问QQ互联的任何资源都需要access token 目前access token是长期有效的，除非用户解除与第三方绑定
	         * 如果第三方发现access token失效，请引导用户重新登录QQ互联，授权，获取access token
	         */
	        //www.m3958.com:8888/openid?oauth_token=11895571418137682878&openid=B135B7DFFD8DFE6B31CE48922362E0F2&oauth_signature=TiBCa7SRkFXaNe1TJOgWLNur7xA%3D&oauth_vericode=1371205698&timestamp=1311226907
	        // 授权成功后，会返回用户的openid
	        // 检查返回的openid是否是合法id
	        try {
	            if (!Verify.verifyOpenID(openid, timestamp, oauth_signature)) {
	                out.println("openid verify false!");
	            }
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }

	        // tips
	        // 这里已经获取到了openid，可以处理第三方账户与openid的绑定逻辑
	        // 但是我们建议第三方等到获取accesstoken之后在做绑定逻辑

	        // 用授权的req token换取access token
	        AccessToken token = new AccessToken();
	        String oauth_token_secret = (String) req.getSession().getAttribute("oauth_token_secret");
	        // out.println(oauth_token_secret);
	        // out.println(oauth_vericode);
	        String access_token = null;
	        try {
	            access_token = token.getAccessToken(oauth_token, oauth_token_secret, oauth_vericode);
	        } catch (InvalidKeyException e1) {
	            e1.printStackTrace();
	        } catch (NoSuchAlgorithmException e1) {
	            e1.printStackTrace();
	        }
	        // System.out.println(access_token);
	        HashMap<String, String> tokens = ParseString.parseTokenString(access_token);

	        // error
	        if (tokens.get("error_code") != null) {
	            out.println(tokens.get("error_code"));
	        }

	        // 获取access token成功后也会返回用户的openid
	        // 我们强烈建议第三方使用此openid
	        // 检查返回的openid是否是合法id
	        try {
	            if (!Verify.verifyOpenID(tokens.get("openid"), tokens.get("timestamp"), tokens.get("oauth_signature"))) {
	                out.println("openid verify false2!");
	            }
	        } catch (InvalidKeyException e1) {
	            e1.printStackTrace();
	        } catch (NoSuchAlgorithmException e1) {
	            e1.printStackTrace();
	        }
	        // 将access token，openid保存!!
	        oauth_token = tokens.get("oauth_token");
	        oauth_token_secret = tokens.get("oauth_token_secret");
	        openid = tokens.get("openid");

	        InfoToken infotoken = new InfoToken();
	        String info_xml = null;
	        try {
	            info_xml = infotoken.getInfo(oauth_token, oauth_token_secret, openid, "xml");
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (InvalidKeyException e) {
	            e.printStackTrace();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        // out.println(userinfo_xml);
	        HashMap<String, String> userinfo = null;
	        try {
	            userinfo = XMLHelper.getInstance().getInfo(info_xml);
	        } catch (SAXException e) {
	            e.printStackTrace();
	        }
	        // 第三方处理用户绑定逻辑
	        // 将openid与第三方的帐号做关联
	        // bind_to_openid();

	        req.getSession().setAttribute("oauth_token", oauth_token);
	        req.getSession().setAttribute("oauth_token_secret", oauth_token_secret);
	        
//	        ret: 返回码
//	        nickname: 昵称
//	        gender: 性别
//	        province: 省
//	        city: 市
//	        figureurl: 头像URL
	        
            UserDao udao = injector.getInstance(UserDao.class);
            String bianmachecker = req.getParameter("bianmachecker");
            String nickname;
            String figureurl;
            if("中文".equals(bianmachecker)){
            	nickname = userinfo.get("nickname");
            	figureurl = userinfo.get("figureurl");
            }else{
                nickname = new String(userinfo.get("nickname").getBytes("ISO-8859-1"), "UTF-8");
                figureurl = new String(userinfo.get("figureurl").getBytes("ISO-8859-1"), "UTF-8");
            }

        	User u = udao.findByFcId(openid);
        	if(u!=null){
        		u.setNickname(nickname);
        		u.setAvatar(figureurl);
    			SessionUser su = injector.getInstance(SessionUser.class);
    			su.setContent(u);
    			udao.smartUpdateBaseModel(u);
        	}else{
        		String uuid = UUID.randomUUID().toString();
        		u = new User();
        		u.setEmail(uuid);
        		u.setLoginName(uuid);
        		u.setMobile(uuid);
        		u.setNickname(nickname);
        		u.setFcId(openid);
        		u.setFcUser(true);
        		u.setAvatar(figureurl);
        		u.setPassword(uuid);
        		udao.smartPersistBaseModel(u);
    			SessionUser su = injector.getInstance(SessionUser.class);
    			su.setContent(u);
        	}
        	
	        
	        String hostname = req.getParameter("hostname");
            if(hostname == null || hostname.isEmpty()){
            	hostname = "127.0.0.1:8888";
            }
            Object wg = (String) req.getSession().getAttribute("wheretogo");
            String wheretogo = null;
            if(wg!=null){
            	wheretogo = (String)wg;
            	req.getSession().removeAttribute("wheretogo");
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
	        return;
	    }else{
	    	
	    	String wheretogo = req.getParameter("wheretogo");
	    	if(wheretogo!= null && wheretogo.length() >0)req.getSession().setAttribute("wheretogo", wheretogo);
			String redirect_url = "http://openapi.qzone.qq.com/oauth/qzoneoauth_authorize?oauth_consumer_key=" + Config.APP_ID;
			
			RequestToken rt = new RequestToken();
			
			String tok = null;
			try {
				tok = rt.getRequestToken();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
			Map<String, String> tokens = ParseString.parseTokenString(tok);
			String oauth_token = tokens.get("oauth_token");
			
			String oauth_token_secret = tokens.get("oauth_token_secret");
			req.getSession().setAttribute("oauth_token_secret", oauth_token_secret);
//			CookieUtils.setCookie(request, response, "oauth_token_secret", oauth_token_secret, -1, "/");
			
			redirect_url += "&oauth_token=" + oauth_token;
			redirect_url += "&oauth_callback=" + Config.OAUTH_CALLBACK + URLEncoder.encode("?bianmachecker=中文", "UTF-8");
			
			res.sendRedirect(redirect_url);
//			AccessToken at = new AccessToken();
//			at.getAccessToken(oauth_token, oauth_token_secret, oauth_vericode);
	    }

	}


}
