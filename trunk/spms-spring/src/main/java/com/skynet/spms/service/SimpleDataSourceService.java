package com.skynet.spms.service;

import java.util.Map;

import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;

public interface SimpleDataSourceService {

	Object remove(DSRequest dsRequest,Map item) throws Exception;

	DSResponse update(DSRequest dsRequest, Map newValues) throws Exception;

	DSResponse add(DSRequest dsRequest, Map item) throws Exception;

	DSResponse fetch(DSRequest dsRequest) throws Exception;

}
