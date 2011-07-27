package com.m3958.firstgwt.jpa;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.User;

public class Userfixture{
	
		private static final String PERSISTENCE_UNIT_NAME = "p-unit";
		private EntityManagerFactory factory;

		@Before
		public void setUp() throws Exception {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();

			// Begin a new local transaction so that we can persist a new entity
			em.getTransaction().begin();

			Role role = new Role();
			role.setOrname((new Random().nextInt()) + "testmanager");
			role.setCname((new Random().nextInt()) + "管理者");
			em.persist(role);

			// No, so lets create new entries
			for (int i = 0; i < 100; i++) {
				User user = new User();
				
				user.setLoginName((new Random().nextInt()) + "Jim_" + i);
				user.setEmail((new Random().nextInt()) + "email_" + i);
				
				// First we have to persists the job
				// Now persists the new person
				user.getRoles().add(role);
				em.persist(user);
//					em.persist(user);
//					em.persist(role);
			}


			// Commit the transaction, which will cause the entity to
			// be stored in the database
			em.getTransaction().commit();

			// It is always good practice to close the EntityManager so that
			// resources are conserved.
			em.close();
		}
		
		@Test
		public void t(){
			assertEquals(1, 1);
		}
		
}
