package com.m3958.firstgwt.client.layout;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.m3958.firstgwt.client.IhasUpload;
import com.m3958.firstgwt.client.types.UploadFor;


public class GwtFileUploadLayout extends Composite {
	
	private String fid;
	private String askTimeStamp;

	public GwtFileUploadLayout(IhasUpload hasUpload,String fid,String askTimeStamp) {
		this.fid = fid;
		this.askTimeStamp = askTimeStamp;
		FormPanel formPanel = new FormPanel();
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		initWidget(formPanel);
		formPanel.setSize("450", "100");
		
		Label yongchu = new Label("用处");
		ListBox yongchuSelection = new ListBox();
		
		for(UploadFor uf : hasUpload.getUploadFors()){
			yongchuSelection.addItem(uf.getCname(), uf.toString());
		}
		
		Label miaoshu = new Label("描述");
		TextBox miaoshuBox = new TextBox();
		
		Button uploadBt = new Button("上传");
		uploadBt.setWidth("100");
		
		FlexTable flexTable = new FlexTable();
		flexTable.setBorderWidth(1);
		formPanel.setWidget(flexTable);
		flexTable.setSize("100%", "100%");
		
		FileUpload fileUpload = new FileUpload();
		flexTable.setWidget(0, 0, fileUpload);
		fileUpload.setWidth("400");
		flexTable.getCellFormatter().setHeight(0, 0, "25px");
		flexTable.setWidget(1, 0, yongchu);
		flexTable.setWidget(1, 1, yongchuSelection);
		yongchuSelection.setWidth("80");
		flexTable.setWidget(1, 2, miaoshu);
		flexTable.setWidget(1, 3,miaoshuBox );
		miaoshuBox.setWidth("154");
		flexTable.setWidget(2, 0, uploadBt);
		flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 4);
		flexTable.getFlexCellFormatter().setColSpan(2, 0, 4);
	}
}
