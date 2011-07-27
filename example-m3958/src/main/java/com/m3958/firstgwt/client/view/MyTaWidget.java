package com.m3958.firstgwt.client.view;

import java.util.Iterator;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;


@Singleton
public class MyTaWidget extends Composite implements HasWidgets{
	
//	private int chartHeight = 240;
//	private int chartWidth = 600;
//	
//	@Inject
//	private AppStatusService aservice;
//	
//	private static List<LgbField> groupBys = new ArrayList<LgbField>();
//	
//	static{
//		groupBys.add(LgbField.XB);
//		groupBys.add(LgbField.SR);
//		groupBys.add(LgbField.MINZU);
//		groupBys.add(LgbField.XZJB);
//		groupBys.add(LgbField.XSDY);
//		groupBys.add(LgbField.JKZK);
//		groupBys.add(LgbField.HYZK);
//		groupBys.add(LgbField.DWXZ);
//		groupBys.add(LgbField.JJZK);
//		groupBys.add(LgbField.GBLX);
//	}
//	
//	private static List<String> chartTypes = new ArrayList<String>();
//	
//	static{
//		chartTypes.add("饼图");
//		chartTypes.add("条形图");
//		chartTypes.add("柱状图");
//		chartTypes.add("线条图");
//		chartTypes.add("表格");
//	}
//	
//	private static Map<LgbField, String> defaultGroupByChart = new HashMap<LgbField, String>();
//	
//	static{
//		defaultGroupByChart.put(LgbField.XB, "饼图");
//		defaultGroupByChart.put(LgbField.SR, "线条图");
//		defaultGroupByChart.put(LgbField.MINZU, "线条图");
//	}
//	
//    final ListBox groupByDropBox = new ListBox(false);
//    final ListBox chartTypeDropBox = new ListBox(false);
//	
//	private static LgbField firstShowField = LgbField.XB;
//	
//	private VerticalPanel tabContentPanel = new VerticalPanel();
//	
//	private boolean visualPackageLoaded = false;
//	private boolean isFirstLoad = true;
//	
//	private VerticalPanel chartPanel; 
//	
//	public MyTaWidget() {
//		tabContentPanel.setStyleName("vpDotted");
//		tabContentPanel.setHeight("100%");
//        
//        HorizontalPanel selectsLayout = new HorizontalPanel();
//        selectsLayout.setSpacing(15);
//        
//
//        
//        for (LgbField lf : groupBys) {
//        	switch (lf) {
//			case SR:
//				groupByDropBox.addItem(lf.getAlias(),lf.getEname());
//				break;
//			default:
//				groupByDropBox.addItem(lf.getCname(),lf.getEname());
//				break;
//			}
//		}
//        groupByDropBox.setSelectedIndex(0);
//        
//        groupByDropBox.addChangeHandler(new ChangeHandler(){
//			@Override
//			public void onChange(ChangeEvent event) {
//				String groupByName = groupByDropBox.getValue((groupByDropBox.getSelectedIndex()));
//				String chartName = defaultGroupByChart.get(LgbField.getFieldEnumByEname(groupByName));
//				if(chartName == null)chartName = "饼图";
//				chartTypeDropBox.setSelectedIndex(getChartTypeIndex(chartName));
//				createChart(LgbField.getFieldEnumByEname(groupByName), chartName);
//				
//			}
//        });
//        VerticalPanel groupByPanel = new VerticalPanel();
//        groupByPanel.add(new HTML("统计信息选择"));
//        groupByPanel.add(groupByDropBox);
//        
//        
//        for(String s:chartTypes){
//        	chartTypeDropBox.addItem(s);
//        }
//        chartTypeDropBox.setSelectedIndex(0);
//        
//        chartTypeDropBox.addChangeHandler(new ChangeHandler(){
//			@Override
//			public void onChange(ChangeEvent event) {
//				createSelectChart();
//					String groupByEname = groupByDropBox.getValue((groupByDropBox.getSelectedIndex()));
//					String cType = chartTypeDropBox.getValue((chartTypeDropBox.getSelectedIndex()));
//					createChart(LgbField.getFieldEnumByEname(groupByEname), cType);
//			}});
//        VerticalPanel chartTypePanel = new VerticalPanel();
//        chartTypePanel.add(new HTML("图类型选择"));
//        chartTypePanel.add(chartTypeDropBox);
//        
//        selectsLayout.add(groupByPanel);
//        selectsLayout.add(chartTypePanel);
//        
//        Button resetbt = new Button("复位");
//        Button hbt = new Button("更高");
//        Button wbt = new Button("更宽");
//        
//        selectsLayout.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
//        resetbt.addClickHandler(new ClickHandler(){
//			@Override
//			public void onClick(ClickEvent event) {
//				chartHeight = 240;
//				chartWidth = 600;
//				createSelectChart();
//			}});
//        
//        selectsLayout.add(resetbt);
//        selectsLayout.add(hbt);
//        hbt.addClickHandler(new ClickHandler(){
//			@Override
//			public void onClick(ClickEvent event) {
//				chartHeight += 10;
//				createSelectChart();
//			}});
//        
//        selectsLayout.add(wbt);
//        wbt.addClickHandler(new ClickHandler(){
//			@Override
//			public void onClick(ClickEvent event) {
//				chartWidth += 20;
//				createSelectChart();
//			}});
//        
//        
//        tabContentPanel.add(selectsLayout);
//		initWidget(tabContentPanel);
//		
//		loadChartPackages(PieChart.PACKAGE,LineChart.PACKAGE);
//		
//	}
//	
//	private int getChartTypeIndex(String chartName) {
//		for(int i=0;i<chartTypes.size();i++) {
//			if(chartName.equals(chartTypes.get(i))){
//				return i;
//			}
//		}
//		return 0;
//	}
//	
//
//	private void loadChartPackages(String...pakegs) {
//		//虽然第二次请求的时候不会在发出url请求，但是callback还是会执行。还是用query吧。
//		VisualizationUtils.loadVisualizationApi(new Runnable(){
//			@Override
//			public void run() {
//				visualPackageLoaded = true;
//				if(isFirstLoad){
//					String ctype = defaultGroupByChart.get(firstShowField);
//					if(ctype == null)ctype ="饼图";
//					createChart(firstShowField, ctype);
//					isFirstLoad = false;
//				}
//		}}, pakegs);
//	}
//	
//	private void createSelectChart() {
//		String groupByName = groupByDropBox.getValue((groupByDropBox.getSelectedIndex()));
//		String chartName = defaultGroupByChart.get(LgbField.getFieldEnumByEname(groupByName));
//		createChart(LgbField.getFieldEnumByEname(groupByName), chartName);		
//	}
//	
//	private void createChart(LgbField field,String chartType) {
//		if(!visualPackageLoaded){
//			Window.alert("visual包还没有加载！");
//			return;
//		}
//		if("饼图".equals(chartType)){
//			createPieChart(field,chartType);
//		}else if("线条图".equals(chartType)){
//			createLineChart(field, chartType);
//		}
//	}
//	
//	//modelName,groupBy,fileds,relationModelName,relationModelId,
//	//select l FROM Lgb l WHERE xxx group by groupBy;
//	private String getDataUrl(LgbField field){
//		String qs = null;
//		switch (field) {
//		case SR:
//			qs = "lgbVisual?groupByName=" + field.getEname() + "&did="+ aservice.getCurDepartment()  + "&groupByType=date" + "&sortdir=" + SortDirection.DESCENDING;
//			break;
//
//		default:
//			qs = "lgbVisual?groupByName=" + field.getEname()+ "&did="+aservice.getCurDepartment();
//			break;
//		}
//		return qs;
//	}
//
//
//	//------------------------------------------start--LineChart-------------------------------------------------------------------------	
//	private void createLineChart(final LgbField field,String chartType) {
////	    Query.Options queryOptions = Query.Options.create();
////	    queryOptions.setSendMethod(SendMethod.SCRIPT_INJECTION);
////	    Query query = Query.create(getDataUrl(field), queryOptions);
//	    
//		Query query = Query.create(getDataUrl(field));
//		query.send(new Query.Callback(){
//			@Override
//			public void onResponse(QueryResponse response) {
//				if(response.isError()){
//					Window.alert(response.getMessage());
//				}else{
//	        	  if(chartPanel != null){
//	        		  tabContentPanel.remove(chartPanel);
//	        	  }
//	        	  chartPanel = new VerticalPanel();
//	        	  DataTable data = response.getDataTable();
//	        	  //其实服务器返回的就应该是年龄，而不是生日。
//	        	  
////	        	  PatternFormat pformat = PatternFormat.create("{0}岁");
////	        	  pformat.format(data, JsoUtils.getJsArrayInteger("([0])"), 0);
//	        	  LineChart.Options options  = createLineChartOptions(field);
//	        	  LineChart line = new LineChart(data,options);
//	        	  chartPanel.add(line);
//		          tabContentPanel.insert(chartPanel,1);
//				}
//			}
//
//		});
//	}
//	
//	private LineChart.Options createLineChartOptions(
//			LgbField field) {
//		LineChart.Options options = LineChart.Options.create();
//		options.setEnableTooltip(true);
//		options.setShowCategories(true);
//		switch (field) {
//		case SR:
//			options.setTitle(field.getAlias());
//			break;
//		default:
//			options.setTitle(field.getCname());
//			break;
//		}
//        options.setWidth(chartWidth);
//        options.setHeight(chartHeight);
//        options.setSmoothLine(true);
//        options.set("curveType", "function");
//        return options;
//	}
//	//-----------------------------------------end---LineChart-------------------------------------------------------------------------	
//
//	
//	//------------------------------------------start--PieChart-------------------------------------------------------------------------	
//	private void createPieChart(final LgbField field,String chartType) {
//		Query query = Query.create(getDataUrl(field));
//		query.send(new Query.Callback(){
//			@Override
//			public void onResponse(QueryResponse response) {
//				if(response.isError()){
//					Window.alert(response.getMessage());
//				}else{
//	        	  if(chartPanel != null){
//	        		  tabContentPanel.remove(chartPanel);
//	        	  }
//	        	  chartPanel = new VerticalPanel();
//	        	  DataTable data = response.getDataTable();
//	        	  PieChart.Options options  = createPieChartOptions(field);
//	        	  PieChart pie = new PieChart(data,options);
//		          chartPanel.add(pie);
//		          tabContentPanel.insert(chartPanel,1);
//				}
//			}});
//	}
//	
//	private PieChart.Options createPieChartOptions(LgbField field) {
//        PieChart.Options options = PieChart.Options.create();
//        options.setWidth(chartWidth);
//        options.setHeight(chartHeight);
//        options.set3D(true);
//        options.setTitle(field.getCname());
//        return options;
//      }
//	//------------------------------------------end--PieChart-------------------------------------------------------------------------
//
//	
//	
////      private SelectHandler createSelectHandler(final PieChart chart) {
////        return new SelectHandler() {
////          @Override
////          public void onSelect(SelectEvent event) {
////            String message = "";
////            
////            // May be multiple selections.
////            JsArray<Selection> selections = chart.getSelections();
////
////            for (int i = 0; i < selections.length(); i++) {
////              // add a new line for each selection
////              message += i == 0 ? "" : "\n";
////              
////              Selection selection = selections.get(i);
////
////              if (selection.isCell()) {
////                // isCell() returns true if a cell has been selected.
////                
////                // getRow() returns the row number of the selected cell.
////                int row = selection.getRow();
////                // getColumn() returns the column number of the selected cell.
////                int column = selection.getColumn();
////                message += "cell " + row + ":" + column + " selected";
////              } else if (selection.isRow()) {
////                // isRow() returns true if an entire row has been selected.
////                
////                // getRow() returns the row number of the selected row.
////                int row = selection.getRow();
////                message += "row " + row + " selected";
////              } else {
////                // unreachable
////                message += "Pie chart selections should be either row selections or cell selections.";
////                message += "  Other visualizations support column selections as well.";
////              }
////            }
////            
//////            Window.alert(message);
////          }
////        };
////      }
//
////      private AbstractDataTable createTable() {
////        DataTable data = DataTable.create();
////        data.addColumn(ColumnType.STRING, "Task");
////        data.addColumn(ColumnType.NUMBER, "Hours per Day");
////        data.addRows(2);
////        data.setValue(0, 0, "Work");
////        data.setValue(0, 1, 14);
////        data.setValue(1, 0, "Sleep");
////        data.setValue(1, 1, 10);
////        return data;
////      }


	@Override
	public void add(Widget w) {
		
	}

	@Override
	public void clear() {
		
	}

	@Override
	public Iterator<Widget> iterator() {
		return null;
	}

	@Override
	public boolean remove(Widget w) {
		return false;
	}
}