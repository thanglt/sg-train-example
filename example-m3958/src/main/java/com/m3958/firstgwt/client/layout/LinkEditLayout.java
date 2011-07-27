package com.m3958.firstgwt.client.layout;

import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ICanAcceptConfirm;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.ArticleDataSource;
import com.m3958.firstgwt.client.datasource.AssetDataSource;
import com.m3958.firstgwt.client.datasource.AssetFolderDataSource;
import com.m3958.firstgwt.client.datasource.DiskFileDataSource;
import com.m3958.firstgwt.client.datasource.LinkDataSource;
import com.m3958.firstgwt.client.datasource.SectionDataSource;
import com.m3958.firstgwt.client.datasource.XinJianCatDataSource;
import com.m3958.firstgwt.client.datasource.XinJianDataSource;
import com.m3958.firstgwt.client.event.RequestSomeEvent;
import com.m3958.firstgwt.client.event.RequestSomeEvent.AskForWhat;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.DiskFileField;
import com.m3958.firstgwt.client.types.LinkField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.FormValidatorUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

@Singleton
public class LinkEditLayout extends MyTreeEditLayout implements Iview,ICanAcceptConfirm{
	
	@Inject
	private LinkDataSource lds;
	
	@Inject
	private DiskFileDataSource dfds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
	    modelForm.setNumCols(2);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
	    modelForm.setWidth100();
	    modelForm.setHeight(350);
	    modelForm.setDataSource(lds);
	    
	    TextItem nameItem = new TextItem(CommonField.NAME.getEname(),CommonField.NAME.getCname());
	    nameItem.setRequired(true);
	    nameItem.setValidators(FormValidatorUtils.getLengthRangeValidator(2, 40));
	    
	    HiddenItem parentIdItem = new HiddenItem(SmartParameterName.PARENTID);
	    
	    String[] sv = new String[]{"URL","文章目录或文章","资产目录或资产","信箱"};
	    SelectItem lsitem = new SelectItem("ltype","链接类型");
	    lsitem.setValueMap(sv);
	    lsitem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				modelForm.clearValue(LinkField.LINK_TO.getEname());
				modelForm.clearValue(LinkField.MODEL_ID.getEname());
				modelForm.clearValue(LinkField.TPL_NAME.getEname());
			}
		});
	    
	    TextAreaItem kvItem = new TextAreaItem(CommonField.KEY_VALUES.getEname(),CommonField.KEY_VALUES.getCname());
	    kvItem.setHeight(100);

	    
	    HiddenItem linkToItem = new HiddenItem(LinkField.LINK_TO.getEname());
	    TextItem modelIdItem = new TextItem(LinkField.MODEL_ID.getEname(), LinkField.MODEL_ID.getCname());
	    FormItemIcon fii = new FormItemIcon();
	    modelIdItem.setIcons(fii);
	    
	    TextItem positionItem = new TextItem(CommonField.POSITION.getEname(),CommonField.POSITION.getCname());
	    
	    
	    
	    //SECTION,ARTICLE,ASSET,ASSET_FOLDER,URL
	    modelIdItem.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {
				AskForWhat afw;
				String lt = modelForm.getValueAsString("ltype"); 
				if(lt == null || lt.isEmpty()){
					SC.warn("请先选择链接类型！");
					return;
				}else if("文章目录或文章".equals(lt)){
					afw = AskForWhat.SECTION_ARTICLE;
				}else if("资产目录或资产".equals(lt)){
					afw = AskForWhat.FOLDER_ASSET;
				}else if("信箱".equals(lt)){
					afw = AskForWhat.XJCAT_XJ;
				}else{
					SC.warn("链接类型不支持对象选择！");
					return;
				}
				RequestSomeEvent rse = new RequestSomeEvent(ViewNameEnum.LINK_EDIT,afw,"");
				eventBus.fireEvent(rse);
			}
		});
	    
	    ComboBoxItem tplItem = new ComboBoxItem(LinkField.TPL_NAME.getEname(), LinkField.TPL_NAME.getCname());
	    
	    CheckboxItem hideMeItem = new CheckboxItem(CommonField.HIDDEN.getEname(), CommonField.HIDDEN.getCname());

	    tplItem.setOptionDataSource(dfds);
	    tplItem.setValueField(DiskFileField.FILE_NAME.getEname());
	    tplItem.setAnimatePickList(true);
	    tplItem.setTextMatchStyle(TextMatchStyle.EXACT);
	    tplItem.setPickListCriteria(getPickerCriteria());
	    
	    TextItem urlItem = new TextItem(LinkField.LURL.getEname(), LinkField.LURL.getCname());
	    
	    modelForm.setItems(nameItem,auiService.getIdHiddenItem(),parentIdItem,auiService.getVersionHiddenItem(),auiService.getCreatedAtHiddenItem(),lsitem,modelIdItem,urlItem,tplItem,linkToItem,positionItem,hideMeItem,kvItem);
	}
	
	@Override
	protected void editModel(Record r){
		if(!aservice.isConfirmInProgress()){
			modelForm.editRecord(r);
			String linkTo = r.getAttributeAsString(LinkField.LINK_TO.getEname());
			modelForm.setValue("ltype", linkToToLtype(linkTo));
		}
		aservice.setConfirmInProgress(false);
		button.setTitle(constants.cwFormSaveButtonLabel());
	}
	
	private String linkToToLtype(String linkTo){
		if(AssetFolderDataSource.className.equals(linkTo) || AssetDataSource.className.equals(linkTo)){
			return "资产目录或资产";
		}else if(SectionDataSource.className.equals(linkTo) || ArticleDataSource.className.equals(linkTo)){
			return "文章目录或文章";
		}else if(XinJianCatDataSource.className.equals(linkTo) || XinJianDataSource.className.equals(linkTo)){
			return "信箱";
		}else{
			return "URL";
		}
	}
	
	@Override
	public void newModel(){
		if(!aservice.isConfirmInProgress()){
			modelForm.editNewRecord();
			button.setTitle(constants.cwFormCreateButtonLabel());
			button.setDisabled(false);
		}
		aservice.setConfirmInProgress(false);
	}
	

	
	@Override
	public void changeDisplay(ViewAction va,String... paras) {
		super.changeDisplay(va, paras);
		ComboBoxItem si = (ComboBoxItem)modelForm.getItem(LinkField.TPL_NAME.getEname());
		si.setPickListCriteria(getPickerCriteria());
		si.fetchData();
	}
	
	private Criteria getPickerCriteria(){
	    Criteria c = new Criteria();
	    c.addCriteria(DiskFileField.SITE_ID.getEname(),bservice.getSiteId());
	    c.addCriteria(DiskFileField.DIR_PATH.getEname(),"/");
	    c.addCriteria(CommonField.PURPOSE.getEname(),"linktpl");
	    return c;
	}
	
	
	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.LINK_EDIT;
	}
	
	@Override
    protected ClickHandler getBtTreeHandler(){
	     return new ClickHandler() {
		        public void onClick(ClickEvent event) {
		        	if(modelForm.validate(false)){
		        		
		        		if(editing){
			        		modelForm.saveData(new MyDsCallback(){
								@Override
								public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
									String token = bservice.getLatestToken(ViewNameEnum.LINK);
									if(token != null){
										History.newItem(token);
									}
								}});
		        		}else{
			        		modelForm.saveData(new MyDsCallback(){
								@Override
								public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
									String token = bservice.getLatestToken(ViewNameEnum.LINK);
									if(token != null){
										History.newItem(token);
									}
								}},createTreeDsRequest());
		        		}
		        		

		        	}
		        }
		    };
    }

	@Override
	protected String getModelName() {
		return LinkDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}

	@Override
	public void giveConfirmedThing(Record[] rs, String confirmHint) {
		if(rs.length == 0)return;
		String rid = rs[0].getAttributeAsString(CommonField.ID.getEname());
		modelForm.setValue(LinkField.MODEL_ID.getEname(), rid);
		String ltype = modelForm.getValueAsString("ltype");
		if("资产目录或资产".equals(ltype)){
			if(auService.hasAttribute(rs[0],CommonField.NAME.getEname())){
				modelForm.setValue(LinkField.LINK_TO.getEname(), AssetFolderDataSource.className);
				modelForm.setValue(LinkField.TPL_NAME.getEname(), "assetfolder");
			}else{
				modelForm.setValue(LinkField.LINK_TO.getEname(), AssetDataSource.className);
				modelForm.setValue(LinkField.TPL_NAME.getEname(), "asset");
			}
		}else if("文章目录或文章".equals(ltype)){
			if(auService.hasAttribute(rs[0],CommonField.NAME.getEname())){
				modelForm.setValue(LinkField.LINK_TO.getEname(), SectionDataSource.className);
				modelForm.setValue(LinkField.TPL_NAME.getEname(), "section");
			}else{
				modelForm.setValue(LinkField.LINK_TO.getEname(), ArticleDataSource.className);
				modelForm.setValue(LinkField.TPL_NAME.getEname(), "article");
			}
		}else if("信箱".equals(ltype)){
			if(auService.hasAttribute(rs[0],CommonField.NAME.getEname())){
				modelForm.setValue(LinkField.LINK_TO.getEname(), XinJianCatDataSource.className);
				modelForm.setValue(LinkField.TPL_NAME.getEname(), "xjcat");
			}else{
				modelForm.setValue(LinkField.LINK_TO.getEname(), XinJianDataSource.className);
				modelForm.setValue(LinkField.TPL_NAME.getEname(), "xj");
			}
		}else{
			modelForm.setValue(LinkField.LINK_TO.getEname(), "url");
		}
	}
}
