package com.m3958.firstgwt.dao;


import com.m3958.firstgwt.client.types.MenuItemCategory;
import com.m3958.firstgwt.client.types.MenuItemField;
import com.m3958.firstgwt.model.MenuItem;
import com.m3958.firstgwt.model.MenuLevel;



public class MenuItemChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<MenuItem> {

	@Override
	public boolean extraPersistTask(MenuItem model){
		setInitCat(model);
		return true;
	}

	@Override
	public boolean extraUpdateTask(MenuItem model,MenuItem newModel){
		setInitCat(model);
		return true;
	}
	
	@Override
	public boolean extraRemoveTask(MenuItem model) {
		for(MenuLevel ml : model.getMenuLevels()){
			ml.getMenuitems().remove(model);
		}
		return true;
	}
	
	private boolean setInitCat(MenuItem model){
		if(getReqPs() != null){
			String cat = getReqPs().getStringValue(MenuItemField.MENUITEMCAT.getEname());
			model.setMenuItemCat(MenuItemCategory.valueOf(cat));
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean afterPersist(MenuItem model) {
		return false;
	}
}

	
