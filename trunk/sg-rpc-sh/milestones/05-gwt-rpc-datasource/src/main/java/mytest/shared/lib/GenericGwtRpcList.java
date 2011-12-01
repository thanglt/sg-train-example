package mytest.shared.lib;

import mytest.client.lib.GenericGwtRpcDataSource;
import mytest.client.lib.GenericGwtRpcService;

import java.util.ArrayList;

/**
 * Represents a {@link java.util.List} for transferring Data Transfer Objects using {@link mytest.client.lib.GenericGwtRpcDataSource} in paged mode.
 * Use it to set totalRows, which will tell your Smart GWT canvas how many rows to except. Set this in the fetch()-method of your {@link mytest.client.lib.GenericGwtRpcService}-implementation.
 * Inherits {@link java.util.ArrayList}.
 * 
 * @author Francois Marbot
 *
 * @param <D> 
 *            type of the transfer object holding the data (will most likely be
 *            a simple POJO), must implement {@link java.io.Serializable} or {@link com.google.gwt.user.client.rpc.IsSerializable}.
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
	 * Set this in the fetch()-method of your {@link mytest.client.lib.GenericGwtRpcService}-implementation.
	 * 
	 * @param totalRows
	 * 	the total number of rows to except.
	 */
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

}
