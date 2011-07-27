package com.m3958.firstgwt.server.types;

import java.util.Comparator;

import com.m3958.firstgwt.model.BaseModel;

public class LimitFiledOrderComparator<T extends BaseModel<T>> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
	}

}
