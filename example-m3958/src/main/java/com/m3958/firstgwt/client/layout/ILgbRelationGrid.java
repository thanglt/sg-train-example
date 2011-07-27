package com.m3958.firstgwt.client.layout;

import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.grid.ListGrid;

public interface ILgbRelationGrid {
	void fetchData(String lgbId);
	void removeSelectedData();
	ViewNameEnum getNextView();
	ListGrid asGrid();
	String getLbname();
	ILgbRelationGrid init();
}
