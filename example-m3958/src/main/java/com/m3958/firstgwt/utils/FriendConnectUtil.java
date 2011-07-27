package com.m3958.firstgwt.utils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.opensocial.Client;
import org.opensocial.RequestException;
import org.opensocial.Response;
import org.opensocial.auth.AuthScheme;
import org.opensocial.auth.FCAuthScheme;
import org.opensocial.models.Model;
import org.opensocial.models.Person;
import org.opensocial.providers.FriendConnectProvider;
import org.opensocial.providers.Provider;
import org.opensocial.services.PeopleService;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.DemoData;
import com.m3958.firstgwt.dao.RoleDao;
import com.m3958.firstgwt.dao.UserDao;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.session.SessionUser;

@Singleton
public class FriendConnectUtil {
	
	@Inject 
	private Injector injector;
	

	public static String FCSITEID = "15387073527982291758";
	public static String FCCOOKIENAME = "fcauth15387073527982291758";
	private static boolean addIfMissing = true;
	
	public boolean startFCCheck(String loginName){
		Provider provider = new FriendConnectProvider();
		AuthScheme scheme;
		String fcToken = getFauthCookie();
//		fcToken = "ALhR-_sLBJx2fh3s9hQMOzuwHD2Tbk8Z5QqF6YNwl7qzWfiYc8uMuhQgO13UbrsOJirzctLO4_VAG7Jvfoh7vIw4jApERyA3_A~08347242695057973357~jlH8tsmfmLTq";
		if(fcToken != "" && fcToken != null){
			scheme = new FCAuthScheme(fcToken);
		}else{
			return false;
//			scheme = new OAuth2LeggedScheme("*:15387073527982291758","cMZKcWtCRRI=",loginName);
		}
		
		Client client = new Client(provider,scheme);
		
		Person person = null;
		try {
			Response res = client.send(PeopleService.getViewer());
			List<Model> ms = res.getEntries();
			for (Model model : ms) {
				if(model instanceof Person){
					person = (Person) model;
				}
			}
		} catch (RequestException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//查询
		//如果结果出错，返回null
		//正确的话create新用户
		//看看是不是本地用户（已经保存的用户）？
		//保存用户并返回用户
		//如果是本地用户，根据情况处理。
//		person.getDisplayName();
//		person.getId();
//		person.getThumbnailUrl();
		
		if(person != null){
			UserDao dao = injector.getInstance(UserDao.class);
			
			User u = dao.findByFcId(person.getId());
			if(u == null){
				u = new User();
				u.setFcId(person.getId());
				if(addIfMissing){
					String uuid = UUID.randomUUID().toString();
					u.setLoginName(uuid);
					u.setEmail(uuid);
					u.setMobile(uuid);
					u.setFcUser(true);
					
					RoleDao rdao = injector.getInstance(RoleDao.class);
					Role demoRole = rdao.findByEname(DemoData.DEMO_ROLE_ENAME);
					u.addRole(demoRole);
					dao.smartPersistBaseModel(u);
					
				}
			}
			SessionUser su = injector.getInstance(SessionUser.class);
			su.setContent(u);
			su.setLoginName(person.getDisplayName());
			su.setThumbnailUrl(person.getThumbnailUrl());
			return true;
		}
		return false;
	}
	
	public boolean fcLogin(String loginName){
		return startFCCheck(loginName);
	}
	
	public String getFauthCookie(){
		HttpServletRequest req = injector.getInstance(HttpServletRequest.class);
		Cookie[] cookies = req.getCookies();
		if(cookies == null)return null;
		for (int i = 0; i < cookies.length; i++) {
			if(FCCOOKIENAME.equals(cookies[i].getName())){
				return cookies[i].getValue();
			}
		}
		return null;
	}
}
