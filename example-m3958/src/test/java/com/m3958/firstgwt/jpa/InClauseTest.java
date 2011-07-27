package com.m3958.firstgwt.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Test;

import com.m3958.firstgwt.model.Department;



public class InClauseTest {
	
	private static final String PERSISTENCE_UNIT_NAME = "p-unit";
	private EntityManagerFactory factory;
	
//	@Before
//	public void createPermision(){
//		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//		EntityManager em = factory.createEntityManager();
//		
//		em.getTransaction().begin();
//		
//		
//		em.getTransaction().commit();
//		em.close();
//		
//	}
	
	@Test
	public void testPermision(){
//		factory = Persistence.createEntityManagTerFactory(PERSISTENCE_UNIT_NAME);
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
//		em.getTransaction().begin();
//		User user = (User) em.createQuery("SELECT u FROM User AS u WHERE u.loginName = '奉化123'").getSingleResult();
//		

		//		List<Role> rs = user.getRoles();
		
		String rs = "SELECT r.id FROM User u,IN (u.roles) r  WHERE u.loginName = '奉化123'";
		Query rq = em.createQuery(rs);
		List<Integer> rss = rq.getResultList();
		
		
		String qs = "SELECT p.id FROM Permission AS p,IN (p.roles) r WHERE r.id IN (SELECT rr.id FROM User u,IN (u.roles) rr  WHERE u.loginName = '奉化123')";
		Query q = em.createQuery(qs);
		
		List<Integer> ps = q.getResultList();
		
		String ds = "SELECT d FROM Department d WHERE d.id IN (SELECT p.objectIdentity FROM Permission AS p,IN (p.roles) r WHERE r.id IN (SELECT rr.id FROM User u,IN (u.roles) rr  WHERE u.loginName = '奉化123'))";
		Query dq = em.createQuery(ds);
		List<Department> deps = dq.getResultList();
		
		
//		em.getTransaction().commit();
		em.close();

	}
	
//	@After
//	public void deletePermision(){
//		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//		EntityManager em = factory.createEntityManager();
//		
//		em.getTransaction().begin();
//		
//		
//		em.getTransaction().commit();
//		em.close();
//
//	}
}
