package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.dao.HgllDao;
import com.m3958.firstgwt.model.Hgll;
import com.m3958.firstgwt.service.AppUtilService;

@Singleton
public class ExportCVSServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Injector injector;
	
	@Inject
	private HgllDao hdao;
	
	@Inject
	private AppUtilService autils;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
		
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		String s = autils.read2String(req.getInputStream(), "UTF-8");
		
		String sp = req.getParameter("sperator");
		if(sp == null || sp.isEmpty())sp = ",";
		res.setContentType("text/plain; charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		PrintWriter writer = res.getWriter();
		List<Hgll> hglls = hdao.fetchAll();
		for(Hgll hg : hglls){
			writer.write(hg.toCVS(sp));
			writer.write("\r\n");
		}
		writer.flush();
		writer.close();
	}
}
