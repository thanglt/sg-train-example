package com.m3958.firstgwt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.sf.json.JSONObject;

@Entity
@Table(name="CACHED_CONTENT",uniqueConstraints = { @UniqueConstraint(columnNames = { "hostName","theme","tplName","obName","obid" }),
		@UniqueConstraint(columnNames = {"requestUrl"}),
		@UniqueConstraint(columnNames = {"etag"})})
@NamedQueries({
	@NamedQuery(name="findCachedContentByEtag",
			query="SELECT c FROM CachedContent AS c WHERE c.etag = :etag"),
	@NamedQuery(name="findCachedContentByFields",
			query="SELECT c FROM CachedContent c WHERE c.hostName = :hostName AND c.theme = :theme AND c.tplName = :tplName AND c.obName = :obName AND c.obid = :obid"),
	@NamedQuery(name="findCachedContentByUrl",
			query="SELECT c FROM CachedContent c WHERE c.requestUrl = :urlPlusQueryString")			
})		
public class CachedContent extends BaseModel<CachedContent>{

	public static class NamedQueries{
		public final static String FIND_BY_ETAG = "findCachedContentByEtag";
		public final static String FIND_BY_FIELDS = "findCachedContentByFields";
		public final static String FIND_BY_URL = "findCachedContentByUrl";
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="FTL_ID")
	protected int id;
	
	private String hostName;
	
	private String theme;
	
	private String tplName;
	
	private String obName;
	
	private int obid;
	
	private String requestUrl;
	
	@Lob
	private String content;
	
	private String etag;

	@Override
	public JSONObject toJson() {
		return null;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getTplName() {
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public String getObName() {
		return obName;
	}

	public void setObName(String obName) {
		this.obName = obName;
	}

	public int getObid() {
		return obid;
	}

	public void setObid(int obid) {
		this.obid = obid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestUrl() {
		return requestUrl;
	}
}
