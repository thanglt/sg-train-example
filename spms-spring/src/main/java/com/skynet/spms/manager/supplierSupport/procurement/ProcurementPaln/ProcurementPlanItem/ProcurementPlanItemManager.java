package com.skynet.spms.manager.supplierSupport.procurement.ProcurementPaln.ProcurementPlanItem;

/**
 * 采购计划明细接口
 * 
 * @version 1.0
 * @author 赵小强
 * @date 2011-07-011
 */
import java.util.List;
import java.util.Map;

import com.skynet.spms.persistence.entity.supplierSupport.procurement.ProcurementPlan.procurementPlanItem.ProcurementPlanItem;

public interface ProcurementPlanItemManager {
	/**
	 * 
	 * 添加采购计划明细
	 * 
	 * @param 采购计划明细对象
	 * @return void
	 */

	public void addProcurementPlanItem(ProcurementPlanItem o);

	/**
	 * 
	 * 删除采购计划明细的方法
	 * 
	 * @param 采购计划明细ID
	 * @return void
	 */
	public void deleteProcurementPlanItem(String id);

	/**
	 * 
	 * 修改采购计划明细
	 * 
	 * @param 新数据对象
	 * @param 采购计划明细ID
	 * @return 采购计划明细对象
	 */
	public ProcurementPlanItem updateProcurementPlanItem(
			Map<String, Object> newValues, String itemID);

	/**
	 * 
	 * 分页查询采购计划明细的方法
	 * 
	 * @param 首页
	 * @param 尾页
	 * @return 采购计划明细对象集合
	 */
	public List<ProcurementPlanItem> queryProcurementPlanItemList(int startRow,
			int endRow);

	/**
	 * 
	 * 根据ID查询采购计划明细
	 * 
	 * @param 采购计划明细的ID
	 * @return 采购计划明细对象集合
	 */
	public List<ProcurementPlanItem> queryProcurementPlanItemListById(String id);

	/**
	 * 
	 * 根据采购计划编号查询采购计划明细数据
	 * 
	 * @param 采购计划编号
	 * @return 采购计划编号对象集合
	 */
	public List<ProcurementPlanItem> queryProcurementOrderByprocurementPlanNumber(
			String procurementPlanNumber);
}
