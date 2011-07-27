package com.m3958.firstgwt.client.layout;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.m3958.firstgwt.client.event.AfterFileUploadEvent;
import com.m3958.firstgwt.client.event.AfterFileUploadEventHandler;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.jso.AssetJso;
import com.m3958.firstgwt.client.jso.UploadResponseJso;
import com.m3958.firstgwt.client.service.AssetUploadService;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.client.types.UploadFormField;
import com.m3958.firstgwt.client.types.UploadProgressFields;
import com.m3958.firstgwt.client.types.UploadResponseStatus;
import com.m3958.firstgwt.client.types.UploadWantedResponse;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.StringUtils;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AssetUploadForm extends VLayout{
	
	private DynamicForm uploadForm;
	private Progressbar hBar1 = new Progressbar();
	private Label pl = new Label();
	private String fuTimeStamp;
	
	private UploadFor[] uploadFors;
	
	private ViewNameEnum vname;
	
	private Map<UploadFor, String[]> allowExts;
	
	private VblockService bservice;
	
	private boolean showResult = false;
	
	private Timer t = new Timer(){
		@Override
		public void run() {
        	RPCRequest rr = new RPCRequest();
        	rr.setHttpMethod("GET");
        	rr.setActionURL("/uploadprogress/?" + UploadFormField.FU_TIMESTAMP + "=" + fuTimeStamp);
        	RPCManager.sendRequest(rr,new RPCCallback(){

				@Override
				public void execute(RPCResponse response, Object rawData,
						RPCRequest request) {
					try {
						if(response.getStatus() == RPCResponse.STATUS_SUCCESS){
							inProgress((String) rawData);
						}
					} catch (Exception e) {
					}
				}});
			}};
	
	private String formDomId = "fdi" + new Date().getTime();
	
	final IButton button = new IButton("开始上传");
	
	final IButton stopBt = new IButton("停止上传");
	
	final IButton hideBt = new IButton("关闭");
	
	private AssetUploadService uploadService;
	
	private HandlerRegistration reg;

	public AssetUploadForm(MyEventBus eventBus,VblockService bservice,AssetUploadService uploadService,ViewNameEnum vname,
							UploadFor[] uploadFors,Map<UploadFor, String[]> allowExts){
		this.uploadFors = uploadFors;
		this.vname = vname;
		this.allowExts = allowExts;
		this.uploadService = uploadService;
		reg = eventBus.addHandler(AfterFileUploadEvent.TYPE, afeh);
		this.bservice = bservice;
		initContent();
		initStatus();
	}
	
	protected AssetJso curAsset;
	
	private AfterFileUploadEventHandler afeh = new AfterFileUploadEventHandler() {
		@Override
		public void onAfterUpload(AfterFileUploadEvent event) {
			tcancel();
			UploadResponseJso jso = event.getUploadResponse();
			if(!fuTimeStamp.equals(jso.getFuTimeStamp()))return;
			if(jso.getStatus() == UploadResponseStatus.SUCCESS){
				curAsset = uploadService.getFirstAsset(jso);
				reg.removeHandler();
				removeFromParent();
				uninitialize();
				if(showResult){
					Label l = new Label(curAsset.getOriginName() + "("+ curAsset.getMimeType() +")");
					addMember(l);
					setVisibleMember(l);
					setHeight(20);
				}else{
					hide();
					removeFromParent();
				}
			}else{
				uploadForm.clearValue(UploadFormField.FILE.toString());
				hBar1.setPercentDone(0);
				pl.setContents("上传失败！");
				button.setDisabled(false);
				stopBt.setDisabled(true);
			}

		}
	};
	
	private void uninitialize(){
		t = null;
		hBar1 = null;
		pl = null;
		uploadForm = null;
		afeh = null;
	}
	
	
	private void initContent() {
		setWidth100();
		setHeight(100);
		setMembersMargin(5);
		addMember(initAssetFormLayout());
		addMember(intProgress());
		addMember(initBtLayout());
	}
	
	private IFrameElement ife;
	
	private Canvas initAssetFormLayout() {
		uploadForm = new DynamicForm();
		uploadForm.setNumCols(4);
		ife = Document.get().createIFrameElement();
		ife.setId(formDomId);
		ife.setName(formDomId);
		ife.getStyle().setVisibility(Visibility.HIDDEN);
		Document.get().getBody().appendChild(ife);
		uploadForm.setTarget(formDomId);
		uploadForm.setCanSubmit(true);
		uploadForm.setWidth100();
		uploadForm.setHeight100();
		uploadForm.setEncoding(Encoding.MULTIPART);
		UploadItem uploadItem = new UploadItem(UploadFormField.FILE.toString(),UploadFormField.FILE.getCname());
		uploadItem.setColSpan(4);
		TextItem descriptionItem = new TextItem(UploadFormField.DESCRIPTION.toString(),UploadFormField.DESCRIPTION.getCname());
		LinkedHashMap<String, String> vm = new LinkedHashMap<String, String>();
		for(UploadFor fuf:uploadFors){
			vm.put(fuf.toString(), fuf.getCname());
		}
		
		SelectItem uploadForItem = new SelectItem(UploadFormField.UPLOAD_FOR.toString(),UploadFormField.UPLOAD_FOR.getCname());
		uploadForItem.setValueMap(vm);
		uploadForItem.setDefaultToFirstOption(true);
		uploadItem.setRequired(true);

		
		HiddenItem assetIdItem = new HiddenItem(UploadFormField.ASSET_ID.toString());
		
		uploadForm.setItems(uploadItem,uploadForItem,assetIdItem,descriptionItem);
		return uploadForm;
	}
	
	private Canvas intProgress() {
		HLayout layout = new HLayout();
		HLayout space = new HLayout();
		space.setWidth(22);
		layout.setHeight(5);
		hBar1.setBreadth(5);
		hBar1.setLength(378);
		hBar1.setVertical(false);
		layout.addMember(space);
		layout.addMember(hBar1);
		pl.setWidth(200);
		layout.addMember(pl);
		hBar1.setPercentDone(0);
		return layout;
	}
	
	private UploadFor getCurUploadFor(){
		return UploadFor.valueOf(uploadForm.getValueAsString(UploadFormField.UPLOAD_FOR.toString().split("\\.")[0]));
	}
	
	private boolean allow(String ext){
		for(String s:allowExts.get(getCurUploadFor())){
			if(s.equalsIgnoreCase(ext)){
				return true;
			}
		}
		return false;
	}
	
	private String getAllowdExtStr(UploadFor curUploadFor){
		String result = "";
		for(String s: allowExts.get(curUploadFor)){
			result = result + s + ",";
		}
		return result;
	}
	
	private HLayout initBtLayout() {
		    button.addClickHandler(new ClickHandler() {
		        public void onClick(ClickEvent event) {
		        	if(uploadForm.validate(false)){
						String fn = uploadForm.getValueAsString(UploadFormField.FILE.toString());
						if(fn == null || fn.isEmpty()){
							SC.say("请检查上传的文件类型,允许的文件类型是：" + getAllowdExtStr(getCurUploadFor()));
						}
						String[] ss = fn.split("\\.");
						String ext = ss[ss.length - 1];
						if(!allow("*")){
							if(!allow(ext)){
								SC.say("请检查上传的文件类型,允许的文件类型是："+ getAllowdExtStr(getCurUploadFor()));
								return;
							}
						}
						setExtraFormAction();
						tcancel();
						uploadForm.submitForm();
						startTimer();
						pl.setContents("准备上传......");
						button.setDisabled(true);
						stopBt.setDisabled(false);
		        	}
		        }
		    });
    
	    HLayout hl = new HLayout();
	    hl.setMembersMargin(5);
	    Label l = new Label("");
	    l.setWidth(15);
	    hl.addMember(l);
	    hl.addMember(button);
	    
//	    java.lang.Object
//	    com.google.gwt.core.client.JavaScriptObject
//	        com.google.gwt.dom.client.Node
//	            com.google.gwt.dom.client.Element
//	                com.google.gwt.user.client.Element
	    
	    
//	    java.lang.Object
//	    com.google.gwt.core.client.JavaScriptObject
//	        com.google.gwt.dom.client.Node
//	            com.google.gwt.dom.client.Element
//	                com.google.gwt.dom.client.IFrameElement
	    
//	    com.google.gwt.user.client.Element
//	    An opaque handle to a native DOM Element. An Element cannot be created directly. 
//	    Instead, use the Element type when returning a native DOM element from JSNI methods. 
//	    An Element passed back into JSNI becomes the original DOM element the Element was created from, 
//	    and can be accessed in JavaScript code as expected. This is typically done by calling methods in the DOM class.

	    //DOM.getElemetnById返回的是 com.google.gwt.user.client.Element
	    
//	    java.lang.Object
//	    com.google.gwt.core.client.JavaScriptObject
//	        com.google.gwt.dom.client.Node
//	            com.google.gwt.dom.client.Element
//	                com.google.gwt.dom.client.IFrameElement
    
	    
	    stopBt.setDisabled(true);
	    stopBt.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				userCanceled();
			}});
	    hl.addMember(stopBt);
	    
	    hideBt.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				hide();
				reg.removeHandler();
				removeFromParent();
			}});
	    hl.addMember(hideBt);
		return hl;
	}
	
	private void userCanceled(){
		IFrameElement itarget = IFrameElement.as(DOM.getElementById(formDomId));
		itarget.setSrc("/blank.html");
		tcancel();
		uploadForm.clearValue(UploadFormField.FILE.toString());
		
		pl.setContents("用户终止了上传！");
		hBar1.setPercentDone(0);
		button.setDisabled(false);
		stopBt.setDisabled(true);
	}
	
	private void initStatus(){
		hBar1.setPercentDone(0);
		pl.setContents("");
		button.setDisabled(false);
		stopBt.setDisabled(true);
	}
	
	

	

	private void inProgress(String rawData){
		JSONObject jo = JSONParser.parseStrict(rawData).isObject();
		Double bytesRead = jo.get(UploadProgressFields.BYTES_READ).isNumber().doubleValue();
		Double totalBytes = jo.get(UploadProgressFields.TOTAL_BYTES).isNumber().doubleValue();
		Double itemNumd = jo.get(UploadProgressFields.ITEM_NUM).isNumber().doubleValue();
		int itemNum = itemNumd.intValue();
		Double percent = (bytesRead/totalBytes) * 100;
		JSONArray ja = jo.get(UploadProgressFields.FILE_NAMES).isArray();
		String fn = "";
		if(ja.size() == itemNum){
			fn = ja.get(itemNum - 1).isString().stringValue();
			pl.setContents( fn + "正在上传");
		}
		hBar1.setPercentDone(percent.intValue());
	}
	
	
	private void setExtraFormAction(){
		Map<String,String> params = new HashMap<String, String>();
		params.put(UploadFormField.UPLOAD_FOR.toString(), uploadForm.getValueAsString(UploadFormField.UPLOAD_FOR.toString()));
		params.put(UploadFormField.FU_TIMESTAMP.toString(), createFuTimeStamp());
		params.put(UploadFormField.WANTED_RESPONSE.toString(), UploadWantedResponse.PJSON.toString());
		params.put(UploadFormField.VIEWNAME.toString(), vname.toString());
		params.put(CommonField.SITE_ID.getEname(), bservice.getSiteId());
		String url = StringUtils.addUrlParams("", params);
		uploadForm.setAction(SmartConstants.UPLOAD_URL + "/?" + url);
	}
	
	private String createFuTimeStamp(){
		fuTimeStamp = (new Date()).getTime() + "";
		return fuTimeStamp;
	}
	
	private void startTimer(){
		hBar1.setPercentDone(0);
		t.scheduleRepeating(2000);
	}

	private void tcancel(){
		if(t != null)t.cancel();
	}
}
