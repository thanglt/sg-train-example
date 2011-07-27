package com.m3958.firstgwt.client;


import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.StringUtils;

public class Vblock {
	public static class BlockName{
		public static String B = "b";
		public static String C1 = "c1";
		public static String C2 = "c2";
		public static String D1 = "d1";
		public static String D2 = "d2";
		public static String E1 = "e1";
		public static String E2 = "e2";
		public static String F1 = "f1";
		public static String F2 = "f2";
		public static String G1 = "g1";
		public static String G2 = "g2";
	}
	
	private String name;
	private ViewNameEnum viewName;
	private ViewAction viewAction = ViewAction.NO_ACTION;
	private String[] paras = null;
	
	public Vblock(String s){
		if(s.trim().isEmpty())return;
		name = s;
	}
	
	
	public void clearValues(){
		viewName = null;
		viewAction = null;
		paras = null;
	}
	
	public void setValues(ViewNameEnum vname,ViewAction viewAction,String...paras){
		this.viewName = vname;
		this.viewAction = viewAction;
		this.paras = paras;
	}
	
	/*
	 * 不需要name字段。
	 */
	public void setValues(String[] splitedToken){
		int l = splitedToken.length;
		if(l < 2)return;
		
		if(l > 1){
			viewName = ViewNameEnum.valueOf(splitedToken[1]);
		}else{
			viewName = null;
		}
		if(l > 2){
			viewAction = ViewAction.valueOf(splitedToken[2]);
		}else{
			viewAction = ViewAction.NO_ACTION;
		}
		if(l > 3){
			paras = new String[l - 3];
			for(int i = 0;i < l - 3;i++){
				paras[i] = splitedToken[i+3];
			}
		}else{
			paras = null;
		}
	}
	
	public boolean isEmpty(){
		return viewName == null;
	}

	public ViewNameEnum getViewName() {
		return viewName;
	}
	public void setViewName(ViewNameEnum viewName) {
		this.viewName = viewName;
	}
	public String[] getParas() {
		return paras;
	}
	
	public void setParas(String...paras){
		this.paras = paras;
	}

	public String toToken(){
		if(name == null || viewName == null)return "";
		int l = 3 + (paras == null ? 0 : paras.length);
		String[] ss = new String[l];
		ss[0] = name;
		ss[1] = viewName.toString();
		ss[2] = viewAction.toString();
		if(paras != null){
			for(int i=0;i<paras.length;i++){
				ss[i+3] = paras[i];
			}
		}
		return StringUtils.join(ss, "/");
	}

	public void setViewAction(ViewAction viewAction) {
		this.viewAction = viewAction;
	}

	public ViewAction getViewAction() {
		return viewAction;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
}
