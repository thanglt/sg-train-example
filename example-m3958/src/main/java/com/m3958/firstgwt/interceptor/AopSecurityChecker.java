package com.m3958.firstgwt.interceptor;

import javax.persistence.EntityManager;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


import com.google.inject.Inject;
import com.google.inject.Injector;
import com.m3958.firstgwt.accesschecker.DepartmentChecker;
import com.m3958.firstgwt.accesschecker.RoleChecker;
import com.m3958.firstgwt.accesschecker.UserChecker;

public class AopSecurityChecker implements MethodInterceptor {
	
	@Inject
	UserChecker userChecker;
	
	@Inject
	RoleChecker roleChecker;
	
	@Inject
	DepartmentChecker departmentChecker;
	
	@Inject
	Injector injector;
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	
	//通过方法名称的约定，可以极大的简化编程代码，比如删除以remove开头，列表以fetch开头等等。
	//参数的约定。
	@Override
	public Object invoke(MethodInvocation invacation) throws Throwable {
//	     System.out.println("method "+invacation.getMethod()+" is called on "+
//                 invacation.getThis()+" with args "+invacation.getArguments());
	     Object ret = null;
//		if(accessCheck(invacation)){
//			 ret=invacation.proceed();
//		 }else{
//			 throw new LgbRpcException(LgbExceptionType.ACCESS_DENIED);
//		 }
//	     System.out.println("method "+invacation.getMethod()+" returns "+ret);
	     return ret;
	}
	
//	private boolean accessCheck(MethodInvocation invacation) throws Throwable {
//		Object what = invacation.getArguments()[0];
//		//如果是超级用户，可行！
//		SessionUser su = injector.getInstance(SessionUser.class);
//		if(su.isSuperman())return true;
//		
//		//下面几种类型只能由supmern操作。
//		if(what instanceof ObjectClassName || what instanceof Operation || what instanceof FieldEnum){
//			return false;
//		}
//		//如果是对象的创建者，可以进行操作。
//		if(what instanceof BaseModel){
//			BaseModel bm = (BaseModel) what;
//			bm = emp.get().find(bm.getClass(), bm.getId());
//			if(bm.getCreatorIds() != null && bm.getCreatorIds().indexOf("," + su.getUserId() + ",") != -1){
//				return true;
//			}
//		}
//		
//		//继续细化
//		if(what instanceof User){
////			return userChecker.checkAccess(invacation);
//		}else if(what instanceof Role){
//			return roleChecker.checkAccess(invacation);
//		}else if(what instanceof Department){
//			return departmentChecker.checkAccess(invacation);
//		}else{
//			return true;
//		}
//	}
}
