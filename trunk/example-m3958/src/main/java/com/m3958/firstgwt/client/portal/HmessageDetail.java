package com.m3958.firstgwt.client.portal;

import com.m3958.firstgwt.client.types.AssetField;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.HmessageField;
import com.m3958.firstgwt.client.types.HmessageReceiverField;
import com.m3958.firstgwt.client.types.UserField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class HmessageDetail extends Window{
	
	Canvas ca = new Canvas();
	
	private HmessagePorlet hp;
	
	public HmessageDetail(HmessagePorlet hp){
		this.hp = hp;
		setWidth(600);
		setHeight(450);
		setHeaderControls(HeaderControls.HEADER_LABEL,HeaderControls.MINIMIZE_BUTTON,HeaderControls.MAXIMIZE_BUTTON,HeaderControls.CLOSE_BUTTON);
		centerInPage();
		ca.setWidth100();
		ca.setHeight100();
		setAutoSize(true);
		
		addItem(ca);
		addItem(initBt());
	}
	
	private Canvas initBt() {
		HLayout h = new HLayout(5);
		IButton closebt = new IButton("关闭");
		closebt.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		IButton replyBt = new IButton("回复");
		
		replyBt.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hp.replyHmessage(hm,sender,attas);
				hide();
			}
		});
		
		h.addMember(closebt);
		h.addMember(replyBt);
		return h;
	}


	private Record hm;
	private Record sender;
	private Record[] attas;
	

	public void showMessage(Record record) {
		hm = new Record(record.getAttributeAsJavaScriptObject(HmessageReceiverField.HMESSAGE.getEname()));
		attas = hm.getAttributeAsRecordArray(HmessageField.ATTACHMENTS.getEname());
		sender = new Record(hm.getAttributeAsJavaScriptObject(HmessageField.SENDER.getEname()));
		setTitle("来自：" + sender.getAttributeAsString(UserField.LOGIN_NAME.getEname()));
		String t = hm.getAttributeAsString(HmessageField.TITLE.getEname());
		String caContent = "<div align=\"center\">" + t + "</div><p></p><p></p>";
		caContent += "<div>" + hm.getAttributeAsString(HmessageField.MESSAGE.getEname()) +  "</div><p></p><p></p>";
		
		caContent += "<div>" + getAttaString(attas) + "</div>";
		ca.setContents(caContent);
		show();
		
	}
	
	//"/assetFeed/?assetId=" + curAsset.getId()

	private String getAttaString(Record[] attas) {
		String html = "";
		for(int i=0;i< attas.length;i++){
			html += "<a href=\"/assetfeed/?assetId=" + attas[i].getAttributeAsString(CommonField.ID.getEname()) + "\" target=\"_blank\">"  + attas[i].getAttributeAsString(AssetField.ORIGIN_NAME.getEname()) + "</a><br/>";
		}
		return html;
	}
}
