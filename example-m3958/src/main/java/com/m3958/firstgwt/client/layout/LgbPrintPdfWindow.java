package com.m3958.firstgwt.client.layout;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.FirstGwtConstants;
import com.m3958.firstgwt.client.datasource.JrxmlFileDataSource;
import com.m3958.firstgwt.client.datasource.LgbDataSource;
import com.m3958.firstgwt.client.jso.AssetJso;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.JasperRequestParaNames;
import com.m3958.firstgwt.client.types.JrxmlFileField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.utils.StringUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

@Singleton
public class LgbPrintPdfWindow extends Window{
	final FirstGwtConstants constants = GWT.create(FirstGwtConstants.class);
	
    final TileGrid tileGrid = new TileGrid();
    
    private ListGrid lgbGrid;
    
    private boolean initialized = false;
    
    @Inject
    private JrxmlFileDataSource jrds;
	
	public LgbPrintPdfWindow(){
		setTitle("打印老干部资料");
        setWidth(600);
        setHeight(370);
        centerInPage();
        setAlign(Alignment.CENTER);
        setCanDragReposition(true);
        setCanDragResize(true);
		
	}
	
	private Canvas initContent() {
		VLayout layout = new VLayout();
		layout.setMembersMargin(5);
		layout.addMember(initJrxmlPreViewLayout());
		layout.addMember(initBtLayout());
		return layout;
	}


	private HLayout initBtLayout() {
	    HLayout hl = new HLayout();
	    hl.setWidth100();
	    hl.setHeight(50);
	    hl.setMembersMargin(5);
	    SelectItem reportType = new SelectItem("format","打印格式");
	    LinkedHashMap<String, String> rvalues = new LinkedHashMap<String, String>();
	    rvalues.put("pdf", "pdf格式");
	    rvalues.put("html", "网页格式");
	    reportType.setValueMap(rvalues);
	    reportType.setDefaultValue("html");
		return hl;
	}
	
	
	
	@SuppressWarnings("unchecked")
	private String getExportUrl(String jid){
		String url = StringUtils.addUrlParams("",lgbGrid.getCriteria().getValues());
		Map<String, String> params = new HashMap<String, String>();
		params.put(SmartParameterName.OPERATION_TYPE, SmartOperationName.RPCM_REQ.toString());
		params.put(SmartParameterName.MODEL_NAME, LgbDataSource.className);
		params.put(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.EXPORT_LGB_TO_PDF.toString());
		params.put(JasperRequestParaNames.JRXML_ID, jid);
		params.put(JasperRequestParaNames.FORMAT, "pdf");
		params.put(SmartParameterName.TEXT_MATCHSTYLE, lgbGrid.getAutoFetchTextMatchStyle().getValue());
		url = StringUtils.addUrlParams(url,params);
		return url;
	}


/*
 * A4 210*297,1:1.89
 */
	private HLayout initJrxmlPreViewLayout() {
		HLayout layout = new HLayout();
        VStack vStack = new VStack(20);
        vStack.setWidth100();
        tileGrid.setTileWidth(150);
        tileGrid.setTileHeight(205);
        tileGrid.setHeight(250);
        tileGrid.setCanDrag(true);
        tileGrid.setCanAcceptDrop(true);
        tileGrid.setShowAllRecords(true);
 
        tileGrid.setDataSource(jrds);
        tileGrid.setAnimateTileChange(true);

        DetailViewerField pictureField = new DetailViewerField(JrxmlFileField.previewImg.getEname());
        pictureField.setType("image");
        pictureField.setDetailFormatter(new DetailFormatter(){
			@Override
			public String format(Object value, Record record,
					DetailViewerField field) {
				AssetJso a = (AssetJso) value;
//				return "/assetfeed/?assetId=" + a.getId();
				return "/asset/" + a.getId();
			}});
        
        DetailViewerField nameField = new DetailViewerField(JrxmlFileField.NAME.getEname());
        
        DetailViewerField idField = new DetailViewerField(CommonField.ID.getEname());
        
        nameField.setDetailFormatter(new DetailFormatter() {
			@Override
			public String format(Object value, Record record, DetailViewerField field) {
				return "<a href='" + SmartConstants.SMART_CRUD_URL + "?" + getExportUrl(record.getAttributeAsString(CommonField.ID.getEname())) + "' target='_blank'>" + value + "</a>";
			}
		});
        
        tileGrid.setFields(pictureField,nameField,idField);
        vStack.addMember(tileGrid);
	    layout.addMember(vStack);
	    return layout;
	}
	
	public void showMe(ListGrid lgbGrid){
		if(!initialized){
			this.lgbGrid = lgbGrid;
			addItem(initContent());
			initialized = true;
		}
		fetchMyJrxmlFile();
		show();
	}
	
	public void fetchMyJrxmlFile(){
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_CHILDREN.toString());
	    c.addCriteria(SmartParameterName.PARENTID, SmartConstants.NONE_EXIST_MODEL_ID);
	    tileGrid.filterData(c);
	}
}
