package com.m3958.firstgwt.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.m3958.firstgwt.model.User;

public class CriteriaQueryTest {
	private static final String PERSISTENCE_UNIT_NAME = "p-unit";
	private EntityManagerFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		em.getTransaction().begin();
		for (int i = 0; i < 40; i++) {
			User user = new User();
			
			user.setLoginName("88920556Jim_" + i);
			user.setEmail("88920556email_" + i);
			em.persist(user);
		}
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void testUserNum(){
//		EntityManager em = factory.createEntityManager();
//		
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaQuery<User> cq = cb.createQuery(User.class);
//		
//		
//		Root<User> user = cq.from(User.class);
//		cq.where(user.get("email").isNull());
//		
//		TypedQuery<User> tq = em.createQuery(cq);
//
//		
//		List<User> users = tq.getResultList();
//		
//		Assert.assertEquals(0, users.size());
		
	}
	
	@Test
	public void testLike(){
//		EntityManager em = factory.createEntityManager();
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaQuery<User> cq = cb.createQuery(User.class);
//
//		
//		Root<User> user = cq.from(User.class);
//		cq.select(user);
//		
//		cq.where(cb.like(user.get("email").as(String.class),"88920556%"));
//		
//		cq.orderBy(cb.desc(user.get("createdAt").as(Date.class)));
//		
//		TypedQuery<User> tq = em.createQuery(cq);
//		
//		List<User> users = tq.getResultList();
//		
//		Assert.assertEquals(40, users.size());
//		
//		cq.where(cb.like(user.get("loginName").as(String.class), "88920556%")).where(cb.like(user.get("email").as(String.class), "88922323223320556%"));
//		tq = em.createQuery(cq);
//		users = tq.getResultList();
//		Assert.assertEquals(40, users.size());
//		
//		cq.where(cb.and(cb.like(user.get("loginName").as(String.class), "88920556%")),cb.like(user.get("email").as(String.class), "88920556%"));
//		tq = em.createQuery(cq);
//		users = tq.getResultList();
//		Assert.assertEquals(40, users.size());
		
		
	}
	
	@After
	public void tearDown() throws Exception{
//		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//		EntityManager em = factory.createEntityManager();
//		em.getTransaction().begin();
//
//		Query q = em.createQuery("select u from User u WHERE u.email LIKE :ename");
//		q.setParameter("ename", "88920556%");
//		
//		List<User> users = q.getResultList();
//		
//		for (User user : users) {
//			em.remove(user);
//		}
//		
//
//		// Commit the transaction, which will cause the entity to
//		// be stored in the database
//		em.getTransaction().commit();
//
//		// It is always good practice to close the EntityManager so that
//		// resources are conserved.
//		em.close();
	}
}
