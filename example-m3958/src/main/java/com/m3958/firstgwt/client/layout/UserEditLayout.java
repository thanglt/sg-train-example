package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.MenuLevelDataSource;
import com.m3958.firstgwt.client.datasource.UserDataSource;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.MenuLevelField;
import com.m3958.firstgwt.client.types.UserField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.FormValidatorUtils;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class UserEditLayout extends HasMMRelationEditLayout implements Iview{
	
	@Inject
	private UserDataSource uds;
	
	@Inject
	private MenuLevelDataSource mlds;

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
	    
	    TextItem nicknameItem = new TextItem(UserField.NICKNAME.getEname(),UserField.NICKNAME.getCname());
	    
	    
	    TextItem avatarItem = new TextItem(UserField.AVATAR.getEname(),UserField.AVATAR.getCname());
	    
	    TextItem mobileItem = new TextItem(UserField.MOBILE.getEname(),UserField.MOBILE.getCname());
	    mobileItem.setRequired(true);
	    mobileItem.setValidators(FormValidatorUtils.getDigitalValidator());
	    
	    TextItem passwordItem = new TextItem(UserField.PASSWORD.getEname(),UserField.PASSWORD.getCname());
	    passwordItem.setRequired(true);
	    passwordItem.setValidators(FormValidatorUtils.getLengthRangeValidator(4, 29));
	    
	    SelectItem menusItem = new SelectItem(UserField.MENULEVEL_ID.getEname(),"菜单级别");
	    
	    menusItem.setOptionDataSource(mlds);
	    menusItem.setValueField(CommonField.ID.getEname());
	    menusItem.setDisplayField(MenuLevelField.NAME.getEname());
	    menusItem.setAnimatePickList(true);
	    menusItem.setTextMatchStyle(TextMatchStyle.EXACT);
	    menusItem.setRequired(true);
	    modelForm.setItems(loginNameItem,emailItem,mobileItem,passwordItem,auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),nicknameItem,avatarItem,auiService.getCreatedAtHiddenItem(),menusItem);
	}
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.USER_EDIT;
	}


	@Override
	protected String getModelName() {
		return UserDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}


}
