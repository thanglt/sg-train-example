package com.mycompany.java.sales.entity;

import com.mycompany.java.common.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: 用于测试的类-订单
 */
@Entity
@Table(name = "tbl_sales_order")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String code; //订单编号
    private String remark;//备注

    public Order() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
