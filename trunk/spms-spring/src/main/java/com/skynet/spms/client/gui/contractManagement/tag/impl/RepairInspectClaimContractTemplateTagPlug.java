package com.skynet.spms.client.gui.contractManagement.tag.impl;

import java.util.List;
import java.util.Map;

import com.skynet.spms.client.gui.contractManagement.tag.BaseTagPlug;
import com.skynet.spms.client.gui.contractManagement.tag.Tag;
import com.skynet.spms.client.gui.contractManagement.tag.TagEnum;

public class RepairInspectClaimContractTemplateTagPlug extends BaseTagPlug {

	public RepairInspectClaimContractTemplateTagPlug() {
		tagType = TagEnum.RepairInspectClaimContractTemplate;
	}

	@Override
	public void plug(Map<TagEnum, List<Tag>> tags) {
		// 合同编号
		Tag contractNumberTag = new Tag("合同编号", "contractNumber");
		// 优先级
		Tag priorityTag = new Tag("优先级", "m_Priority");
		// 飞机机尾号
		Tag airIdentificationNumberTag = new Tag("飞机机尾号",
				"airIdentificationNumber");
		// 是否指定运代
		Tag buyerFreightAgentTag = new Tag("是否指定运代", "buyerFreightAgent");
		// 运代商
		Tag carrierNameTag = new Tag("运代商 ", "carrierName");
		// 运代商联系人
		Tag carrierLinkManTag = new Tag("运代商联系人", "carrierLinkMan");
		// 运代商联系方式
		Tag carrierLinkWayTag = new Tag("运代商联系方式", "carrierLinkWay");
		// 交货方式
		Tag deliveryTypeTag = new Tag("交货方式", "m_DeliveryType");
		// 运输方式
		Tag transportationCodeTag = new Tag("运输方式", "m_TransportationCode");
		// 贸易方式
		Tag tradeMethodsTag = new Tag("贸易方式", "m_TradeMethods");
		// GTA协议
		Tag enterpriseIdsTag = new Tag("GTA协议", "enterpriseIds");

		// 批次加入
		addModuleTag(contractNumberTag, priorityTag,
				airIdentificationNumberTag, buyerFreightAgentTag,
				carrierNameTag, carrierLinkManTag, carrierLinkWayTag,
				deliveryTypeTag, transportationCodeTag, tradeMethodsTag,
				enterpriseIdsTag);

		// 加入tag Map
		tags.put(tagType, moduleTags);
	}

}
