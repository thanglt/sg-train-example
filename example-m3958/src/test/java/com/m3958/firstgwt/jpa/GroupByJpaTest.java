package com.m3958.firstgwt.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.m3958.firstgwt.model.Article;



public class GroupByJpaTest {
	
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
		String qs = "SELECT DISTINCT FUNC('YEAR',a.publishedAt),FUNC('MONTH',a.publishedAt) FROM Article AS a";
		Query q = em.createQuery(qs);
		List<String> ars = q.getResultList();
		
		qs = "SELECT a FROM Article AS a WHERE FUNC('YEAR',a.publishedAt) = :year AND FUNC('MONTH',a.publishedAt) = :month";
		q = em.createQuery(qs);
		q.setParameter("year", 2011);
		q.setParameter("month",5);
		List<Article> as = q.getResultList();
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
