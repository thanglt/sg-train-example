package com.m3958.firstgwt.jpa;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.m3958.firstgwt.model.Lgb;



public class LgbForJasper {
	
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
		
		try {
			FileOutputStream fos = new FileOutputStream("e:/ooput");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			String qs = "SELECT l FROM Lgb AS l";
			Query q = em.createQuery(qs);
			List<Lgb> lgbs = q.getResultList();
			oos.writeObject(lgbs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			
		}
		
		
		Assert.assertEquals(1, 1);
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
