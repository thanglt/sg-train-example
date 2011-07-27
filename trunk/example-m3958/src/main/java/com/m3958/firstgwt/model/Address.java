package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.server.types.BelongToLgb;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="ADDRESSES")
public class Address extends BaseModel<Address> implements BelongToLgb{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="ADDRESS_ID")
	protected int id;
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.ADDRESS_EXCLUDES);

		return JSONObject.fromObject(this, jc);
	}	
	
	
	private boolean mainAddress; 
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2400704949459391026L;
	//家庭住址
	private String dizhi;
	
//	@ManyToOne
//	@JoinColumn(name="SHEQU_ID",referencedColumnName="SHEQU_ID")
//	private Shequ shequ;
	
	//社区联系人
	private String sqlxr;
	
	//住宅电话
	private String dianhua;
	
	//手机
	private String shouji;
	
	//备注
	@Lob
	private String bz;

	public String getDizhi() {
		return dizhi;
	}

	public void setDizhi(String dizhi) {
		this.dizhi = dizhi;
	}


	public String getSqlxr() {
		return sqlxr;
	}

	public void setSqlxr(String sqlxr) {
		this.sqlxr = sqlxr;
	}

	public String getShouji() {
		return shouji;
	}

	public void setShouji(String shouji) {
		this.shouji = shouji;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public void setDianhua(String dianhua) {
		this.dianhua = dianhua;
	}

	public String getDianhua() {
		return dianhua;
	}

//	public void setLgb(Lgb lgb) {
//		this.lgb = lgb;
//	}
//
//	public Lgb getLgb() {
//		return lgb;
//	}

//	public void setShequ(Shequ shequ) {
//		this.shequ = shequ;
//	}
//
//	public Shequ getShequ() {
//		return shequ;
//	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setMainAddress(boolean mainAddress) {
		this.mainAddress = mainAddress;
	}

	public boolean isMainAddress() {
		return mainAddress;
	}

}
