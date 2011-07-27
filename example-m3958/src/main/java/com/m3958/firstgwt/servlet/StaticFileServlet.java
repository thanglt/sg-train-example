package com.m3958.firstgwt.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.service.AppUtilService;

@Singleton
public class StaticFileServlet  extends HttpServlet {
		  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Inject
	private AppUtilService autils;
	

		@Override
		public void doGet(HttpServletRequest req, HttpServletResponse res) 
		                               throws ServletException, IOException {
		    // Get the file to view
		    String file = req.getPathTranslated();
		    File f = new File(file);
		    
		    // No file, nothing to view
		    if (!f.isFile()) {
		    	RequestDispatcher rd = req.getRequestDispatcher("/webfront");
		    	rd.forward(req, res);
		    	return;
		    }
		    autils.sendSiteFile(req,res,f,getServletContext().getMimeType(f.getAbsolutePath()));
		  }
	}
