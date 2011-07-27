package com.m3958.firstgwt.client.layout;

import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.AssetDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.types.AssetField;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class AssetEditLayout  extends HasMMRelationEditLayout implements Iview{
	
	@Inject
	private AssetDataSource ads;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
	    modelForm.setWidth100();
	    modelForm.setDataSource(ads);
	    
	    
		HiddenItem createdAtItem = new HiddenItem(CommonField.CREATED_AT.getEname());
	    HiddenItem folderIdItem = new HiddenItem(AssetField.FOLDER_ID.getEname());
	    TextItem originNameItem = new TextItem(AssetField.ORIGIN_NAME.getEname(),AssetField.ORIGIN_NAME.getCname());
	    modelForm.setItems(originNameItem,auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),createdAtItem,folderIdItem,auiService.getSiteIdItem());
	}
	
	@Override
    protected ClickHandler getCreateSaveBtHandler(){
    	ClickHandler bth = new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	if(modelForm.validate(false)){
	        		String sn = modelForm.getValueAsString(CommonField.SITE_ID.getEname());
	        		if( sn == null || sn.isEmpty() || "0".equals(sn) || SmartConstants.NONE_EXIST_MODEL_ID_STR.equals(sn))modelForm.setValue(CommonField.SITE_ID.getEname(), bservice.getSiteId());
	        		modelForm.saveData(new MyDsCallback(){
						@Override
						public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
							String token = bservice.getLatestToken(ViewNameEnum.ASSET);
							if(token != null){
								History.newItem(token);
							}
						}},createDsRequest());
	        		
	        	}
	        }
	    };
	    return bth;
    }


	@Override
	public String[] getParas() {
		return null;
	}

	@Override
	public Btname[] getStripStatus() {
		return null;
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.ASSET_EDIT;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}


	@Override
	protected String getModelName() {
		return AssetDataSource.className;
	}


	@Override
	protected void initOtherWidget() {
		
	}

}
