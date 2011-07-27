package com.m3958.firstgwt.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Paginator {
	
	private static Pattern pattern = Pattern.compile("(p=\\d+)");
	private boolean showFirst;
	private boolean showPrevious;
	private boolean showNext;
	private boolean showLast;
	private boolean showFrontEllipse;
	private boolean showEndEllipse;
	private int start;
	private int end;
	private int currentPage;
	private int totalPage;
	private int pageWindow;
	private String uriAndQs;
	

	public Paginator(int totalRecords,int perpage,int pageWindow,int currentPage){
		int pp = perpage == 0 ? 20 : perpage;
		if(totalRecords % pp == 0){
			totalPage = totalRecords/perpage;
			if(totalPage == 0){
				totalPage = 1;
			}
		}else{
			totalPage = totalRecords/perpage + 1;
		}
		
		this.pageWindow = pageWindow;
		this.currentPage = currentPage;
		init();
	}
	
	public Paginator(int totalRecords,int perpage,int pageWindow,int currentPage,String uriAndQs){
		int pp = perpage == 0 ? 20 : perpage;
		if(totalRecords % pp == 0){
			totalPage = totalRecords/perpage;
			if(totalPage == 0){
				totalPage = 1;
			}
		}else{
			totalPage = totalRecords/perpage + 1;
		}
		
		this.pageWindow = pageWindow;
		this.currentPage = currentPage;
		this.uriAndQs = uriAndQs;
		init();
	}
	
	
	public String getUrl(String uriAndQs,int pageNum){
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

	private void init() {
		if(totalPage <= pageWindow){
			start = 1;
			end = totalPage;
		}else{
			if(currentPage > 1){
				setShowFirst(true);
				setShowPrevious(true);
			}
			if(currentPage > pageWindow/2){
				start = currentPage - pageWindow/2;
				if(start > 1)setShowFrontEllipse(true);
			}else{
				start = 1;
			}
			
			if(currentPage < totalPage - pageWindow/2){
				end = currentPage + pageWindow/2;
				if(end < totalPage)setShowEndellipse(true);
			}else{
				end = totalPage;
			}

			if(currentPage < totalPage){
				setShowNext(true);
				setShowLast(true);
			}
		}
	}


	public void setFirst(boolean first) {
		this.setShowFirst(first);
	}


	public void setPrevious(boolean previous) {
		this.setShowPrevious(previous);
	}


	public void setNext(boolean next) {
		this.setShowNext(next);
	}


	public void setLast(boolean last) {
		this.setShowLast(last);
	}


	public void setPellipse(boolean pellipse) {
		this.setShowFrontEllipse(pellipse);
	}


	public void setNellipse(boolean nellipse) {
		this.setShowEndellipse(nellipse);
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}


	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageWindow() {
		return pageWindow;
	}

	public void setPageWindow(int pageWindow) {
		this.pageWindow = pageWindow;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setShowFirst(boolean showFirst) {
		this.showFirst = showFirst;
	}

	public boolean isShowFirst() {
		return showFirst;
	}

	public void setShowPrevious(boolean showPrevious) {
		this.showPrevious = showPrevious;
	}

	public boolean isShowPrevious() {
		return showPrevious;
	}

	public void setShowNext(boolean showNext) {
		this.showNext = showNext;
	}

	public boolean isShowNext() {
		return showNext;
	}

	public void setShowLast(boolean showLast) {
		this.showLast = showLast;
	}

	public boolean isShowLast() {
		return showLast;
	}

	public void setShowFrontEllipse(boolean showFrontEllipse) {
		this.showFrontEllipse = showFrontEllipse;
	}

	public boolean isShowFrontEllipse() {
		return showFrontEllipse;
	}

	public void setShowEndellipse(boolean showEndellipse) {
		this.showEndEllipse = showEndellipse;
	}

	public boolean isShowEndEllipse() {
		return showEndEllipse;
	}

}
