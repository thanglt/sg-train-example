package com.m3958.firstgwt.client.gin;

import com.google.inject.Provider;
import com.m3958.firstgwt.client.widgets.MainMenuListGrid;
import com.smartgwt.client.widgets.grid.ListGrid;


public class MainMenuGridProvider implements Provider<ListGrid>{
	

	@Override
	public ListGrid get() {
		return new MainMenuListGrid();
	}

}
