package com.m3958.firstgwt.jpa;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Before;
import org.junit.Test;

import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.User;

public class TestJpa{
	
		private static final String PERSISTENCE_UNIT_NAME = "p-unit";
		private EntityManagerFactory factory;

		@Before
		public void setUp() throws Exception {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();

			// Begin a new local transaction so that we can persist a new entity
			em.getTransaction().begin();

			// Read the existing entries
			Query q = em.createQuery("select r from Role r");

			// Do we have entries?
			boolean createNewRole = (q.getResultList().size() == 0);
			
			Query q1 = em.createQuery("select u from User u");
			
			boolean createNewUsers = (q1.getResultList().size() == 0);
			
			Role role = null;
			
			
			if(createNewRole){
				role = new Role();
				role.setOrname("testmanager");
				role.setCname("管理者");
				em.persist(role);
			}else{
				role = (Role) q.getResultList().get(0);
			}

			// No, so lets create new entries
			if (createNewUsers) {
				assertTrue(q.getResultList().size() == 0);

				for (int i = 0; i < 40; i++) {
					User user = new User();
					
					user.setLoginName("Jim_" + i);
					user.setEmail("email_" + i);
					
					// First we have to persists the job
					// Now persists the new person
					user.getRoles().add(role);
					em.persist(user);
//					em.persist(user);
//					em.persist(role);
				}
			}

			// Commit the transaction, which will cause the entity to
			// be stored in the database
			em.getTransaction().commit();

			// It is always good practice to close the EntityManager so that
			// resources are conserved.
			em.close();
		}
		
//		@After
//		public void tearDown() throws Exception{
//			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//			EntityManager em = factory.createEntityManager();
//			em.getTransaction().begin();
//
//			Query q = em.createQuery("select r from Role r WHERE r.ename = :ename");
//			q.setParameter("ename", "testmanager");
//			
//			Role r = (Role) q.getSingleResult();
//			for (User user : r.getUsers()) {
//				em.remove(user);
//			}
//			
//			em.remove(r);
//
//			// Commit the transaction, which will cause the entity to
//			// be stored in the database
//			em.getTransaction().commit();
//
//			// It is always good practice to close the EntityManager so that
//			// resources are conserved.
//			em.close();
//		}
		
		@Test
		public void beanutils() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
			Class<User> uc = (Class<User>) Class.forName("com.m3958.firstgwt.model.User");
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("select u from User u");
			List<User> users = q.getResultList();
			User u = users.get(0);
			List<Role> roles = (List<Role>) PropertyUtils.getProperty(u, "roles");
			assertNotNull(roles);
			em.close();
			
		}

		@Test
		public void checkAvailableUser() {

			// Now lets check the database and see if the created entries are there
			// Create a fresh, new EntityManager
			EntityManager em = factory.createEntityManager();

			// Perform a simple query for all the Message entities
			Query q = em.createQuery("select u from User u");

			// We should have 40 Persons in the database
			assertTrue(q.getResultList().size() == 40);

			em.close();
		}

		@Test
		public void checkRole() {
			EntityManager em = factory.createEntityManager();
			// Go through each of the entities and print out each of their
			// messages, as well as the date on which it was created
			Query q = em.createQuery("select r from Role r");

			// We should have one family with 40 persons
			assertTrue(q.getResultList().size() == 1);
//			assertTrue(((Role) q.getSingleResult()).getUsers().size() == 40);
			em.close();
		}

		@Test(expected = javax.persistence.NoResultException.class)
		public void deletePerson() {
			EntityManager em = factory.createEntityManager();
			// Begin a new local transaction so that we can persist a new entity
			em.getTransaction().begin();
			Query q = em
					.createQuery("SELECT U FROM User u WHERE u.loginName = :loginName AND u.email = :email");
			q.setParameter("loginName", "Jim_1");
			q.setParameter("email", "email_!");
			User user = (User) q.getSingleResult();
			em.remove(user);
			em.getTransaction().commit();
			User user1 = (User) q.getSingleResult();
			// Begin a new local transaction so that we can persist a new entity

			em.close();
		}
}
