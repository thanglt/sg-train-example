package com.m3958.firstgwt.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.server.types.HasRelation;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="MENULEVELS",uniqueConstraints = {@UniqueConstraint(columnNames={"name"})})
public class MenuLevel extends BaseModel<MenuLevel> implements HasRelation{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.MENULEVEL_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	
	private String name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="MENULEVEL_ID")
	protected int id;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="MENU_LEVEL_ITEM",
				joinColumns=
					@JoinColumn(name="MENULEVEL_MENULEVEL_ID"),
				inverseJoinColumns=
					@JoinColumn(name="MENUITEMS_MENUITEM_ID")
	)
	@OrderBy("position ASC")
	private List<MenuItem> menuitems;
	
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setMenuitems(List<MenuItem> menuitems) {
		this.menuitems = menuitems;
	}

	public List<MenuItem> getMenuitems() {
		return menuitems;
	}

	@Override
	public boolean manageRelation(BaseModel bm, boolean isAdd,String hint,ErrorMessages errors) {
		if(isAdd){
			if(bm instanceof MenuItem){
				if(menuitems.contains(bm)){
					return false;
				}else{
					menuitems.add((MenuItem) bm);
					return true;
				}
			}
		}else{// is remove
			if(bm instanceof MenuItem){			
				if(menuitems.contains(bm)){
					menuitems.remove(bm);
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
}
