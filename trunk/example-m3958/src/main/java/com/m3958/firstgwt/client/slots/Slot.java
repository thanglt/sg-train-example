package com.m3958.firstgwt.client.slots;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.AppSlot;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.IHasSiteChangHandler;
import com.m3958.firstgwt.client.IhasLoginEventHandler;
import com.m3958.firstgwt.client.Vblock;
import com.m3958.firstgwt.client.Vblock.BlockName;
import com.m3958.firstgwt.client.ViewService;
import com.m3958.firstgwt.client.datasource.WebSiteDataSource;
import com.m3958.firstgwt.client.event.AfterFileUploadEvent;
import com.m3958.firstgwt.client.event.AfterFileUploadEventHandler;
import com.m3958.firstgwt.client.event.ApplicationChangeEvent;
import com.m3958.firstgwt.client.event.ApplicationChangeEventHandler;
import com.m3958.firstgwt.client.event.AttachmentBoxEvent;
import com.m3958.firstgwt.client.event.AttachmentBoxEventHandler;
import com.m3958.firstgwt.client.event.LoginEvent;
import com.m3958.firstgwt.client.event.LoginEventHandler;
import com.m3958.firstgwt.client.event.RecordEvent;
import com.m3958.firstgwt.client.event.RecordEventHandler;
import com.m3958.firstgwt.client.event.SiteChangeEvent;
import com.m3958.firstgwt.client.event.SiteChangeEventHandler;
import com.m3958.firstgwt.client.event.StripEvent;
import com.m3958.firstgwt.client.event.StripEventHandler;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.jso.AssetJso;
import com.m3958.firstgwt.client.jso.UploadResponseJso;
import com.m3958.firstgwt.client.jso.WebSiteJso;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.layout.ArticleEditLayout;
import com.m3958.firstgwt.client.layout.Iview;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.service.AppUtilService;
import com.m3958.firstgwt.client.service.AssetUploadService;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.IHasAttachment;
import com.m3958.firstgwt.client.types.IHasRichArea;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.client.types.UploadResponseStatus;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.SC;


@Singleton
public class Slot {
	
	private MyEventBus eventBus;
	
	@Inject
	private VblockService bservice;
	
	@Inject
	private AppStatusService aservice;
	
	private LinkedHashMap<String,ISlot> nameSlotsMap = new LinkedHashMap<String,ISlot>();
	
	@Inject
	private AppUtilService autils;
	
	@Inject
	private ViewService vservice;
	
	@Inject
	private AssetUploadService aus;
	
	private Map<String, WebSiteJso> cachedSiteJso = new HashMap<String, WebSiteJso>();
	
	@Inject
	public Slot(MyEventBus eventBus,AppSlot b1slot,C1slot c1Slot,C2slot c2Slot,D1slot d1Slot,D2slot d2Slot,E1slot e1Slot,E2slot e2Slot,F1slot f1Slot,F2slot f2Slot,G1slot g1Slot,G2slot g2Slot){
		this.eventBus = eventBus;
		nameSlotsMap.put(BlockName.B, b1slot);
		nameSlotsMap.put(BlockName.C1, c1Slot);
		nameSlotsMap.put(BlockName.C2, c2Slot);
		nameSlotsMap.put(BlockName.D1, d1Slot);
		nameSlotsMap.put(BlockName.D2, d2Slot);
		nameSlotsMap.put(BlockName.E1, e1Slot);
		nameSlotsMap.put(BlockName.E2, e2Slot);
		nameSlotsMap.put(BlockName.F1, f1Slot);
		nameSlotsMap.put(BlockName.F2, f2Slot);
		nameSlotsMap.put(BlockName.G1, g1Slot);
		nameSlotsMap.put(BlockName.G2, g2Slot);
		initListener();
	}
	
	private AttachmentBoxEventHandler abeh = new AttachmentBoxEventHandler() {
		@Override
		public void onAttachmentsEvent(AttachmentBoxEvent event) {
			Iview iv = vservice.getView(event.getSrcViewName());
			((IHasRichArea)iv).insertContent(event.getAssetJsos(), event.getAtype(), event.getHint());
		}
	};
	
	
	private AfterFileUploadEventHandler afeh = new AfterFileUploadEventHandler() {
		@Override
		public void onAfterUpload(AfterFileUploadEvent event) {
			UploadResponseJso jso = event.getUploadResponse();
			if(jso.getStatus() == UploadResponseStatus.SUCCESS){
				AssetJso curAsset = aus.getFirstAsset(jso);
				Iview iv = vservice.getView(ViewNameEnum.valueOf(jso.getViewName()));
				((IHasAttachment)(iv)).addAttachment(curAsset,UploadFor.valueOf(jso.getUploadFor()));
			}

		}
	};
	
	private ApplicationChangeEventHandler ah = new ApplicationChangeEventHandler() {
		@Override
		public void onApplicationChange(ApplicationChangeEvent event) {
			for(Vblock vb : bservice.getBlocks()){
				ISlot is = nameSlotsMap.get(vb.getName());
				if(vb.isEmpty()){
					is.hiddenAllMembers();
				}else{
					is.onApplicationEvent();
				}
			}
		}
	};
	
	private RecordEventHandler reh = new RecordEventHandler() {
		@Override
		public void onViewEvent(RecordEvent event) {
			Vblock b = bservice.getBlock(event.getVname());
			ISlot is = nameSlotsMap.get(b.getName());
			is.onRecordEvnet(event.getVname(), event.getRtype(), event.getParas());
		}
	};
	
	
	private void ove(ViewNameEnum vname,Btname sourceBt){
		switch (vname) {
		case RIGHT_STRIP:
			nameSlotsMap.get(BlockName.G2).onStripEvent(vname, sourceBt);
			break;
		case LEFT_STRIP:
			nameSlotsMap.get(BlockName.G1).onStripEvent(vname, sourceBt);
			break;
		case STRIP:
			nameSlotsMap.get(BlockName.E2).onStripEvent(vname, sourceBt);
			break;
		default:
			break;
		}
	}
	
	private StripEventHandler stripH = new StripEventHandler() {
		@Override
		public void onViewEvent(StripEvent event) {
			ove(event.getVname(),event.getSourceBt());
		}
	};
	
	
	private LoginEventHandler leh =  new LoginEventHandler() {
		@Override
		public void onLoginEvent(LoginEvent event) {
				boolean login = event.isLogin();
				cachedSiteJso.put(aservice.getWebSite().getId()+"", aservice.getWebSite());
				bservice.setSiteId(aservice.getWebSite().getId() + "");
				((IhasLoginEventHandler)vservice.getView(ViewNameEnum.TOP)).onLoginEvent(login);
				
				if(!login){
						for(ViewNameEnum vne : ViewNameEnum.values()){
							if(vservice.getView(vne) != null)
								vservice.getView(vne).setUnInitialized();
						}
				}
			}
		};
		
	private void aftersitechange(){
		for(Iview iv : vservice.getAllViews()){
			if(iv instanceof IHasSiteChangHandler){
				((IHasSiteChangHandler)(iv)).onSiteChange();
			}
		}
	}
		
	private SiteChangeEventHandler sceh = new SiteChangeEventHandler() {
		@Override
		public void onSiteChange(SiteChangeEvent event) {
			final String  siteId = event.getSiteId();
			bservice.setSiteId(siteId);
			if(siteId.equals(aservice.getWebSite().getId()+"")){
				;
			}else{
				bservice.setSiteId(siteId);
				if(cachedSiteJso.get(siteId) != null){
					aservice.setWebSite(cachedSiteJso.get(siteId));
					aftersitechange();
				}else{
					RPCManager.sendRequest(autils.getJsonPRequest(event.getSiteId(), WebSiteDataSource.className), new MyRpcCallback() {
						@Override
						public void afterSuccess(RPCResponse response, Object rawData,
								RPCRequest request, JSONValue data) {
							JSONArray ja = data.isArray();
							if(ja.size() == 1){
								WebSiteJso wjo = (WebSiteJso) ja.get(0).isObject().getJavaScriptObject();
								aservice.setWebSite(wjo);
								cachedSiteJso.put(siteId, wjo);
								aftersitechange();
							}else{
								SC.say("编辑对象不存在，可能已经删除！");
							}
						}
					});					
				}
			}
		}
	};
	
	public ArticleEditLayout getArticleEditLayout(){
		return (ArticleEditLayout) vservice.getView(ViewNameEnum.ARTICLE_EDIT);
	}
	
	public final native void exportStaticMethod()/*-{
		var as = this.@com.m3958.firstgwt.client.slots.Slot::aservice;
		var articleEditLayout = this.@com.m3958.firstgwt.client.slots.Slot::getArticleEditLayout()();
		$wnd.firstgwtns = {
			getCurrentWebSite:function(){
				return as.@com.m3958.firstgwt.client.AppStatusService::getWebSite()();
			},
			getAreaHtml:function(){
				return articleEditLayout.@com.m3958.firstgwt.client.layout.ArticleEditLayout::getAreaHtml()();
			},
			setAreaHtml:function(kindeditcontent){
				articleEditLayout.@com.m3958.firstgwt.client.layout.ArticleEditLayout::setAreaHtml(Ljava/lang/String;)(kindeditcontent);
			},
			getEditingArticle:function(){
				return articleEditLayout.@com.m3958.firstgwt.client.layout.ArticleEditLayout::getEditingArticle()();
			},
			setAttaches:function(attachmentsJson){
				articleEditLayout.@com.m3958.firstgwt.client.layout.ArticleEditLayout::setAttaches(Ljava/lang/String;)(attachmentsJson);
			},
			getAttachmentJson:function(){
				return articleEditLayout.@com.m3958.firstgwt.client.layout.ArticleEditLayout::getAttachmentJson()();
			}
		};
	}-*/;
	
//	setAttaches:function(attachmentsJson){
//	articleEditLayout.@com.m3958.firstgwt.client.layout.ArticleEditLayout::setAttaches(Lcom/google/gwt/core/client/JsArray;)(attachmentsJson);
//},
	
	public void initListener(){
		eventBus.addHandler(ApplicationChangeEvent.TYPE,ah);
		eventBus.addHandler(RecordEvent.TYPE, reh);
		eventBus.addHandler(StripEvent.TYPE, stripH);
		eventBus.addHandler(AfterFileUploadEvent.TYPE, afeh);
		eventBus.addHandler(AttachmentBoxEvent.TYPE, abeh);
		eventBus.addHandler(LoginEvent.TYPE,leh);
		eventBus.addHandler(SiteChangeEvent.TYPE, sceh);
	}

}
