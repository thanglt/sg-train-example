package com.m3958.firstgwt.client.layout;


import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.VoteDataSource;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.types.VoteField;
import com.m3958.firstgwt.client.utils.FormValidatorUtils;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class VoteEditLayout extends MyTreeEditLayout implements Iview{
	
	@Inject
	private VoteDataSource vds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
	    modelForm.setNumCols(2);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
	    modelForm.setWidth100();
	    modelForm.setHeight(350);
	    modelForm.setDataSource(vds);
	    
	    TextItem nameItem = new TextItem(CommonField.NAME.getEname(),CommonField.NAME.getCname());
	    nameItem.setRequired(true);
	    nameItem.setValidators(FormValidatorUtils.getLengthRangeValidator(2, 100));
	    
	    DateTimeItem startItem = new DateTimeItem(VoteField.START_DATE.getEname(), VoteField.START_DATE.getCname());
	    DateTimeItem endItem = new DateTimeItem(VoteField.END_DATE.getEname(), VoteField.END_DATE.getCname());
	    
	    HiddenItem parentIdItem = new HiddenItem(SmartParameterName.PARENTID);
	    
	    SelectItem chartTypeItem = new SelectItem(VoteField.CHART_TYPE.getEname(), VoteField.CHART_TYPE.getCname());
	    chartTypeItem.setValueMap("area","areaspline","bar","column","combo","combospline","line","markerseries","pie","spline");
	    
	    TextItem minSelectItem = new TextItem(VoteField.MIN_SELECT.getEname(),VoteField.MIN_SELECT.getCname());
	    
	    TextItem maxSelectItem = new TextItem(VoteField.MAX_SELECT.getEname(),VoteField.MAX_SELECT.getCname());
	    
	    TextItem positionItem = new TextItem(CommonField.POSITION.getEname(),CommonField.POSITION.getCname());
	    
    modelForm.setItems(nameItem,auiService.getIdHiddenItem(),parentIdItem,auiService.getVersionHiddenItem(),auiService.getCreatedAtHiddenItem(),chartTypeItem,startItem,endItem,getTplNameItem(false),minSelectItem,maxSelectItem,positionItem);
	}
	
	
	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.VOTE_EDIT;
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
									String token = bservice.getLatestToken(ViewNameEnum.VOTE);
									if(token != null){
										History.newItem(token);
									}
								}});
		        		}else{
			        		modelForm.saveData(new MyDsCallback(){
								@Override
								public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
									String token = bservice.getLatestToken(ViewNameEnum.VOTE);
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
		return VoteDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}
}
