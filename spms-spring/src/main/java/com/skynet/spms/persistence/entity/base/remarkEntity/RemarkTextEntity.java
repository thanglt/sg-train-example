package com.skynet.spms.persistence.entity.base.remarkEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.skynet.spms.persistence.entity.base.baseEntity.BaseIDEntity;

/**
 * @author 曹宏炜
 * @version 1.0
 * @created 05-五月-2011 11:15:41
 */
@Entity
@Table(name="SPMS_REMARKTEXTENTITY")
public class RemarkTextEntity extends BaseIDEntity{

	
	private String remarkText;
	@Column(name="REMARKTEXT")
	public String getRemarkText() {
		return remarkText;
	}

	public void setRemarkText(String remarkText) {
		this.remarkText = remarkText;
	}
	
}