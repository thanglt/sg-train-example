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
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="FIELDENUMS")
public class FieldEnum extends BaseModel<FieldEnum> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="FIELD_ENUM_ID")
	protected int id;
	

	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		
			jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
			jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			jc.setExcludes(JsonExcludeFields.FIELDENUM_EXCLUDES);
		
		return JSONObject.fromObject(this, jc);
	}
	

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8067558297518368216L;
	
	private String fieldType;
	
	private int position;
	
	private String fieldValue;
	
	
	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}
}
