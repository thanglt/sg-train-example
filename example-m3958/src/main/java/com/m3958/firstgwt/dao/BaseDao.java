package com.m3958.firstgwt.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.RequestScoped;
import com.m3958.firstgwt.annotation.HowManyMilliSec;
import com.m3958.firstgwt.client.types.AssetExtraField;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.client.types.OperationField;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SortDirection;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Group;
import com.m3958.firstgwt.model.HasCreator;
import com.m3958.firstgwt.model.JrxmlFile;
import com.m3958.firstgwt.model.Lgb;
import com.m3958.firstgwt.model.Permission;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.Tag;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.types.BelongToLgb;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.server.types.HasAttachments;
import com.m3958.firstgwt.server.types.HasObjectPermission;
import com.m3958.firstgwt.server.types.HasRelation;
import com.m3958.firstgwt.server.types.HasTags;
import com.m3958.firstgwt.server.types.HasToJson;
import com.m3958.firstgwt.server.types.IHasSiteId;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.server.types.TreeModel;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.service.ParaUtilService;
import com.m3958.firstgwt.service.PropertyTypeService;
import com.m3958.firstgwt.service.ReqParaService;
import com.m3958.firstgwt.service.RequestScopeObjectService;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.session.SessionUser;
import com.m3958.firstgwt.utils.BaseModelUtilsBean;
import com.wideplay.warp.persist.Transactional;

/**
 * @author <a href="jianglibo@gmail.com">Dave Rapin</a>
 * 
 *         <p>
 *         Abstract implementation for data access classes. Extending this class
 *         give's us the benefit of generic working find, findAll, save, and
 *         remove methods.
 * 
 * @param <T>
 * 
 */
@SuppressWarnings("unchecked")
public abstract class BaseDao<T extends BaseModel<T>> implements IDao<T> {

	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	@Inject
	Injector injector;
	
//	@Inject
//	protected AppUtilService autil;
	
	@Inject
	BaseModelUtilsBean bmub;
	
	protected ModelChangeStrategy<T> extraStrategyTask;
	
	protected Class<T> persistentClass;
	
	@Inject
	protected ParaUtilService paraUtilService;
	
	private ErrorMessages errors;
	
	protected ErrorMessages getErrors(){
		if(errors == null){
			errors = injector.getInstance(ErrorMessages.class);
		}
		return errors;
	}
	
	private ReqParaService reqPs;
	
	private RequestScopeObjectService rso;
	
	protected RequestScopeObjectService getRso(){
		try {
			if(rso == null){
				rso = injector.getInstance(RequestScopeObjectService.class);
			}
			return rso;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	protected ReqParaService getReqPs(){
		try {
			if(reqPs == null){
				reqPs = injector.getInstance(ReqParaService.class);
			}
			return reqPs;
		} catch (Exception e) {
			return null;
		}
	}
	
	private User loginUser;
	
	private SessionUser su;
	
	protected SessionUser getSu(){
		if(su == null)
			su = injector.getInstance(SessionUser.class);
		return su;
	}
	
	protected User getLoginUser(){
		try {
			if(loginUser == null && getSu().getUserId() != null){
				loginUser = emp.get().find(User.class, getSu().getUserId()); 
			}
			return loginUser;
		} catch (Exception e) {
			return null;
		}
	}

	//-------------------------------------------Create---------------------------------------------------------------------
	
	public Class<T> getPersistentClass() {
		return persistentClass;
	}
	
	protected void setCreator(HasCreator hc){
		User u = getLoginUser();
		hc.setCreator(u);
	}
	
	protected void setParent(TreeModel<T> tmodel){
		int pint = getReqPs().getParentId();
		if(pint != SmartConstants.NONE_EXIST_MODEL_ID){
			T p = find(pint);
			((TreeModel<T>)tmodel).setParent(p);
			((TreeModel<T>)p).addChildren((T) tmodel);
		}
	}

	@Transactional
	public T smartPersistBaseModel(T baseModel){
		if(baseModel instanceof BelongToLgb){
			addToLgb(baseModel);
		}
		if(baseModel.getCreatedAt() == null){
			baseModel.setCreatedAt(new Date());
		}
		if(baseModel instanceof HasCreator){
			setCreator((HasCreator) baseModel);
		}
		if(baseModel instanceof TreeModel && getReqPs() != null){
			setParent((TreeModel<T>) baseModel);
		}
		if(baseModel instanceof HasAttachments){
			attachAttachments((HasAttachments) baseModel);
		}
		if(baseModel instanceof HasTags){
			processTags((HasTags)baseModel);
		}
		if(extraStrategyTask != null){
			if(extraStrategyTask.extraPersistTask(baseModel)){
				persist(baseModel);
				extraStrategyTask.afterPersist(baseModel);
			}
		}else{
			persist( baseModel);
		}
		return baseModel;
	}
	
	private void processTags(HasTags baseModel) {
		String tgs = getReqPs().getStringValue(CommonField.TAG_NAMES.getEname());
		if(tgs == null)return;
		List<Tag> tags;
		if(tgs.isEmpty()){
			tags = new ArrayList<Tag>();
		}else{
			String[] tagNames = tgs.split("\\s+");
			List<String> tas = Arrays.asList(tagNames);
			Query q  = emp.get().createNamedQuery(Tag.NamedQueries.FIND_TAGS_BY_NAMES);
			q.setParameter("names", tas);
			tags = q.getResultList();
			if(tags.size() < tagNames.length){
				for(String s : tagNames){
					boolean find = false;
					for(Tag t : tags){
						if(t.getName().equals(s)){
							find = true;
							break;
						}
					}
					if(!find){
						Tag tag = new Tag();
						tag.setName(s);
						emp.get().persist(tag);
						tags.add(tag);
					}
				}
			}
		}
		baseModel.setTags(tags);
	}


	protected void attachAttachments(HasAttachments model) {
		String at = getReqPs().getStringValue(SmartParameterName.ATTACHMENTIDS);
		if(at == null)return;
		JSONArray ja = JSONArray.fromObject(at);
		for(int i = 0;i < ja.size();i++){
			JSONObject jo = (JSONObject) ja.get(i);
			if(jo != null){
				Integer ai = (Integer) jo.get(AssetExtraField.JASSETID);
				Asset a = emp.get().find(Asset.class, ai);
				model.addAttachment(a);
			}
		}
	}

	private void addToLgb(T baseModel) {
		int lgbId = getReqPs().getRelationModelId();
		Lgb lgb = emp.get().find(Lgb.class, lgbId);
		lgb.addProperty(baseModel);
	}
	
	
	//如果出现差错，首先分析客户端的代码！is_master值第一个是不是master。
	@Transactional
	public List<BaseModel> manageRelation(){
		List<BaseModel> bms = new ArrayList<BaseModel>();
		String oids = getReqPs().getStringValue(SmartParameterName.EXTRAPARAS);
		String[] os = oids.split(",");
		boolean isAdd = getReqPs().getBooleanValueObject(SmartParameterName.IS_ADD_RELATION);
		String hint = reqPs.getStringValue(SmartParameterName.HINT);
		@SuppressWarnings("rawtypes")
		Class c = null;
		try {
			c = Class.forName(getReqPs().getRelationModelName());
		} catch (ClassNotFoundException e) {}
		
		BaseModel destModel = find(getReqPs().getModelId());
		if(destModel == null)return bms;
		if(getReqPs().getBooleanValueObject(SmartParameterName.IS_MASTER)){
			for(int i=0;i<os.length;i++){
				BaseModel bm = emp.get().find(c,Integer.parseInt(os[i]));
				if(((HasRelation)destModel).manageRelation(bm, isAdd,hint,getErrors())){
					bms.add(bm);
				}
			}
		}else{
			for(int i=0;i<os.length;i++){
				HasRelation bm = (HasRelation) emp.get().find(c, Integer.parseInt(os[i]));
				if(bm.manageRelation(destModel, isAdd,hint,getErrors())){
					bms.add((BaseModel) bm);
				}
			}
		}
		if(getErrors().getErrors().size() > 0){
			return null;
		}
		return bms;
	}
	


	
	@Transactional
	public T persist(T  baseModel){
		emp.get().persist(baseModel);
		return baseModel;
	}
	

	
	//-------------------------------------------Read---------------------------------------------------------------------
	
	public List<T> getMyModel(int creatorId){
		String cname = this.persistentClass.getSimpleName();
		String qs = "SELECT M FROM " + cname + " AS M WHERE M.creator.id = " + creatorId;
		Query q = emp.get().createQuery(qs);
		return q.getResultList();
	}
	
//	public List<BaseModel> gwtGetManySide(BaseModel otherModel,
//			String otherMethod) {
//		try {
//			BaseModel bm = emp.get().find(otherModel.getClass(),otherModel.getId());
//			
//			Method m = bm.getClass().getMethod(otherMethod, new Class[]{});
//			
//			List<BaseModel> results = (List<BaseModel>) m.invoke(bm);
//			
//			return results;
//			
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public T find(Integer id) {
		return emp.get().find(this.persistentClass, id);
	}
	
	@Override
	public T find(Class<? extends BaseModel<T>> bmc, int id) {
		return (T) emp.get().find(bmc, id);
	}
	
//	@Override
//	public T find(Class<T> bmc,int id) {
//		return emp.get().find(bmc, id);
//	}

	
//	public List<T> gwtQuery(BaseModel emptyModel, int startRow, int num,String sortField,SortDirection sortDir){
//		String qs = "SELECT m FROM " + emptyModel.getClass().getSimpleName() + " AS m ORDER BY m." + sortField + " " + sortDir.getValue();
//		Query q = emp.get().createQuery(qs);
//		q.setFirstResult(startRow);
//		if(num > 0)q.setMaxResults(num);
//		return q.getResultList();
//	}
//	
//	public List<T> gwtQuery(BaseModel emptyModel, int startRow, int num,String sortField,SortDirection sortDir, Integer otherObjId, String myProperty){
//		String qs = "SELECT m FROM " + emptyModel.getClass().getSimpleName() + " AS m, IN(m." + myProperty + ") AS o WHERE o.id = " + otherObjId  + "ORDER BY m." + sortField + " " + sortDir.getValue();
//		Query q = emp.get().createQuery(qs);
//		q.setFirstResult(startRow);
//		if(num > 0)q.setMaxResults(num);
//		return q.getResultList();
//	}
	
	public List<T> fetchAll() {
		return emp.get().createQuery(
				"SELECT P FROM " + this.persistentClass.getSimpleName() + " P"/*,
				persistentClass*/).getResultList();
	}
	
//	public List<T> fetchAll(BaseModel emptyModel) {
//		String qs = "SELECT m FROM " + emptyModel.getClass().getSimpleName() + " AS m";
//		Query q = emp.get().createQuery(qs);
//		return q.getResultList();
//	}
//	
//	
//	public List<BaseModel> fetchAll(String modelName){
//		if(modelName.contains(".")){
//			String[] ms = modelName.split("\\."); 
//			modelName = ms[ms.length - 1];
//		}
//		String qs = "SELECT m FROM " + modelName + " AS m";
//		Query q = emp.get().createQuery(qs);
//		return q.getResultList();
//	}
	
	public List<T> smartOneToManyFetch() {
		String cname = this.persistentClass.getSimpleName();
		String relationStr = "";
		String qs;
		if(getReqPs().getBooleanValueObject(SmartParameterName.IS_MASTER)){
			relationStr = " P." + getReqPs().getRelationPropertyName() + ".id = " + getReqPs().getRelationModelId();
			String whereString = relationStr + getReqPs().getWhereString() + getOwnerString();
			qs = "SELECT P FROM " + cname + " P " + paraUtilService.getFinalWhereString(whereString) + reqPs.getOrderString();
		}else{
			relationStr = "Q.id = " + getReqPs().getRelationModelId();
			String whereString = relationStr + getReqPs().getWhereString() + getOwnerString();
			qs = "SELECT P FROM " + getReqPs().getRelationModelName(true) + " Q, IN(Q." + getReqPs().getRelationPropertyName()  + ") AS P" + paraUtilService.getFinalWhereString(whereString) + reqPs.getOrderString();			
		}
		Query q = emp.get().createQuery(qs);
		setFirstMax(q);
		return q.getResultList();
	}
	
	protected void setFirstMax(Query q){
		if(getReqPs().getEndRow() != 0){
			q.setFirstResult(getReqPs().getStartRow());
			q.setMaxResults( getReqPs().getEndRow()- getReqPs().getStartRow());
		}
	}
	
	public Integer smartOneToManyCount() {
		String cname = this.persistentClass.getSimpleName();
		String relationStr = "";
		String qs;
		if(getReqPs().getBooleanValueObject(SmartParameterName.IS_MASTER)){
			relationStr = " P." + getReqPs().getRelationPropertyName() + ".id = " + getReqPs().getRelationModelId();
			String whereString = relationStr + getReqPs().getWhereString() + getOwnerString();
			qs = "SELECT COUNT(DISTINCT P) FROM " + cname + " P " + paraUtilService.getFinalWhereString(whereString);
		}else{
			relationStr = "Q.id = " + getReqPs().getRelationModelId();
			String whereString = relationStr + getReqPs().getWhereString() + getOwnerString();
			qs = "SELECT COUNT(DISTINCT P) FROM " + getReqPs().getRelationModelName(true) + " Q, IN(Q." + getReqPs().getRelationPropertyName()  + ") AS P" + paraUtilService.getFinalWhereString(whereString);			
		}
		
		Query q = emp.get().createQuery(qs);
		
		Long l = (Long) q.getSingleResult(); 
		return l.intValue();
		
	}
	
	protected List<T> smartFetchTop(){
		String cname = this.persistentClass.getSimpleName();
		String	relationStr = " AND P.parent IS NULL ";
		String whereString = relationStr + getReqPs().getWhereString() + getOwnerString();
		
		String qs = "SELECT DISTINCT(P) FROM " + cname + " P " + paraUtilService.getFinalWhereString(whereString) + reqPs.getOrderString();
		Query q = emp.get().createQuery(qs);
		setFirstMax(q);
		return q.getResultList();
	}
	
	protected Integer smartFetchTopCount(){
		String cname = this.persistentClass.getSimpleName();
		String	relationStr = " AND P.parent IS NULL ";
		String whereString = relationStr + getReqPs().getWhereString() + getOwnerString();
		
		String qs = "SELECT COUNT(DISTINCT P) FROM " + cname + " P " + paraUtilService.getFinalWhereString(whereString);
		Query q = emp.get().createQuery(qs);
		Long l = (Long) q.getSingleResult(); 
		return l.intValue();
	}
	
	
	
	public List<T> smartFetchChildren(){
		String cname = this.persistentClass.getSimpleName();
		if(getReqPs().getParentId() == SmartConstants.NONE_EXIST_MODEL_ID){
			if(HasObjectPermission.class.isAssignableFrom(this.persistentClass)){
				return smartHPAllFetch();
			}else{
				return smartFetchTop();
			}
		}
		String	relationStr = " AND P.parent.id = " + getReqPs().getParentId();
		String whereString = relationStr + getReqPs().getWhereString() + getOwnerString();
		
		String qs = "SELECT DISTINCT(P) FROM " + cname + " P " + paraUtilService.getFinalWhereString(whereString) + reqPs.getOrderString();
		Query q = emp.get().createQuery(qs);
		setFirstMax(q);
		return q.getResultList();
	}
	
	public Integer smartFetchChildrenCount() {
		String cname = this.persistentClass.getSimpleName();
		
		if(getReqPs().getParentId() == SmartConstants.NONE_EXIST_MODEL_ID){
			if(HasObjectPermission.class.isAssignableFrom(this.persistentClass)){
				return smartHPAllFetchCount();
			}else{
				return smartFetchTopCount();
			}
		}
		String relationStr = " AND P.parent.id = " + getReqPs().getParentId();
		
		String whereString = relationStr + getReqPs().getWhereString() + getOwnerString();
		
		String qs = "SELECT COUNT(DISTINCT P) FROM " + cname + " P " + paraUtilService.getFinalWhereString(whereString);
		Query q = emp.get().createQuery(qs);
		Long l = (Long) q.getSingleResult(); 
		return l.intValue();
	}
	

	protected String getRelationString() {
		String relationStr = "";		
		if(getReqPs().getRelationModelId() != SmartConstants.NONE_EXIST_MODEL_ID){
			relationStr = " P." + getReqPs().getRelationPropertyName() + ".id = " + getReqPs().getRelationModelId();
		}
		return relationStr;
	}
	public List<BaseModel> smartTwoJoinFetch() {
		String equalStr = "";
		if(getReqPs().getRelationModelId() != SmartConstants.NONE_EXIST_MODEL_ID){
			equalStr = " AND P." + getReqPs().getRelationPropertyName() + ".id = " + getReqPs().getRelationModelId();
		}
		
		//select mr.hMessage from HmessageReceiver AS mr  WHERE hr.receiver.id = xxx;
		String whereString = equalStr +  getReqPs().getWhereString() + getOwnerString();
		String qs = "SELECT P." + getReqPs().getRelationPropertyNamea() +  " FROM " + getReqPs().getRelationModelName(true) + " AS P "  +  paraUtilService.getFinalWhereString(whereString) + reqPs.getOrderString();
		Query q = emp.get().createQuery(qs);
		setFirstMax(q);
		return q.getResultList();
	}

	public Integer smartTwoJoinRowCount() {
		
		String equalStr = "";
		if(getReqPs().getRelationModelId() != SmartConstants.NONE_EXIST_MODEL_ID){
			equalStr = " AND P." + getReqPs().getRelationPropertyName() + ".id = " + getReqPs().getRelationModelId();
		}
		//select m from HmessageReceiver AS mr , in(hr.receiver) r,in(hr.hMessage) m WHERE r.id = xxx;
		String whereString = equalStr + getReqPs().getWhereString() + getOwnerString();
		String qs = "SELECT COUNT(P." + getReqPs().getRelationPropertyNamea() +  ") FROM " + getReqPs().getRelationModelName(true) + " AS P "  +  paraUtilService.getFinalWhereString(whereString);
		Query q = emp.get().createQuery(qs);
		Long l = (Long) q.getSingleResult(); 
		return l.intValue();
	}
	
	/*
	 * manyToMany总是以主动方为主来获取。(错)，我们查询的时候往往是相反。所以还要一个参数，到底是主还是从。指名被查询的对象是主还是从。
	 * 比如，user 和 role，从user得到他的roles是由意义的，反之意义不大。
	 * 所以对应的是SELECT r FROM User(relationModelName) AS u,IN(u.roles(relationPropertyName)) r WHERE u.id = relationModelId;
	 * 这里我们要得到的是r，是从，主方是u，属性总是在主方。
	 * 而select u from User(modelName) u , IN (u.groups(relationModelName)) g WHERE g.id = gid;
	 * 这里的u是主方，所以这里有3个变化。
	 * SELECT P FROM relationModelName AS Q,IN(Q.relationPropertyName) P WHERE Q.id = relationModelId;
	 * select P from modelName P , IN (P.relationModelName) Q WHERE Q.id = relationModelId;
	 * 
	 */

	
	public List<T> smartManyToManyFetch() {
		String qs;
		String whereString = getRelationEqualString() + getReqPs().getWhereString() + getOwnerString();
		Boolean isMaster = getReqPs().getBooleanValueObject(SmartParameterName.IS_MASTER); 
		if(isMaster != null && isMaster){
			//master
			qs = "SELECT P FROM " + getReqPs().getModelName(true) + " P,IN(P." + getReqPs().getRelationPropertyName() + ") AS Q " + paraUtilService.getFinalWhereString(whereString) + reqPs.getOrderString();
		}else{
			//not master
			qs = "SELECT P FROM " + getReqPs().getRelationModelName(true) + " Q,IN(Q." + getReqPs().getRelationPropertyName() + ") AS P " + paraUtilService.getFinalWhereString(whereString) + reqPs.getOrderString();
		}
		Query q = emp.get().createQuery(qs);
		setFirstMax(q);
		return q.getResultList();
	}
	
	public Integer smartManyToManyRowCount() {
		String whereString = getRelationEqualString() + getReqPs().getWhereString() + getOwnerString();
		String qs;
		Boolean isMaster = getReqPs().getBooleanValueObject(SmartParameterName.IS_MASTER); 
		if(isMaster != null && isMaster){
			//master
			qs = "SELECT COUNT(DISTINCT P) FROM " + getReqPs().getModelName(true) + " P,IN(P." + getReqPs().getRelationPropertyName() + ") AS Q " + paraUtilService.getFinalWhereString(whereString);
		}else{
			//not master
			qs = "SELECT COUNT(DISTINCT P) FROM " + getReqPs().getRelationModelName(true) + " Q,IN(Q." + getReqPs().getRelationPropertyName() + ") AS P " + paraUtilService.getFinalWhereString(whereString);
		}
		Query q = emp.get().createQuery(qs);
		Long l = (Long) q.getSingleResult();
		return l.intValue();
	}

	//返回對象的某個relation屬性為空的值。
	public List<T> smartNoRelationFetch() {
		String whereString = getCollectionEmptyString() + getReqPs().getWhereString() + getOwnerString();
		String qs = "SELECT P FROM " + getReqPs().getModelName(true) + " P " + paraUtilService.getFinalWhereString(whereString) + reqPs.getOrderString();
		Query q = emp.get().createQuery(qs);
		setFirstMax(q);
		return q.getResultList();
	}
	
	public Integer smartNoRelationRowCount() {
		String whereString = getCollectionEmptyString() + getReqPs().getWhereString() + getOwnerString();
		String qs = "SELECT COUNT(DISTINCT P) FROM " + getReqPs().getModelName(true) + " P " + paraUtilService.getFinalWhereString(whereString);
		Query q = emp.get().createQuery(qs);
		Long l = (Long) q.getSingleResult();
		return l.intValue();
	}



	private String getCollectionEmptyString() {
			return " P." + getReqPs().getRelationPropertyName() + " IS EMPTY ";
	}

	
	private String getRelationEqualString() {
		String equalString = "";
		if(getReqPs().getRelationModelId() != SmartConstants.NONE_EXIST_MODEL_ID){
			equalString = " AND Q.id = " + getReqPs().getRelationModelId();
		}
		return equalString;
	}
	
	
	protected String getOwnerString() {
		String ownerString = "";
		Boolean b = getReqPs().getFetchOwner();
		if(b != null && b){
			ownerString = " AND P.creator.id = " +  getSu().getUserId();
		}
		return ownerString;
	}
	

	
	@Inject
	PropertyTypeService pts;


	@HowManyMilliSec
	public List<T> smartFetch(){
		String whereString = getReqPs().getWhereString() + getOwnerString();
		String qs = "SELECT p FROM " + this.persistentClass.getSimpleName() + " p " + paraUtilService.getFinalWhereString(whereString) + reqPs.getOrderString();
		Query q = emp.get().createQuery(qs);
		setFirstMax(q);
		return q.getResultList();
	}
	
	//SELECT p FROM Permission p  WHERE  P.objectIdentity LIKE '4853'整数问题。


	public Integer smartFetchCount(){
		String whereString = getReqPs().getWhereString() + getOwnerString();
		String qs = "SELECT COUNT(DISTINCT p) FROM " + this.persistentClass.getSimpleName() + " P" + paraUtilService.getFinalWhereString(whereString);
		Query q = emp.get().createQuery(qs);
		Long l = (Long) q.getSingleResult();
		return l.intValue();
	}	


//	public List<T> fetchChildren(BaseModel model) {
//		//这是请求根目录
//		String cn = model.getClass().getSimpleName();
//		if(model.getId() == 0){
//			String qs = "SELECT m FROM " + cn + " m WHERE m.parent IS NULL";
//			Query q = emp.get().createQuery(qs);
//			return q.getResultList();
//		}else{
//			String qs = "SELECT m FROM " + cn + " m WHERE m.parent.id = " + model.getId();
//			Query q = emp.get().createQuery(qs);
//			return q.getResultList();
//		}
//	}
	
	
	//-------------------------------------------Update---------------------------------------------------------------------
	
	@Transactional
	public T merge(T object) {
		return emp.get().merge(object);
	}
	
	@Transactional
	public T update(T object) {
		return emp.get().merge(object);
	}
	
	
	//默认情况下，此方法仅仅更新普通的属性，不包括关系对象。但是也可以在hook函数中做进一步处理。
	//这个方法不仅仅是smartgwt在使用，gwtrpc也在使用，所以还是必须保留这个方式。所以不能从params直接复制属性值。
	@Transactional
	public T smartUpdateBaseModel(T model){
		try {
			T oldModel = find(model.getId());
			if(oldModel instanceof TreeModel){
				int pid = getReqPs().getParentId();
				TreeModel<T> op = ((TreeModel<T>)oldModel).getParent();
				
				if(pid == SmartConstants.NONE_EXIST_MODEL_ID){//提升到根目录
					if(op != null)op.removeChildren(oldModel);//原來的目錄要刪除
					((TreeModel<T>)oldModel).setParent(null);
				}else{
					T p = find(pid);
					if(op != null){
						if(op.getId() != pid){
							((TreeModel<T>)p).addChildren(oldModel);//新的父目錄
							((TreeModel<T>)oldModel).setParent(p);
							op.removeChildren(oldModel);//原來的父目錄要刪除兒子。
						}else{
							;//相等，就沒有必要處理。
						}
					}else{//op是null，原來是頂級目錄
						((TreeModel<T>)p).addChildren(oldModel);//新的父目錄
						((TreeModel<T>)oldModel).setParent(p);
					}
				}
			}
			if(oldModel instanceof HasTags){
				processTags((HasTags) oldModel);
			}
			if(extraStrategyTask != null){
				if(extraStrategyTask.extraUpdateTask(oldModel,model)){
					bmub.copyProperties(oldModel, model);
					merge(oldModel);
				}
			}else{
				bmub.copyProperties(oldModel, model);
				merge(oldModel);
			}
			return oldModel;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional
	public T smartRemoveBaseModel(T model) {
		if(model instanceof TreeModel){
			if(checkHasChildren((TreeModel<T>)model)){
				return model;
			}
		}
		if(extraStrategyTask != null && extraStrategyTask.extraRemoveTask(model)){
			return remove(model);
		}else if(extraStrategyTask == null){
			return remove(model);
		}else{
			return model;
		}
	}
	
	private boolean checkHasChildren(TreeModel<T> tm) {
		if(tm.getChildren().size() > 0){
			injector.getInstance(ErrorMessages.class).addError(new Error("请先删除子目录！",ServerErrorCode.DEPENDENCY_ERROR));
			return true;
		}
		return false;
	}

	
	@Transactional
	public T updateBaseModel(T model) {
		return emp.get().merge(model);
	}


	//-------------------------------------------Delete---------------------------------------------------------------------

	@Transactional
	public T remove(BaseModel<T> baseModel) {
		EntityManager em = emp.get();
		T bm = em.find(this.persistentClass, baseModel.getId()); 
		if(bm instanceof TreeModel){
			TreeModel<T> tm = (TreeModel<T>) bm;
			if(tm.getParent() != null){
				((TreeModel<T>)tm.getParent()).removeChildren(bm);
			}
			tm.setParent(null);
		}
		em.remove(bm);
		return bm;
	}
	

	@Transactional
	public T remove(int modelId) {
		EntityManager em = emp.get();
		T bm = em.find(this.persistentClass, modelId); 
		if(bm instanceof TreeModel){
			((TreeModel<T>) bm).setParent(null);
		}
		em.remove(bm);
		return bm;
	}


	
	//-------------------------------------------Utils---------------------------------------------------------------------
	public BaseDao(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}
	
	/**
	 * 
	 * @param other
	 * @return
	 * 要不要包括自己呢？现在是包括的。
	 */
	protected String getParentPath(TreeModel<T> treeModel) {
		List<T> parents = new ArrayList<T>();
		while(treeModel != null){
			parents.add((T) treeModel);
			treeModel = (TreeModel<T>)treeModel.getParent();
		}
		String s = ",";
		for (BaseModel<T> baseModel : parents) {
			s += baseModel.getId() + ",";
		}
		return s;
	}


	public Integer getRowCount() {
		Long l = (Long) emp.get().createQuery(
				"SELECT COUNT(DISTINCT c) FROM " + this.persistentClass.getSimpleName()
						+ " AS c"/*, this.persistentClass*/).getSingleResult();
		return l.intValue();
	}

	public Integer getRowCount(BaseModel model) {
		Long l = (Long) emp.get().createQuery(
				"SELECT COUNT(DISTINCT c) FROM " + model.getClass().getSimpleName()
						+ " AS c"/*, this.persistentClass*/).getSingleResult();
		return l.intValue();
	}

	protected String getCreatorPath(User u) {
		String path = ",";
		User tmpu = u;
		while(tmpu != null){
			path += tmpu.getId() + ",";
			tmpu = tmpu.getCreator();
		}
		return path;
	}
	
//	public String getObjectValueResponse(Object svalue){
//		JSONObject responseField = new JSONObject().element("status", 0);
//		responseField.element("data", svalue);
//		JSONObject root = new JSONObject();
//		root.element("response", responseField);
//		return root.toString();		
//	}
//	
//	public String getRawValueResponse(Object svalue){
//		return svalue.toString();		
//	}
//	
//
//	@HowManyMilliSec
//	public String getListResponse(List<? extends BaseModel> results,Integer totalRows){
//		JSONArray dataField = new JSONArray();
//		JSONObject responseField = new JSONObject().element("status", 0);
//		if(results != null){
//			int startRow = getReqPs().getStartRow();
//			responseField.element("startRow", startRow);
//			responseField.element("endRow",startRow + results.size());
//			responseField.element("totalRows", totalRows);
//			for(BaseModel bm : results){
//				dataField.add(bm.toJson());
//			}
//		}else{
//			responseField.element("startRow", 0);
//			responseField.element("endRow",0);
//			responseField.element("totalRows", 0);
//		}
//		
//		responseField.element("data", dataField);
//		
//		JSONObject root = new JSONObject();
//		root.element("response", responseField);
//		
//		return root.toString();
//	}
//	
//	public String getJsonResponse(List<HasToJson> results,Integer totalRows){
//		JSONArray dataField = new JSONArray();
//		JSONObject responseField = new JSONObject().element("status", 0);
//		if(results != null){
//			int startRow = getReqPs().getStartRow();
//			responseField.element("startRow", startRow);
//			responseField.element("endRow",startRow + results.size());
//			responseField.element("totalRows", totalRows);
//			for(HasToJson bm : results){
//				dataField.add(bm.toJson());
//			}
//		}else{
//			responseField.element("startRow", 0);
//			responseField.element("endRow",0);
//			responseField.element("totalRows", 0);
//		}
//		
//		responseField.element("data", dataField);
//		
//		JSONObject root = new JSONObject();
//		root.element("response", responseField);
//		
//		return root.toString();
//	}
//	
//	
//	public String getListResponse(BaseModel model){
//		JSONObject responseField = new JSONObject().element("status", 0);
//		JSONArray dataField = new JSONArray();
//		
//		if(model != null){
//			responseField.element("startRow", 0);
//			responseField.element("endRow",1);
//			responseField.element("totalRows", 1);
//			dataField.add(model.toJsonOne());
//		}else{
//			responseField.element("startRow", 0);
//			responseField.element("endRow",0);
//			responseField.element("totalRows", 0);
//		}
//		responseField.element("data", dataField);
//		JSONObject root = new JSONObject();
//		root.element("response", responseField);
//		
//		return root.toString();
//		
//	}

	public abstract List<BaseModel> smartCustomFetch();
	
	public abstract Integer smartCustomCount();
	

	public List<BaseModel> getAllowedNodes(String modelName,Integer userId) {
		String ds = "SELECT d FROM " + modelName +  " d WHERE d.id IN (SELECT p.objectIdentity FROM  User u ,IN(u.roles) r, IN(r.permissions) p WHERE u.id = :uid)";
		Query dq = emp.get().createQuery(ds);
		dq.setParameter("uid", userId);
		return dq.getResultList();
	}

	public Integer getAllowedNodesNumber(String modelName,Integer userId) {
		String ds = "SELECT COUNT(DISTINCT d) FROM " + modelName +  " d WHERE d.id IN (SELECT p.objectIdentity FROM  User u ,IN(u.roles) r, IN(r.permissions) p WHERE u.id = :uid)";
		Query dq = emp.get().createQuery(ds);
		dq.setParameter("uid", userId);
		Long l = (Long) dq.getSingleResult();
		return l.intValue();
	}

	public abstract List<T> smartNamedQueryFetch();

	public abstract Integer smartNamedQueryFetchCount();
	
	
	public List<T> smartHasPermissionFetch() {
		if(TreeModel.class.isAssignableFrom(persistentClass) && getSu().isSuperman()){
			return getTopTreeNode();
		}
		Query q;
		String opCode = getReqPs().getStringValue(OperationField.OPCODE.toString());
		Boolean b = getReqPs().getBooleanValueObject(SmartParameterName.IS_GROUP_PERM); 
		if(b != null && b){
			if( opCode != null && !opCode.isEmpty()){
				String qs = "SELECT DISTINCTf) FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.groups) AS g,IN(g.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid AND q.opCode = :opCode)" + getReqPs().getWhereString() +  reqPs.getOrderString();
				q = emp.get().createQuery(qs);
				q.setParameter("uid", getLoginUser().getId());
				q.setParameter("opCode", ObjectOperation.valueOf(opCode));
			}else{
				String qs = "SELECT DISTINCT(f) FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.groups) AS g,IN(g.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid)" + getReqPs().getWhereString() +  reqPs.getOrderString();
				q = emp.get().createQuery(qs);
				q.setParameter("uid", getLoginUser().getId());
			}
		}else{
			if( opCode != null && !opCode.isEmpty()){
				String qs = "SELECT DISTINCT(f) FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid AND q.opCode = :opCode)" + getReqPs().getWhereString() +  reqPs.getOrderString();
				q = emp.get().createQuery(qs);
				q.setParameter("uid", getLoginUser().getId());
				q.setParameter("opCode", ObjectOperation.valueOf(opCode));
			}else{
				String qs = "SELECT DISTINCT(f) FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid)" + getReqPs().getWhereString() +  reqPs.getOrderString();
				q = emp.get().createQuery(qs);
				q.setParameter("uid", getLoginUser().getId());
			}
		}
		setFirstMax(q);
		return q.getResultList();
	}

	public Integer smartHasPermissionFetchCount() {
		if(TreeModel.class.isAssignableFrom(persistentClass) && getSu().isSuperman()){
			return getTopTreeNodeCount();
		}
		Query q;
		String opCode = getReqPs().getStringValue(OperationField.OPCODE.toString());
		Boolean b = getReqPs().getBooleanValueObject(SmartParameterName.IS_GROUP_PERM);
		if(b != null && b){
			if( opCode != null && !opCode.isEmpty()){
				String qs = "SELECT COUNT(DISTINCT f) FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.groups) AS g,IN(g.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid AND q.opCode = :opCode)" + getReqPs().getWhereString();
				q = emp.get().createQuery(qs);
				q.setParameter("uid", getLoginUser().getId());
				q.setParameter("opCode", ObjectOperation.valueOf(opCode));
			}else{
				String qs = "SELECT COUNT(DISTINCT f) FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.groups) AS g,IN(g.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid)" + getReqPs().getWhereString();
				q = emp.get().createQuery(qs);
				q.setParameter("uid", getLoginUser().getId());
			}
		}else{
			if( opCode != null && !opCode.isEmpty()){
				String qs = "SELECT COUNT(DISTINCT f) FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid AND q.opCode = :opCode)" + getReqPs().getWhereString();
				q = emp.get().createQuery(qs);
				q.setParameter("uid", getLoginUser().getId());
				q.setParameter("opCode", ObjectOperation.valueOf(opCode));
			}else{
				String qs = "SELECT COUNT(DISTINCT f) FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid)" + getReqPs().getWhereString();
				q = emp.get().createQuery(qs);
				q.setParameter("uid", getLoginUser().getId());
			}
		}
		Long l = (Long) q.getSingleResult();
		return l.intValue();
	}
	
	
	protected boolean isPublicClass(String cname){
		if(JrxmlFile.class.getName() == cname){
			return true;
		}
		return false;
	}
	
	
	public List<T> smartHPAllFetch() {
		if(isPublicClass(persistentClass.getName()))return getTopTreeNode();
		if(TreeModel.class.isAssignableFrom(persistentClass) && getSu().isSuperman()){
			return getTopTreeNode();
		}
		Query q;
		String opCode = getReqPs().getStringValue(OperationField.OPCODE.toString());
		if( opCode != null && !opCode.isEmpty()){
			String qs = "SELECT DISTINCT(p) FROM " + persistentClass.getSimpleName() +  
			" AS p,IN(p.objectPermissions) AS pe WHERE (pe.id IN (SELECT q.id FROM User AS u,IN(u.groups) AS g,IN(g.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid AND q.opCode = :opCode)) OR " +
			"(pe.id IN (SELECT q1.id FROM User AS u1,IN(u1.roles) AS r1,IN(r1.permissions) AS q1 WHERE  u1.id = :uid AND q.opCode = :opCode))" + getReqPs().getWhereString() +  reqPs.getOrderString();
			q = emp.get().createQuery(qs);
			q.setParameter("uid", getLoginUser().getId());
			q.setParameter("opCode", ObjectOperation.valueOf(opCode));
		}else{
			String qs = "SELECT DISTINCT(p) FROM " + persistentClass.getSimpleName() +  
			" AS p,IN(p.objectPermissions) AS pe WHERE (pe.id IN (SELECT q.id FROM User AS u,IN(u.groups) AS g,IN(g.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid)) OR " +
			"(pe.id IN (SELECT q1.id FROM User AS u1,IN(u1.roles) AS r1,IN(r1.permissions) AS q1 WHERE  u1.id = :uid))" + getReqPs().getWhereString() +  reqPs.getOrderString();
			q = emp.get().createQuery(qs);
			q.setParameter("uid", getLoginUser().getId());
		}
		setFirstMax(q);
		return q.getResultList();
	}

	public Integer smartHPAllFetchCount() {
		if(isPublicClass(persistentClass.getName()))return getTopTreeNodeCount();
		if(TreeModel.class.isAssignableFrom(persistentClass) && getSu().isSuperman()){
			return getTopTreeNodeCount();
		}
		Query q;
		String opCode = getReqPs().getStringValue(OperationField.OPCODE.toString());
		if( opCode != null && !opCode.isEmpty()){
			String qs = "SELECT COUNT(DISTINCT p) FROM " + persistentClass.getSimpleName() +  
			" AS p,IN(p.objectPermissions) AS pe WHERE (pe.id IN (SELECT q.id FROM User AS u,IN(u.groups) AS g,IN(g.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid AND q.opCode = :opCode)) OR " +
			"(pe.id IN (SELECT q1.id FROM User AS u1,IN(u1.roles) AS r1,IN(r1.permissions) AS q1 WHERE  u1.id = :uid AND q.opCode = :opCode))" + getReqPs().getWhereString();
			q = emp.get().createQuery(qs);
			q.setParameter("uid", getLoginUser().getId());
			q.setParameter("opCode", ObjectOperation.valueOf(opCode));
		}else{
			String qs = "SELECT COUNT(DISTINCT p) FROM " + persistentClass.getSimpleName() +  
			" AS p,IN(p.objectPermissions) AS pe WHERE (pe.id IN (SELECT q.id FROM User AS u,IN(u.groups) AS g,IN(g.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid)) OR " +
			"(pe.id IN (SELECT q1.id FROM User AS u1,IN(u1.roles) AS r1,IN(r1.permissions) AS q1 WHERE  u1.id = :uid))" + getReqPs().getWhereString();
			q = emp.get().createQuery(qs);
			q.setParameter("uid", getLoginUser().getId());
		}
		Long l = (Long) q.getSingleResult();
		return l.intValue();
	}

	protected List<T> getTopTreeNode() {
		String qs = "SELECT p FROM " + persistentClass.getSimpleName() + " AS p WHERE p.parent is NULL" + getReqPs().getWhereString() +  reqPs.getOrderString();
		Query q = emp.get().createQuery(qs);
		return q.getResultList();
	}
	
	protected Integer getTopTreeNodeCount() {
		String qs = "SELECT COUNT(DISTINCT p) FROM " + persistentClass.getSimpleName() + " AS p WHERE p.parent is NULL" +  getReqPs().getWhereString();
		Query q = emp.get().createQuery(qs);
		Long l = (Long) q.getSingleResult();
		return l.intValue();
	}

	public Integer getRelationCount() {
		String cname = this.persistentClass.getSimpleName();
		String relationStr = "";
		String qs;
		if(getReqPs().getBooleanValueObject(SmartParameterName.IS_MASTER)){
			relationStr = " P." + getReqPs().getRelationPropertyName() + ".id = " + getReqPs().getRelationModelId();
			String whereString = relationStr;
			qs = "SELECT COUNT(DISTINCT P) FROM " + cname + " P " + paraUtilService.getFinalWhereString(whereString);
		}else{
			relationStr = "Q.id = " + getReqPs().getRelationModelId();
			String whereString = relationStr;
			qs = "SELECT COUNT(DISTINCT P) FROM " + getReqPs().getRelationModelName(true) + " Q, IN(Q." + getReqPs().getRelationPropertyName()  + ") AS P" + paraUtilService.getFinalWhereString(whereString);			
		}
		Query q = emp.get().createQuery(qs);
		Long l = (Long) q.getSingleResult(); 
		return l.intValue();
	}
	
	public int[] getAdminFirstMax(){
		int first = 0;
		int max = 100;
		String p = getReqPs().getStringValue(SmartParameterName.EXTRAPARAS);
		String[] paras = p.trim().split(",");
		if(paras.length == 0){
			;
		}else if(paras.length == 1){
			try {
				max = Integer.parseInt(paras[0]);
			} catch (NumberFormatException e) {}
		}else if(paras.length > 1){
			try {
				first = Integer.parseInt(paras[0]);
				max = Integer.parseInt(paras[1]);
			} catch (NumberFormatException e) {}
		}
		return new int[]{first,max};
	}

	public List<HasToJson> fetchSharedUsers() {
		String qs = "SELECT new com.m3958.firstgwt.model.ShareTarget(u.id,r.id,r.cname,u.loginName,u.email) FROM User u," +
				" IN(u.roles) r WHERE r.id IN (SELECT r1.id FROM " + getReqPs().getRelationModelName(true) +" f,IN(f.objectRoles) r1 WHERE f.id = :oid)";
		Query q = emp.get().createQuery(qs);
		q.setParameter("oid", getReqPs().getRelationModelId());
		setFirstMax(q);
		return q.getResultList();
	}

	public Integer fetchSharedUsersCount() {
		String qs = "SELECT COUNT(u) FROM User u," +
		" IN(u.roles) r WHERE r.id IN (SELECT r1.id FROM " + getReqPs().getRelationModelName(true) +" f,IN(f.objectRoles) r1 WHERE f.id = :oid)";
		Query q = emp.get().createQuery(qs);
		q.setParameter("oid", getReqPs().getRelationModelId());
		Long l = (Long) q.getSingleResult(); 
		return l.intValue();
	}
	
	
	public List<HasToJson> fetchSharedGroups() {
		String qs = "SELECT new com.m3958.firstgwt.model.ShareTarget(g.id,r.id,r.cname,g.name,g.name) FROM Group g," +
				" IN(g.roles) r WHERE r.id IN (SELECT r1.id FROM " + getReqPs().getRelationModelName(true) +" f,IN(f.objectRoles) r1 WHERE f.id = :oid)";
		Query q = emp.get().createQuery(qs);
		q.setParameter("oid", getReqPs().getRelationModelId());
		setFirstMax(q);
		return q.getResultList();
	}

	public Integer fetchSharedGroupsCount() {
		String qs = "SELECT COUNT(DISTINCT g) FROM Group g," +
		" IN(g.roles) r WHERE r.id IN (SELECT r1.id FROM " + getReqPs().getRelationModelName(true) +" f,IN(f.objectRoles) r1 WHERE f.id = :oid)";
		Query q = emp.get().createQuery(qs);
		q.setParameter("oid", getReqPs().getRelationModelId());
		Long l = (Long) q.getSingleResult(); 
		return l.intValue();
	}
	
	
	@Transactional
	public User shareToUser() {
		boolean isAsign = getReqPs().getBooleanValueObject(SmartParameterName.ADD_OR_REMOVE);
		HasObjectPermission hp = (HasObjectPermission) find(getReqPs().getModelId());
		
		UserDao udao = injector.getInstance(UserDao.class);
		User u;

		if(isAsign){
			String uid_orn = getReqPs().getExtraParas();
			String[] uo = uid_orn.split(",");
			u = udao.findByLoginNameOrEmailOrMobile(uo[0]);
			if(u == null){
				getErrors().addError(new Error("用户不存在！", ServerErrorCode.DEPENDENCY_ERROR));
				return null;
			}
			
			if(hp instanceof IHasSiteId){
				WebSite ws = emp.get().find(WebSite.class,((IHasSiteId)hp).getSiteId());
				
				if(!getRso().hasObjectRole(u.getId(),ws)){
					Role wrr = ws.getObjectRole(ObjectRoleName.READER); 
					if( wrr == null){//如果有wwr角色的話，肯定已經有了objectpermissions。
						if(ws.getObjectPermissions().size() == 0){
							ws.createObjectPermissions();
							PermissionDao pdao = injector.getInstance(PermissionDao.class);
							for(Permission p : ws.getObjectPermissions()){
								pdao.smartPersistBaseModel(p);
							}
						}
						
						wrr = ws.createObjectRole(ObjectRoleName.READER);//角色需要的时候才建立
						RoleDao rdao = injector.getInstance(RoleDao.class);
						rdao.smartPersistBaseModel(wrr);
						WebSiteDao wsdao = injector.getInstance(WebSiteDao.class);
						wsdao.merge(ws);
					}
					u.addRole(wrr);
					udao.merge(u);
				}
			}
			

			Role r = hp.getObjectRole(ObjectRoleName.valueOf(uo[1]));
			if(r == null){
				if(hp.getObjectPermissions().size() == 0){//权限是一次性的保存
					hp.createObjectPermissions();
					PermissionDao pdao = injector.getInstance(PermissionDao.class);
					for(Permission p : hp.getObjectPermissions()){
						pdao.smartPersistBaseModel(p);
					}
				}
				r = hp.createObjectRole(ObjectRoleName.valueOf(uo[1]));//角色需要的时候才建立
				RoleDao rdao = injector.getInstance(RoleDao.class);
				rdao.smartPersistBaseModel(r);
				merge((T) hp);
			}
			//当再次保存hp的时候，引起role再次保存，可能会引起两次保存r-p关系。

			boolean b = u.addRole(r);
			if(b){
				udao.merge(u);
			}
			
		}else{
			Role r = emp.get().find(Role.class, getReqPs().getIntegerValue("rid"));
			u = udao.find(getReqPs().getIntegerValue("uid"));
			boolean b = u.dropRole(r);
			if(b){
				udao.merge(u);
			}
		}
		return u;
	}
	
//	private 
	
	@Transactional
	public Group shareToGroup() {
		
		boolean isAsign = getReqPs().getBooleanValueObject(SmartParameterName.ADD_OR_REMOVE);
		HasObjectPermission hp = (HasObjectPermission) find(getReqPs().getModelId());
		GroupDao gdao = injector.getInstance(GroupDao.class);
		Group g;
		if(isAsign){
			String uid_orn = getReqPs().getExtraParas();
			String[] uo = uid_orn.split(",");
			boolean needMerge = false;
			if(hp.getObjectPermissions().size() == 0){
				hp.createObjectPermissions();
				PermissionDao pdao = injector.getInstance(PermissionDao.class);
				for(Permission p : hp.getObjectPermissions()){
					pdao.smartPersistBaseModel(p);
				}
				needMerge = true;
			}
			Role r = hp.getObjectRole(ObjectRoleName.valueOf(uo[1]));
			if(r == null){
				//这里hp已经和role关联。而Role persist的时候，已经将permissions保存。
				r = hp.createObjectRole(ObjectRoleName.valueOf(uo[1]));
				RoleDao rdao = injector.getInstance(RoleDao.class);
				rdao.smartPersistBaseModel(r);
				needMerge = true;
			}
			//当再次保存hp的时候，引起role再次保存，可能会引起两次保存r-p关系。
			g = gdao.findByNameOrId(uo[0]);
			if(g == null){
				getErrors().addError(new Error("組不存在！", ServerErrorCode.DEPENDENCY_ERROR));
				return null;
			}
			boolean b = g.addRole(r);
			if(b){
				gdao.merge(g);
			}
			
			if(needMerge)merge((T) hp);
		}else{
			Role r = emp.get().find(Role.class, getReqPs().getIntegerValue("rid"));
			g = gdao.find(getReqPs().getIntegerValue("gid"));
			boolean b = g.dropRole(r);
			if(b){
				gdao.merge(g);
			}			
		}
		return g;
	}
	
	public List<BaseModel> fetchNotCreatorHasPermission() {
		Query q;
		String opCode = getReqPs().getStringValue(OperationField.OPCODE.toString());
		if( opCode != null && !opCode.isEmpty()){
			String qs = "SELECT f FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid AND q.opCode = :opCode)" + " AND f.creator.id <> :uid " + getReqPs().getWhereString("f") +  reqPs.getOrderString("f");
			q = emp.get().createQuery(qs);
			q.setParameter("uid", getLoginUser().getId());
			q.setParameter("opCode", ObjectOperation.valueOf(opCode));
		}else{
			String qs = "SELECT f FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid)" + " AND f.creator.id <> :uid "  + getReqPs().getWhereString("f") +  reqPs.getOrderString("f");
			q = emp.get().createQuery(qs);
			q.setParameter("uid", getLoginUser().getId());
		}
		setFirstMax(q);
		return q.getResultList();
	}

	public Integer fetchNotCreatorHasPermissionCount() {
		Query q;
		String opCode = getReqPs().getStringValue(OperationField.OPCODE.toString());
		if( opCode != null && !opCode.isEmpty()){
			String qs = "SELECT COUNT(DISTINCT f) FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid AND q.opCode = :opCode)" + " AND f.creator.id <> :uid "  + getReqPs().getWhereString("f");
			q = emp.get().createQuery(qs);
			q.setParameter("uid", getLoginUser().getId());
			q.setParameter("opCode", ObjectOperation.valueOf(opCode));
		}else{
			String qs = "SELECT COUNT(DISTINCT f) FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid)" + " AND f.creator.id <> :uid "  + getReqPs().getWhereString("f");
			q = emp.get().createQuery(qs);
			q.setParameter("uid", getLoginUser().getId());
		}
		
		Long l = (Long) q.getSingleResult();
		return l.intValue();
	}
	
	
	
	public List<BaseModel> fetchNotCreatorHasGPermission() {
		Query q;
		String opCode = getReqPs().getStringValue(OperationField.OPCODE.toString());
		if( opCode != null && !opCode.isEmpty()){
			String qs = "SELECT f FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.groups) AS g,IN(g.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid AND q.opCode = :opCode)" + " AND f.creator.id <> :uid " + getReqPs().getWhereString("f") +  reqPs.getOrderString("f");
			q = emp.get().createQuery(qs);
			q.setParameter("uid", getLoginUser().getId());
			q.setParameter("opCode", ObjectOperation.valueOf(opCode));
		}else{
			String qs = "SELECT f FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.groups) AS g,IN(g.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid)" + " AND f.creator.id <> :uid "  + getReqPs().getWhereString("f") +  reqPs.getOrderString("f");
			q = emp.get().createQuery(qs);
			q.setParameter("uid", getLoginUser().getId());
		}
		setFirstMax(q);
		return q.getResultList();
	}

	public Integer fetchNotCreatorHasGPermissionCount() {
		Query q;
		String opCode = getReqPs().getStringValue(OperationField.OPCODE.toString());
		if( opCode != null && !opCode.isEmpty()){
			String qs = "SELECT COUNT(DISTINCT f) FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.groups) AS g,IN(g.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid AND q.opCode = :opCode)" + " AND f.creator.id <> :uid "  + getReqPs().getWhereString("f");
			q = emp.get().createQuery(qs);
			q.setParameter("uid", getLoginUser().getId());
			q.setParameter("opCode", ObjectOperation.valueOf(opCode));
		}else{
			String qs = "SELECT COUNT(DISTINCT f) FROM " + persistentClass.getSimpleName() +  " AS f,IN(f.objectPermissions) AS p WHERE p.id IN (SELECT q.id FROM User AS u,IN(u.groups) AS g,IN(g.roles) AS r,IN(r.permissions) AS q WHERE  u.id = :uid)" + " AND f.creator.id <> :uid "  + getReqPs().getWhereString("f");
			q = emp.get().createQuery(qs);
			q.setParameter("uid", getLoginUser().getId());
		}
		
		Long l = (Long) q.getSingleResult();
		return l.intValue();
	}
}