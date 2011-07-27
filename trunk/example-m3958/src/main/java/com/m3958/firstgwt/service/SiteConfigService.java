package com.m3958.firstgwt.service;

import java.io.File;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.ConfigKey;
import com.m3958.firstgwt.client.types.FileSaveTo;
import com.m3958.firstgwt.dao.SiteConfigDao;
import com.m3958.firstgwt.model.SiteConfig;
import com.m3958.firstgwt.utils.StringUtils;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class SiteConfigService {
	
	private String assetSavePath;
	private Long maxUpload = 2*1024*1024L;
	private String uploadTmpDir;
	private FileSaveTo saveTo;
	private String jasperTemplateDir;
	private boolean cache_enable;
	private String siteRoot;
	private String appDomain;
	private String perlExec;
	private String allowEditExts;
	private String error404File;
	private String demoHostName;
	private String sorlCamelDir;
	
	private String[] allowedTypes = new String[]{};
	

	private SiteConfigDao dao;

	@Inject
	public SiteConfigService(SiteConfigDao dao){
		this.dao = dao;
		reload();
	}
	
	@Transactional
	public void reload(){
		List<SiteConfig> configs = dao.fetchAll();
		for (SiteConfig siteConfig : configs) {
			try {
				ConfigKey key = ConfigKey.getFieldEnumByEname(siteConfig.getConfigKey());
				if(key == null)continue;
				String value = siteConfig.getConfigValue();
				if(value == null || value.trim().isEmpty())continue;
				switch (key) {
				case APP_DOMAIN:
					setAppDomain(value);
					break;
				case ASSET_SAVE_TO:
					if("db".equals(value)){
						setSaveTo(FileSaveTo.BERKELEY_DB);
					}else{
						setSaveTo(FileSaveTo.FILE_SYSTEM);
					}
					break;
				case JASPER_TEMPLATE_DIR:
					setJasperTemplateDir(value);
					break;
				case ASSET_PATH:
					setAssetSavePath(value);
					break;
				case ENABLE_CACHE:
					if("yes".equalsIgnoreCase(value) || "1".equalsIgnoreCase(value) || "是".equalsIgnoreCase(value)){
						setCache_enable(true);
					}else{
						setCache_enable(false);
					}
					break;
				case MAX_UPLOAD:
					setMaxUpload(StringUtils.getFileSizeLongValue(value));
					break;
				case PERL_EXEC:
					setPerlExec(value);
					break;
				case SITE_ROOT:
					setSiteRoot(value);
					break;
				case UPLOAD_TMP_DIR:
					setUploadTmpDir(value);
					break;
				case ALLOW_EDIT_EXTS:
					setAllowEditExts(value);
					allowedTypes = getAllowEditExts().split("\\s");
					break;
				case ERROR_FOUROFOUR_FILE:
					setError404File(value);
					break;
				case DEMO_HOST_NAME:
					setDemoHostName(value);
					break;
				case SOLR_CAMEL_DIR:
					setSorlCamelDir(value);
					break;
				default:
					break;
				}
			} catch (NumberFormatException e) {}
		}
	}
	
	public boolean editable(File fn){
		String ext = StringUtils.getFileExtensionWithDot(fn);
		for(String s:allowedTypes){
			if(s.equalsIgnoreCase(ext)){
				return true;
			}
		}
		return false;
	}
	
	public void setAssetSavePath(String assetSavePath) {
		this.assetSavePath = assetSavePath;
	}

	public String getAssetSavePath() {
		return assetSavePath;
	}
	
	public Long getMaxUpload() {
		return maxUpload;
	}

	public void setMaxUpload(Long maxUpload) {
		this.maxUpload = maxUpload;
	}

	public void setUploadTmpDir(String uploadTmpDir) {
		this.uploadTmpDir = uploadTmpDir;
	}

	public String getUploadTmpDir() {
		return uploadTmpDir;
	}

	public void setSaveTo(FileSaveTo saveTo) {
		this.saveTo = saveTo;
	}

	public FileSaveTo getSaveTo() {
		return saveTo;
	}

	public void setJasperTemplateDir(String jasperTemplateDir) {
		this.jasperTemplateDir = jasperTemplateDir;
	}

	public String getJasperTemplateDir() {
		return jasperTemplateDir;
	}
	
	public void createDirIfNotExist(String dir){
		File f = new File(getAssetSavePath(),dir);
		if(!f.exists()){
			f.mkdirs();
		}
	}

	public void setCache_enable(boolean cache_enable) {
		this.cache_enable = cache_enable;
	}

	public boolean isCache_enable() {
		return cache_enable;
	}

	public void setSiteRoot(String siteRoot) {
		this.siteRoot = siteRoot;
	}

	public String getSiteRoot() {
		return siteRoot;
	}

	public void setAppDomain(String appDomain) {
		this.appDomain = appDomain;
	}

	public String getAppDomain() {
		return appDomain;
	}

	public void setPerlExec(String perlExec) {
		this.perlExec = perlExec;
	}

	public String getPerlExec() {
		return perlExec;
	}

	public void setAllowEditExts(String allowEditExts) {
		this.allowEditExts = allowEditExts;
	}

	public String getAllowEditExts() {
		return allowEditExts;
	}

	public void setError404File(String error404File) {
		this.error404File = error404File;
	}

	public String getError404File() {
		return error404File;
	}

	public void setDemoHostName(String demoHostName) {
		this.demoHostName = demoHostName;
	}

	public String getDemoHostName() {
		return demoHostName;
	}

	public void setSorlCamelDir(String sorlCamelDir) {
		this.sorlCamelDir = sorlCamelDir;
	}

	public String getSorlCamelDir() {
		return sorlCamelDir;
	}

	}
