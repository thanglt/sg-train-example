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

import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.User;

public class ManyToManyCriteriaQueryTest {
	private static final String PERSISTENCE_UNIT_NAME = "p-unit";
	private EntityManagerFactory factory;
	
	private User u;
	private Role r;

	@Before
	public void setUp() throws Exception {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		String qu = "SELECT u FROM User u WHERE u.loginName = 'u'";
		String ru = "SELECT r FROM Role r WHERE r.ename = 'e'";
		
		try {
			u = (User) em.createQuery(qu).getSingleResult();
			r = (Role) em.createQuery(ru).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.getTransaction().begin();
		
		if(u == null){
			u = new User();
			u.setLoginName("u");
			u.setEmail("e");
			
			r = new Role();
			r.setOrname("e");
			r.setCname("c");
			
			u.addRole(r);
			
			em.persist(u);
		}
		
		em.getTransaction().commit();
		em.close();
	}
	
	
	@Test
	public void testLike(){
		EntityManager em = factory.createEntityManager();
//		u = em.find(User.class, u.getId());
		String qs = "SELECT r FROM Role r,IN(r.users) u WHERE u.id = :uid";
		Query q = em.createQuery(qs);
		q.setParameter("uid", u.getId());
		List<Role> rs = q.getResultList();
		Assert.assertEquals(1, rs.size());
//		em.close();
	}
	
	@After
	public void tearDown() throws Exception{
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		u = em.find(User.class, u.getId());
		em.remove(u);
		
		r = em.find(Role.class, r.getId());
		em.remove(r);


		em.getTransaction().commit();

		em.close();
	}
}
