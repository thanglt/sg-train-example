package com.m3958.firstgwt.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.m3958.firstgwt.client.types.GroupByResult;





public class GroupByTest {
	
	private static final String PERSISTENCE_UNIT_NAME = "p-unit";
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	
	
	@Before
	public void create(){
		EntityManager em = factory.createEntityManager();
		em.close();
	}
	
	

	@Test
	public void testMe(){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		//对象名称User，属性loginName
		String qs = "SELECT new com.m3958.firstgwt.client.types.GroupByResult(u.loginName,count(u)) FROM User AS u GROUP BY u.loginName";
		Query q = em.createQuery(qs);
		
		List<GroupByResult> results = q.getResultList();
		for (GroupByResult gr : results) {
			System.out.println(gr.getGroupByName());
			System.out.println(gr.getCount());
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
