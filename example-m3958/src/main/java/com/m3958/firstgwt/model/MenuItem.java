package com.m3958.firstgwt.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.MenuItemCategory;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="MENUITEMS",uniqueConstraints = {@UniqueConstraint(columnNames={"title","menuItemCat"})})
public class MenuItem extends BaseModel<MenuItem>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	
	private String uniqueName;
	
	private int position;

	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.MENUITEM_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	
	@Enumerated(EnumType.STRING)
	private MenuItemCategory menuItemCat;
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="MENUITEM_ID")
	protected int id;
	
	@ManyToMany(mappedBy="menuitems",fetch=FetchType.LAZY)
	private List<MenuLevel> menuLevels;
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setMenuItemCat(MenuItemCategory menuItemCat) {
		this.menuItemCat = menuItemCat;
	}

	public MenuItemCategory getMenuItemCat() {
		return menuItemCat;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public void setMenuLevels(List<MenuLevel> menuLevels) {
		this.menuLevels = menuLevels;
	}

	public List<MenuLevel> getMenuLevels() {
		return menuLevels;
	}
}
