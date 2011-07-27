package com.m3958.firstgwt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.sf.json.JSONObject;
/*
 * 一个对象一天一条记录
 */
@Entity
@Table(name="PAGE_HIT")
@NamedQueries({
	@NamedQuery(name="findPageHitByObject",
			query="SELECT SUM(p.hitNum) FROM PageHit AS p WHERE p.siteId = :siteId AND p.obId = :obId AND p.createdAt >= :startDate"),
	@NamedQuery(name="findPageHitBySite",
			query="SELECT SUM(p.hitNum) FROM PageHit AS p WHERE p.siteId = :siteId AND p.createdAt >= :startDate"),
	@NamedQuery(name="findTopVisits",
			query="SELECT new com.m3958.firstgwt.client.types.PageHitObject(p.obId,sum(p.hitNum)) FROM PageHit AS p WHERE p.siteId = :siteId AND p.obname = :obname GROUP BY p.obId ORDER BY sum(p.hitNum) DESC"),
//SELECT FUNC('YEAR', whenRegistered) Y, COUNT(registration.id) C FROM registration GROUP BY Y		 
	@NamedQuery(name="siteDayVisitNum",
			query="SELECT new com.m3958.firstgwt.client.types.VisitStatItem(FUNC('DATE',p.createdAt),sum(p.hitNum)) FROM PageHit AS p WHERE p.siteId = :siteId AND p.createdAt >= :startDate GROUP BY FUNC('DATE',p.createdAt) ORDER BY FUNC('DATE',p.createdAt) ASC")
})
public class PageHit extends BaseModel<PageHit>{
	
	public static class NamedQueries{
		public final static String FIND_PAGE_HIT_BY_SITE = "findPageHitBySite";
		public final static String FIND_PAGE_HIT_BY_OBJECT = "findPageHitByObject";
		public final static String FIND_TOP_VISITS = "findTopVisits";
		public final static String SITE_DAY_VISIT_NUM = "siteDayVisitNum";
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="PAGE_HIT_ID")
	protected int id;
	
	private int siteId;
	
	@Column(length=40)
	private String obname;
	
	@Column(length=40)
	private String obId;
	
	private int hitNum;

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getObId() {
		return obId;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public JSONObject toJson() {
		return null;
	}

	public void setHitNum(int hitNum) {
		this.hitNum = hitNum;
	}

	public int getHitNum() {
		return hitNum;
	}

	public void setObname(String obname) {
		this.obname = obname;
	}

	public String getObname() {
		return obname;
	}
}
