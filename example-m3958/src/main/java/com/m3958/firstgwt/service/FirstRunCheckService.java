package com.m3958.firstgwt.service;

import java.io.File;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.ConfigKey;
import com.m3958.firstgwt.client.types.MenuItemCategory;
import com.m3958.firstgwt.dao.MenuItemDao;
import com.m3958.firstgwt.dao.MenuLevelDao;
import com.m3958.firstgwt.dao.RoleDao;
import com.m3958.firstgwt.dao.SiteConfigDao;
import com.m3958.firstgwt.dao.UserDao;
import com.m3958.firstgwt.model.MenuItem;
import com.m3958.firstgwt.model.MenuLevel;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.SiteConfig;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.server.types.AppConstants;
import com.m3958.firstgwt.utils.Osdetecter;

@Singleton
public class FirstRunCheckService {
	
	private UserDao udao;
	
	private MenuLevelDao mlDao;
	
	private MenuItemDao miDao;
	
	private SiteConfigDao scDao;
	
	private RoleDao rdao;
	
	private boolean initialized = false;
	
	@Inject
	public FirstRunCheckService(UserDao udao,MenuLevelDao mlDao,MenuItemDao miDao,SiteConfigDao scDao,RoleDao rdao){
		this.udao = udao;
		this.mlDao = mlDao;
		this.miDao = miDao;
		this.scDao = scDao;
		this.rdao = rdao;
		check();
	}

	private void check() {
		Integer userCount = udao.getRowCount();
		setInitialized(userCount > 0);
		if(!initialized){
			initializeApp();
		}else{
			System.out.print("already initialized!");
		}
	}

	private void initializeApp() {
		initMenuItems();
		initConfigs();
		initSuperman();
	}

	private void initConfigs() {
		if(Osdetecter.isWindows()){
			SiteConfig sc = new SiteConfig();
			sc.setConfigKey(ConfigKey.ASSET_PATH.toString());
			sc.setConfigValue("");
			scDao.persist(sc);
			
			sc = new SiteConfig();
			sc.setConfigKey(ConfigKey.MAX_UPLOAD.toString());
			sc.setConfigValue("");
			scDao.persist(sc);
			
			sc = new SiteConfig();
			sc.setConfigKey(ConfigKey.UPLOAD_TMP_DIR.toString());
			sc.setConfigValue("");
			scDao.persist(sc);
	
			sc = new SiteConfig();
			sc.setConfigKey(ConfigKey.ASSET_SAVE_TO.toString());
			sc.setConfigValue("");
			scDao.persist(sc);
	
			sc = new SiteConfig();
			sc.setConfigKey(ConfigKey.JASPER_TEMPLATE_DIR.toString());
			sc.setConfigValue("");
			scDao.persist(sc);
	
			sc = new SiteConfig();
			sc.setConfigKey(ConfigKey.ENABLE_CACHE.toString());
			sc.setConfigValue("");
			scDao.persist(sc);
	
			sc = new SiteConfig();
			sc.setConfigKey(ConfigKey.SITE_ROOT.toString());
			sc.setConfigValue("c:/psalms-cms-sites");
			scDao.persist(sc);
			
			File f = new File(sc.getConfigValue());
			if(!f.exists())f.mkdir();
	
			sc = new SiteConfig();
			sc.setConfigKey(ConfigKey.APP_DOMAIN.toString());
			sc.setConfigValue("");
			scDao.persist(sc);		
			
			sc = new SiteConfig();
			sc.setConfigKey(ConfigKey.PERL_EXEC.toString());
			sc.setConfigValue("");
			scDao.persist(sc);
		}else{
			
		}
		
	}
	
	private MenuLevel ml;

	private void initMenuItems() {

		MenuItem mi1 = new MenuItem();
		mi1.setTitle("用户管理");
		mi1.setUniqueName("user");
		mi1.setMenuItemCat(MenuItemCategory.BASE);
		miDao.smartPersistBaseModel(mi1);
		
		MenuItem mi2 = new MenuItem();
		mi2.setTitle("角色管理");
		mi2.setUniqueName("role");
		mi2.setMenuItemCat(MenuItemCategory.BASE);
		miDao.smartPersistBaseModel(mi2);

		
		MenuItem mi3 = new MenuItem();
		mi3.setTitle("字段管理");
		mi3.setUniqueName("fieldenum");
		mi3.setMenuItemCat(MenuItemCategory.BASE);
		miDao.smartPersistBaseModel(mi3);

		
		MenuItem mi4 = new MenuItem();
		mi4.setTitle("对象类型");
		mi4.setUniqueName("objectclass");
		mi4.setMenuItemCat(MenuItemCategory.BASE);
		miDao.smartPersistBaseModel(mi4);

		
		MenuItem mi5 = new MenuItem();
		mi5.setTitle("模板管理");
		mi5.setUniqueName("ftl");
		mi5.setMenuItemCat(MenuItemCategory.BASE);
		miDao.smartPersistBaseModel(mi5);
		
		MenuItem mi9 = new MenuItem();
		mi9.setTitle("菜单管理");
		mi9.setUniqueName("menuitem");
		mi9.setMenuItemCat(MenuItemCategory.BASE);
		miDao.smartPersistBaseModel(mi9);
		

		MenuItem mi7 = new MenuItem();
		mi7.setTitle("网站管理");
		mi7.setUniqueName("website");
		mi7.setMenuItemCat(MenuItemCategory.BASE);
		miDao.smartPersistBaseModel(mi7);
		
		MenuItem mi8 = new MenuItem();
		mi8.setTitle("反馈管理");
		mi8.setUniqueName("feedback");
		mi8.setMenuItemCat(MenuItemCategory.BASE);
		miDao.smartPersistBaseModel(mi8);
		
		ml = new MenuLevel();
		ml.setName("超级用户菜单");
		
		ml.getMenuitems().add(mi1);
		ml.getMenuitems().add(mi2);
		ml.getMenuitems().add(mi3);
		ml.getMenuitems().add(mi4);
		ml.getMenuitems().add(mi5);
		ml.getMenuitems().add(mi7);
		ml.getMenuitems().add(mi8);
		ml.getMenuitems().add(mi9);
		
		mlDao.smartPersistBaseModel(ml);
		
		MenuLevel ml1 = new MenuLevel();
		ml1.setName(AppConstants.DEFAULT_MENULEVEL);
		
		ml1.getMenuitems().add(mi1);
		ml1.getMenuitems().add(mi2);
		ml1.getMenuitems().add(mi7);
	}

	private void initSuperman() {
		User u = new User();
		u.setLoginName("superman");
		u.setEmail("superman@m3958.com");
		u.setPassword("superman");
		u.setMenuLevel(ml);
		
		Role r = new Role();
		r.setCname("超级用户");
		r.setOrname("superman");
		rdao.smartPersistBaseModel(r);
		
		u.addRole(r);
		
		udao.smartPersistBaseModel(u);
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public boolean isInitialized() {
		return initialized;
	}
}
