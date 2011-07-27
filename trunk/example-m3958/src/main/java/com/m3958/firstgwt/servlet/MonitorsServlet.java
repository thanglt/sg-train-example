package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.service.Monitor;

@Singleton
public class MonitorsServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Injector injector;
	
	@Inject
	private Monitor monitor;
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		res.setContentType("text/html; charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		
		PrintWriter writer = res.getWriter();
		
		writer.write("start Time:" + monitor.getStartDate());
		writer.write("<br/>");
		writer.write("request times:" + monitor.getRequestCounter());
		writer.write("<br/>");
		writer.close();
		
	}

}
