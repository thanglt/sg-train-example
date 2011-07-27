package com.m3958.firstgwt.servlet;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Singleton;
import com.google.visualization.datasource.DataSourceServlet;
import com.google.visualization.datasource.base.DataSourceException;
import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.google.visualization.datasource.query.Query;

@Singleton
public class SimpleExampleServlet extends DataSourceServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public DataTable generateDataTable(Query query, HttpServletRequest request)
			throws DataSourceException {
	    DataTable data = new DataTable();
	    ArrayList cd = new ArrayList();
	    cd.add(new ColumnDescription("name", ValueType.TEXT, "Animal name"));
	    cd.add(new ColumnDescription("link", ValueType.TEXT, "Link to wikipedia"));
	    cd.add(new ColumnDescription("population", ValueType.NUMBER, "Population size"));
	    cd.add(new ColumnDescription("vegeterian", ValueType.BOOLEAN, "Vegetarian?"));

	    data.addColumns(cd);

	    // Fill the data table.
	    try {
	      data.addRowFromValues("Aye-aye", "http://en.wikipedia.org/wiki/Aye-aye", 100, true);
	      data.addRowFromValues("Sloth", "http://en.wikipedia.org/wiki/Sloth", 300, true);
	      data.addRowFromValues("Leopard", "http://en.wikipedia.org/wiki/Leopard", 50, false);
	      data.addRowFromValues("Tiger", "http://en.wikipedia.org/wiki/Tiger", 80, false);
	    } catch (TypeMismatchException e) {
	      System.out.println("Invalid type!");
	    }
	    return data;
	}

}
