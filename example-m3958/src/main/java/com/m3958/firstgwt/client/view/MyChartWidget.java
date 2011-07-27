package com.m3958.firstgwt.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Singleton;

@Singleton
public class MyChartWidget extends Composite{
//	private DecoratedTabPanel chartTabPanel = new DecoratedTabPanel();
//	
//	private final VerticalPanel lgbStaticsTab = new VerticalPanel();
//	private final VerticalPanel visitStaticsTab = new VerticalPanel();
//	
//	private enum TabName{
//		LGB_STATICS_TAB("老干部信息统计"),
//		VISIT_STATICS_TAB("访问信息统计");
//		
//		private String title;
//		TabName(String tn){
//			this.setTitle(tn);
//		}
//		public void setTitle(String title) {
//			this.title = title;
//		}
//		public String getTitle() {
//			return title;
//		}
//	}
//	
//	@SuppressWarnings("unused")
//	private TabName defaultTab = TabName.LGB_STATICS_TAB;
//	
//	
//	@Inject
//	public MyChartWidget(MyTaWidget myta){
//        chartTabPanel.setWidth("100%");
//        chartTabPanel.setHeight("100%");
//        chartTabPanel.setAnimationEnabled(true);
//         
//        initLgbStaticsTab();
//        initVisitStaticsTab();
//
//        chartTabPanel.add(myta,TabName.LGB_STATICS_TAB.getTitle());
//        chartTabPanel.add(visitStaticsTab, TabName.VISIT_STATICS_TAB.getTitle());
//        
//        chartTabPanel.selectTab(0);
//        chartTabPanel.ensureDebugId("cwTabPanel");
//        
//        initWidget(chartTabPanel);
//	}
//	
//	
//
//	private void initVisitStaticsTab() {
//        visitStaticsTab.setSpacing(15);
//        visitStaticsTab.setHeight("100%");
//        
//        Button gwtButton = new Button("in progress...",new ClickHandler(){
//			@Override
//			public void onClick(ClickEvent event) {
//			}});
//        visitStaticsTab.add(gwtButton);
//	}
//
//
//	private void initLgbStaticsTab() {
//        lgbStaticsTab.setStyleName("vpDotted");
//        lgbStaticsTab.setHeight("100%");
//        HTML homeText = new HTML("I am a GWT 'HTML' Widget. Click one of the tabs to see more content.");
//        lgbStaticsTab.add(homeText);
//	}

}