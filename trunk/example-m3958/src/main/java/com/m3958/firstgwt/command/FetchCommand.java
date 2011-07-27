package com.m3958.firstgwt.command;

import java.io.IOException;
import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.exception.SmartJpaException;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Lgb;
import com.m3958.firstgwt.server.response.ErrorMessageResponse;
import com.m3958.firstgwt.server.types.HasToJson;
import com.m3958.firstgwt.service.MemcachedService;
import com.m3958.firstgwt.service.SiteConfigService;



public class FetchCommand extends BaseCommand implements Command{
	
	@Inject
	private MemcachedService memcached;
	
	private boolean writeResponse = true;
	
	@Inject
	private SiteConfigService msc;
	
	private String result = null;
	
	public void execute() throws IOException{
		
		// if hit cache,return cache;
		SmartSubOperationName sson = SmartSubOperationName.NO_SUB_OPERATION;
		if(reqPs.getSubOpType() != null)
			sson = SmartSubOperationName.valueOf(reqPs.getSubOpType());
		
		switch (sson) {
		case FETCH_ONE_TO_MANY:
			result = smartOneToManyFetch();
			break;
		case FETCH_CHILDREN:
			result = smartFetchChildren();
			break;
		case MANY_TO_MANY:
			result = smartManyToManyFetch();
			break;
		case FETCH_TWO_JOIN:
			result = smartFetchTwoJoin();
			break;
		case CUSTOM_FETCH:
			result = smartCustomFetch();
			break;
		case FETCH_NO_RELATION:
			result = smartNoRelationFetch();
			break;
		case FETCH_ONE:
			result = smartFetchOne();
			break;
		case NAMED_QUERY:
			result = smartNamedQueryFetch();
			break;
		case HAS_PERMISSION:
			result = hasPermission();
			break;
		case FETCH_SHARED_USERS:
			result = fetchSharedUsers();
			break;
		case FETCH_SHARED_GROUPS:
			result = fetchSharedGroups();
			break;
		case NOT_CREATOR_HAS_PERMISSION:
			result = fetchNotCreatorHasPermission();
			break;
		case NOT_CREATOR_HAS_GPERMISSION:
			result = fetchNotCreatorHasGPermission();
			break;
		default:
			result = smartFetch();
		}
		
		if(!errors.isEmpty()){
			result = injector.getInstance(ErrorMessageResponse.class).toString();
		}
		if(writeResponse){
			if(reqPs.isJsonp()){
				autils.writeJsonpResponse(res, result, reqPs.getStringValue("callback"));
			}else{
				autils.writeJsonResponse(res,result);
			}
		}
	}
	
	private String fetchNotCreatorHasPermission() {
		List<BaseModel> results = getDao().fetchNotCreatorHasPermission();
		Integer totalRows = getDao().fetchNotCreatorHasPermissionCount();
		return  autils.getListResponse(results,reqPs.getStartRow(),totalRows);
	}
	
	private String fetchNotCreatorHasGPermission() {
		List<BaseModel> results = getDao().fetchNotCreatorHasGPermission();
		Integer totalRows = getDao().fetchNotCreatorHasGPermissionCount();
		return autils.getListResponse(results,reqPs.getStartRow(),totalRows);
	}


	private String fetchSharedUsers() {
		List<HasToJson> results = getDao().fetchSharedUsers();
		Integer totalRows = getDao().fetchSharedUsersCount();
		return  autils.getJsonResponse(results,reqPs.getStartRow(),totalRows);
	}
	
	private String fetchSharedGroups() {
		List<HasToJson> results = getDao().fetchSharedGroups();
		Integer totalRows = getDao().fetchSharedGroupsCount();
		return  autils.getJsonResponse(results,reqPs.getStartRow(),totalRows);
	}

	private String hasPermission() {
		List<BaseModel> results = getDao().smartHasPermissionFetch();
		Integer totalRows = getDao().smartHasPermissionFetchCount();
		return  autils.getListResponse(results,reqPs.getStartRow(),totalRows);
	}

	private String smartNamedQueryFetch() {
		List<BaseModel> results = getDao().smartNamedQueryFetch();
		Integer totalRows = getDao().smartNamedQueryFetchCount();
		return  autils.getListResponse(results,reqPs.getStartRow(),totalRows);
	}

	private String smartFetchOne() {
		BaseModel bm = getDao().find(reqPs.getModelId());
		return  autils.getListResponse(bm);
	}

	private String  smartNoRelationFetch() {
		List<BaseModel> results = getDao().smartNoRelationFetch();
		Integer totalRows = getDao().smartNoRelationRowCount();
		return  autils.getListResponse(results,reqPs.getStartRow(),totalRows);
	}

	private String smartCustomFetch(){
		List<BaseModel> results = getDao().smartCustomFetch();
		Integer totalRows = getDao().smartCustomCount();
		return  autils.getListResponse(results,reqPs.getStartRow(),totalRows);
	}
	
	private String smartFetchTwoJoin() {
		List<BaseModel> results = getDao().smartTwoJoinFetch();
		Integer totalRows = getDao().smartTwoJoinRowCount();
		return  autils.getListResponse(results,reqPs.getStartRow(),totalRows);
	}
	
	

	private String smartOneToManyFetch() {
		List<BaseModel> results = getDao().smartOneToManyFetch();
		Integer totalRows = getDao().smartOneToManyCount();
		return  autils.getListResponse(results,reqPs.getStartRow(),totalRows);
	}
	


	protected String smartFetch(){
		if(msc.isCache_enable() && Lgb.class.getName().equals(reqPs.getModelName())){
			String key = reqPs.getStringValue(LgbField.DEPARTMENT_IDS.getEname());
			String v = memcached.getLgbs(key,req.getQueryString());
			if(v == null){
				List<BaseModel> results = getDao().smartFetch();
				Integer totalRows = getDao().smartFetchCount();
				v = autils.getListResponse(results,reqPs.getStartRow(),totalRows);
				memcached.setLgbs(key,req.getQueryString(),v);
			}
			return  v;
		}else{
			List<BaseModel> results = getDao().smartFetch();
			Integer totalRows = getDao().smartFetchCount();
			return  autils.getListResponse(results,reqPs.getStartRow(),totalRows);
		}
	}
	
	private String  smartFetchChildren() {
		List<BaseModel> results = getDao().smartFetchChildren();
		Integer totalRows = getDao().smartFetchChildrenCount();
		return  autils.getListResponse(results,reqPs.getStartRow(),totalRows);
	}
	
	private String smartManyToManyFetch() {
		List<BaseModel> results = getDao().smartManyToManyFetch();
		Integer totalRows = getDao().smartManyToManyRowCount();
		return  autils.getListResponse(results,reqPs.getStartRow(),totalRows);
	}

	public String getResult() {
		return result;
	}
	
	@Override
	public void execute(boolean writeResponse) throws SmartJpaException,
			IOException {
		this.writeResponse = writeResponse;
		execute();
	}

	

}
