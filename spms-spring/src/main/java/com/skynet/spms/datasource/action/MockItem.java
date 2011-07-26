package com.skynet.spms.datasource.action;

import java.util.Date;

public class MockItem {
	protected double unitCost;
	protected String units;
	protected boolean inStock;
	protected Date nextShipmentDate;
	
	public double getUnitCost() {
		return unitCost;
	}

	public String getUnits() {
		return units;
	}

	public boolean getInStock() {
		return inStock;
	}

	public Date getNextShipment() {
		return nextShipmentDate;
	}
	
	public void setUnitCost(double cost) {
		unitCost = cost;
	}

	public void setUnits(String newUnits) {
		units = newUnits;
	}

	public void setInStock(boolean val) {
		inStock = val;
	}

	public void setNextShipment(Date date) {
		nextShipmentDate = date;
	}
}
