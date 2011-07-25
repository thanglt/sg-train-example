package com.skynet.spms.persistence.entity.base.logEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.skynet.spms.persistence.entity.base.baseEntity.BaseEntity;
import com.skynet.spms.persistence.entity.base.baseEntity.BaseIDEntity;
import com.skynet.spms.persistence.entity.base.baseEntity.BaseStatusEntity;
import com.skynet.spms.persistence.entity.spmsdd.GeneralOperateLogStatus;

/**
 * @author 曹宏炜
 * @version 1.0
 * @created 10-三月-2011 11:10:46
 */
@Entity
@Table(name = "SPMS_GENE_OPER_LOG")
public class GeneralOperateLogEntity extends BaseIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3472149793181056687L;

	public GeneralOperateLogStatus m_GeneralOperateLogStatus;

	private String recordId;

	private String recordName;

	private String operator;
		
	private Date actionDate;
	
	private String actionDescription;
	
	private int version;
	
	public void setVersion(int ver){
		this.version=ver;
	}
	
	@Column(name="VERSION")
	public int getVersion(){
		return version;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "OPERATE_STATUS")
	public GeneralOperateLogStatus getM_GeneralOperateLogStatus() {
		return m_GeneralOperateLogStatus;
	}

	@Column(name = "RECORD_ID")
	public String getRecordId() {
		return recordId;
	}

	@Column(name = "RECORD_NAME")
	public String getRecordName() {
		return recordName;
	}

	public void setM_GeneralOperateLogStatus(
			GeneralOperateLogStatus m_GeneralOperateLogStatus) {
		this.m_GeneralOperateLogStatus = m_GeneralOperateLogStatus;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	@Column(name = "OPERATOR")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	

	@Column(name = "ACTION_DATE")
	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	@Lob
	@Column(name = "ACTION_DESCRIPTION")
	public String getActionDescription() {
		return actionDescription;
	}

	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

}