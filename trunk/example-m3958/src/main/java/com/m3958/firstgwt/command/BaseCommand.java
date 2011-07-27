package com.m3958.firstgwt.command;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.m3958.firstgwt.dao.BaseDao;
import com.m3958.firstgwt.exception.SmartJpaException;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.server.response.ErrorMessageResponse;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.service.ModelAndDao;
import com.m3958.firstgwt.service.ReqParaService;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.utils.BaseModelUtilsBean;


public abstract class BaseCommand implements Command{

	
	@Inject
	protected ModelAndDao modelConfig;
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	@Inject
	protected AppUtilService autils;
	
	@Inject
	Injector injector;
	
	@Inject
	BaseModelUtilsBean bmub;
	
	
	@Inject
	protected ReqParaService reqPs;
	
	
	@Inject
	protected HttpServletRequest req;
	
	@Inject
	protected HttpServletResponse res;
	
	
	private BaseDao dao;
	
	
	public BaseDao getDao(){
		if(dao == null){
			dao = modelConfig.getDaoInstance(reqPs.getModelName());
		}
		return dao;
	}
	
	
	@Inject
	protected ErrorMessages errors;
	
	
	protected void writeErrorResponse() throws IOException{
		autils.writeJsonResponse(res, injector.getInstance(ErrorMessageResponse.class).toString());
	}
	
	
	protected BaseModel getBaseModelFromParams() throws SmartJpaException{
		BaseModel model = injector.getInstance(getDao().getPersistentClass());
		try {
			bmub.copyProperties(model, reqPs.getAllParas());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return model;
	}
}
