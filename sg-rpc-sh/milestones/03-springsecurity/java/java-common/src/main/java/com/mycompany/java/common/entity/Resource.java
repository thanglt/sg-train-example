package com.mycompany.java.common.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * 实体类 - 资源
 */

@Entity
@Table(name="tbl_common_resource")
public class Resource extends BaseEntity{

	private static final long serialVersionUID = 8231644891304446093L;

	public static final String SEPARATOR = ",";

	private String name;// 资源名称
	private String value;// 资源标识
	private Boolean isSystem;// 是否为系统内置资源
	private String description;// 描述
	private Integer seq;// 排序
	
	private Set<Role> roleSet;// 权限

    @Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(nullable = false, updatable = false)
	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	@Column(length = 5000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(nullable = false)
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@ManyToMany(mappedBy = "resourceSet", fetch = FetchType.EAGER)
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	// 获取权限字符串（以分隔符间隔）
	@Transient
	public String getRoleSetString() {
		StringBuffer stringBuffer = new StringBuffer();
		if(this.roleSet == null || this.roleSet.size() == 0) {
			return "";
		}
		for (Role role : this.roleSet) {
			stringBuffer.append(SEPARATOR + role.getValue());
		}
		if (stringBuffer.length() > 0) {
			stringBuffer.deleteCharAt(0);
		}
		return stringBuffer.toString();
	}

}