package com.m3958.firstgwt.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class PermissionTest {
	
	private static final String PERSISTENCE_UNIT_NAME = "p-unit";
	private EntityManagerFactory factory;
	
	private Long Id;
	
	@Before
	public void createPermision(){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		em.getTransaction().begin();
		
		
		
		
		em.getTransaction().commit();
		em.close();
		
	}
	
	@Test
	public void testPermision(){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		em.getTransaction().begin();
		
		
		em.getTransaction().commit();
		em.close();

	}
	
	@After
	public void deletePermision(){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		em.getTransaction().begin();
		
		
		em.getTransaction().commit();
		em.close();

	}
}
