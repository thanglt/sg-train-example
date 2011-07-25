package com.skynet.spms.client.gui.commonOrder.delivery.business;

import com.skynet.spms.client.gui.base.BaseBusiness;
import com.skynet.spms.client.gui.base.ValidateUtil;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;

public class DeliveryOrderBusiness extends BaseBusiness{

	/**
	 * 发布
	 * 
	 * @param grid
	 */
	@Override
	public void publishSheet(final ListGrid grid) {
		if (ValidateUtil.isRecordSelected(grid)) {
			final Record target = grid.getSelectedRecord();// 获得选中的数据
			String state = target.getAttribute("isPublish");
			if (state.equals("1")) {
				SC.say("信息已经发布过了！");
				return;
			}
			target.setAttribute("isPublish", "1");// 设置发布状态为 已状态
			grid.updateData(target, new DSCallback() {
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					SC.say("发布成功！");
					grid.selectRecord(target);

				}

			});

		}
	}

	/**
	 * 取消发布
	 * 
	 * @param grid
	 */
	@Override
	public void cancelPublishSheet(final ListGrid grid) {
		if (ValidateUtil.isRecordSelected(grid)) {
			final Record target = grid.getSelectedRecord();// 获得选中的数据
			String state = target.getAttribute("isPublish");
			if (state.equals("0")) {
				SC.warn("信息尚未发布！！");
				return;
			}
			SC.ask("确定要取消发布吗？", new BooleanCallback() {
				@Override
				public void execute(Boolean value) {
					if (value) {
						target.setAttribute("isPublish", "0");// 设置状态
						grid.updateData(target, new DSCallback() {
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
								SC.say("取消发布成功！");
								grid.selectRecord(target);
							}
						});

					}
				}
			});
		}
	}

}
