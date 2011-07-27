package com.m3958.firstgwt.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.accesschecker.BaseChecker;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.command.AddCommand;
import com.m3958.firstgwt.command.AdminCommand;
import com.m3958.firstgwt.command.Command;
import com.m3958.firstgwt.command.CustomCommand;
import com.m3958.firstgwt.command.DiskFileCommand;
import com.m3958.firstgwt.command.FetchCommand;
import com.m3958.firstgwt.command.RpcmReqCommand;
import com.m3958.firstgwt.command.NoOperationCommand;
import com.m3958.firstgwt.command.RemoveCommand;
import com.m3958.firstgwt.command.UpdateCommand;
import com.m3958.firstgwt.exception.SmartJpaException;
import com.m3958.firstgwt.model.DiskFile;
import com.m3958.firstgwt.model.Hgll;
import com.m3958.firstgwt.model.HtmlCss;
import com.m3958.firstgwt.model.Token;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.service.ModelAndDao;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.session.SessionUser;

/**
 * author jianglibo@gmail.com
 *  
 */
@Singleton
public class SmartCFUDServiceImpl extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject 
	private Injector injector;
	
	@Inject
	private AppUtilService autils;
	
	private static Set<String> allowAddModels = new HashSet<String>();
	
	static{
		allowAddModels.add(Hgll.class.getName());
		allowAddModels.add(HtmlCss.class.getName());
	}
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
		doPost(req, res);
	}
	
	
	@SuppressWarnings("unused")
	private void writeExcel(HttpServletRequest req, HttpServletResponse res,ByteArrayOutputStream baos) throws IOException{
		res.setContentType("application/vnd.ms-excel");
		res.setHeader("Content-Disposition", "attachment; filename=lgb.xls");
		baos.writeTo(res.getOutputStream());
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
		
		SessionUser su = injector.getInstance(SessionUser.class);
		ErrorMessages errors = injector.getInstance(ErrorMessages.class);
//		if(!su.isLogined() && !isRegisteUser(req, res) && !allowAnanymousAdd(req, res)){
//			errors.addError(new Error("需要登录之后操作！", ServerErrorCode.LOGIN_REQUIRED));
//			autils.writeJsonErrorResponse(res);
//			return;
//		}
		
		if(DiskFile.class.getName().equals(req.getParameter(SmartParameterName.MODEL_NAME))){
			DiskFileCommand dfc = injector.getInstance(DiskFileCommand.class);
			try {
				dfc.execute();
			} catch (SmartJpaException e) {}
			return;
		}
		
		if(!allowAnanymousAdd(req, res)){
			if(!doCheck(req, res)){
				errors.addError(new Error("操作拒绝！", ServerErrorCode.ACCESS_DENY));
				autils.writeJsonErrorResponse(res);
				return;
			}
		}
		
		String s = req.getParameter(SmartParameterName.OPERATION_TYPE);
		SmartOperationName opName = SmartOperationName.NO_OPERATION;
		if(s!=null){
			opName = SmartOperationName.valueOf(s.toUpperCase());
		}
		
		Command c = null;
		switch (opName) {
		case FETCH:
			c = injector.getInstance(FetchCommand.class);
			break;
		case REMOVE:
			c = injector.getInstance(RemoveCommand.class);
			break;
		case UPDATE:
			c = injector.getInstance(UpdateCommand.class);
			break;
		case ADD:
			c = injector.getInstance(AddCommand.class);
			break;
		case CUSTOM:
			c = injector.getInstance(CustomCommand.class);
			break;
		case RPCM_REQ:
			c = injector.getInstance(RpcmReqCommand.class);
			break;
		case ADMIN:
			c = injector.getInstance(AdminCommand.class);
			break;
		default:
			c = injector.getInstance(NoOperationCommand.class);
			break;
		}
		
		try {
			c.execute();
		} catch (SmartJpaException e) {
			e.printStackTrace();
			errors.addError(new Error(e.getMessage(), ServerErrorCode.JPA_ERROR));
			autils.writeJsonErrorResponse(res);
		} catch (Exception e) {
			e.printStackTrace();
			errors.addError(new Error(e.getMessage(), ServerErrorCode.JPA_ERROR));
			autils.writeJsonErrorResponse(res);
		}
	}
	
	private boolean isRegisteUser(HttpServletRequest req, HttpServletResponse res){
		String modelName = req.getParameter(SmartParameterName.MODEL_NAME);
		String optype = req.getParameter(SmartParameterName.OPERATION_TYPE);
		if(User.class.getName().equals(modelName) && SmartOperationName.ADD.getValue().equals(optype)){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean allowAnanymousAdd(HttpServletRequest req, HttpServletResponse res){
		String modelName = req.getParameter(SmartParameterName.MODEL_NAME);
		if(Token.class.getName().equals(modelName))return true;
		String optype = req.getParameter(SmartParameterName.OPERATION_TYPE);
		if(SmartOperationName.ADD.getValue().equals(optype)){
			if(allowAddModels.contains(modelName)){
				return true;
			}
		}
		return false;
	}
	
	private boolean doCheck(HttpServletRequest req, HttpServletResponse res){
		String modelName = req.getParameter(SmartParameterName.MODEL_NAME);
		ModelAndDao modelConfig = injector.getInstance(ModelAndDao.class);
		BaseChecker checker = modelConfig.getCheckerInstance(modelName);
		return checker.doCheck();
	}
}
