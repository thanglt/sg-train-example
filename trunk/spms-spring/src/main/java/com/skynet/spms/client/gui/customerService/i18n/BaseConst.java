package com.skynet.spms.client.gui.customerService.i18n;

import com.google.gwt.i18n.client.Messages;

/**
 * 基本国际化常量
 * 
 * @author tqc
 * 
 */
public interface BaseConst extends Messages {

	@DefaultMessage("新增{0}")
	String addFormTitle(String domain);

	@DefaultMessage("修改{0}")
	String modifyFormTitle(String domain);

	@DefaultMessage("关闭")
	String btnClose();

	@DefaultMessage("取消")
	String btnCancel();

	@DefaultMessage("保存添加")
	String btnSaveAdd();

	@DefaultMessage("删除")
	String btnRemove();

	@DefaultMessage("保存添加并发布")
	String btnSaveAddAndPublish();

	@DefaultMessage("保存修改")
	String btnSaveModify();

	@DefaultMessage("保存修改并发布")
	String btnSaveModifyAndPublish();

	@DefaultMessage("重置")
	String btnClearForm();

	@DefaultMessage("保存成功")
	String msgAddOpSuccess();

	@DefaultMessage("修改成功")
	String msgModifyOpSuccess();

	@DefaultMessage("删除成功")
	String msgRemoveOpSuccess();

	@DefaultMessage("请选择一条您要操作的数据")
	String msgOpSeletedData();
	
	@DefaultMessage("业务编号系统自动生成")
	String msgAutoIdInfo();
	
	@DefaultMessage("您确定要删除该记录吗?")
	String msgDelWarn();
	

	String listTitlePublishStatus();

	String listTitleBusStatus();

	String validateMsgNotNull(String item);

	String validateMsgInt(String item);

}
