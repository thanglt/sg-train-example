package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="FTLS",uniqueConstraints = { @UniqueConstraint(columnNames = { "name"})})
@NamedQueries({
	@NamedQuery(name="findFtlByName",
			query="SELECT f FROM Ftl AS f WHERE f.name = :name")
})
public class Ftl extends BaseModel<Ftl>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static class NamedQueries{
		public final static String FIND_BY_NAME = "findFtlByName";
	}
	

	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.FTL_EXCLUDES);
		
		return JSONObject.fromObject(this, jc);
	}
	
	@Override
	public JSONObject toJsonOne(){
		JSONObject jo = toJson();
		jo.element("ftl", ftl);
		return jo;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="FTL_ID")
	protected int id;
	
	private String name;
	
	private String description;
	
	@Lob
	@Column(length=16777210)
	@Basic(fetch=FetchType.LAZY)
	private String ftl;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setFtl(String ftl) {
		this.ftl = ftl;
	}

	public String getFtl() {
		return ftl;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

//	public void setHistories(List<FtlHistory> histories) {
//		this.histories = histories;
//	}
//
//	public List<FtlHistory> getHistories() {
//		return histories;
//	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
