package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.DepartmentDataSource;
import com.m3958.firstgwt.client.event.NextViewEvent;
import com.m3958.firstgwt.client.types.DepartmentField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;


@Singleton
public class DepartmentEditLayout extends MyTreeEditLayout implements Iview {


	private IButton shequButton;
	
	@Inject
	private DepartmentDataSource dds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
	    modelForm.setNumCols(2);
	    modelForm.setAlign(Alignment.LEFT);
	    modelForm.setDataSource(dds);
	    
	    TextItem cnameItem = new TextItem(DepartmentField.NAME.getEname(),DepartmentField.NAME.getCname());
	    cnameItem.setRequired(true);
	    CheckboxItem shequContainerItem = new CheckboxItem(DepartmentField.SHEQU_CONTAINER.getEname(),DepartmentField.SHEQU_CONTAINER.getCname());
	    shequContainerItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if((Boolean) event.getValue()){
					if(editing){
						shequButton.setDisabled(false);
					}
				}else{
					shequButton.setDisabled(true);
				}
			}
		});
	    
	    HiddenItem parentIdItem = new HiddenItem(SmartParameterName.PARENTID);
	    TextItem bzItem = new TextItem(DepartmentField.BZ.getEname(),DepartmentField.BZ.getCname());
	    LengthRangeValidator lrv = new LengthRangeValidator();
	    lrv.setMin(2);
	    lrv.setMax(40);
	    
	    cnameItem.setValidators(lrv);
	    
	    final HiddenItem id = new HiddenItem("id");
	    final HiddenItem version = new HiddenItem("version");
	    
	    modelForm.setFields(cnameItem,shequContainerItem,id,version,parentIdItem,bzItem);
	}
	
	
	@Override
	protected void newModel(){
		editing = false;
		modelForm.editNewRecord();
		if(SmartConstants.NONE_EXIST_MODEL_ID_STR.equals(parentId)){
			if(!aservice.isSuperman()){
				SC.warn("你没有创建顶级部门的权限！");
				return;
			}
		}
		button.setTitle(constants.cwFormCreateButtonLabel());
		shequButton.setDisabled(true);
	}
	
	@Override
	protected void editModel(Record r){
		editing = true;
		modelForm.editRecord(r);
		button.setTitle(constants.cwFormSaveButtonLabel());
		if(r.getAttributeAsBoolean(DepartmentField.SHEQU_CONTAINER.getEname())){
			shequButton.setDisabled(false);
		}else{
			shequButton.setDisabled(true);
		}
	}
	
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.DEPARTMENT_EDIT;
	}


	@Override
	protected String getModelName() {
		return DepartmentDataSource.className;
	}


	@Override
	protected void initOtherWidget() {
		shequButton  = new IButton("社区管理");
	    shequButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				NextViewEvent nve = new NextViewEvent(getViewName(), ViewNameEnum.SHEQU, editRecordId);
				eventBus.fireEvent(nve);
			}
		});
	    addMember(shequButton);
	}


}
