package com.m3958.firstgwt.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="OBJECT_CLASS_NAME",uniqueConstraints = { @UniqueConstraint(columnNames = { "ename" }),@UniqueConstraint(columnNames = {"cname"}) })
@NamedQueries({
	@NamedQuery(name="findByEname",
		query = "SELECT o FROM ObjectClassName AS o WHERE o.ename = ?1"
	)
})
public class ObjectClassName extends BaseModel<ObjectClassName>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static class OcnNamedQueries{
		public final static String FIND_BY_ENAME = "findByEname";
	}
	

	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.OBJECTCLASS_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="O_C_ID")
	protected int id;
	

	@Override
	public int getId() {
		return id;
	}
	

	@Override
	public void setId(int id) {
		this.id = id;
	}
	
//	@OneToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="OPCAT_ID",referencedColumnName="OPERATION_CAT_ID")
//	private OperationCat operationCat;
//	
//	
//	public int getOperationCatId(){
//		if(operationCat == null){
//			return SmartConstants.NONE_EXIST_MODEL_ID;
//		}else{
//			return operationCat.getId();
//		}
//		
//	}
	
	private String ename;
	
	private String cname;
	
	private String daoName;
	
	private String checkerName;
	
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}

	public String getDaoName() {
		return daoName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	public String getCheckerName() {
		return checkerName;
	}


//	public void setOperationCat(OperationCat operationCat) {
//		this.operationCat = operationCat;
//	}
//
//
//	public OperationCat getOperationCat() {
//		return operationCat;
//	}


}
