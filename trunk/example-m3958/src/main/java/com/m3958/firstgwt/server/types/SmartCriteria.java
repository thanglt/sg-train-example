package com.m3958.firstgwt.server.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SortDirection;
import com.m3958.firstgwt.client.types.TextMatchStyle;

public class SmartCriteria {
	
	private String sortField;
	private SortDirection sortDir = SortDirection.ASCENDING;
	private int startRow;
	private int endRow;
	private String creatorIds;
	private TextMatchStyle matchStyle = TextMatchStyle.SUBSTRING;
	private int parentId;
	private String fetchOwner;
	private String modelName;
	private int relationModelId;
	private String relationPropertyName;
	private String relationPropertyNamea;
	private String relationModelName;
	private int modelId;
	private String[] ignorFields;
	private String smartOperationType;
	private String smartSubOperationType;
	private String extraParas;
	private boolean addRelation = false;
	private int userId;
	
	private Map<String, String> stringFieldMap = new HashMap<String, String>();
	
	private Map<String,List<String>> dateFieldMap = new HashMap<String, List<String>>();
	
	private Map<String,String> booleanFieldMap = new HashMap<String, String>();
	
	private Map<String,String> allParams = new HashMap<String, String>();
	
	
	public int getRelationModelId() {
		return relationModelId;
	}

	public void setRelationModelId(String ridStr) {
		int i = SmartConstants.NONE_EXIST_MODEL_ID;
		try{
			i = Integer.parseInt(ridStr);
		}catch (Exception e) {}

		this.relationModelId = i;
	}

	public String getRelationPropertyName() {
		return relationPropertyName;
	}

	public void setRelationPropertyName(String relationPropertyName) {
		this.relationPropertyName = relationPropertyName;
	}

	public String getRelationModelName() {
		return relationModelName;
	}
	
	public String getRelationModelName(boolean isSimple) {
		if(isSimple){
			if(relationModelName != null && !relationModelName.isEmpty()){
				String[] ss = relationModelName.split("\\.");
				return ss[ss.length - 1];
			}
		}
		return relationModelName;
	}
	

	public void setRelationModelName(String relationModelName) {
		this.relationModelName = relationModelName;
	}

	
	public void addStringMap(String key,String value){
		stringFieldMap.put(key, value);
	}
	
	public String getLikeString(String s){
		switch (matchStyle) {
		case EXACT:
			return stringFieldMap.get(s);
		case STARTSWITH:
			return stringFieldMap.get(s) + "%";
		case SUBSTRING:
			return "%" + stringFieldMap.get(s) + "%";
		default:
			return null;
		}
	}
	
	
	public Map<String, String> getStringFieldMap() {
		return stringFieldMap;
	}

	public void setStringFieldMap(Map<String, String> stringFieldMap) {
		this.stringFieldMap = stringFieldMap;
	}
	
	public void addStringFieldMap(Map<String, String> stringFieldMap) {
		this.stringFieldMap.putAll(stringFieldMap);
	}


	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public SortDirection getSortDir() {
		return sortDir;
	}

	public void setSortDir(SortDirection sortDir) {
		this.sortDir = sortDir;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public TextMatchStyle getMatchStyle() {
		return matchStyle;
	}

	public void setMatchStyle(TextMatchStyle matchStyle) {
		this.matchStyle = matchStyle;
	}



	public void setDateFieldMap(Map<String,List<String>> dateFieldMap) {
		this.dateFieldMap = dateFieldMap;
	}
	
	public void addDateFieldMap(Map<String,List<String>> dateFieldMap) {
		this.dateFieldMap.putAll(dateFieldMap);
	}


	public Map<String,List<String>> getDateFieldMap() {
		return dateFieldMap;
	}

	public void setCreatorIds(String creatorIds) {
		this.creatorIds = creatorIds;
	}

	public String getCreatorIds() {
		return creatorIds;
	}

	public void setBooleanFieldMap(Map<String,String> booleanFieldMap) {
		this.booleanFieldMap = booleanFieldMap;
	}

	public void addBooleanFieldMap(Map<String,String> booleanFieldMap) {
		this.booleanFieldMap.putAll(booleanFieldMap);
	}


	public Map<String,String> getBooleanFieldMap() {
		return booleanFieldMap;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getParentId() {
		if(parentId == 0){
			return SmartConstants.NONE_EXIST_MODEL_ID;
		}
		return parentId;
	}

	public void setFetchOwner(String fetchOwner) {
		this.fetchOwner = fetchOwner;
	}

	public String getFetchOwner() {
		return fetchOwner;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelName() {
		return modelName;
	}
	
	public String getModelName(boolean isSimple) {
		if(isSimple){
			if(modelName != null && !modelName.isEmpty()){
				String[] ss = modelName.split("\\.");
				return ss[ss.length -1];
			}
		}
		return modelName;
	}

	
//	public int getModelId(){
//		String idStr = stringFieldMap.get("id");
//		int id = -1;
//		try {
//			id = Integer.parseInt(idStr);
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		}
//		return id;
//	}

//	public void setNomalParams(Map<String,String> nomalParams) {
//		this.allParams = nomalParams;
//	}
	public void addAllParams(Map<String, String> map){
		allParams.putAll(map);
	}
	
	public Map<String,String> getAllParams() {
		return allParams;
	}

	public void setModelId(String midStr) {
		int i = SmartConstants.NONE_EXIST_MODEL_ID;
		try{
			i = Integer.parseInt(midStr);
		}catch (Exception e) {}
		this.modelId = i;
	}
	
	public int getModelId() {
		return modelId;
	}

	public void setIgnorFields(String[] ignorFields) {
		this.ignorFields = ignorFields;
	}

	public String[] getIgnorFields() {
		return ignorFields;
	}

	public void setSmartOperationType(String smartOperationType) {
		this.smartOperationType = smartOperationType;
	}

	public String getSmartOperationType() {
		return smartOperationType;
	}

	public void setSmartSubOperationType(String smartSubOperationType) {
		this.smartSubOperationType = smartSubOperationType;
	}

	public String getSmartSubOperationType() {
		if("null".equalsIgnoreCase(smartSubOperationType))return null;
		return smartSubOperationType;
	}

	public void setExtraParas(String extraParas) {
		this.extraParas = extraParas;
	}

	public String getExtraParas() {
		return extraParas;
	}

	public void setAddRelation(boolean isAddRelation) {
		this.addRelation = isAddRelation;
	}
	
	public void setAddRelation(String isAddRelation){
		if("true".equalsIgnoreCase(isAddRelation)){
			this.addRelation = true;
		}else{
			this.addRelation = false;
		}
	}

	public boolean getAddRelation() {
		return addRelation;
	}

	public void removeExtraFieldFromStringFieldMap() {
		stringFieldMap.remove(SmartParameterName.PARENTID);
		stringFieldMap.remove(SmartParameterName.RESERVED_OPERATOR);
	}
	
	public int getCreatorIdFromIds(){
		int cid = SmartConstants.NONE_EXIST_MODEL_ID;
		try{
			cid = Integer.parseInt(creatorIds.split(",")[1]);
		}catch (Exception e) {}
		return cid;
	}
	
	public int getIdValue(String key){
		int i = SmartConstants.NONE_EXIST_MODEL_ID;
		try{
			i = Integer.parseInt(allParams.get(key));
		}catch (Exception e) {}
		return i;
	}
	
	public int getIntegerValue(String key){
		int i = 0;
		try{
			i = Integer.parseInt(allParams.get(key));
		}catch (Exception e) {}
		return i;
	}
	
	public Boolean getBooleanValue(String key){
		if(allParams.get(key) == null)return null;
		if("true".equalsIgnoreCase(allParams.get(key))){
			return true;
		}else{
			return null;
		}
		
	}
	
	public String getStringValue(String key){
		return allParams.get(key);
	}

	public void setRelationPropertyNamea(String relationPropertyNamea) {
		this.relationPropertyNamea = relationPropertyNamea;
	}

	public String getRelationPropertyNamea() {
		return relationPropertyNamea;
	}

	@SuppressWarnings("unchecked")
	public Enum getEnumValue(Class<? extends Enum> class1,
			String ename) {
		Enum en = null;
		try {
			en = Enum.valueOf(class1, allParams.get(ename));
		} catch (Exception e) {}
		return en;
	}

	public void setUserId(String userId) {
		int uid = SmartConstants.NONE_EXIST_MODEL_ID;
		try{
			uid = Integer.parseInt(userId);
		}catch (Exception e) {}
		this.userId = uid;
	}

	public int getUserId() {
		return userId;
	}

	
	
}
