package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.WebSiteDataSource;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.types.WebSiteField;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;

@Singleton
public class WebSiteEditLayout extends MySimpleEditLayout implements Iview{
	
	@Inject
	private WebSiteDataSource wsds;
	
	private Label hintLabel;

	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setNumCols(4);
		modelForm.setIsGroup(true);
		modelForm.setGroupTitle(constants.cwFormTitle());
		modelForm.setAlign(Alignment.LEFT);
		modelForm.setDataSource(wsds);
	}
	
	@Override
	protected ClickHandler getBtSimpleHandler(){ 
		return new ClickHandler() {
        public void onClick(ClickEvent event) {
        	if(modelForm.validate(false)){
        		String imgSizes = modelForm.getValueAsString(WebSiteField.IMG_SIZES.getEname());
        		if(imgSizes != null && imgSizes.length()>0){
        			if(!imgSizes.contains("48x48")){
        				SC.warn("至少包含缩略图的尺寸：48x48");
        				return;
        			};
        			imgSizes = imgSizes.replaceAll("\\s", "");
        			String[] ss = imgSizes.split(",");
        			for(String s:ss){
        				if(!s.matches("\\d+x\\d+")){
        					SC.warn("服务器生成的缩略图尺寸。格式：800x600，逗号分隔.");
        					return;
        				}
        			}
        		}
        		modelForm.saveData(new MyDsCallback(){
					@Override
					public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
						bservice.back();
					}});
        	}
        }
     };
	}

	@Override
	protected String getModelName() {
		return WebSiteDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
		hintLabel = new Label("图片尺寸，格式，宽x高，逗号分隔，注意从大到小排列");
		addMember(hintLabel);
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.WEBSITE_EDIT;
	}
}
