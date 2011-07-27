package com.m3958.firstgwt.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class SimplePaginator {
	
	private static Pattern pattern = Pattern.compile("(p=\\d+)");
	
	private int totalRecords;
	
	private int perpage;
	
	private int pages;
	
	private int currentPage;
	
	private int lastPageRecords;
	
	public boolean hasRecords(){
		return totalRecords > 0;
	}
	
	
	public SimplePaginator(int totalRecords,int perpage,int currentPage){
		this.totalRecords = totalRecords;
		this.perpage = perpage == 0 ? 20 : perpage;
		this.currentPage = currentPage < 1 ? 1 : currentPage;
		init();
	}
	
	//http://sfj.m3958.com/search?searchfor=article&q=&sw=title
	/*
	 * 1、是否存在p=\d+
	 */
	public String getNextUrl(String uriAndQs){
		return getUrl(uriAndQs,getNext());
	}
	
	private String getUrl(String uriAndQs,int pageNum){
		if(pageNum == 0 || pageNum == 1){
			String s = uriAndQs.replaceAll("p=\\d+", "");
			s = StringUtils.removeEnd(s, "?");
			s = StringUtils.removeEnd(s, "&");
			return s;
		}
		String nexts = "p=" + pageNum;
		Matcher m = pattern.matcher(uriAndQs);
		if(m.find()){
			uriAndQs = uriAndQs.replace(m.group(1), nexts);
		}else{
			if(uriAndQs.indexOf('?') == -1){
				uriAndQs += "?" + nexts;
			}else{
				uriAndQs += "&" + nexts;
			}
		}
		return uriAndQs.replace("?&", "?");
	}
	
	public String getPreviousUrl(String uriAndQs){
		return getUrl(uriAndQs,getPrevious());
	}
	
	public String getLastUrl(String uriAndQs){
		return getUrl(uriAndQs,pages);
	}
	
	public String getFirstUrl(String uriAndQs){
		return getUrl(uriAndQs,0);
	}

	
	public int getPageRecords(){
		if(totalRecords == 0)return 0;
		if(isLast())return lastPageRecords;
		return perpage;
	}
	

	private void init() {
		if(totalRecords % perpage == 0){
			pages = totalRecords/perpage;
			lastPageRecords = perpage;
			if(pages == 0){
				pages = 1;
				lastPageRecords = 1;
			}
		}else{
			pages = totalRecords/perpage + 1;
			lastPageRecords = totalRecords % perpage;
		}
	}
	
	public boolean isFirst(){
		return currentPage == 1;
	}
	
	public boolean isLast(){
		return currentPage == pages;
	}
	
	public int getNext(){
		return currentPage + 1 > pages ? pages : currentPage + 1;
	}
	
	public int getPrevious(){
		return currentPage -1 > 0 ? currentPage -1 : 1;
	}


	public int getTotalRecords() {
		return totalRecords;
	}


	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}


	public int getPerpage() {
		return perpage;
	}


	public void setPerpage(int perpage) {
		this.perpage = perpage;
	}


	public int getPages() {
		return pages;
	}


	public void setPages(int pages) {
		this.pages = pages;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public int getCurrentPage() {
		return currentPage;
	}

}
