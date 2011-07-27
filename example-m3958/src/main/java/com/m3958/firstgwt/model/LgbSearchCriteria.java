package com.m3958.firstgwt.model;

import java.util.Date;
import java.util.HashSet;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.BooleanSearchValue;
import com.m3958.firstgwt.client.types.HeadTailIncludeType;
import com.m3958.firstgwt.service.ParaUtilService;
import com.m3958.firstgwt.service.ReqParaService;


public class LgbSearchCriteria {
	
	@Inject
	private ParaUtilService paraUtilService;
	
	@Inject
	private ReqParaService reqPs;
	
	private int sr_start;
	private int sr_end;
	private HeadTailIncludeType sr_htit;
	
	private Date rwsj_start;
	private Date rwsj_end;
	private HeadTailIncludeType rwsj_htit;
	
	private Date rdsj_start;
	private Date rdsj_end;
	private HeadTailIncludeType rdsj_htit;
	
	
	private Date ltxsj_start;
	private Date ltxsj_end;
	private HeadTailIncludeType ltxsj_htit;
	
	private Date swsj_start;
	private Date swsj_end;
	private HeadTailIncludeType swsj_htit;
	

	
	private BooleanSearchValue passAway_radio;
	
	private BooleanSearchValue rdsj_radio;
	
	
	private String allStrClause;
	
	private String dateString;
	
	private String radioString;
	
	public String getStringClause(){
		if(allStrClause == null){
			allStrClause = paraUtilService.getAllStrClause(Lgb.class.getName(), new HashSet<String>(), reqPs.getAllParas(), reqPs.getTextMetchStyle());
		}
		return allStrClause;
	}
	
	public String getDateClause(){
		if(dateString == null){
			dateString = "";
			dateString += paraUtilService.getDateBetweenClause(this, "sr");
			dateString += paraUtilService.getDateBetweenClause(this, "rwsj");
			dateString += paraUtilService.getDateBetweenClause(this, "rdsj");
			dateString += paraUtilService.getDateBetweenClause(this, "ltxsj");
			dateString += paraUtilService.getDateBetweenClause(this, "swsj");
		}
		return dateString;
	}
	
	public String getRadioClause(){
		if(radioString == null){
			radioString = "";
			radioString += paraUtilService.getBooleanRadioStr(this, "passAway");
			radioString += paraUtilService.getEmptyRadioStr(this, "rdsj");
		}
		return radioString;
	}
	
	public String getWhereString(){
		return getStringClause() + getDateClause() + getRadioClause();
	}

	public Date getRwsj_start() {
		return rwsj_start;
	}
	public void setRwsj_start(Date rwsjStart) {
		rwsj_start = rwsjStart;
	}
	public Date getRwsj_end() {
		return rwsj_end;
	}
	public void setRwsj_end(Date rwsjEnd) {
		rwsj_end = rwsjEnd;
	}
	public HeadTailIncludeType getRwsj_htit() {
		return rwsj_htit;
	}
	public void setRwsj_htit(HeadTailIncludeType rwsjHtit) {
		rwsj_htit = rwsjHtit;
	}
	public Date getRdsj_start() {
		return rdsj_start;
	}
	public void setRdsj_start(Date rdsjStart) {
		rdsj_start = rdsjStart;
	}
	public Date getRdsj_end() {
		return rdsj_end;
	}
	public void setRdsj_end(Date rdsjEnd) {
		rdsj_end = rdsjEnd;
	}
	public HeadTailIncludeType getRdsj_htit() {
		return rdsj_htit;
	}
	public void setRdsj_htit(HeadTailIncludeType rdsjHtit) {
		rdsj_htit = rdsjHtit;
	}
	public Date getLtxsj_start() {
		return ltxsj_start;
	}
	public void setLtxsj_start(Date ltxsjStart) {
		ltxsj_start = ltxsjStart;
	}
	public Date getLtxsj_end() {
		return ltxsj_end;
	}
	public void setLtxsj_end(Date ltxsjEnd) {
		ltxsj_end = ltxsjEnd;
	}
	public HeadTailIncludeType getLtxsj_htit() {
		return ltxsj_htit;
	}
	public void setLtxsj_htit(HeadTailIncludeType ltxsjHtit) {
		ltxsj_htit = ltxsjHtit;
	}
	public Date getSwsj_start() {
		return swsj_start;
	}
	public void setSwsj_start(Date swsjStart) {
		swsj_start = swsjStart;
	}
	public Date getSwsj_end() {
		return swsj_end;
	}
	public void setSwsj_end(Date swsjEnd) {
		swsj_end = swsjEnd;
	}
	public HeadTailIncludeType getSwsj_htit() {
		return swsj_htit;
	}
	public void setSwsj_htit(HeadTailIncludeType swsjHtit) {
		swsj_htit = swsjHtit;
	}


	public void setRdsj_radio(BooleanSearchValue rdsj_radio) {
		this.rdsj_radio = rdsj_radio;
	}

	public BooleanSearchValue getRdsj_radio() {
		return rdsj_radio;
	}

	public void setSr_start(int sr_start) {
		this.sr_start = sr_start;
	}

	public int getSr_start() {
		return sr_start;
	}

	public void setSr_end(int sr_end) {
		this.sr_end = sr_end;
	}

	public int getSr_end() {
		return sr_end;
	}

	public void setSr_htit(HeadTailIncludeType sr_htit) {
		this.sr_htit = sr_htit;
	}

	public HeadTailIncludeType getSr_htit() {
		return sr_htit;
	}

	public void setPassAway_radio(BooleanSearchValue passAway_radio) {
		this.passAway_radio = passAway_radio;
	}

	public BooleanSearchValue getPassAway_radio() {
		return passAway_radio;
	}

}
