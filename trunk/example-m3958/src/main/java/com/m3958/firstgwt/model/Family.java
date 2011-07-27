package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="FAMILIES")
public class Family extends BaseModel<Family> implements BelongToLgb{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.FAMILY_EXCLUDES);
		
		return JSONObject.fromObject(this, jc);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="FAMILY_ID")
	protected int id;
	
	private String xm;
	
	private String gx;
	
	private String xb;
	
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	private String zzmm;
	
	private String shouji;
	
	private String jkzk;
	
	private String dizhi;
	
	private String dianhua;
	
	
	private String bz;
	
	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getGx() {
		return gx;
	}

	public void setGx(String gx) {
		this.gx = gx;
	}


	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getZzmm() {
		return zzmm;
	}

	public void setZzmm(String zzmm) {
		this.zzmm = zzmm;
	}

	public String getJkzk() {
		return jkzk;
	}

	public void setJkzk(String jkzk) {
		this.jkzk = jkzk;
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

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setShouji(String shouji) {
		this.shouji = shouji;
	}

	public String getShouji() {
		return shouji;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public String getXb() {
		return xb;
	}

}
