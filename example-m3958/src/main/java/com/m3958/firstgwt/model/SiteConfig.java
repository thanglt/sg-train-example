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
@Table(name="SITE_CONFIGS",uniqueConstraints = { @UniqueConstraint(columnNames = { "configKey" })})
@NamedQueries({
	@NamedQuery(name="findClientSideConfig",
				query="SELECT c FROM SiteConfig c WHERE c.clientSide = ?1")
})
public class SiteConfig extends BaseModel<SiteConfig>{
	
	public static class NamedQueries{
		public static String FIND_CLIENT_SIDE_CONFIG = "findClientSideConfig";
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.SITE_CONFIG_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="SITE_CONFIG_ID")
	protected int id;
	

	private String configKey;
	
	private String configValue;
	
	private String description;
	
	private boolean clientSide;
	
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}


	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setClientSide(boolean clientSide) {
		this.clientSide = clientSide;
	}

	public boolean isClientSide() {
		return clientSide;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigKey() {
		return configKey;
	}


}
