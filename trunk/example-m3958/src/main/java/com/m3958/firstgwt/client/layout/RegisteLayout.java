package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.UserDataSource;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.types.UserField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.FormValidatorUtils;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class RegisteLayout extends HasMMRelationEditLayout implements Iview{
	
	@Inject
	private UserDataSource uds;

	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
	    modelForm.setWidth100();
	    modelForm.setHeight(350);
	    modelForm.setDataSource(uds);
	    
	    TextItem loginNameItem = new TextItem(UserField.LOGIN_NAME.getEname(),UserField.LOGIN_NAME.getCname());
	    
	    loginNameItem.setRequired(true);
	    loginNameItem.setValidators(FormValidatorUtils.getLengthRangeValidator(2, 40));
	    
	    TextItem emailItem = new TextItem(UserField.EMAIL.getEname(),UserField.EMAIL.getCname());
	    emailItem.setRequired(true);
	    emailItem.setValidators(FormValidatorUtils.getEmailValidator());
	    
	    TextItem mobileItem = new TextItem(UserField.MOBILE.getEname(),UserField.MOBILE.getCname());
	    mobileItem.setRequired(true);
	    mobileItem.setValidators(FormValidatorUtils.getDigitalValidator());
	    
	    TextItem passwordItem = new TextItem(UserField.PASSWORD.getEname(),UserField.PASSWORD.getCname());
	    passwordItem.setRequired(true);
	    passwordItem.setValidators(FormValidatorUtils.getLengthRangeValidator(4, 29));
	    
	    modelForm.setItems(loginNameItem,emailItem,mobileItem,passwordItem,auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),auiService.getCreatedAtHiddenItem());
	}
	
	@Override
    protected ClickHandler getCreateSaveBtHandler(){
    	ClickHandler bth = new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	if(modelForm.validate(false)){
	        		modelForm.saveData(new MyDsCallback(){
						@Override
						public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
								auiService.showTmpPrompt("注册成功！", 2000);
								bservice.back();
						}},createDsRequest());
	        	}
	        }
	    };
	    return bth;
    }
	
	
	
	@Override
	protected void newModel(){
		modelForm.editNewRecord();
		button.setTitle("注册");
	}
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.REGISTE;
	}


	@Override
	protected String getModelName() {
		return UserDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}


}
