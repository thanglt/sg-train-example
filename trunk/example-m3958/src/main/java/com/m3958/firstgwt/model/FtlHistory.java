package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="FTL_HISTORIES")
public class FtlHistory extends BaseModel<FtlHistory>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.FTL_EXCLUDES);
		
		return JSONObject.fromObject(this, jc);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="FTL_HISTORY_ID")
	protected int id;
	
	@Lob
	@Column(length=16777210)
	private String ftl;
	
	@ManyToOne
	@JoinColumn(name="CUR_FTL_ID",referencedColumnName="FTL_ID")
	private Ftl currentFtl; 

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

	public void setCurrentFtl(Ftl currentFtl) {
		this.currentFtl = currentFtl;
	}

	public Ftl getCurrentFtl() {
		return currentFtl;
	}
}
