package com.m3958.firstgwt.service;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SortDirection;
import com.m3958.firstgwt.client.types.TextMatchStyle;
import com.m3958.firstgwt.utils.BaseModelUtilsBean;

@RequestScoped
public class ReqParaService {
	
	private HttpServletRequest req;
	
	private Map<String, String> allParas;
	
	private Map<String, String[]> mvParas;
	
	@Inject
	public ReqParaService(HttpServletRequest req){
		this.req = req;
		mvParas = req.getParameterMap();
		allParas  = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Enumeration<String> paraNames = req.getParameterNames();
		while(paraNames.hasMoreElements()){
			String pKey = paraNames.nextElement();
			if(SmartConstants.CRITERIA_PARAMETER_NAME.equals(pKey)){
				processCriteria(req.getParameterValues(SmartConstants.CRITERIA_PARAMETER_NAME));
			}else{
				allParas.put(pKey, req.getParameter(pKey));
			}
		}
	}
	
	
	public String[] getMvParaValues(String key){
		return mvParas.get(key);
	}
	
	private void processCriteria(String[] parameterValues) {
		for(String value : parameterValues){
			JSONObject jo = JSONObject.fromObject(value);
			allParas.put(jo.getString("fieldName"), jo.getString("value"));
		}
		
	}

	public Map<String, String> getAllParas(){
		return allParas;
	}
	
	public void setKeyValue(String key,String value){
		allParas.put(key, value);
	}
	
	@Inject
	protected BaseModelUtilsBean bmub;
	
	@Inject
	private ParaUtilService paraUtilService;
	
	@Inject
	private PropertyTypeService pts;
	
	public boolean isJsonp(){
		return !(req.getParameter("jsonp") == null);
	}
	
	public int getIntegerValue(String pKey){
		int i = 0;
		try {
			i = Integer.parseInt(allParas.get(pKey));
		} catch (NumberFormatException e) {}
		return i;
	}
	
	public int getIdValue(String pKey){
		int i = SmartConstants.NONE_EXIST_MODEL_ID;
		try {
			i = Integer.parseInt(allParas.get(pKey));
		} catch (NumberFormatException e) {}
		return i;
	}
	
	public String getSortField(){
		String ss = allParas.get(SmartParameterName.SORTBY);
		if(ss != null && ss.length()>1){
			if(ss.startsWith("-"))return ss.substring(1);
			return ss;
		}
		return null;
	}
	
	public TextMatchStyle getTextMetchStyle(){
		String ms = allParas.get(SmartParameterName.TEXT_MATCHSTYLE);
		if("substring".equals(ms)){
			return TextMatchStyle.SUBSTRING;
		}else if("startsWith".equals(ms)){
			return TextMatchStyle.STARTSWITH;
		}else{
			return TextMatchStyle.EXACT;
		}
	}
	
	public SortDirection getSortDir(){
		String ss = allParas.get(SmartParameterName.SORTBY);
		if(ss == null)return SortDirection.DESCENDING;
		if(ss.startsWith("-")){
			return SortDirection.DESCENDING;
		}else{
			return SortDirection.ASCENDING;
		}
	}
	
	public int getModelId(){
		int i = getIntegerValue(SmartParameterName.MODEL_ID);
		if(i == 0)i = SmartConstants.NONE_EXIST_MODEL_ID;
		return i;
	}
	
	public int getParentId(){
		int i = getIntegerValue(SmartParameterName.PARENTID);
		if(i == 0)i = SmartConstants.NONE_EXIST_MODEL_ID;
		return i;
	}
	
	public int getRelationModelId(){
		int i = getIntegerValue(SmartParameterName.RELATION_MODEL_ID);
		if(i == 0)i = SmartConstants.NONE_EXIST_MODEL_ID;
		return i;		
	}
	
	public String getModelName(){
		return allParas.get(SmartParameterName.MODEL_NAME);
	}
	
	public String getModelName(boolean isSimple){
		if(isSimple){
			return getAfterLastDot(getModelName());
		}
		return getModelName();
	}
	
	public String getRelationModelName(){
		return allParas.get(SmartParameterName.RELATION_MODEL_NAME);
	}
	
	public String getRelationPropertyNamea() {
		return allParas.get(SmartParameterName.RELATION_PROPERTY_NAMEA);
	}
	
	private String getAfterLastDot(String hasDotString){
		if(hasDotString != null && !hasDotString.isEmpty()){
			String[] ss = hasDotString.split("\\.");
			return ss[ss.length - 1];
		}
		return null;
	}
	
	public String getRelationModelName(boolean isSimple){
		if(isSimple){
			return getAfterLastDot(getRelationModelName());
		}
		return getRelationModelName();
	}
	
	public String getStringValue(String key){
		return allParas.get(key);
	}
	
	public String getStringValue(String key,String defaultStr){
		if(allParas.get(key) == null)return defaultStr;
		return allParas.get(key);
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Enum getEnumValue(String modelName,String key){
		Class c = pts.getPropertyType(modelName, key);
		if(c != null){
			return Enum.valueOf(c, allParas.get(key));
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Enum getEnumValue(Class<? extends Enum> class1,
			String ename) {
		Enum en = null;
		try {
			en = Enum.valueOf(class1, allParas.get(ename));
		} catch (Exception e) {}
		return en;
	}

	
	public int getCreatorIdFromIds(){
		int cid = SmartConstants.NONE_EXIST_MODEL_ID;
		try{
			cid = Integer.parseInt(allParas.get(SmartParameterName.CREATORIDS).split(",")[1]);
		}catch (Exception e) {}
		return cid;
	}

	
	public String getRelationPropertyName(){
		return allParas.get(SmartParameterName.RELATION_PROPERTY_NAME);
	}
	
	public String getOpType(){
		return allParas.get(SmartParameterName.OPERATION_TYPE);
	}
	
	public String getSubOpType(){
		return allParas.get(SmartParameterName.SUB_OPERATION_TYPE);
	}
	
	public String getExtraParas() {
		return allParas.get(SmartParameterName.EXTRAPARAS);
	}

	
	public int getStartRow(){
		return getIntegerValue(SmartParameterName.START_ROW);
	}
	
	public int getEndRow(){
		return getIntegerValue(SmartParameterName.END_ROW);
	}
	
	public Boolean getFetchOwner(){
		return getBooleanValueObject(SmartParameterName.FETCH_OWNER);
	}
	
	public boolean isAddRelation(){
		return getBooleanValueObject(SmartParameterName.IS_ADD_RELATION);
	}
	
	public Boolean getBooleanValueObject(String pKey){
		String pValue = allParas.get(pKey);
		return paraUtilService.getBooleanValueObject(pValue);
	}
	
	public Boolean getBooleanValue(String pKey,boolean defaultValue){
		String pValue = allParas.get(pKey);
		return paraUtilService.getBooleanValue(pValue,defaultValue);
	}

	
	public Boolean getBooleanValue(String pKey){
		String pValue = allParas.get(pKey);
		return paraUtilService.getBooleanValue(pValue);
	}
	
	private static Set<String> ignoreWhereFields = new HashSet<String>();
	
	static{
		ignoreWhereFields.add(SmartParameterName.PARENTID);
	}
	
	public String getWhereString(){
		return paraUtilService.getWhereString(getModelName(), ignoreWhereFields, getAllParas(), getTextMetchStyle());
	}
	
	
	public String getWhereString(String asObjectName){
		return paraUtilService.getWhereString(asObjectName,getModelName(), ignoreWhereFields, getAllParas(), getTextMetchStyle());
	}
	
	
	public Date getDateValue(String key){
		return paraUtilService.parseDate(allParas.get(key));
	}

	public HttpServletRequest getReq() {
		return req;
	}
	
	public String getOrderString() {
		String orderString = "";
		if(getSortField() != null){
			if(getSortDir() == SortDirection.ASCENDING){
				orderString = " ORDER BY P." + getSortField() + " ASC";
			}else{
				orderString = " ORDER BY P." + getSortField() + " DESC";
			}
		}
		return orderString;
	}
	
	public String getOrderString(String asObjectName) {
		String orderString = "";
		if(getSortField() != null){
			if(getSortDir() == SortDirection.ASCENDING){
				orderString = " ORDER BY " + asObjectName + "." + getSortField() + " ASC";
			}else{
				orderString = " ORDER BY " + asObjectName + "." + getSortField() + " DESC";
			}
		}
		return orderString;
	}

}
