package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="WEBHOSTS",uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
@NamedQueries({
	@NamedQuery(name="findWebHostByName",
			query="SELECT w FROM WebHost AS w WHERE w.name = ?1")
})
public class WebHost extends BaseModel<WebHost>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static class NamedQueries{
		public final static String FIND_BY_NAME = "findWebHostByName";
	}
	
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.WEBHOST_EXCLUDES);
		JSONObject jo =  JSONObject.fromObject(this, jc);
		jo.element("name", "http://" + name);
		return jo;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="WEBHOST_ID")
	protected int id;
	
	private String name;
	
	private String theme = "default";
	
	private boolean audit;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private WebSite webSite;
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	
	public void setName(String name) {
		String tname = name;
		if(tname.toLowerCase().startsWith("http://")){
			tname = tname.substring(7);
		}
		this.name = tname;
	}

	public String getName() {
		return name;
	}

	public void setWebSite(WebSite webSite) {
		this.webSite = webSite;
	}

	public WebSite getWebSite() {
		return webSite;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getTheme() {
		if(theme == null){
			return "default";
		}
		return theme;
	}

	public void setAudit(boolean audit) {
		this.audit = audit;
	}

	public boolean isAudit() {
		return audit;
	}

}
