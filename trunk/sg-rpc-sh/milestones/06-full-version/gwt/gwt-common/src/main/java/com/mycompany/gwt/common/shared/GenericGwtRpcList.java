package com.mycompany.gwt.common.shared;

import java.util.ArrayList;

/**
 * Represents a {@link java.util.List} for transferring Data Transfer Objects using {@link com.mycompany.gwt.common.client.GenericGwtRpcDataSource} in paged mode.
 * Use it to set totalRows, which will tell your Smart GWT canvas how many rows to except. Set this in the fetch()-method of your {@link com.mycompany.gwt.common.client.GenericGwtRpcService}-implementation.
 * Inherits {@link java.util.ArrayList}.
 *
 * @param <D> type of the transfer object holding the data (will most likely be
 *            a simple POJO), must implement {@link java.io.Serializable} or {@link com.google.gwt.user.client.rpc.IsSerializable}.
 * @author Francois Marbot
 */
@SuppressWarnings("serial")
public class GenericGwtRpcList<D> extends ArrayList<D> {

    private Integer totalRows;

    /**
     * @return Total number of rows available from the server that match the current filter criteria, when using paged result fetching.
     */
    public Integer getTotalRows() {
        return totalRows;
    }

    /**
     * Total number of rows available from the server that match the current filter criteria, when using paged result fetching.
     * Set this in the fetch()-method of your {@link com.mycompany.gwt.common.client.GenericGwtRpcService}-implementation.
     *
     * @param totalRows the total number of rows to except.
     */
    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

}
