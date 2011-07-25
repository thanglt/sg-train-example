/**
 * 
 */
package com.skynet.spms.client.gui.basedatamanager.logisticsCustomsDeclaration.customsDeclaration.exportCustomsDeclaration.exportCustomsDeclaration;

import com.skynet.spms.client.PanelFactory;
import com.skynet.spms.client.ShowcasePanel;
import com.skynet.spms.client.entity.DataInfo;
import com.skynet.spms.client.feature.data.DataSourceTool;
import com.skynet.spms.client.feature.data.DataSourceTool.PostDataSourceInit;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * @author Administrator
 *
 */
public class ExportCustomsDeclarationPanel extends ShowcasePanel {

	private static final String DESCRIPTION = "出口报关信息维护页面";

	private VLayout mainPanelLayout;
	private SectionStack mainPanelSection;

	public static class Factory implements PanelFactory {

		private String DESCRIPTION = "出口报关管理模块";
		private String id;
		
		public Canvas create() {
			ExportCustomsDeclarationPanel panel = new ExportCustomsDeclarationPanel();
			
			id = panel.getID();
			return panel;
		}
	
		public String getID() {
			return id;
		}
		
		public String getDescription() {
			return DESCRIPTION;
		}
	}
	
	public Canvas getViewPanel() {
		// 主Layout
		mainPanelLayout = new VLayout();
		mainPanelLayout.setLayoutTopMargin(5);
		mainPanelLayout.setMembersMargin(2);
		mainPanelLayout.setWidth100();
		mainPanelLayout.setHeight100();
		
		// 主列表Grid
		final ExportCustomsDeclarationListgrid exportCustomsDeclarationListgrid = new ExportCustomsDeclarationListgrid();
		exportCustomsDeclarationListgrid.setHeight("50%");
        
		//获得数据源
		String modeName = "logisticsCustomsDeclaration.dispatchLogisticsTask.customsClearanceJob.exportCustomsDeclaration.exportCustomsDeclaration";
		String dsName = "exportCustomsDeclaration_dataSource";

		// 取得Grid中需要显示的数据源
		DataSourceTool dataSourceTool = new DataSourceTool();	
		dataSourceTool.onCreateDataSource(modeName, dsName,
				new PostDataSourceInit() {
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						exportCustomsDeclarationListgrid.setDataSource(dataSource);
						Criteria criteria = new Criteria();
						criteria.addCriteria("workStatus", "2");
						exportCustomsDeclarationListgrid.fetchData(criteria);
						exportCustomsDeclarationListgrid.drawExportCustomsDeclarationListgrid();
					}
				});
		
		// 明细列表Grid
		final ExportCustomsDeclarationItemsListgrid exportCustomsDeclarationItemsListgrid = new ExportCustomsDeclarationItemsListgrid();
		exportCustomsDeclarationItemsListgrid.setAutoSaveEdits(false);
		exportCustomsDeclarationItemsListgrid.setEditEvent(ListGridEditEvent.CLICK);
		exportCustomsDeclarationItemsListgrid.setHeight("100%");
		// 获取数据源
		String detailmodeName = "logisticsCustomsDeclaration.dispatchLogisticsTask.customsClearanceJob.exportCustomsDeclaration.exportCustomsDeclaration";
		String detaildsName = "exportCustomsDeclarationItems_dataSource";
		DataSourceTool detaildataSourceTool = new DataSourceTool();
		detaildataSourceTool.onCreateDataSource(detailmodeName, detaildsName,
				new PostDataSourceInit() {
					@Override
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						exportCustomsDeclarationItemsListgrid.setDataSource(dataSource);
						exportCustomsDeclarationItemsListgrid.drawExportCustomsDeclarationItemsListgrid();
					}
				});
		
		// 根据选择的仓库，取得相应的逻辑库
		exportCustomsDeclarationListgrid.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				exportCustomsDeclarationItemsListgrid.setData(new ListGridRecord[]{});
				exportCustomsDeclarationItemsListgrid.fetchData(new Criteria("customsID",
						exportCustomsDeclarationListgrid.getSelectedRecord().getAttribute("id").toString()));
			}
		});
		
		// 共用按钮面板
		final ExportCustomsDeclarationButtonPanel exportCustomsDeclarationButtonPanel =
			new ExportCustomsDeclarationButtonPanel(exportCustomsDeclarationListgrid, exportCustomsDeclarationItemsListgrid);
		
		// 加载各面板到容器
		HLayout detailHLayout = new HLayout();
		detailHLayout.addMember(exportCustomsDeclarationItemsListgrid);
		VLayout detailVLayout = new VLayout();
		detailVLayout.addMember(detailHLayout);

		// 主列表面板
		SectionStackSection headSection = new SectionStackSection("出口报关信息");
		headSection.setItems(exportCustomsDeclarationListgrid);
		headSection.setExpanded(true);

		// 明细列表面板
		SectionStackSection detailSection = new SectionStackSection("出口报关明细信息");
		detailSection.setItems(detailVLayout);
		detailSection.setExpanded(true);

		// 主Section容器
		mainPanelSection = new SectionStack();
		mainPanelSection.setHeight100();
		mainPanelSection.setVisibilityMode(VisibilityMode.MULTIPLE);
		mainPanelSection.setAnimateSections(true);
		
		// 加载各面板到容器
		mainPanelSection.addSection(headSection);
		mainPanelSection.addSection(detailSection);
		mainPanelLayout.addMember(exportCustomsDeclarationButtonPanel);
		mainPanelLayout.addMember(mainPanelSection);

		return mainPanelLayout;
	}
}
