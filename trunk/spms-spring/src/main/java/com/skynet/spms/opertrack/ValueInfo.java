package com.skynet.spms.opertrack;

public class ValueInfo {

	public ValueInfo(Object previousStatus, Object currStatus) {
		this.previousVal=previousStatus;
		this.currVal=currStatus;
	}

	private final Object previousVal;
	
	private final Object currVal;
		
	public String toString(){
		StringBuilder sb=new StringBuilder();
		
		if(previousVal!=null){
			sb.append("previous val:").append(previousVal);
		}else{
			sb.append("previous val is null");			
		}
		sb.append("\t");
		if(currVal!=null){
			sb.append("curr val:").append(currVal);
		}else{
			sb.append("curr val is null");
		}
		return sb.toString();
	}
}
