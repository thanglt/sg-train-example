package com.m3958.firstgwt.client.types;


public enum UploadFormField{
	FILE("请选择文件"),TO_IFRAME("返回到IFrame"),DESCRIPTION("描述"),
	RELATION_MODEL_ID("relationModelId"),RELATION_MODEL_NAME("relationModelName"),VIEWNAME("viewname"),
	UPLOAD_FOR("附件用处"),ASSET_ID("assetId"),FU_TIMESTAMP(""),WANTED_RESPONSE(""),ASKER_TIMESTAMP(""),EXTRA_PARAMS("extraParams");
	
	private String cname;
	
	UploadFormField(String cname){
		this.setCname(cname);
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCname() {
		return cname;
	}
}