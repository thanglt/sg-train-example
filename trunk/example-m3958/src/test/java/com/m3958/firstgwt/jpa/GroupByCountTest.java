package com.m3958.firstgwt.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.m3958.firstgwt.model.User;





public class GroupByCountTest {
	
	public static class UserArticleCount{
		
		private User u;
		private Long c;
		
		public UserArticleCount(User u,Long c){
			this.setU(u);
			this.setC(c);
		}

		public void setU(User u) {
			this.u = u;
		}

		public User getU() {
			return u;
		}

		public void setC(Long c) {
			this.c = c;
		}

		public Long getC() {
			return c;
		}
	}
	
	private static final String PERSISTENCE_UNIT_NAME = "p-unit";
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	
	
	@Before
	public void create(){
		EntityManager em = factory.createEntityManager();
		em.close();
	}
	
	

/*
	SELECT FUNC('YEAR', whenRegistered) Y, COUNT(registration.id) C FROM registration GROUP BY Y
	
	
	
	SELECT c
	FROM Customer c
	WHERE (SELECT COUNT(o) FROM c.orders o) > 10
	
	
	SELECT DISTINCT emp
	FROM Employee emp
	WHERE EXISTS (
	    SELECT spouseEmp
	    FROM Employee spouseEmp
	    WHERE spouseEmp = emp.spouse)
	
	SELECT emp
	FROM Employee emp
	WHERE emp.salary > ALL (
	    SELECT m.salary
	    FROM Manager m
	    WHERE m.department = emp.department)
	
	Constructor Expressions
	Constructor expressions allow you to return Java instances that store a query result element instead of an Object[].

	The following query creates a CustomerDetail instance per Customer matching the WHERE clause. A CustomerDetail stores the customer name and customer’s country name. So the query returns a List of CustomerDetail instances:

	SELECT NEW com.xyz.CustomerDetail(c.name, c.country.name)
	 FROM customer c
	WHERE c.lastname = ’Coss’ AND c.firstname = ’Roxane’
	
	SELECT c.status, AVG(o.totalPrice)
 	FROM Order o JOIN o.customer c
	GROUP BY c.status HAVING c.status IN (1, 2, 3)
*/
	@Test
	public void testMe(){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		//对象名称User，属性loginName
		String qs = "SELECT NEW com.m3958.firstgwt.jpa.GroupByCountTest.UserArticleCount(a.creator,COUNT(a)) FROM Article AS a WHERE a.siteId = :siteId GROUP BY a.creator ORDER BY COUNT(a)";
		Query q = em.createQuery(qs);
		q.setParameter("siteId", 12315);
		List<UserArticleCount> results = q.getResultList();
		for(UserArticleCount uac:results){
			System.out.print(uac.getU());
			System.out.print(",");
			System.out.print(uac.getC());
			System.out.print("\n");
		}
		
		em.getTransaction().commit();
		em.close();

	}
	
	@After
	public void destroy(){
		EntityManager em = factory.createEntityManager();
		
		em.getTransaction().begin();
		em.getTransaction().commit();
		em.close();

	}
}
