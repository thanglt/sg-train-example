package com.m3958.firstgwt.model;

import java.io.Serializable;

import com.m3958.firstgwt.client.types.FileSaveTo;


public class AssetVo implements Serializable{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 5343457320033103336L;
	
	public AssetVo(Asset asset){
		setId(asset.getId());
		setOriginName(asset.getOriginName());
	}

	protected int id;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	private String filePath;
	private String fileId;
	private long fileSize;
	private String mimeType;
	private String originName;
	
	

	private String description;
	
	private FileSaveTo saveTo;

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	

	public String getFilePath() {
		return filePath;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getOriginName() {
		return originName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setSaveTo(FileSaveTo saveTo) {
		this.saveTo = saveTo;
	}

	public FileSaveTo getSaveTo() {
		return saveTo;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}

	
	protected String creatorIds;
	

}
