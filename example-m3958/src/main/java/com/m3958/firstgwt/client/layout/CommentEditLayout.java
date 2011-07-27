package com.m3958.firstgwt.client.layout;


import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.CommentDataSource;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;

@Singleton
public class CommentEditLayout extends MyTreeEditLayout implements Iview{
	
	@Inject
	private CommentDataSource cds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
	    modelForm.setNumCols(2);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
	    modelForm.setWidth100();
	    modelForm.setHeight(350);
	    modelForm.setDataSource(cds);
	}
	
	
	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.COMMENT_EDIT;
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
									String token = bservice.getLatestToken(ViewNameEnum.COMMENT);
									if(token != null){
										History.newItem(token);
									}
								}});
		        		}else{
			        		modelForm.saveData(new MyDsCallback(){
								@Override
								public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
									String token = bservice.getLatestToken(ViewNameEnum.COMMENT);
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
		return CommentDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}
}
