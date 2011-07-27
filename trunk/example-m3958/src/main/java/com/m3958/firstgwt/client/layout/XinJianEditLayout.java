package com.m3958.firstgwt.client.layout;

import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.m3958.firstgwt.client.ICanAcceptConfirm;
import com.m3958.firstgwt.client.IhasLoginEventHandler;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.XinJianDataSource;
import com.m3958.firstgwt.client.event.AttachmentBoxEvent.AttachmentBoxEventType;
import com.m3958.firstgwt.client.jso.ArticleJso;
import com.m3958.firstgwt.client.jso.AssetJso;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.IHasAttachment;
import com.m3958.firstgwt.client.types.IHasRichArea;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.types.XinJianField;
import com.m3958.firstgwt.client.view.gwt.RichTextToolbar;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class XinJianEditLayout extends HasMMRelationEditLayout implements Iview,IHasRichArea,ICanAcceptConfirm,IHasAttachment,IhasLoginEventHandler{
	
	@Inject
	private XinJianDataSource xjds;
	
	@Inject @Named("XINJIAN_EDIT")
	private AssetUploadLayout aul;

	private RichTextArea area;
	
	
	@Inject
	private XinJianAttachmentBox attachmentBox;
	
	private RichTextToolbar areaToolbar;
	
	private Canvas initRichEditor() {
		VLayout vl = new VLayout();
		vl.setWidth100();
		vl.setHeight100();
		vl.setBorder("1px solid blue");
		area = new RichTextArea();
	    area.setSize("100%", "100%");
	    areaToolbar = new RichTextToolbar(area);
	    Grid grid = new Grid(2, 1);
	    grid.setWidth("100%");
	    grid.setHeight("100%");
	    grid.setStyleName("cw-RichText");
	    grid.setWidget(0, 0, areaToolbar);
	    grid.setWidget(1, 0, area);
	    vl.addMember(grid);
	    return vl;
	}
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setWidth100();
		modelForm.setNumCols(4);
		modelForm.setAlign(Alignment.LEFT);
		modelForm.setDataSource(xjds);
	    
	    TextItem titleItem = new TextItem(XinJianField.TITLE.getEname(),XinJianField.TITLE.getCname());
	    titleItem.setRequired(true);
	    titleItem.setWidth("100%");
	    titleItem.setColSpan(4);
	    
	    TextAreaItem contentItem = new TextAreaItem(XinJianField.CONTENT.getEname(),XinJianField.CONTENT.getCname());
	    contentItem.setWidth("100%");
	    contentItem.setHeight(160);
	    contentItem.setTitleOrientation(TitleOrientation.TOP);
	    contentItem.setColSpan(6);

	    
	    TextItem xingmingItem = new TextItem(XinJianField.XINGMING.getEname(),XinJianField.XINGMING.getCname());
	    TextItem xingbieItem = new TextItem(XinJianField.XINGBIE.getEname(),XinJianField.XINGBIE.getCname());
	    DateItem shengriItem = new DateItem(XinJianField.SHENGRI.getEname(),XinJianField.SHENGRI.getCname());
	    shengriItem.setUseTextField(true);
	    shengriItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
	    
	    DateTimeItem publishedAtItem = new DateTimeItem(XinJianField.PUBLISHEDAT.getEname(), XinJianField.PUBLISHEDAT.getCname());
	    publishedAtItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
	    
	    DateTimeItem repliedAtItem = new DateTimeItem(XinJianField.REPLIEDAT.getEname(), XinJianField.REPLIEDAT.getCname());
	    repliedAtItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
	    
	    
	    TextItem shoujiItem = new TextItem(XinJianField.SHOUJI.getEname(),XinJianField.SHOUJI.getCname());
	    TextItem emailItem = new TextItem(XinJianField.EMAIL.getEname(),XinJianField.EMAIL.getCname());
	    TextItem dianhuaItem = new TextItem(XinJianField.DIANHUA.getEname(),XinJianField.DIANHUA.getCname());
	    TextItem dizhiItem = new TextItem(XinJianField.DIZHI.getEname(),XinJianField.DIZHI.getCname());
	    TextItem leixingItem = new TextItem(XinJianField.LEIXING.getEname(),XinJianField.LEIXING.getCname());
	    TextItem youbianItem = new TextItem(XinJianField.YOUBIAN.getEname(),XinJianField.YOUBIAN.getCname());
	    TextItem baomimaItem = new TextItem(XinJianField.BAOMIMA.getEname(),XinJianField.BAOMIMA.getCname());
	    CheckboxItem gongkaiItem = new CheckboxItem(XinJianField.GONGKAI.getEname(),XinJianField.GONGKAI.getCname());
	    
	    
	    HiddenItem attachmentIdsItem = new HiddenItem(SmartParameterName.ATTACHMENTIDS);
	    
	    HiddenItem attachmentJsonItem = new HiddenItem(SmartParameterName.ATTACHMENT_JSON);
	    
	    CheckboxItem auditItem = new CheckboxItem(CommonField.AUDIT.getEname(), CommonField.AUDIT.getCname());
    
	    HiddenItem replyItem = new HiddenItem(XinJianField.REPLY.getEname());

	    modelForm.setFields(xingmingItem,xingbieItem,shengriItem,shoujiItem,emailItem,dianhuaItem,dizhiItem,
	    		leixingItem,youbianItem,baomimaItem,gongkaiItem,auditItem,auiService.getIdHiddenItem(),publishedAtItem,repliedAtItem,
	    		auiService.getVersionHiddenItem(),getTplNameItem(false),titleItem,contentItem,replyItem,attachmentIdsItem,attachmentJsonItem);
	}
	
	@Override
	public void newModel(){
		if(!aservice.isConfirmInProgress()){
			modelForm.editNewRecord();
			area.setHTML("");
			modelForm.setValue(XinJianField.REPLY.getEname(), "");
			button.setTitle(constants.cwFormCreateButtonLabel());
			button.setDisabled(false);
			clearCattachments();
		}
		aservice.setConfirmInProgress(false);
	}
	
	private void clearCattachments(){
		attachmentBox.clearCattachments();
	}
	
	
	@Override
	public void editModel(Record r){
		if(!aservice.isConfirmInProgress()){
			modelForm.editRecord(r);
			area.setHTML(r.getAttributeAsString(XinJianField.REPLY.getEname()));
			button.setTitle(constants.cwFormSaveButtonLabel());
			ArticleJso ajso = (ArticleJso) r.getJsObj();
			attachmentBox.setCattachments(ajso.getAttachments());
		}
	}
	
	@Override
    protected ClickHandler getCreateSaveBtHandler(){
    	ClickHandler bth = new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	if(modelForm.validate(false)){
	        		modelForm.setValue(XinJianField.REPLY.getEname(), area.getHTML());
	        		modelForm.setValue(SmartParameterName.ATTACHMENT_JSON, getAttachmentJsonStr());
	        		modelForm.saveData(new MyDsCallback(){
						@Override
						public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
							String token = bservice.getLatestToken(ViewNameEnum.XINJIAN);
							if(token != null){
								History.newItem(token);
							}
						}},createDsRequest());
	        	}
	        }
	    };
	    return bth;
    }
    
    private String getAttachmentJsonStr(){
    	JSONArray ja = new JSONArray();
    	ja.set(0, attachmentBox.getJsonObject());
    	return ja.toString();
    }
    
	@Override
	public void changeDisplay(ViewAction va,String... paras) {
		if(!initialized){
			initLayout();
			initialized = true;
		}
		relationModelId = auService.getStringIdPara(paras, 0);
		switch (va) {
		case EDIT:
			setEdit(true);
			editRecordId = auService.getStringIdPara(paras, 1);
			RPCManager.sendRequest(auService.getFetchOneRpcRequest(editRecordId, getModelName()), new MyRpcCallback() {
				@Override
				public void afterSuccess(RPCResponse response, Object rawData,
						RPCRequest request, JSONValue data) {
					JSONArray ja = data.isArray();
					if(ja.size() == 1){
						Record r = new Record(ja.get(0).isObject().getJavaScriptObject());
						editModel(r);
					}else{
						SC.say("编辑对象不存在，可能已经删除！");
					}
				}
			});
			break;
		case ADD:
			editRecordId = null;
			setEdit(false);
			newModel();
			break;
		default:
			break;
		}
	}
	
	
	private Canvas initBtLayout() {
		HLayout l = new HLayout();
		l.setAlign(Alignment.CENTER);
	    l.addMember(button);
		return l;
	}


	@Override
	protected String getModelName() {
		return XinJianDataSource.className;
	}


	@Override
	protected void initOtherWidget() {
		addMember(initRichEditor());
		addMember(initBtLayout());
		addMember(aul);
		addMember(attachmentBox);
	}


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.XINJIAN_EDIT;
	}

	@Override
	public void giveConfirmedThing(Record[] rs,String confirmHint) {
		for(Record r :rs){
			AssetJso ajs = (AssetJso) r.getJsObj();
			attachmentBox.addCattachment(ajs);
		}
	}

	@Override
	public void insertContent(List<AssetJso> jsos, AttachmentBoxEventType abe,
			String hint) {
		for(AssetJso aj : jsos){
			switch (abe) {
			case INSERT_IMG:
				areaToolbar.insertImg(aj,hint);
				break;
			default:
				areaToolbar.insertLink(aj);
				break;
			}
		}
	}

	@Override
	public void addAttachment(AssetJso assetJso,UploadFor uploadFor) {
		switch (uploadFor) {
		case ATTACHMENT:
			attachmentBox.addCattachment(assetJso);
			break;
		default:
			break;
		}
	}

	@Override
	public void onLoginEvent(boolean login) {
		if(login)
			attachmentBox.setImgMenuItem(aservice.getWebSite().getImgSizes());
	}

}
