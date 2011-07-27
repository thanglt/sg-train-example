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
@Table(name="TAGS",uniqueConstraints={@UniqueConstraint(columnNames="name")})
@NamedQueries({
	@NamedQuery(name="findTagsByNames",
			query="SELECT t FROM Tag AS t WHERE t.name IN :names")
})

public class Tag extends BaseModel<Tag>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static class NamedQueries{
		public final static String FIND_TAGS_BY_NAMES = "findTagsByNames";
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="TAG_ID")
	protected int id;
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.TAB_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}	
	
	private String name;
	
	

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
