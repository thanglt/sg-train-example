package com.skynet.spms.client.gui.customerService.leaseService.leasecontract.ui;

import com.skynet.spms.client.gui.base.SuperWindow;
import com.skynet.spms.client.widgets.form.LayoutDynamicForm;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LeaseContractItemAddWindow extends SuperWindow {

	public LeaseContractItemAddWindow(String windowTitle, boolean isMax,
			Rectangle srcRect, ListGrid listGrid, String iconUrl) {
	}

	protected Canvas getLeftLayout(Rectangle srcRect, ListGrid listGrid) {

		VLayout v = new VLayout();
		LayoutDynamicForm form = new LayoutDynamicForm();
		form.setNumCols(6);
		form.setWidth100();
		SelectItem item1 = new SelectItem();
		item1.setTitle("件号");
		item1.setName("item1");
		item1.setColSpan(3);
		FormItemIcon icon = new FormItemIcon();
		item1.setIcons(icon);
		icon.addFormItemClickHandler(new FormItemClickHandler() {
			public void onFormItemClick(FormItemIconClickEvent event) {
			}
		});
		SelectItem item2 = new SelectItem();
		item2.setTitle("备件状态代码");
		item2.setName("item2");
		SelectItem item3 = new SelectItem();
		item3.setTitle("制造商代码");
		item3.setName("item3");
		SelectItem item4 = new SelectItem();
		item4.setTitle("包装代码");
		item4.setName("item4");
		SelectItem item5 = new SelectItem();
		item5.setTitle("时寿代码");
		item5.setName("item5");
		SelectItem item6 = new SelectItem();
		item6.setTitle("机型识别码");
		item6.setName("item6");
		SelectItem item7 = new SelectItem();
		item7.setTitle("证书类型");
		item7.setName("item7");
		SelectItem item8 = new SelectItem("");
		item8.setTitle("担保时间/循环代码");
		item8.setName("item8");
		TextItem item9 = new TextItem();
		item9.setTitle("数量");
		item9.setName("item9");
		SelectItem item10 = new SelectItem();
		item10.setTitle("单位测量代码");
		item10.setName("item10");
		TextAreaItem item11 = new TextAreaItem();
		item11.setTitle("备注");
		item11.setName("item11");
		item11.setColSpan(6);
		item11.setWidth(680);
		item11.setHeight(100);
		TextItem item12 = new TextItem();
		item12.setTitle("原价值");
		item12.setName("item12");
		SelectItem item13 = new SelectItem();
		item13.setTitle("国际货币代码");
		item13.setName("item13");
		SelectItem item14 = new SelectItem();
		item14.setTitle("租金计算单位");
		item14.setName("item14");
		DateItem item15 = new DateItem();
		item15.setTitle("租赁开始日期");
		item15.setName("item15");
		DateItem item16 = new DateItem();
		item16.setTitle("租赁结束日期");
		item16.setName("item16");
		TextItem item17 = new TextItem();
		item17.setTitle("租赁天数");
		item17.setName("item17");
		TextItem item18 = new TextItem();
		item18.setTitle("标准单位租金");
		item18.setName("item18");
		TextItem item19 = new TextItem();
		item19.setTitle("延期单位租金");
		item19.setName("item19");
		TextItem item20 = new TextItem();
		item20.setTitle("手续费");
		item20.setName("item20");

		form.setFields(item1, item2, item3, item4, item5, item6, item7, item8,
				item9, item10, item11, item12, item13, item14, item15, item16,
				item17, item18, item19, item20);
		// // v.setMembers(form);
		IButton btn1 = new IButton("保存");
		IButton btn2 = new IButton("取消");
		HLayout h = new HLayout();
		h.setHeight(50);
		h.setMembers(btn1, btn2);
		v.addMember(form);
		v.addMember(h);
		return v;
	}
}
