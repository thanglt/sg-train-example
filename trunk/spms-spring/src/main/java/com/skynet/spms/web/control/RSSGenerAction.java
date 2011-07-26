/*package com.skynet.spms.web.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skynet.common.gwt.GwtActionHelper;
import com.skynet.common.prop.PropEnum;
import com.skynet.common.prop.PropManager;
import com.skynet.common.strtemplate.TemplateTool;
import com.skynet.common.strtemplate.TemplateToolFactory;
import com.skynet.spms.manager.stockServiceBusiness.stockTask.StockTaskManager;
import com.skynet.spms.persistence.entity.spmsdd.TaskType;
import com.skynet.spms.persistence.entity.stockServiceBusiness.stockTask.StockTask;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

@Controller
public class RSSGenerAction {

	@Autowired
	private StockTaskManager taskMang;
	
	@Autowired
	private PropManager propMang;
	
	@Autowired
	private TemplateToolFactory templateFactory;
	
	
	@RequestMapping("/rss/{name}")
	public  void sendRss(@PathVariable("name") String name,HttpServletRequest request,HttpServletResponse  response) throws IOException, FeedException {

		
		if(StringUtils.isBlank(name)){
			name="SC";
		}
		
		String loc=request.getParameter("locale");
		
		Locale locale=Locale.SIMPLIFIED_CHINESE;
		
		if(StringUtils.isNotBlank(loc)){
			locale=new Locale(loc);
		}
		name=name.toUpperCase();
		
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("atom_1.0");

		feed.setTitle(name+" task list");
		String path=request.getServletPath();			
		feed.setLink(path);		
		feed.setEncoding("UTF-8");
		feed.setPublishedDate(new Date());
		
		List<SyndEntry> entries = new ArrayList<SyndEntry>();
		TaskType taskType=TaskType.valueOf(name);
		
		List<StockTask> taskList=taskMang.getStockTaskByType(taskType);
		
		TemplateTool tool=templateFactory.getGlobeTemplateTool("rss");
		Map<TaskType,String> valMap=propMang.getLocaleMapByEnum(TaskType.class, locale);

		for(StockTask task:taskList){
			SyndEntry entry=new SyndEntryImpl();		
			
			entry.setTitle("Task "+task.getDescription());			
			entry.setPublishedDate(task.getTaskDate());
			
			SyndContent description=new SyndContentImpl();		
			description.setType("html");
			
			Map<String,Object> map=new HashMap<String,Object>();
			
			map.put("taskBy", task.getTaskBy());
			map.put("taskDate", task.getTaskDate());
			map.put("actionBy",task.getActionBy());
			map.put("actionDate",task.getActionDate());
			map.put("taskNo", task.getTaskNo());
			map.put("taskType", valMap.get(task.getTaskType()));
			map.put("taskStatus", valMap.get(task.getTaskStatus()));
						
			description.setValue(tool.gener(map));
			description.setMode("rss");
//			description.setMode(s);
			
			entry.setDescription(description);
			
			entries.add(entry);
			
		}
		feed.setEntries(entries);
	
		response.setContentType(MediaType.APPLICATION_ATOM_XML.toString());
		SyndFeedOutput output = new SyndFeedOutput();
		output.output(feed, response.getWriter());
		
		return;
	}
	*/
//	private String getDescription(StockTask task){
//		/*任务
//  任务类型(须国际化)task.getTaskType();
//  任务编号		task.getTaskNo();
//任务状态(须国际化)		task.getTaskStatus();
//任务创建人  		task.getTaskBy();
//任务创建日期 		task.getTaskDate();
//任务执行人  		task.getActionBy();
//任务执行日期 		task.getActionDate();
//		
//		
//
//		
//		
//		return 
//	}
		
//}
