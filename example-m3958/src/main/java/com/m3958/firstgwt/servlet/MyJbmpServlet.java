package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class MyJbmpServlet extends HttpServlet implements Servlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	Injector injector;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		PrintWriter out = res.getWriter();
		res.setContentType("text/html;charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		
//		Activity ac = injector.getInstance(Activity.class);
//		ac.exec();
//		SessionFactory sf = HibernateUtil.getSessionFactory();
		
//		Session session = sf.openSession();
//        session.beginTransaction();
//        List<UserImpl> result = session.createQuery("from UserImpl").list();
//     
//        
//		for(UserImpl u:result){
//			out.write(u.getId());
//		}
//	   session.getTransaction().commit();
//	   session.close();
		out.flush();
		out.close();
	}

}
