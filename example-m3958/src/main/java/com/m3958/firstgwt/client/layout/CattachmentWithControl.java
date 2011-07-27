package com.m3958.firstgwt.client.layout;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONObject;
import com.google.inject.Inject;
import com.m3958.firstgwt.client.event.AttachmentBoxEvent;
import com.m3958.firstgwt.client.event.AttachmentBoxEvent.AttachmentBoxEventType;
import com.m3958.firstgwt.client.event.RequestSomeEvent;
import com.m3958.firstgwt.client.event.RequestSomeEvent.AskForWhat;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.jso.AssetJso;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

public abstract class CattachmentWithControl extends VLayout{
	
	@Inject
	protected MyEventBus eventBus;
	
	
	protected CattachmentContainer cc;

	protected HLayout controlLayout = new HLayout(5);
	protected ToolStripButton removeBt = new ToolStripButton("删除");
	protected ToolStripButton insertLinkBt = new ToolStripButton("插入链接");
	protected ToolStripMenuButton insertImgBt = new ToolStripMenuButton("插入图片");;
	
	protected ToolStripButton selectBt = new ToolStripButton("选择附件");
	
	protected ToolStrip toolStrip;
	
	protected abstract String getBoxType();
	protected abstract Label getBoxLabel();
	protected abstract ViewNameEnum getSrctViewName();
	
	public void setImgMenuItem(String imgSizes){
        Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(3);
        String[] ss = imgSizes.split(",");
        MenuItem[] miis = new MenuItem[ss.length + 1];
        int i=0;
        for(;i<ss.length;i++){
        	MenuItem mi = new MenuItem(ss[i]);
        	miis[i] = mi;
        }
        miis[i] = new MenuItem("原图");
        menu.setItems(miis);
		menu.addItemClickHandler(new ItemClickHandler() {
			@Override
			public void onItemClick(ItemClickEvent event) {
				AttachmentBoxEvent abe = new AttachmentBoxEvent(getSrctViewName(),AttachmentBoxEventType.INSERT_IMG,getCheckedJsos(),event.getItem().getTitle());
				eventBus.fireEvent(abe);
			}
		});
        insertImgBt.setMenu(menu);
	}
	
	public CattachmentWithControl(CattachmentContainer cc){
		this.cc = cc;
		init();
	}
	
	public JSONObject getJsonObject(){
		JSONObject jo = new JSONObject();
		jo.put(getBoxType(), cc.getJsonArray());
		return jo;
	}
	
	public void init(){
		addMember(getBoxLabel());
		addMember(cc);
		controlLayout.setHeight(20);
		controlLayout.addMember(initStrip());
		changeBtStatus(true);
		addMember(controlLayout);
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean hasChecked = false;
				for(Cattachment ca :cc.getCattachments()){
					if(ca.isChecked()){
						hasChecked = true;
					}
				}
				if(hasChecked){
					changeBtStatus(false);
				}else{
					changeBtStatus(true);
				}
			}
		});
		removeBt.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				cc.removeSelectedCattachment();
			}
		});
		
		selectBt.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RequestSomeEvent rse = new RequestSomeEvent(getSrctViewName(),AskForWhat.FOLDER_ASSET,getBoxType());
				eventBus.fireEvent(rse);
			}
		});
		
		insertLinkBt.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AttachmentBoxEvent abe = new AttachmentBoxEvent(getSrctViewName(),AttachmentBoxEventType.INSERT_LINK,getCheckedJsos(),"");
				eventBus.fireEvent(abe);
			}
		});
	}
	
	private Canvas initStrip() {
		toolStrip = new ToolStrip();
		toolStrip.setWidth(200);
		toolStrip.addMenuButton(insertImgBt);
		toolStrip.addButton(insertLinkBt);
		toolStrip.addButton(removeBt);
		toolStrip.addButton(selectBt);
		return toolStrip;
	}

	private void changeBtStatus(boolean disable){
		removeBt.setDisabled(disable);
		insertImgBt.setDisabled(disable);
		insertLinkBt.setDisabled(disable);
	}
	
	public void addCattachment(AssetJso aj){
		cc.addCattachment(aj);
	}
	
	public void setCattachment(AssetJso a){
		cc.setCattachment(a);
	}
	
	public void addCattachments(JsArray<AssetJso> ajs){
		cc.addCattachments(ajs);
	}
	
	public void setCattachments(JsArray<AssetJso> ajs){
		cc.setCattachments(ajs);
	}

	
	public void removeSelectedCattachments(){
		cc.removeSelectedCattachment();
	}
	
	public void clearCattachments(){
		cc.clearCattachments();
	}
	
	public List<Cattachment> getChecked(){
		return cc.getChecked();
	}

	public List<AssetJso> getCheckedJsos(){
		List<AssetJso> jsos = new ArrayList<AssetJso>();
		for(Cattachment ca : getChecked()){
			jsos.add(ca.getAssetJso());
		}
		return jsos;
	}
}
