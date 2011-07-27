package com.m3958.firstgwt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.sf.json.JSONObject;

@Entity
@Table(name="CACHE_BREAKER")
@NamedQueries({
	@NamedQuery(name="findDupCacheBreakerItems",
			query="SELECT c FROM CacheBreakerItem AS c WHERE c.siteId = :siteId AND c.obType = :obType " +
					"AND c.obId = :obId AND c.obName = :obName AND c.action = :action AND c.done = :done")
})
public class CacheBreakerItem extends BaseModel<CacheBreakerItem>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static class NamedQueries{
		public final static String FIND_DUPLICATION = "findDupCacheBreakerItems";
	}

	public static enum ObType{
		TEMPLATE,SECTION,ARTICLE,XJ_CAT,XX
	}
	
	public static enum ObAction{
		ADD,UPDATE,DELETE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="ADDRESS_ID")
	protected int id;
	
	private int siteId;
	
	@Enumerated(EnumType.STRING)
	private ObType obType;
	
	private int obId;
	
	private String obName;
	
	@Enumerated(EnumType.STRING)
	private ObAction action;
	
	private boolean done;
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public JSONObject toJson() {
		return null;
	}

	public ObType getObType() {
		return obType;
	}

	public void setObType(ObType obType) {
		this.obType = obType;
	}

	public int getObId() {
		return obId;
	}

	public void setObId(int obId) {
		this.obId = obId;
	}

	public String getObName() {
		return obName;
	}

	public void setObName(String obName) {
		this.obName = obName;
	}

	public ObAction getAction() {
		return action;
	}

	public void setAction(ObAction action) {
		this.action = action;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}


	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getSiteId() {
		return siteId;
	}
}
