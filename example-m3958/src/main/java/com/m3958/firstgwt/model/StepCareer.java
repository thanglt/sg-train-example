package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.server.types.BelongToLgb;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="STEP_CAREERS")
public class StepCareer extends BaseModel<StepCareer> implements BelongToLgb{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.STEPCAREER_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="STEP_CAREER_ID")
	protected int id;
	
	
	@Temporal(TemporalType.DATE)
	private Date start;
	
	@Temporal(TemporalType.DATE)
	private Date end;
	
	@Lob
	private String zysj;
	
	
	private String bz;

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getBz() {
		return bz;
	}

	public void setZysj(String zysj) {
		this.zysj = zysj;
	}

	public String getZysj() {
		return zysj;
	}

}
