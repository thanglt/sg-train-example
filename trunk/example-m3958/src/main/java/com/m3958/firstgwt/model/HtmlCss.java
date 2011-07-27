package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.server.types.IHasAudit;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

@Entity
@Table(name="HTML_CSS")
@NamedQueries({
	@NamedQuery(name="findUniqueHtmlCss",
			query="SELECT h FROM HtmlCss AS h WHERE h.name = :name AND h.contentType = :contentType AND h.contentVersion = :contentVersion"),
	@NamedQuery(name="findHtmlCssByType",
			query="SELECT h FROM HtmlCss AS h WHERE h.contentType = :contentType AND h.contentVersion = :contentVersion AND h.audit = :audit ORDER BY h.name ASC"),
	@NamedQuery(name="findHtmlCssByTypeCount",
			query="SELECT COUNT(DISTINCT h) FROM HtmlCss AS h WHERE h.contentType = :contentType AND h.contentVersion = :contentVersion AND h.audit = :audit")
})
public class HtmlCss extends BaseModel<HtmlCss> implements IHasAudit{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	public static class NamedQueries{
		public final static String FIND_UNIQUE = "findUniqueHtmlCss";
		public final static String FIND_BY_TYPE = "findHtmlCssByType";
		public final static String FIND_BY_TYPE_COUNT = "findHtmlCssByTypeCount";
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="HTML_CSS_ID")
	protected int id;
	
	private String name;
	
	private String content;
	
	@Lob
	private String description;
	
	private String contentType;
	
	private boolean audit;
	
	private String contentVersion;
	
	private String contributor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public JSONObject toJson() {
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.HTMLCSS_EXCLUDES);
		
		return JSONObject.fromObject(this,jc);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public boolean isAudit() {
		return audit;
	}

	public void setAudit(boolean audit) {
		this.audit = audit;
	}

	public String getContentVersion() {
		return contentVersion;
	}

	public void setContentVersion(String contentVersion) {
		this.contentVersion = contentVersion;
	}

	public String getContributor() {
		return contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
