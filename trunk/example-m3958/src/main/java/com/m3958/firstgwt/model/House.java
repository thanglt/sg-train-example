package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.server.types.BelongToLgb;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="HOUSES")
public class House extends BaseModel<House> implements BelongToLgb{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		
			jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
			jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			jc.setExcludes(JsonExcludeFields.HOUSE_EXCLUDES);
		
		return JSONObject.fromObject(this, jc);
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="HOUSE_ID")
	protected int id;
	
	
	private String zfxz;
	
	private Float area;
	
	private String structure;
	
	private String bz;
	
	public String getZfxz() {
		return zfxz;
	}

	public void setZfxz(String zfxz) {
		this.zfxz = zfxz;
	}


	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setArea(Float area) {
		this.area = area;
	}

	public Float getArea() {
		return area;
	}

}
