package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.AssetDataSource;
import com.m3958.firstgwt.client.datasource.LgbDataSource;
import com.m3958.firstgwt.client.service.AppUtilService;
import com.m3958.firstgwt.client.types.AssetField;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;


@Singleton
public class LgbAttachmentGrid  extends LgbRelationGridImpl implements ILgbRelationGrid{
	
	@Inject
	private AssetDataSource ads;
	
	@Override
	public ViewNameEnum getNextView() {
		return ViewNameEnum.ASSET_EDIT;
	}

	@Override
	protected void initGrid() {
	    grid.setWidth100();
	    grid.setHeight100();
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);

	    grid.setDataSource(ads);
	    grid.setAutoFetchData(false);
	    
        ListGridField descriptionField = new ListGridField(AssetField.DESCRIPTION.getEname(),AssetField.DESCRIPTION.getCname());
        

        ListGridField filePathField = new ListGridField(AssetField.FILE_PATH.getEname(),AssetField.FILE_PATH.getCname());
        filePathField.setHidden(true);
        ListGridField fileIdField = new ListGridField(AssetField.FILE_ID.getEname(),AssetField.FILE_ID.getCname());
        fileIdField.setHidden(true);
        ListGridField fileSizeField = new ListGridField(AssetField.FILE_SIZE.getEname(),AssetField.FILE_SIZE.getCname());
        fileSizeField.setCellFormatter(new AppUtilService.fileSizeFormatter());
        ListGridField mimeTypeField = new ListGridField(AssetField.MIME_TYPE.getEname(),AssetField.MIME_TYPE.getCname());
        ListGridField originNameField = new ListGridField(AssetField.ORIGIN_NAME.getEname(),AssetField.ORIGIN_NAME.getCname());
        
        originNameField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
//				return "<a href='/assetfeed/?assetId=" + record.getAttributeAsString(SmartParameterName.MODEL_ID) + "' target='_blank'>" + value + "</a>";
				return "<a href='" + record.getAttributeAsString("url") + "' target='_blank'>" + value + "</a>";
			}
		});
	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),auiService.getCreatedAtField(false),originNameField,mimeTypeField,fileIdField,filePathField,fileSizeField,descriptionField);
	}

	@Override
	public void fetchData(String lgbId) {
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.RELATION_MODEL_ID, lgbId);
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_ONE_TO_MANY.toString());
	    c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME, LgbField.ATTACHMENTS.getEname());
	    c.addCriteria(SmartParameterName.RELATION_MODEL_NAME, LgbDataSource.className);
	    c.addCriteria(SmartParameterName.IS_MASTER, "0");
	    grid.filterData(c);
	}

	@Override
	public String getLbname() {
		return "附件列表：";
	}

}
