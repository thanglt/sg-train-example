package com.m3958.firstgwt.client.portal;

@SuppressWarnings("serial")
public class HmessageWindow/* extends HasAfterUploadBase implements HasUseUserSelect,HasAfterUpload*/{
	
//	private DynamicForm f;
//	
//	private UserSelectWindow usw;
//	
//	private ListGrid groupGrid;
//	
//	private ListGrid userGrid;
//	
//	private FileUploadWindow fuw;
//	
//	private List<UploadFor> uploadFor = new ArrayList<UploadFor>(){{add(UploadFor.JUST_UPLOAD);}};
//	private List<String> allowedExts = new ArrayList<String>(){{add("jpg");add("gif");add("bmp");add("png");add("jpeg");add("*");}};
//
//	
//	private FileUploadWindow getFuw(){
//		fuw = new FileUploadWindow(this,LgbDataSource.className,null,null,uploadFor,allowedExts,askerTimeStamp,"");
//		return fuw;
//	}
//	
//	
//	private UserSelectWindow getUsw(){
//		if(usw == null){
//			usw = new UserSelectWindow(this);
//		}
//		return usw;
//	}
//	
//	public HmessageWindow() {
//		setTitle("发送消息");
//		setWidth(800);
//		setHeight(450);
//		centerInPage();
//		createTimeStamp();
//		
//		addCloseClickHandler(new CloseClickHandler() {
//			
//			@Override
//			public void onCloseClick(CloseClientEvent event) {
//				resetAttachment();
////				groupGrid.setRecords(new ListGridRecord[0]);
////				userGrid.setRecords(new ListGridRecord[0]);
//				hide();
//			}
//		});
//		
//		HLayout hl = new HLayout();
//		hl.addMember(initForm());
//		hl.addMember(initDest());
//		addItem(hl);
//	}
//
//	private Canvas initDest() {
//		VLayout l = new VLayout();
//		groupGrid = UserSelectWindow.initGroupListGrid("没有选定的组");
//		l.addMember(groupGrid);
//		userGrid = UserSelectWindow.initUserDestGrid("没有选定的用户");
//		userGrid.addDragStopHandler(new DragStopHandler() {
//			@Override
//			public void onDragStop(DragStopEvent event) {
//				if(!GlobalStaffs.dotInRect(userGrid.getPageRect(), event.getX(), event.getY())){
//					userGrid.removeSelectedData();
//				}
//				event.cancel();
//			}
//		});
//		
//		groupGrid.addDragStopHandler(new DragStopHandler() {
//			@Override
//			public void onDragStop(DragStopEvent event) {
//				if(!GlobalStaffs.dotInRect(groupGrid.getPageRect(), event.getX(), event.getY())){
//					groupGrid.removeSelectedData();
//				}
//			}
//		});
//		l.addMember(userGrid);
//		return l;
//	}
//	
//	private RichTextEditor richTextEditor;
//
//	private Canvas initForm() {
//		 VLayout l = new VLayout();
//		 l.setWidth("70%");
//		 l.setShowResizeBar(true);
//		 f = new DynamicForm();
//		 f.setWidth100();
//		 f.setHeight(20);
//		 f.setMargin(5);
//		 f.setNumCols(2);
//		 f.setDataSource(HmessageDataSource.getInstance());
//		 
//		 TextItem titleItem = new TextItem(HmessageAllField.HmessageField.TITLE.getEname(),HmessageAllField.HmessageField.TITLE.getCname());
//		 titleItem.setWidth(450);
//		 titleItem.setTitleAlign(Alignment.LEFT);
//		 titleItem.setRequired(true);
//		 
//		 HiddenItem guidsItem = new HiddenItem(SmartParameterName.GUIDS);
//		 HiddenItem attaItem = new HiddenItem(SmartParameterName.ATTACHMENTIDS);
//
//		 
//		 HiddenItem contentItem = new HiddenItem(HmessageAllField.HmessageField.MESSAGE.getEname());
//		 contentItem.setRequired(true);
//		 
//		 f.setFields(titleItem,contentItem,guidsItem,attaItem);
//		 
//        richTextEditor = new RichTextEditor();  
//        richTextEditor.setWidth100();
//        richTextEditor.setHeight100();
//        richTextEditor.setOverflow(Overflow.HIDDEN);  
//        richTextEditor.setCanDragResize(true);  
//        richTextEditor.setShowEdges(true); 
//        l.addMember(f);
//		l.addMember(richTextEditor);
//		
//		l.addMember(initBtns());
//		return l;
//	}
//	
//	private Label l = new Label();
//
//	private Canvas initBtns() {
//		HLayout hl = new HLayout(5);
//		hl.setMargin(5);
//		IButton sendbt = new IButton("发送");
//		
//		sendbt.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				f.setValue(HmessageAllField.HmessageField.MESSAGE.getEname(), richTextEditor.getValue());
//				if(f.validate(true)){
//					if(groupGrid.getRecords().length == 0 && userGrid.getRecords().length == 0){
//						SC.warn("请选择接收人！");
//						return;
//					}
//					f.setValue(SmartParameterName.GUIDS, GlobalStaffs.getSelectedUg(groupGrid.getRecords(), userGrid.getRecords()));
////					f.setValue(SmartParameterName.ATTACHMENTIDS, JsoUtils.getFormAssets(askerTimeStamp).toString());
//					f.saveData(new MyDsCallback() {
//						@Override
//						public void afterSuccess(DSResponse response, Object rawData,
//								DSRequest request) {
//							Record[] rs = response.getData();
//							if(rs.length > 0){
//								GlobalStaffs.showTmpPrompt("发送成功！");
//								f.clearValues();
//								resetAttachment();
//								hide();
//							}else{
//								GlobalStaffs.showTmpPrompt("不知道怎么回事，竟然没有发送出去！");
//							}
//							
//						}
//					});
//				}
//				
//			}
//		});
//		
//		IButton selectUserBt = new IButton("选择接收人");
//		
//		selectUserBt.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				 getUsw().show();
//				
//			}
//		});
//		
//		IButton attabt = new IButton("上传附件");
//		
//		attabt.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				getFuw().show();
//			}
//		});
//		
//		l.setContents(attaNum + "");
//		
//		l.setCursor(Cursor.HAND);
//		l.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				NattachmentsWindow naw = new NattachmentsWindow();
//				naw.showMe(assets,"当前已有附件");
//			}
//		});
//		
//		hl.addMember(sendbt);
//		hl.addMember(selectUserBt);
//		hl.addMember(attabt);
//		hl.addMember(l);
//		return hl;
//	}
//	
//
//	@Override
//	public void onUserSelectCancel() {
//		
//		
//	}
//	
//	@SuppressWarnings("unused")
//	private String[] gids;
//	
//	@SuppressWarnings("unused")
//	private String[] uids;
//
//	@Override
//	public void onUserSelected(String[] gids, String[] uids) {
//		this.gids = gids;
//		this.uids = uids;
//	}
//
//	@Override
//	public void groupAdded(Record r) {
//		groupGrid.addData(r);
//		
//	}
//
//	@Override
//	public void groupDeleted(Record r) {
//		Record[] rs = groupGrid.getRecords();
//		Record mer = find(rs,GroupField.ID.getEname(), r.getAttributeAsInt(GroupField.ID.getEname()));
//		groupGrid.removeData(mer);
//		
//	}
//
//	@Override
//	public void userAdded(Record r) {
//		userGrid.addData(r);
//		
//	}
//
//	@Override
//	public void userDeleted(Record r) {
//		Record[] rs = userGrid.getRecords();
//		Record mer = find(rs,UserField.ID.getEname(), r.getAttributeAsInt(UserField.ID.getEname()));
//		userGrid.removeData(mer);
//	}
//	
//	
//	private Record find(Record[] rs,String idp,int id){
//		for(int i=0;i<rs.length;i++){
//			if(rs[i].getAttributeAsInt(idp) == id){
//				return rs[i];
//			}
//		}
//		return null;
//	}
//	
//	
//
//	@Override
//	public void upSuccess(UploadResponseJso urj) {
//			l.setContents(attaNum + "");
//	}
//	
//	@Override
//	public void upFailure(UploadResponseJso urj) {
//		;
//	}
//
//
//	public void newHmessage() {
//		show();
//	}
//
//
//	public void replyHmessage(Record hm, Record sender, Record[] attas) {
//		userGrid.addData(sender);
//		richTextEditor.setValue("<br/><br/>原文：-------------------------<br/>" + hm.getAttributeAsString(HmessageAllField.HmessageField.MESSAGE.getEname()));
//		f.setValue(HmessageAllField.HmessageField.TITLE.getEname(), "回复：" + HmessageAllField.HmessageField.TITLE.getEname());
//		show();
//	}

}
