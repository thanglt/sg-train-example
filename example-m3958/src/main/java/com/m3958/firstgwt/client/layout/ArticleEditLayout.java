package com.m3958.firstgwt.client.layout;

import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.m3958.firstgwt.client.ICanAcceptConfirm;
import com.m3958.firstgwt.client.IHasSiteChangHandler;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.ArticleDataSource;
import com.m3958.firstgwt.client.datasource.SectionDataSource;
import com.m3958.firstgwt.client.event.AttachmentBoxEvent.AttachmentBoxEventType;
import com.m3958.firstgwt.client.jso.ArticleJso;
import com.m3958.firstgwt.client.jso.AssetJso;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.ArticleField;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.IHasAttachment;
import com.m3958.firstgwt.client.types.IHasRichArea;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.view.gwt.RichTextToolbar;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class ArticleEditLayout extends HasMMRelationEditLayout implements Iview,IHasRichArea,ICanAcceptConfirm,IHasAttachment,IHasSiteChangHandler{
	
	@Inject
	private ArticleDataSource ads;
	
	@Inject
	private SectionDataSource sds;
	
	
//	@Inject @Named("ARTICLE_EDIT")
//	private AssetUploadLayout aul;

//	private RichTextArea area;
//	
//	@Inject
//	private ArticleTitleImgBox titleImgBox;
//	
//	@Inject
//	private ArticleContentImgBox contentImgBox;
//	
//	@Inject
//	private ArticleAttachmentBox attachmentBox;
//	
//	private RichTextToolbar areaToolbar;
	
	private ArticleJso editingArticle = null;
	
	private JSONObject attachmentsJsonObject = new JSONObject();
	
	private String attachmentsJsonStr;
	
	public ArticleJso getEditingArticle() {
		return editingArticle;
	}

	public void setEditingArticle(ArticleJso editingArticle) {
		this.editingArticle = editingArticle;
	}

//	private Canvas initRichEditor() {
//		VLayout vl = new VLayout();
//		vl.setWidth("95%");
//		vl.setHeight100();
//		vl.setBorder("1px solid grey");
//		area = new RichTextArea();
//	    area.setSize("800px", "400px");
//	    areaToolbar = new RichTextToolbar(area);
//	    Grid grid = new Grid(2, 1);
//	    grid.setWidth("100%");
//	    grid.setHeight("100%");
//	    grid.setStyleName("cw-RichText");
//	    grid.setWidget(0, 0, areaToolbar);
//	    grid.setWidget(1, 0, area);
//	    vl.addMember(grid);
//	    return vl;
//	}
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setWidth100();
		modelForm.setNumCols(8);
		modelForm.setAlign(Alignment.LEFT);
		modelForm.setDataSource(ads);
	    modelForm.setHoverWidth(200);
	    TextItem titleItem = new TextItem(ArticleField.TITLE.getEname(),ArticleField.TITLE.getCname());
	    titleItem.setRequired(true);
	    titleItem.setWidth("100%");
	    titleItem.setColSpan(8);
	    
	    HiddenItem attachmentIdsItem = new HiddenItem(SmartParameterName.ATTACHMENTIDS);
	    
	    HiddenItem attachmentJsonItem = new HiddenItem(SmartParameterName.ATTACHMENT_JSON);
	    
		SelectItem sitem = new SelectItem(ArticleField.DEFAULT_SECTION_ID.getEname(), ArticleField.DEFAULT_SECTION_ID.getCname());
	    sitem.setOptionDataSource(sds);
	    sitem.setValueField(CommonField.ID.getEname());
	    sitem.setDisplayField(CommonField.NAME.getEname());
	    sitem.setAnimatePickList(true);
	    sitem.setTextMatchStyle(TextMatchStyle.EXACT);
	    
		ComboBoxItem flagItem = new ComboBoxItem(ArticleField.FLAG.getEname(), ArticleField.FLAG.getCname());
		String s0 = aservice.getWebSite().getArticleFlags();
		String[] ss0 = s0.split(",");
		flagItem.setValueMap(ss0);

		ComboBoxItem displayAuthorItem = new ComboBoxItem(ArticleField.DISPLAY_AUTHOR.getEname(), ArticleField.DISPLAY_AUTHOR.getCname());
		String s = aservice.getWebSite().getDisplayAuthors();
		String[] ss = s.split(",");
		displayAuthorItem.setValueMap(ss);
		
		TextItem kvItem = new TextItem(CommonField.KEY_VALUES.getEname(), CommonField.KEY_VALUES.getCname());
		kvItem.setColSpan(8);
		kvItem.setWidth("100%");
		
		TextItem tagsItem = new TextItem(CommonField.TAG_NAMES.getEname(), CommonField.TAG_NAMES.getCname());
		tagsItem.setColSpan(8);
		tagsItem.setWidth("100%");
		tagsItem.setPrompt("空格分隔");
		
		ComboBoxItem sourceItem = new ComboBoxItem(ArticleField.SOURCE.getEname(), ArticleField.SOURCE.getCname());
		String s1 = aservice.getWebSite().getArticleSources();
		String[] ss1 = s1.split(",");
		sourceItem.setValueMap(ss1);
	    
	    CheckboxItem auditItem = new CheckboxItem(CommonField.AUDIT.getEname(), CommonField.AUDIT.getCname());
	    
	    
	    TextAreaItem excerptItem = new TextAreaItem(ArticleField.EXCERPT.getEname(),ArticleField.EXCERPT.getCname());
	    excerptItem.setWidth("100%");
	    excerptItem.setHeight(40);
	    excerptItem.setColSpan(8);
	    
	    DateTimeItem publishedAtItem = new DateTimeItem(ArticleField.PUBLISHED_AT.getEname(), ArticleField.PUBLISHED_AT.getCname());
	    publishedAtItem.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATETIME);
/*	    
	    FormItemIcon icon = new FormItemIcon(); 
	    icon.setSrc("/images/icons/16/help.png"); 
	    
	    CheckboxItem showSource = new CheckboxItem("showSource");
	    showSource.setTitle("显示源");  
	    showSource.setRedrawOnChange(true);  
	    showSource.setWidth(50);  
	    showSource.setValue(false);
	    showSource.setColSpan(8);
	    showSource.setIcons(icon);
	    showSource.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if((Boolean)event.getValue()){
					modelForm.setValue(ArticleField.CONTENT.getEname(), area.getHTML());
				}else{
					area.setHTML(modelForm.getValueAsString(ArticleField.CONTENT.getEname()));
				}
				
			}
		});
	    showSource.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				SC.say("显示源打开的时候，内容从下面复制到上面，关闭的时候从上面复制到下面。所以在源里面修改之后，请关闭源。然后继续在下面修改。");
				
			}
		});
*/	    
//	    HiddenItem contentItem = new HiddenItem(ArticleField.CONTENT.getEname());
	    HiddenItem contentItem = new HiddenItem(ArticleField.CONTENT.getEname());
/*	    
	    contentItem.setColSpan(8);
	    contentItem.setHeight(100);
	    contentItem.setWidth(800);
*/	    
	    SelectItem commentLevelItem = new SelectItem(CommonField.COMMENT_LEVEL.getEname(), CommonField.COMMENT_LEVEL.getCname());
        LinkedHashMap<String, String> vm = new LinkedHashMap<String, String>();
        vm.put("0", "继承");
        vm.put("1", "允许");
        vm.put("2", "禁止");
        commentLevelItem.setDefaultToFirstOption(true);
        commentLevelItem.setValueMap(vm);
	    
//	    contentItem.setShowIfCondition(new FormItemIfFunction() {  
//            public boolean execute(FormItem item, Object value, DynamicForm form) {
//            	if(form.getValue("showSource") == null || !(Boolean)form.getValue("showSource")){
//            		
//            		return false;
//            	}else{
//            		
//            		return true;
//            	}
//            }  
//        }); 

	    modelForm.setFields(auiService.getIdDisableItem(false),auiService.getVersionHiddenItem(), titleItem,excerptItem,kvItem,tagsItem,sitem,getTplNameItem(false),auditItem,auiService.getProtectLevelItem(),publishedAtItem,flagItem,displayAuthorItem,commentLevelItem,sourceItem,/*showSource,*/contentItem,attachmentIdsItem,attachmentJsonItem);
	}
	
	private Criteria getPickerCriteria(){
		Criteria c = new Criteria();
		c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME,ArticleField.SECTIONS.getEname());
		c.addCriteria(SmartParameterName.RELATION_MODEL_NAME,ArticleDataSource.className);
		c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.MANY_TO_MANY.toString());
		c.addCriteria(SmartParameterName.RELATION_MODEL_ID, editRecordId);
		c.addCriteria(SmartParameterName.IS_MASTER,false);
	    return c;
	}
	
	public void setAreaHtml(String html){
//		area.setHTML(html);
		modelForm.setValue(ArticleField.CONTENT.getEname(), html);
	}
//
	public String getAreaHtml(){
//		return area.getHTML();
		return modelForm.getValueAsString(ArticleField.CONTENT.getEname());		
	}

	
	@Override
	public void newModel(){
		if(!aservice.isConfirmInProgress()){
			modelForm.editNewRecord();
//			area.setHTML("");
			modelForm.setValue(ArticleField.CONTENT.getEname(), "");
			button.setTitle(constants.cwFormCreateButtonLabel());
			button.setDisabled(false);
//			clearCattachments();
		}
		aservice.setConfirmInProgress(false);
		getSelectItem().hide();
	}
	
//	private void clearCattachments(){
//		titleImgBox.clearCattachments();
//		contentImgBox.clearCattachments();
//		attachmentBox.clearCattachments();
//	}
	
	
	@Override
	public void editModel(Record r){
		if(!aservice.isConfirmInProgress()){
			modelForm.editRecord(r);
			attachmentsJsonObject.put("titleImg", new JSONObject(editingArticle.getTitleImg()));
			attachmentsJsonObject.put("contentImgs", new JSONArray(editingArticle.getContentImgs()));
			attachmentsJsonObject.put("attachments", new JSONArray(editingArticle.getAttachments()));
			
//			area.setHTML(r.getAttributeAsString(ArticleField.CONTENT.getEname()));
			button.setTitle(constants.cwFormSaveButtonLabel());
//			ArticleJso ajso = (ArticleJso) r.getJsObj();
//			titleImgBox.setCattachment(ajso.getTitleImg());
//			contentImgBox.setCattachments(ajso.getContentImgs());
//			attachmentBox.setCattachments(ajso.getAttachments());
			getSelectItem().show();
		}
		aservice.setConfirmInProgress(false);
		button.setTitle(constants.cwFormSaveButtonLabel());
	}
	
	@Override
    protected ClickHandler getCreateSaveBtHandler(){
    	ClickHandler bth = new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	if(modelForm.validate(false)){
//	        		if(modelForm.getValue("showSource") == null || !(Boolean)modelForm.getValue("showSource")){
//	        			modelForm.setValue(ArticleField.CONTENT.getEname(), area.getHTML());
//	        		}
	        		modelForm.setValue(SmartParameterName.ATTACHMENT_JSON, getAttachmentJsonStr());
	        		modelForm.saveData(new MyDsCallback(){
						@Override
						public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
							String token = bservice.getLatestToken(ViewNameEnum.ARTICLE);
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
//    	JSONArray ja = new JSONArray();
//    	ja.set(0, titleImgBox.getJsonObject());
//    	ja.set(1, contentImgBox.getJsonObject());
//    	ja.set(2, attachmentBox.getJsonObject());
//    	return ja.toString();
    	return attachmentsJsonStr;
    }
//    
    public JavaScriptObject getAttachmentJson(){
//    	JSONArray ja = new JSONArray();
//    	ja.set(0, titleImgBox.getJsonObject());
//    	ja.set(1, contentImgBox.getJsonObject());
//    	ja.set(2, attachmentBox.getJsonObject());
//    	return ja.getJavaScriptObject();
    	return attachmentsJsonObject.getJavaScriptObject();
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
						editingArticle = (ArticleJso) ja.get(0).isObject().getJavaScriptObject();
						editModel(new Record(editingArticle));
					}else{
						SC.say("编辑对象不存在，可能已经删除！");
					}
				}
			});
			SelectItem si = getSelectItem();
			si.setPickListCriteria(getPickerCriteria());
			si.fetchData();
			break;
		case ADD:
			if(relationModelId == null || SmartConstants.NONE_EXIST_MODEL_ID_STR.equals(relationModelId)){
				SC.warn("请先选择目录！");
			}
			editRecordId = null;
			editingArticle = null;
			setEdit(false);
			newModel();
			break;
		default:
			break;
		}
	}
	
	private SelectItem getSelectItem(){
		return (SelectItem)modelForm.getItem(ArticleField.DEFAULT_SECTION_ID.getEname());
	}
	
	private Canvas initBtLayout() {
		HLayout l = new HLayout();
		l.setAlign(Alignment.CENTER);
	    l.addMember(button);
		return l;
	}


	@Override
	protected String getModelName() {
		return ArticleDataSource.className;
	}
	
	private Label initPopUpLabel(){
		Label l = new Label();
		l.setWidth100();
		l.setAlign(Alignment.CENTER);
		l.setHeight(35);
		l.setContents("<span id=\"openKindEditorWindow\" style=\"cursor:pointer;color:blue;border:1px solid blue;\" onclick=\"openwindow('/ceshidx/beopen.html','kindeditor',850,650);\">编辑文章内容</span>");
		return l;
	}

	@Override
	protected void initOtherWidget() {
		addMember(initPopUpLabel());
		addMember(initBtLayout());
//		addMember(initRichEditor());
//		addMember(aul);
//		addMember(titleImgBox);
//		addMember(contentImgBox);
//		addMember(attachmentBox);
	}


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.ARTICLE_EDIT;
	}

	@Override
	public void giveConfirmedThing(Record[] rs,String confirmHint) {
//		for(Record r :rs){
//			AssetJso ajs = (AssetJso) r.getJsObj();
//			if(TitleImgBox.class.getName().equals(confirmHint)){
//				titleImgBox.addCattachment(ajs);
//				contentImgBox.addCattachment(ajs);
//				attachmentBox.addCattachment(ajs);
//			}else if(AttachmentBox.class.getName().equals(confirmHint)){
//				attachmentBox.addCattachment(ajs);
//			}else if(ContentImgBox.class.getName().equals(confirmHint)){
//				contentImgBox.addCattachment(ajs);
//				attachmentBox.addCattachment(ajs);
//			}
//		}
	}

	@Override
	public void insertContent(List<AssetJso> jsos, AttachmentBoxEventType abe,
			String hint) {
//		for(AssetJso aj : jsos){
//			switch (abe) {
//			case INSERT_IMG:
//				areaToolbar.insertImg(aj,hint);
//				break;
//			default:
//				areaToolbar.insertLink(aj);
//				break;
//			}
//		}
	}
	
//	public void setAttaches(JsArray<AssetJso> jsa){
//		AssetJso titlei = null;
//		JsArray<AssetJso> contenti = (JsArray<AssetJso>) JsArray.createArray();
//		JsArray<AssetJso> attai = (JsArray<AssetJso>) JsArray.createArray();
//		for(int i=0;i<jsa.length();i++){
//			if(jsa.get(i).isTitleImg()){
//				titlei = jsa.get(i);
//			}
//			if(jsa.get(i).isContentImg()){
//				contenti.push(jsa.get(i));
//			}
//			if(jsa.get(i).isAttachment()){
//				attai.push(jsa.get(i));
//			}
//		}
//		titleImgBox.setCattachment(titlei);
//		contentImgBox.setCattachments(contenti);
//		attachmentBox.setCattachments(attai);
		
//	}
	public void setAttaches(String js){
		attachmentsJsonStr = js;
	}

	@Override
	public void addAttachment(AssetJso assetJso,UploadFor uploadFor) {
//		switch (uploadFor) {
//		case TITLE_IMG:
//			titleImgBox.addCattachment(assetJso);
//			contentImgBox.addCattachment(assetJso);
//			attachmentBox.addCattachment(assetJso);
//			break;
//		case CONTENT_IMG:
//			contentImgBox.addCattachment(assetJso);
//			attachmentBox.addCattachment(assetJso);
//			break;
//		case ATTACHMENT:
//			attachmentBox.addCattachment(assetJso);
//			break;
//		default:
//			break;
//		}
		
	}


	@Override
	public void onSiteChange() {
//		titleImgBox.setImgMenuItem(aservice.getWebSite().getImgSizes());
//		contentImgBox.setImgMenuItem(aservice.getWebSite().getImgSizes());
//		attachmentBox.setImgMenuItem(aservice.getWebSite().getImgSizes());
//
//		if(modelForm != null){
//			ComboBoxItem authorItem = (ComboBoxItem) modelForm.getField(ArticleField.DISPLAY_AUTHOR.getEname());
//			String s = aservice.getWebSite().getDisplayAuthors();
//			String[] ss = s.split(",");
//			authorItem.setValueMap(ss);
//			
//			ComboBoxItem sourceItem = (ComboBoxItem) modelForm.getField(ArticleField.SOURCE.getEname());
//			String s1 = aservice.getWebSite().getArticleSources();
//			String[] ss1 = s1.split(",");
//			sourceItem.setValueMap(ss1);
//			
//			ComboBoxItem flagItem = (ComboBoxItem) modelForm.getField(ArticleField.FLAG.getEname());
//			String s0 = aservice.getWebSite().getArticleFlags();
//			String[] ss0 = s0.split(",");
//			flagItem.setValueMap(ss0);
//		}
	}


}
