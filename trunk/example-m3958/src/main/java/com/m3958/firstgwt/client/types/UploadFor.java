package com.m3958.firstgwt.client.types;


public enum UploadFor{
	LGB_ZP("老干部照片"),
	JRXML_FILE_PREVIEW_IMG("报表预览"),JRXML_FILE_ATTACHMENT("报表附件"),JUST_UPLOAD("只是上传"),
	UPLOAD_TO_FOLDER("传到目录中"),SITE_FILE("站点文件"),TITLE_IMG("图标图片"),
	ATTACHMENT("附件"),CONTENT_IMG("内容图片"),NO_FOR("");
	
	private String cname;
	
	UploadFor(String cname){
		this.setCname(cname);
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCname() {
		return cname;
	}
}