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
@Table(name="SHEQUS")
public class Shequ extends BaseModel<Shequ>{
	

	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.SHEQU_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="SHEQU_ID")
	protected int id;
	
	private String dizhi;
	
	private String dianhua;
	
	private String sqfzr;
	
	private String shouji;
	
	//备注
	@Lob
	private String bz;

	
	@ManyToOne
	@JoinColumn(name="DEPARTMENT_ID",referencedColumnName="DEPARTMENT_ID")
	private Department department;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2400704949459391026L;
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDizhi() {
		return dizhi;
	}

	public void setDizhi(String dizhi) {
		this.dizhi = dizhi;
	}

	public String getDianhua() {
		return dianhua;
	}

	public void setDianhua(String dianhua) {
		this.dianhua = dianhua;
	}




	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Department getDepartment() {
		return department;
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setSqfzr(String sqfzr) {
		this.sqfzr = sqfzr;
	}

	public String getSqfzr() {
		return sqfzr;
	}

	public void setShouji(String shouji) {
		this.shouji = shouji;
	}

	public String getShouji() {
		return shouji;
	}

}
