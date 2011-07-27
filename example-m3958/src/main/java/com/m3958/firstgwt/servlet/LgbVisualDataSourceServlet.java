package com.m3958.firstgwt.servlet;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.visualization.datasource.Capabilities;
import com.google.visualization.datasource.DataSourceHelper;
import com.google.visualization.datasource.DataSourceRequest;
import com.google.visualization.datasource.QueryPair;
import com.google.visualization.datasource.base.DataSourceException;
import com.google.visualization.datasource.base.ReasonType;
import com.google.visualization.datasource.base.ResponseStatus;
import com.google.visualization.datasource.base.StatusType;
import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.TableRow;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.google.visualization.datasource.query.AbstractColumn;
import com.google.visualization.datasource.query.Query;
import com.m3958.firstgwt.client.types.GroupByResult;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.client.types.SortDirection;
import com.m3958.firstgwt.dao.LgbDao;


/**
 * @author jianglibo@gmail.com
 * 这个datasource没有必要通用化，对于我们的系统来说，提供的东西非常限定。
 * 可以cache结果，但是在没有遇到性能问题之前，不做考虑。
 */
@Singleton
public class LgbVisualDataSourceServlet extends HttpServlet {
	

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * The log used throughout the data source library.
   */
	private static final Log log = LogFactory.getLog(LgbVisualDataSourceServlet.class.getName());
  
	private static List<LgbField> allowedGroupBy = new ArrayList<LgbField>();
	
	static{
		allowedGroupBy.add(LgbField.XB);
		allowedGroupBy.add(LgbField.SR);
		allowedGroupBy.add(LgbField.MINZU);
		allowedGroupBy.add(LgbField.XZJB);
		allowedGroupBy.add(LgbField.XSDY);
		allowedGroupBy.add(LgbField.JKZK);
		allowedGroupBy.add(LgbField.HYZK);
		allowedGroupBy.add(LgbField.DWXZ);
		allowedGroupBy.add(LgbField.JJZK);
		allowedGroupBy.add(LgbField.GBLX);
	}
  
	@Inject
	Injector injector;


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    DataSourceRequest dsRequest = null;

    try {
      // Extract the request parameters.
      dsRequest = new DataSourceRequest(req);

      // NOTE: If you want to work in restricted mode, which means that only
      // requests from the same domain can access the data source, you should
      // uncomment the following call.
      //
      // DataSourceHelper.verifyAccessApproved(dsRequest);

      // Split the query.
      QueryPair query = DataSourceHelper.splitQuery(dsRequest.getQuery(),Capabilities.NONE);
      
//     query.getDataSourceQuery().getLabels();

      // Generate the data table.
      DataTable data = generateMyDataTable(query.getDataSourceQuery(), req);

      // Apply the completion query to the data table.
      DataTable newData = DataSourceHelper.applyQuery(query.getCompletionQuery(), data,
          dsRequest.getUserLocale());

      DataSourceHelper.setServletResponse(newData, dsRequest, resp);
    } catch (RuntimeException rte) {
      rte.printStackTrace();
      ResponseStatus status = new ResponseStatus(StatusType.ERROR, ReasonType.INTERNAL_ERROR,
          rte.getMessage());
      if (dsRequest == null) {
        dsRequest = DataSourceRequest.getDefaultDataSourceRequest(req);
      }
      DataSourceHelper.setServletErrorResponse(status, dsRequest, resp);
    } catch (DataSourceException e) {
      if (dsRequest != null) {
        DataSourceHelper.setServletErrorResponse(e, dsRequest, resp);
      } else {
        DataSourceHelper.setServletErrorResponse(e, req, resp);
      }
    }
  }

  /**
   * Returns true if the given column name is requested in the given query.
   * If the query is empty, all columnNames returns true.
   *
   * @param query The given query.
   * @param columnName The requested column name.
   *
   * @return True if the given column name is requested in the given query.
   */
  private boolean isColumnRequested(Query query, String columnName) {
    // If the query is empty return true.
    if (query.isEmpty()) {
      return true;
    }

    List<AbstractColumn> columns = query.getSelection().getColumns();
    for (AbstractColumn column : columns) {
      if (column.getId().equalsIgnoreCase(columnName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Generates a data table - according to the provided tableId url parameter.
   *
   * @param query The query to operate on the underlying data.
   * @param req The HttpServeltRequest.
   *
   * @return The generated data table.
   */
  private DataTable generateMyDataTable(Query query, HttpServletRequest req)
      throws TypeMismatchException {
    String groupByName = req.getParameter("groupByName");
    LgbField lf = LgbField.getFieldEnumByEname(groupByName); 
    String did = req.getParameter("did");
    String groupByType = req.getParameter("groupByType");
    String sd = req.getParameter("sortdir");
    SortDirection sortDir;
    if(sd == null){
    	sortDir = SortDirection.ASCENDING;
    }else{
    	sortDir = SortDirection.valueOf(sd);
    }
    
    if(!allowedGroupBy.contains(lf)){
    	return new DataTable();
    }
    if("date".equals(groupByType)){
    	return generateLgbDateTable(query, lf,did,sortDir);
    }
    return generateLgbStringTable(query,lf,did,sortDir);
  }
  
  private String getCloumnName(LgbField lf){
	  switch (lf) {
	case SR:
		return lf.getAlias();
	default:
		return lf.getCname();
	}
  }
  
  
  @SuppressWarnings("deprecation")
private DataTable generateLgbDateTable(Query query,LgbField groupByField,String did,SortDirection sortdir) throws TypeMismatchException {
	  DataTable data = new DataTable();
	    List<ColumnDescription> cdps = new ArrayList<ColumnDescription>();
	    ColumnDescription cd1 = new ColumnDescription("groupByDate", ValueType.TEXT, getCloumnName(groupByField));
	    cdps.add(cd1);
	    cdps.add(new ColumnDescription("count", ValueType.NUMBER, "人数"));

	    data.addColumns(cdps);
	    //从数据库获取内容。
	    
	    LgbDao dao = injector.getInstance(LgbDao.class);
	    
	    List<GroupByResult> results = dao.getLgbGroupByResults(groupByField.getEname(),did,sortdir);
	    
//	    List<GroupByResult> results = new ArrayList<GroupByResult>();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	    switch (groupByField) {
		case SR:
		    for (GroupByResult groupByResult : results) {
		    	if(groupByResult.getGroupByDate() != null){
					TableRow row = new TableRow();
					Date d = groupByResult.getGroupByDate();
					String howOld = (new Date().getYear() - d.getYear() + 1) + "岁";
					row.addCell(String.valueOf(howOld));
					row.addCell(groupByResult.getCount());
					data.addRow(row);
		    	}
			}
			break;
		default:
		    for (GroupByResult groupByResult : results) {
		    	if(groupByResult.getGroupByDate() != null){
					TableRow row = new TableRow();
					Date d = groupByResult.getGroupByDate();
					row.addCell(sdf.format(d));
					row.addCell(groupByResult.getCount());
					data.addRow(row);
		    	}
			}
			break;
		}
	    return data;
  }

  /**
   * Returns an animals data table. The table contains a list of animals and
   * links to wikipedia pages.
   *
   * @param query The selection query.
   *
   * @return A data table of animals.
   */
  private DataTable generateLgbStringTable(Query query,LgbField groupByField,String did,SortDirection sortdir) throws TypeMismatchException {
    DataTable data = new DataTable();
    List<ColumnDescription> cdps = new ArrayList<ColumnDescription>();
    cdps.add(new ColumnDescription("groupByName", ValueType.TEXT, groupByField.getCname()));
    cdps.add(new ColumnDescription("count", ValueType.NUMBER, "数量"));

    data.addColumns(cdps);
    //从数据库获取内容。
    
    LgbDao dao = injector.getInstance(LgbDao.class);
    
    List<GroupByResult> results = dao.getLgbGroupByResults(groupByField.getEname(),did,sortdir);
    
 
    for (GroupByResult groupByResult : results) {
    	if(groupByResult.getGroupByName() != null){
			TableRow row = new TableRow();
			row.addCell(groupByResult.getGroupByName());
			row.addCell(groupByResult.getCount());
			data.addRow(row);
    	}
	}
    return data;
  }

  /**
   * Returns a list of required columns based on the query and the actual
   * columns.
   *
   * @param query The user selection query.
   * @param availableColumns The list of possible columns.
   *
   * @return A List of required columns for the requested data table.
   */
  private List<ColumnDescription> getRequiredColumns(Query query,
      ColumnDescription[] availableColumns) {
    // Required columns
    List<ColumnDescription> requiredColumns = Lists.newArrayList();
    for (ColumnDescription column : availableColumns) {
      if (isColumnRequested(query, column.getId())) {
        requiredColumns.add(column);
      }
    }
    return requiredColumns;
  }
}
