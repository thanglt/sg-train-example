package com.m3958.firstgwt.utils;


import org.junit.Test;

public class PaginatorTest {
	
	private int pagewindow = 9;
	
	private static String FIRST = "<<first";
	private static String PREVIOUS = "<previous";
	private static String NEXT = "next>";
	private static String LAST = "last>>";
	private static String ELLIPSIS = "...";
	
	private StringBuffer out;
	
	
	
//	@Test
//	public void ptest51(){
//		Paginator p = new Paginator(55, 9, 34,"/section/1?tpl=sec");
//		out = new StringBuffer();
//		if(p.isShowFirst())out.append(FIRST).append(" ");
//		if(p.isShowPrevious())out.append(PREVIOUS).append(" ");
//		if(p.isShowFrontEllipse())out.append(ELLIPSIS).append(" ");
//		
//		for(int i=p.getStart();i<p.getEnd();i++){
//		if(p.getCurrentPage() != i){
//			out.append(p.getUrl() + i).append(" ");
//		}else{
//			out.append("x").append(" ");
//		}
//	}
		
//		if(p.isShowEndEllipse())out.append(ELLIPSIS).append(" ");
//		if(p.isShowNext())out.append(NEXT).append(" ");
//		if(p.isShowLast())out.append(LAST).append(" ");

		
//		if(totalpage <= pagewindow){//输出即可。
//			for(int i=1;i<totalpage+1;i++){
//				if(currentpage != i){
//					out.append(i).append(" ");
//				}else{
//					out.append("x").append(" ");
//				}
//			}
//		}else{
//			int startpage;
//			int endpage;
//			boolean endellipse = false;
//			if(currentpage > 1)out.append(FIRST).append(" ").append(PREVIOUS);
//			if(currentpage > pagewindow/2){
//				out.append(ELLIPSIS);
//				startpage = currentpage - pagewindow/2;
//			}else{
//				startpage = 1;
//			}
//			
//			if(currentpage < totalpage - pagewindow/2){
//				endellipse = true;
//				endpage = currentpage + pagewindow/2 + 1;
//			}else{
//				endpage = totalpage;
//			}
//			
//			for(int i=startpage;i<endpage+1;i++){
//				if(currentpage != i){
//					out.append(i).append(" ");
//				}else{
//					out.append("x").append(" ");
//				}
//			}
//			
//			if(endellipse)out.append(ELLIPSIS);
//			if(currentpage < totalpage)out.append(NEXT).append(" ").append(LAST);
//		}
//		out.append("\n");
//		System.out.print(out);
//	}
	
}
