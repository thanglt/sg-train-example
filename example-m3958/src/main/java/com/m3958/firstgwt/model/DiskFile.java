package com.m3958.firstgwt.model;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import javax.persistence.Transient;

import com.google.inject.Injector;
import com.m3958.firstgwt.server.types.HasToJson;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class DiskFile implements HasToJson{
	
	@Transient
	protected static Injector injector;
	
	public static void setInjector(Injector thatInjector){
		injector = thatInjector;
	}

	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		return JSONObject.fromObject(this, jc);
	}
	
	
	protected int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DiskFile(File f){
		setName(f.getName());
		setLastModified(new Date(f.lastModified()));
		if(f.isFile()){
			setFileSize(f.length());
			setFolder(false);
		}else{
			setFileSize(0l);
			setFolder(true);
		}
	}
	
	public DiskFile(File f,String removeExt){
		String fn = f.getName();
		fn = fn.substring(0, fn.length() - removeExt.length());
		setName(fn);
		setLastModified(new Date(f.lastModified()));
		if(f.isFile()){
			setFileSize(f.length());
			setFolder(false);
		}else{
			setFileSize(0l);
			setFolder(true);
		}
	}
	
	private long fileSize;
	private String name;
	private boolean folder;
	private Date lastModified;
	private String content;

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public long getFileSize() {
		return fileSize;
	}


	public InputStream getStream(String saveToPath){
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	public boolean isFolder() {
		return folder;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}


}
