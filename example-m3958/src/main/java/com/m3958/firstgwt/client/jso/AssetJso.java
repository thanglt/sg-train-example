package com.m3958.firstgwt.client.jso;


public class AssetJso extends BaseModelJso {
	  // Overlay types always have protected, zero-arg ctors
	  protected AssetJso() { }
	  
	  public final native String getFilePath()/*-{return this.filePath}-*/;
	  public final native String getFileId()/*-{return this.fileId}-*/;
	  public final native int getFileSize()/*-{return this.fileSize ? this.fileSize : 0}-*/;
	  public final native String getMimeType()/*-{return this.mimeType}-*/;
	  public final native String getOriginName()/*-{return this.originName}-*/;
	  public final native String getDescription()/*-{return this.description}-*/;
	  public final native String getThumbnail()/*-{return this.thumbnail}-*/;
	  public final native String getExtension()/*-{return this.extension}-*/;
	  public final native boolean isImg()/*-{return this.img ? this.img : false}-*/;
	  public final native boolean isTitleImg()/*-{return this.isTitleImg ? this.isTitleImg : false}-*/;
	  public final native boolean isContentImg()/*-{return this.isContentImg ? this.isContentImg : false}-*/;
	  public final native boolean isAttachment()/*-{return this.isAttachment ? this.isAttachment : false}-*/;
	  
}
