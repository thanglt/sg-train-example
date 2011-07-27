package com.m3958.firstgwt.client.errorhandler;

import com.google.gwt.user.client.History;
import com.smartgwt.client.util.SC;

public class GwtRpcExceptionHandler{
	
	public static void handler(Throwable e){
		if(!(e instanceof LgbRpcException)){
			SC.say("服务器一侧返回未知错误，如果是添加，最大的可能是不允许重复的内容！如果是删除，则是存在依赖关系！");
			return;
		}
		LgbRpcException le = (LgbRpcException) e;
		switch (le.getExceptionType()) {
		case LOGIN_REQUIRED:
			loginRequired(le);
			break;
		case ALTER_SUPERMAN:
			SC.say("不允许删除superman！");
		case REMOVE_HAS_CHILDREN:
			SC.say("在删除目录之前，必须先删除子目录！");
			break;
		case REMOVE_HAS_CONTENT:
			SC.say("在删除目录之前，必须先删除目录中的内容！");
		case REMOVE_CONSTRAINT_VIOLATION:
			SC.say("删除违反了依赖关系，比如删除一个老干部条目，必须先删除他的地址等等！");
		default:
			break;
		}
	}

	private static void loginRequired(LgbRpcException le) {
//		NativeMethods.redirectToLogin();
		History.newItem("C2/LOGIN",true);
	}
}
