package com.mycompany.gwt.sales.client.datasource;

import com.google.gwt.core.client.GWT;
import com.google.inject.Singleton;
import com.mycompany.gwt.common.client.GenericGwtRpcDataSource;
import com.mycompany.gwt.sales.client.dto.OrderDto;
import com.mycompany.gwt.sales.client.service.OrderServiceRPC;
import com.mycompany.gwt.sales.client.service.OrderServiceRPCAsync;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: put description for the type here...
 */
@Singleton
public class OrderDataSource extends GenericGwtRpcDataSource<OrderDto, Record, OrderServiceRPCAsync> {
    @Override
    public List<DataSourceField> getDataSourceFields() {
        List<DataSourceField> fields = new ArrayList<DataSourceField>();

        DataSourceTextField idField = new DataSourceTextField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);
        fields.add(idField);

        DataSourceTextField orderCodeField = new DataSourceTextField("code", "Order Code");
        orderCodeField.setRequired(true);
        fields.add(orderCodeField);

        DataSourceTextField amountField = new DataSourceTextField("remark", "Order Remark");
        amountField.setRequired(true);
        fields.add(amountField);

        return fields;
    }

    @Override
    public void copyValues(Record from, OrderDto to) {
        to.setId(from.getAttributeAsString("id"));
        to.setCode(from.getAttribute("code"));
        to.setRemark(from.getAttribute("remark"));
    }

    @Override
    public void copyValues(OrderDto from, Record to) {
        to.setAttribute("id", from.getId());
        to.setAttribute("code", from.getCode());
        to.setAttribute("remark", from.getRemark());
    }

    @Override
    public OrderServiceRPCAsync getServiceAsync() {
        return GWT.create(OrderServiceRPC.class);
    }

    @Override
    public Record getNewRecordInstance() {
        return new ListGridRecord();
    }

    @Override
    public OrderDto getNewDataObjectInstance() {
        return new OrderDto();
    }
}
