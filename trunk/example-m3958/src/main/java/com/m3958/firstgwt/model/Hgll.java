package com.m3958.firstgwt.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.persistence.UniqueConstraint;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.server.types.IHasToCVS;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="HGLL",uniqueConstraints = { @UniqueConstraint(columnNames = { "sfz"})})
public class Hgll extends BaseModel<Hgll> implements IHasToCVS{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		
			jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
			jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			jc.setExcludes(JsonExcludeFields.HGLL_EXCLUDES);
		
		return JSONObject.fromObject(this, jc);
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="HGLL_ID")
	protected int id;
	
	private String xm;
	
	private String xb;
	
	@Temporal(TemporalType.DATE)
	private Date sr;
	
	private String dh;
	
	private String sj;
	
	private String jg;
	
	private String sfz;
	
	private String lxdz;
	
	private String gzdw;//工作单位
	
	private String mz;
	
	private String ssxz;
	
	private String zps;
	
	private String email;
	
	private String cslb;//参赛类别
	
	private String csgq;//参赛歌曲
	
	private String whcd;//文化程度
	
	@Lob
	private String ly;
	
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public Date getSr() {
		return sr;
	}

	public void setSr(Date sr) {
		this.sr = sr;
	}

	public String getDh() {
		return dh;
	}

	public void setDh(String dh) {
		this.dh = dh;
	}

	public String getJg() {
		return jg;
	}

	public void setJg(String jg) {
		this.jg = jg;
	}

	public String getSfz() {
		return sfz;
	}

	public void setSfz(String sfz) {
		this.sfz = sfz;
	}

	public String getLxdz() {
		return lxdz;
	}

	public void setLxdz(String lxdz) {
		this.lxdz = lxdz;
	}

	public String getGzdw() {
		return gzdw;
	}

	public void setGzdw(String gzdw) {
		this.gzdw = gzdw;
	}

	public String getMz() {
		return mz;
	}

	public void setMz(String mz) {
		this.mz = mz;
	}

	public String getSsxz() {
		return ssxz;
	}

	public void setSsxz(String ssxz) {
		this.ssxz = ssxz;
	}

	public void setLy(String ly) {
		this.ly = ly;
	}

	public String getLy() {
		return ly;
	}

	@Override
	public String toCVS(String sperator) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sb = new StringBuffer();
		sb.append(getId()).append(sperator).
			append(getXm() == null ? "" : getXm().replaceAll("\\s", "")).append(sperator).
			append(getXb() == null ? "" : getXb().replaceAll("\\s", "")).append(sperator).
			append(getSr() == null ? "" : df.format(getSr())).append(sperator).
			append(getDh() == null ? "" : getDh().replaceAll("\\s", "")).append(sperator).
			append(getJg() == null ? "" : getJg().replaceAll("\\s", "")).append(sperator).
			append(getSfz() == null ? "" : getSfz().replaceAll("\\s", "")).append(sperator).
			append(getLxdz() == null ? "" : getLxdz().replaceAll("\\s", "")).append(sperator).
			append(getGzdw() == null ? "" : getGzdw().replaceAll("\\s", "")).append(sperator).
			append(getMz() == null ? "" : getMz().replaceAll("\\s", "")).append(sperator).
			append(getSsxz() == null ? "" : getSsxz().replaceAll("\\s", "")).append(sperator).
			append(getLy() == null ? "" : getLy().replaceAll("\\s", "")).
			
			append(getSj() == null ? "" : getSj().replaceAll("\\s", "")).
			append(getEmail() == null ? "" : getEmail().replaceAll("\\s", "")).
			append(getCslb() == null ? "" : getCslb().replaceAll("\\s", "")).
			append(getCsgq() == null ? ""  : getCsgq().replaceAll("\\s", "")).
			append(getWhcd() == null ? "" : getWhcd().replaceAll("\\s", ""))
			;
			
		
		return sb.toString();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setCslb(String cslb) {
		this.cslb = cslb;
	}

	public String getCslb() {
		return cslb;
	}

	public void setCsgq(String csgq) {
		this.csgq = csgq;
	}

	public String getCsgq() {
		return csgq;
	}

	public void setWhcd(String whcd) {
		this.whcd = whcd;
	}

	public String getWhcd() {
		return whcd;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public String getSj() {
		return sj;
	}

	public void setZps(String zps) {
		this.zps = zps;
	}

	public String getZps() {
		return zps;
	}
}
