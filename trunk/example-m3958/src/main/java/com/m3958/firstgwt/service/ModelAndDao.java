package com.m3958.firstgwt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.accesschecker.BaseChecker;
import com.m3958.firstgwt.dao.BaseDao;
import com.m3958.firstgwt.dao.ObjectClassNameDao;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.DiskFile;
import com.m3958.firstgwt.model.ObjectClassName;

@Singleton
@SuppressWarnings("rawtypes")
public class ModelAndDao {
	
	
	private Injector injector;
	
	
	public Map<String, Class> daos = new HashMap<String, Class>();
	
	
	private com.google.inject.Provider<EntityManager> emp;
	
	public Map<String, Class> getDaos() {
		return daos;
	}

	public Map<String, Class> checkers = new HashMap<String, Class>();

	@Inject
	public ModelAndDao(Injector injector,com.google.inject.Provider<EntityManager> emp,ObjectClassNameDao dao){
		this.dao = dao;
		this.injector = injector;
		BaseModel.setEmpProvider(emp);
		BaseModel.setInjector(injector);
		DiskFile.setInjector(injector);
		reload();
	}
	
	private ObjectClassNameDao dao;

	public void reload() {
		List<ObjectClassName> ocs = dao.fetchAll();
		for (ObjectClassName oc : ocs) {
			Class c = null;
			try {
				if(oc.getDaoName() == null)continue;
				c = Class.forName(oc.getDaoName());
			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
			}
			if(c!=null)daos.put(oc.getEname(), c);
			
			Class c1 = null;
			try {
				if(oc.getCheckerName() == null)continue;
				c1 = Class.forName(oc.getCheckerName());
			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
			}
			if(c1!=null)daos.put(oc.getEname(), c1);
		}
	}
	
	@SuppressWarnings("unchecked")
	public BaseDao getDaoInstance(String modelName){
		Class c = daos.get(modelName);
		if(c != null){
			return (BaseDao) injector.getInstance(c);
		}else{
			return (BaseDao) injector.getInstance(getDaoClassWithDefaultName(modelName));
		}
	}
	
	@SuppressWarnings("unchecked")
	public BaseChecker getCheckerInstance(String modelName){
		Class c = checkers.get(modelName);
		if(c!=null){
			return (BaseChecker)injector.getInstance(c);
		}else{
			return (BaseChecker)injector.getInstance(getCheckerClassWithDefaltName(modelName));
		}
	}
	
	
	private Class getCheckerClassWithDefaltName(String modelName) {
		String checkerName = modelName.replace(".model.", ".accesschecker.")+"Checker";
		try {
			Class c =  Class.forName(checkerName);
			checkers.put(modelName, c);
			return c;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	//com.m3958.firstgwt.model.Asset => com.m3958.firstgwt.dao.AssetDao
	private Class getDaoClassWithDefaultName(String modelName) {
		String daoName = modelName.replace(".model.",".dao.") + "Dao";
		try {
			Class c =  Class.forName(daoName);
			daos.put(modelName, c);
			return c;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
