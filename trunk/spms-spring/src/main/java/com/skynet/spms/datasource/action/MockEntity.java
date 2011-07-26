package com.skynet.spms.datasource.action;

import java.util.Date;
import java.util.List;

public class MockEntity {
	// a zero-argument constructor is not required, but does enable certain
	// convenience
	// features (see the docs for DMI)
	public MockEntity() {
	}

	// when receiving data from client-side SmartClient components, SmartClient
	// will call these
	// setters to modify properties. The setters are found via the Java Beans
	// naming
	// convention, for example, a DataSource field named "category" will be
	// applied via a
	// setter called setCategory().
	public void setItemID(Long id) {
		itemID = id;
	}

	public void setSKU(String sku) {
		SKU = sku;
	}

	public void setCategory(String c) {
		category = c;
	}

	public void setItemName(String name) {
		itemName = name;
	}

	public void setDescription(String d) {
		description = d;
	}



	// SmartClient will call these getters when serializing a Java Bean to be
	// transmitted to
	// client-side components.
	public Long getItemID() {
		return itemID;
	}

	public String getSKU() {
		return SKU;
	}

	public String getCategory() {
		return category;
	}

	public String getItemName() {
		return itemName;
	}

	public String getDescription() {
		return description;
	}



	// this bean has no business logic. It simply stores data in these instance
	// variables.
	protected Long itemID;
	protected String SKU;
	protected String category;
	protected String itemName;
	protected String description;
	private MockItem item;
	
	public void setItem(MockItem item){
		this.item=item;
	}
	
	public MockItem getItem(){
		return item;
	}

	private List<SubItem> itemList;
	
	private SubItem subItem;
	
	public SubItem getSubItem(){
		return subItem;
	}
	
	public void setSubItem(SubItem item){
		this.subItem=item;
	}
	
	public List<SubItem> getItemList(){
		return itemList;
	}
	
	public void setItemList(List<SubItem> itemList){
		this.itemList=itemList;
	}
}
