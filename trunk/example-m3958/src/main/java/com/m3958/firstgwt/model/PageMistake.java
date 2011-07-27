package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="PAGE_MISTAKE")
@NamedQueries({
	@NamedQuery(name="findLatestPageMistakeByIp",
			query="SELECT p FROM PageMistake AS p WHERE p.ip = :ip  ORDER BY p.createdAt DESC")
})
public class PageMistake extends BaseModel<PageMistake>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static class NamedQueries{
		public final static String GET_LATEST_BY_IP = "findLatestPageMistakeByIp";
	}
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.PAGE_MISTAKE_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="MISTAKE_ID")
	protected int id;
	
	private String username;
	
	private String lxfs;
	
	private String pageUrl;
	
	private String errorType;
	
	@Lob
	private String description;
	
	private String ip;
	
	private boolean fixed;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private WebSite webSite;
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	

	public void setWebSite(WebSite webSite) {
		this.webSite = webSite;
	}

	public WebSite getWebSite() {
		return webSite;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLxfs() {
		return lxfs;
	}

	public void setLxfs(String lxfs) {
		this.lxfs = lxfs;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}
}
