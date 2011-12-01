package com.mycompany.gwt.common.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;
import java.util.Map;

public interface GenericGwtRpcServiceAsync<D> {

	void add(D data,
             AsyncCallback<D> callback);

	void fetch(Integer startRow, Integer endRow, String sortBy, Map<String, String> filterCriteria, AsyncCallback<List<D>> callback);

	void remove(D data, AsyncCallback<Void> callback);

	void update(D data,
                AsyncCallback<D> callback);

}
