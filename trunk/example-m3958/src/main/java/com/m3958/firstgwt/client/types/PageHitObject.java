package com.m3958.firstgwt.client.types;

public class PageHitObject {
	private int obId;
	private int hitNum;
	
	public PageHitObject(String obIdStr,Long hitNum){
		try {
			this.obId = Integer.parseInt(obIdStr);
		} catch (NumberFormatException e){}
		this.hitNum = hitNum.intValue();
	}

	public int getObId() {
		return obId;
	}

	public void setObId(int obId) {
		this.obId = obId;
	}

	public int getHitNum() {
		return hitNum;
	}

	public void setHitNum(int hitNum) {
		this.hitNum = hitNum;
	}
}
