package com.m3958.firstgwt.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtilsBean;

import com.google.inject.Injector;
import com.m3958.firstgwt.server.types.AppConstants;
import com.m3958.firstgwt.server.types.HasToJson;
import com.m3958.firstgwt.server.types.TreeModel;
import com.m3958.firstgwt.utils.SpecialDate;

@MappedSuperclass
public abstract class BaseModel<T extends BaseModel<T>> implements Serializable,HasToJson {
	private static final long serialVersionUID = -4639808419000175419L;
	
	@Transient
	protected static com.google.inject.Provider<EntityManager> emp;
	
	@Transient
	protected static Injector injector;
	
	public static void setEmpProvider(com.google.inject.Provider<EntityManager> thatEmp){
		emp = thatEmp;
	}
	
	public static void setInjector(Injector thatInjector){
		injector = thatInjector;
	}
	
	protected WebSite getSite(int siteId){
		return emp.get().find(WebSite.class, siteId);
	}
	
	public abstract int getId();

	public abstract void setId(int id);
	
	public JSONObject toJsonOne(){
		return toJson();
	}

	@Version
	protected int version;
	
	@Transient
	private PropertyUtilsBean pub = new PropertyUtilsBean();
	
	protected Object getAttribute(String attrName){
		try {
			return pub.getProperty(this, attrName);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = new Date();

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	
	
	public String getCreatedAt(String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(getCreatedAt());
	}
	
	public String getCreatedAt(String format,String tformat) {
		SpecialDate sd = new SpecialDate();
		DateFormat df;
		if(sd.isDateToday(getCreatedAt())){
			df = new SimpleDateFormat(tformat);
		}else{
			df = new SimpleDateFormat(format);
		}
		return df.format(getCreatedAt());
	}
	
	protected String getCreatorPath(User u) {
		String path = ",";
		User tmpu = u;
		while(tmpu != null){
			path += tmpu.getId() + ",";
			tmpu = tmpu.getCreator();
		}
		return path;
	}
	
	protected  String getParentPath(TreeModel<T> other) {
		String s = ",";
		TreeModel<T> cu = other;
		while(cu != null){
			s += cu.getId() + ",";
			cu = (TreeModel<T>) cu.getParent();
		}
		return s;
	}
	
	protected String getFriendUrl(String obName,String tplName,int id){
//		if(tplName.endsWith(AppConstants.TPL_FILE_EXTENSION)){
//			tplName = tplName.substring(0, tplName.lastIndexOf(AppConstants.TPL_FILE_EXTENSION));
//		}
		if(obName.equals(tplName)){
			return "/" + obName +"/" + id;
		}else{
			return "/" + obName+ "/" + id + "?" + AppConstants.TPL_PARAMETER_NAME + "=" + tplName;
		}
	}
}
