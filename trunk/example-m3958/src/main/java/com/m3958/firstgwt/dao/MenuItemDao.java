package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.MenuItemCategory;
import com.m3958.firstgwt.client.types.MenuItemField;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.MenuItem;

public class MenuItemDao  extends BaseDao<MenuItem>{
	
	@Inject
	public MenuItemDao(MenuItemChangeStrategy task) {
		super(MenuItem.class);
		extraStrategyTask = task;
	}
	
	
	@Override
	public Integer smartCustomCount() {
		String action = getReqPs().getStringValue(MenuItemField.CUSTOM_ACTION.getEname());
		if("oneToMany".equals(action)){
			int mlid = getReqPs().getIntegerValue(MenuItemField.EXTRA_MENULEVEL_ID.getEname());
			MenuItemCategory mcat = (MenuItemCategory) getReqPs().getEnumValue(MenuItemCategory.class,MenuItemField.MENUITEMCAT.getEname());
			String qs = "SELECT COUNT(m) FROM MenuItem AS m,IN(m.menuLevels) AS l WHERE l.id = :mlId AND m.menuItemCat = :cat";
			Query q = emp.get().createQuery(qs);
			q.setParameter("mlId", mlid);
			q.setParameter("cat", mcat);
			Long l = (Long) q.getSingleResult();
			return l.intValue();
		}
		
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseModel> smartCustomFetch() {
		String action = getReqPs().getStringValue(MenuItemField.CUSTOM_ACTION.getEname());
		if("oneToMany".equals(action)){
			int mlid = getReqPs().getIntegerValue(MenuItemField.EXTRA_MENULEVEL_ID.getEname());
			MenuItemCategory mcat = (MenuItemCategory) getReqPs().getEnumValue(MenuItemCategory.class,MenuItemField.MENUITEMCAT.getEname());
			String qs = "SELECT m FROM MenuItem AS m,IN(m.menuLevels) AS l WHERE l.id = :mlId AND m.menuItemCat = :cat";
			Query q = emp.get().createQuery(qs);
			q.setParameter("mlId", mlid);
			q.setParameter("cat", mcat);
			return q.getResultList();
		}
		return null;
	}


	@Override
	public List<MenuItem> smartNamedQueryFetch() {
		return null;
	}


	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}
