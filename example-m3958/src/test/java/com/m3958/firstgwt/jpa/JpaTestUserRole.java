package com.m3958.firstgwt.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.m3958.firstgwt.model.Role;



public class JpaTestUserRole {
	
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
		String qs = "SELECT new com.m3958.firstgwt.model.SharedUser(u.id,r.id,r.cname,u.loginName,u.email) FROM User u, IN(u.roles) r WHERE r.id IN (SELECT r1.id FROM Folder f,IN(f.objectRoles) r1 WHERE f.id = :fid)";
		Query q = em.createQuery(qs);
		q.setParameter("fid", 555);
		List<Role> r = q.getResultList();
		em.getTransaction().commit();
		em.close();

	}
	
	@After
	public void destroy(){
	}
}
