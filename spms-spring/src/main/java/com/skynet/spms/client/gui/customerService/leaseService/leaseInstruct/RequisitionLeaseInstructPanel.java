package com.skynet.spms.client.gui.customerService.leaseService.leaseInstruct;

import com.skynet.spms.client.PanelFactory;
import com.skynet.spms.client.ShowcasePanel;
import com.skynet.spms.client.entity.DataInfo;
import com.skynet.spms.client.feature.data.DataSourceTool;
import com.skynet.spms.client.feature.data.DataSourceTool.PostDataSourceInit;
import com.skynet.spms.client.gui.customerService.common.DSKey;
import com.skynet.spms.client.gui.customerService.leaseService.model.MainModelLocator;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class RequisitionLeaseInstructPanel extends ShowcasePanel {
	private MainModelLocator modelLocator = MainModelLocator.getInstance();
	private RequisitionLeaseInstructListGrid requisitionLeaseContractListGrid;

	public static class Factory implements PanelFactory {

		private String DESCRIPTION = "申请供应商租赁指令";
		private String id;

		public Canvas create() {
			RequisitionLeaseInstructPanel panel = new RequisitionLeaseInstructPanel();
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
		VLayout v = new VLayout();
		requisitionLeaseContractListGrid = new RequisitionLeaseInstructListGrid();
		// 创建主容器
		SectionStack s = new SectionStack();
		// 创建数据源
		SectionStackSection s1 = new SectionStackSection("申请供应商租赁指令");

		final DataSourceTool dataSourceTool = new DataSourceTool();
		dataSourceTool.onCreateDataSource(DSKey.C_LEASEINSTRUCT,
				DSKey.C_LEASEINSTRUCT_DS, new PostDataSourceInit() {
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						requisitionLeaseContractListGrid
								.setDataSource(dataSource);
						requisitionLeaseContractListGrid.fetchData();
						requisitionLeaseContractListGrid.drawGrid();
						modelLocator.leaseInstructListGrid = requisitionLeaseContractListGrid;
					}
				});
		dataSourceTool.onCreateDataSource(DSKey.C_LEASECONTRACT_ITEM,
				DSKey.C_LEASECONTRACT_TIEM_DS,
				new PostDataSourceInit() {
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						modelLocator.leaseContractItemDs = dataSource;
					}
				});
		s1.addItem(requisitionLeaseContractListGrid);
		s.addSection(s1);
		HLayout h1 = new HLayout();
		h1.setHeight(20);
		RequisitionLeaseInstructToolStrip ts = new RequisitionLeaseInstructToolStrip(
				requisitionLeaseContractListGrid);
		h1.addMember(ts);
		v.addMember(h1);
		v.addMember(s);
		return v;
	}

}
