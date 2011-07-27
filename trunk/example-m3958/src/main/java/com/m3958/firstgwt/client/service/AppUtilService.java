package com.m3958.firstgwt.client.service;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.AssetDataSource;
import com.m3958.firstgwt.client.datasource.DiskFileDataSource;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.DiskFileField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;

@Singleton
public class AppUtilService {
	
	@Inject
	private VblockService bservice;
	
	public String getDiskFileJsStr(String siteId,String dirPath,String fileName,String newFileName,String content,boolean isFolder){
		JSONObject jo = new JSONObject();
		jo.put(DiskFileField.SITE_ID.getEname(), new JSONString(siteId));
		jo.put(DiskFileField.DIR_PATH.getEname(), new JSONString(dirPath));
		if(fileName!=null && !fileName.isEmpty())
			jo.put(DiskFileField.FILE_NAME.getEname(), new JSONString(fileName));
		if(newFileName != null && !newFileName.isEmpty())
			jo.put(DiskFileField.OLD_FILE_NAME.getEname(), new JSONString(newFileName));
		if(content != null && !content.isEmpty())
			jo.put(DiskFileField.CONTENT.getEname(), new JSONString(content));
		jo.put(DiskFileField.IS_FOLDER.getEname(), JSONBoolean.getInstance(isFolder));
		return jo.toString();
	}
	
	public Map<String, String> getDiskFileMap(String siteId,String dirPath,String fileName,String newFileName,String content,boolean isFolder,String rid){
		Map<String, String> m = new HashMap<String, String>();
		m.put(DiskFileField.SITE_ID.getEname(), siteId);
		m.put(DiskFileField.DIR_PATH.getEname(), dirPath);
		m.put(DiskFileField.FILE_NAME.getEname(), fileName);
		m.put(DiskFileField.OLD_FILE_NAME.getEname(), newFileName);
		m.put(DiskFileField.CONTENT.getEname(), content);
		m.put(DiskFileField.IS_FOLDER.getEname(), String.valueOf(isFolder));
		m.put(CommonField.ID.getEname(), rid);
		return m;
	}

		
	public boolean dotInRect(Rectangle rect,int x,int y){
		if(x < rect.getLeft() || x > rect.getLeft() + rect.getWidth()){
			return false;
		}
		
		if(y < rect.getTop() || y > rect.getTop() + rect.getHeight()){
			return false;
		}
		
		return true;
	}
	
	
	public String[] addTwoStringArray(String[] ar1,String...ar2){
		if(ar1 == null || ar1.length == 0){
			return ar2;
		}
		if(ar2 == null || ar2.length == 0){
			return ar1;
		}
		String[] ar = new String[ar1.length + ar2.length];
		for(int i=0;i<ar1.length;i++){
			ar[i] = ar1[i];
		}
		int ar1l = ar1.length;
		for(int i=0;i<ar2.length;i++){
			ar[ar1l + i] = ar2[i]; 
		}
		return ar;
	}
	
	
	public String[] insertParas(String p1,String[] paras){
		String[] pa = null;
		if(paras == null){
			pa = new String[1];
			pa[0] = p1;
		}else{
			pa = new String[paras.length + 1];
			pa[0] = p1;
			for(int i=1;i<pa.length;i++){
				pa[i] = paras[i-1];
			}
		}
		return pa;
	}
	
	public String[] getRecordsIds(Record[] records){
		String[] ss = new String[records.length];
		for(int i=0;i<records.length;i++){
			ss[i] = records[i].getAttributeAsString(CommonField.ID.getEname());
		}
		return ss;
	}
	
	public boolean hasAttribute(Record r,String attr){
		for(String s:r.getAttributes()){
			if(attr.endsWith(s)){
				return true;
			}
		}
		return false;
	}
	
	
	public int getIntegerIdPara(String[] paras,int index){
		if(paras != null && index < paras.length){
			try {
				int i = Integer.parseInt(paras[index]);
				return i;
			} catch (NumberFormatException e) {}
		}
		return SmartConstants.NONE_EXIST_MODEL_ID;
	}
	
	public String getStringIdPara(String[] paras,int index){
		if(paras != null && index < paras.length){
			return paras[index];
		}
		return SmartConstants.NONE_EXIST_MODEL_ID_STR;
	}
	
	public boolean getBooleanPara(String[] paras,int index){
		String bStr;
		if(paras != null && index < paras.length){
			bStr = paras[index];
			if("1".equals(bStr) || "yes".equals(bStr) || "是".equals(bStr)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public int getIntegerPara(String[] paras,int index){
		if(paras != null && index < paras.length){
			try {
				int i = Integer.parseInt(paras[index]);
				return i;
			} catch (NumberFormatException e) {}
		}
		return 0;
	}
	
	public String getStringPara(String[] paras,int index){
		if(paras != null && index < paras.length){
			return paras[index];
		}
		return null;
	}
	
	public RPCRequest getFetchOneRpcRequest(String modelId,String modelName){
    	Map<String, String> m = new HashMap<String, String>();
    	m.put(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_ONE.toString());
    	m.put(SmartParameterName.MODEL_ID, modelId);
    	m.put(SmartParameterName.MODEL_NAME, modelName);
    	m.put(SmartParameterName.OPERATION_TYPE, SmartOperationName.FETCH.getValue());
    	m.put(SmartParameterName.IS_MASTER, "0");
    	m.put(CommonField.TIMEZONE.getEname(), bservice.getTimeZone());
    	RPCRequest rr = new RPCRequest();
    	rr.setHttpMethod("GET");
    	rr.setActionURL(SmartConstants.SMART_CRUD_URL);
    	rr.setParams(m);
    	return rr;
	}
	
	public RPCRequest getJsonPRequest(String modelId,String modelName){
    	Map<String, String> m = new HashMap<String, String>();
    	m.put(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_ONE.toString());
    	m.put(SmartParameterName.MODEL_ID, modelId);
    	m.put(SmartParameterName.MODEL_NAME, modelName);
    	m.put(SmartParameterName.OPERATION_TYPE, SmartOperationName.FETCH.getValue());
    	m.put(CommonField.TIMEZONE.getEname(), bservice.getTimeZone());
    	RPCRequest rr = new RPCRequest();
    	rr.setHttpMethod("GET");
    	rr.setActionURL(SmartConstants.JSONP_URL);
    	rr.setParams(m);
    	return rr;
	}
	
	public RPCRequest getCustomRemoteAssetRequest(String url,String relationModelId,String siteId){
    	Map<String, String> m = new HashMap<String, String>();
    	m.put(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.REMOTE_ASSET.toString());
    	m.put("url", url);
    	m.put(SmartParameterName.MODEL_NAME, AssetDataSource.className);
    	m.put(SmartParameterName.RELATION_MODEL_ID, relationModelId);
    	m.put(CommonField.SITE_ID.getEname(), siteId);
    	m.put(SmartParameterName.OPERATION_TYPE, SmartOperationName.CUSTOM.getValue());
    	m.put(CommonField.TIMEZONE.getEname(), bservice.getTimeZone());
    	RPCRequest rr = new RPCRequest();
    	rr.setHttpMethod("GET");
    	rr.setActionURL(SmartConstants.SMART_CRUD_URL);
    	rr.setParams(m);
    	return rr;
	}
	
	//relationModelName是指ids代表的modelName。
	public RPCRequest getManageRelationRpcRequest(String beEditModelName,String beEditModelId,boolean beEditIsMaster,String idsModelName,Record[] records,boolean isAdd,String hint){
    	String ids = "";
    	for(int i=0;i<records.length;i++){
    		ids = ids + records[i].getAttribute("id") + ",";
    	}
    	Map<String, String> m = new HashMap<String, String>();
    	m.put(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.MANAGE_RELATION.toString());
    	m.put(SmartParameterName.RELATION_MODEL_NAME, idsModelName);
    	m.put(SmartParameterName.EXTRAPARAS, ids);
    	m.put(SmartParameterName.MODEL_ID, beEditModelId);
    	m.put(SmartParameterName.MODEL_NAME, beEditModelName);
    	m.put(SmartParameterName.IS_ADD_RELATION, String.valueOf(isAdd));
    	m.put(SmartParameterName.OPERATION_TYPE, SmartOperationName.CUSTOM.getValue());
    	m.put(SmartParameterName.IS_MASTER, String.valueOf(beEditIsMaster));
    	m.put(SmartParameterName.HINT, hint);
    	m.put(CommonField.TIMEZONE.getEname(), bservice.getTimeZone());
    	
    	RPCRequest rr = new RPCRequest();
    	rr.setHttpMethod("POST");
    	rr.setActionURL(SmartConstants.SMART_CRUD_URL);
    	rr.setParams(m);
    	return rr;
	}
	
	public RPCRequest getManageRelationRpcRequest(String beEditModelName,String beEditModelId,boolean beEditIsMaster,String idsModelName,String ids,boolean isAdd,String hint){
    	Map<String, String> m = new HashMap<String, String>();
    	m.put(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.MANAGE_RELATION.toString());
    	m.put(SmartParameterName.RELATION_MODEL_NAME, idsModelName);
    	m.put(SmartParameterName.EXTRAPARAS, ids);
    	m.put(SmartParameterName.MODEL_ID, beEditModelId);
    	m.put(SmartParameterName.MODEL_NAME, beEditModelName);
    	m.put(SmartParameterName.IS_ADD_RELATION, String.valueOf(isAdd));
    	m.put(SmartParameterName.OPERATION_TYPE, SmartOperationName.CUSTOM.getValue());
    	m.put(SmartParameterName.IS_MASTER, String.valueOf(beEditIsMaster));
    	m.put(SmartParameterName.HINT, hint);
    	m.put(CommonField.TIMEZONE.getEname(), bservice.getTimeZone());
    	
    	RPCRequest rr = new RPCRequest();
    	rr.setHttpMethod("POST");
    	rr.setActionURL(SmartConstants.SMART_CRUD_URL);
    	rr.setParams(m);
    	return rr;
	}
	
	public RPCRequest getDiskFileRpcRequest(Map<String,String> paras,String opType,String subOptype,String httpMethod){
    	Map<String, String> m = paras;
    	m.put(SmartParameterName.MODEL_NAME, DiskFileDataSource.className);
    	m.put(SmartParameterName.OPERATION_TYPE, opType);
    	m.put(SmartParameterName.SUB_OPERATION_TYPE,subOptype);
    	m.put(CommonField.TIMEZONE.getEname(), bservice.getTimeZone());
    	RPCRequest rr = new RPCRequest();
    	rr.setHttpMethod(httpMethod);
    	rr.setActionURL(SmartConstants.SMART_CRUD_URL);
    	rr.setParams(m);
    	return rr;
	}
	
	public static class SizeFormator{
		private static int gb = 1073741824;
		private static int mb = 1048576;
		private static int kb = 1024;
		
		public static String format(int i){
			String s = "";
			double d;
			boolean done = false;
			if(i/gb > 0){
				d = ((int)(i/(double)gb * 100))/100d;
				if(d > 1.1){
					s = d + "G";
					done = true;
				}
			}
			
			if(!done && i/mb>0){
				d = ((int)(i/(double)mb * 100))/100d;
				if(d > 1.1){
					s = d + "M";
					done = true;
				}
			}
			
			if(!done && i/kb>0){
				d = ((int)(i/(double)kb * 100))/100d;
				if(d>1.1){
					s = d + "K";
					done = true;
				}
			}
			
			if(!done){
				s = String.valueOf(i) + "B";
			}
			return s;
		}
	}
	
	public static class fileSizeFormatter implements CellFormatter{
		@Override
		public String format(Object value, ListGridRecord record, int rowNum,
				int colNum) {
			int size = (Integer) value;
			return SizeFormator.format(size);
		}
		
	}
}
