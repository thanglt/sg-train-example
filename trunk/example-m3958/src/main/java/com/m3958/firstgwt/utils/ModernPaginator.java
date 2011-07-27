package com.m3958.firstgwt.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ModernPaginator {
	
	private static Pattern pattern = Pattern.compile("(p=\\d+)");
	private int currentPage;
	private int totalPage;
	private int pageWindow;
	private int prepostPageNum;
	private String prename;
	private String postname;
	private String uriAndQs;
/*	
    <div class="pagination">
    <ul>
    <li><a href="/list/wzlist/xzzx/0/0" class="prevnext">« 前一页</a></li>
    <li><a href="/list/wzlist/xzzx/0/0">1</a></li>
    <li><a href="/list/wzlist/xzzx/0/10" class="currentpage">2</a></li>
    <li><a href="/list/wzlist/xzzx/0/20">3</a></li>
    <li><a href="/list/wzlist/xzzx/0/30">4</a></li>
    <li><a href="/list/wzlist/xzzx/0/40">5</a></li>
    <li><a href="/list/wzlist/xzzx/0/50">6</a></li>
    <li><a href="/list/wzlist/xzzx/0/60">7</a></li>
    <li><a href="/list/wzlist/xzzx/0/70">8</a></li>
    <li><a href="/list/wzlist/xzzx/0/80">9</a></li>
    <li><a href="/list/wzlist/xzzx/0/90">10</a></li>
    <li>...<a href="/list/wzlist/xzzx/0/140">15</a></li>
    <li><a href="/list/wzlist/xzzx/0/150">16</a></li>
    <li><a href="/list/wzlist/xzzx/0/20" class="prevnext">后一页 »</a></li>
    </ul>
    </div>
*/
	
	public String getPaginatorHtml(){
		if(totalPage == -1)return "";
		StringBuffer sb = new StringBuffer("<div class=\"pagination\"><ul>");
		if(currentPage > totalPage)currentPage = totalPage;
		if(currentPage < 2){//前一页是disabled
			sb.append("<li><a href=\"#\" class=\"prevnext disablelink\">" + prename +"</a></li>");
		}else{
			sb.append("<li><a href=\"" + getUrl(uriAndQs, currentPage - 1) + "\" class=\"prevnext\">" + prename +"</a></li>");
		}
		
		//如果totalpage < pagewindow x 2 + prepostpagenum x 2，不需要显示elipse。
		if(totalPage <=  pageWindow*2 + prepostPageNum*2){
			for(int i=1;i < totalPage+1;i++){
				if(i == currentPage){
					sb.append("<li><a href=\"#\" class=\"currentpage\">" + i + "</a></li>");
				}else{
					sb.append("<li><a href=\"" + getUrl(uriAndQs, i) +  "\">" + i + "</a></li>");
				}
			}
		}else{//前面两个总是显示。
			if(currentPage > pageWindow + prepostPageNum + 1){//显示前面的省略号
				for(int i= 1;i<prepostPageNum + 1;i++){
					if(i == prepostPageNum){
						sb.append("<li><a href=\"" + getUrl(uriAndQs, i) + "\">" + i +  "</a>...</li>");
					}else{
						sb.append("<li><a href=\"" + getUrl(uriAndQs, i) + "\">" + i + "</a></li>");
					}
				}
			}else{
				for(int i= 1;i<prepostPageNum + 1;i++){
					if(i == currentPage){
						sb.append("<li><a href=\"#\" class=\"currentpage\">" + i + "</a></li>");
					}else{
						sb.append("<li><a href=\"" + getUrl(uriAndQs, i) + "\">" + i + "</a></li>");
					}
				}
			}
			
			int start = currentPage - pageWindow;
			int end = currentPage + pageWindow;
			if(start <= prepostPageNum)start = prepostPageNum + 1;
			if(end >= totalPage)end = totalPage;
			for(int i = start;i<end + 1;i++){
				if(i == currentPage){
					sb.append("<li><a href=\"#\" class=\"currentpage\">" + i + "</a></li>");
				}else{
					sb.append("<li><a href=\"" + getUrl(uriAndQs, i) +  "\">" + i + "</a></li>");
				}
			}
			
			
			if(currentPage < totalPage - pageWindow - prepostPageNum){//显示后面的省略号
				boolean first = true;
				for(int i = totalPage - prepostPageNum + 1;i < totalPage + 1 ;i++){
					if(first){
						sb.append("<li>...<a href=\"" + getUrl(uriAndQs, i) + "\">" + i + "</a></li>");
						first = false;
					}else{
						sb.append("<li><a href=\"" + getUrl(uriAndQs, i) + "\">" + i + "</a></li>");
					}
				}
			}else{
				for(int i = end + 1;i < totalPage + 1 ;i++){
					sb.append("<li><a href=\"" + getUrl(uriAndQs, i) + "\">" + i + "</a></li>");
				}
			}
		}
		
		if(currentPage < totalPage){
			sb.append("<li><a href=\"" + getUrl(uriAndQs, currentPage + 1) +  "\" class=\"prevnext\">" + postname +"</a></li>");
		}else{//后一页是disabled
			sb.append("<li><a href=\"#\" class=\"prevnext disablelink\">" + postname +"</a></li>");
		}
		
		sb.append("</ul></div>");
		return sb.toString();
	}
	
	public ModernPaginator(int totalRecords,int perpage,int pageWindow,int prepostPageNum ,int currentPage,String prename,String postname,String uriAndQs){
		if(totalRecords == 0){
			totalPage = -1;
		}else{
			int pp = perpage == 0 ? 20 : perpage;
			if(totalRecords % pp == 0){
				totalPage = totalRecords/perpage;
			}else{
				totalPage = totalRecords/perpage + 1;
			}
		}
		this.pageWindow = pageWindow;
		this.currentPage = currentPage;
		this.prepostPageNum = prepostPageNum;
		this.prename = prename;
		this.postname = postname;
		this.uriAndQs = uriAndQs;
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

}
