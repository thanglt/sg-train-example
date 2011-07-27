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

import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.utils.SmartClientCriteria;




public class JpaDateTimeTest {
	
	private static final String PERSISTENCE_UNIT_NAME = "p-unit";
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	

	@Before
	public void create(){
	}
	/*
	 * 
	 * literalTemporal = DATE_LITERAL | TIME_LITERAL | TIMESTAMP_LITERAL 
DATE_LITERAL
    : LEFT_CURLY_BRACKET ('d') (' ' | '\t')+ '\'' DATE_STRING '\'' (' ' | '\t')* RIGHT_CURLY_BRACKET
    ;

TIME_LITERAL
    : LEFT_CURLY_BRACKET ('t') (' ' | '\t')+ '\'' TIME_STRING '\'' (' ' | '\t')* RIGHT_CURLY_BRACKET
    ;
    
TIMESTAMP_LITERAL
    : LEFT_CURLY_BRACKET ('ts') (' ' | '\t')+ '\'' DATE_STRING ' ' TIME_STRING '\'' (' ' | '\t')* RIGHT_CURLY_BRACKET
    ;

DATE_STRING
    : '0'..'9' '0'..'9' '0'..'9' '0'..'9' '-' '0'..'9' '0'..'9' '-' '0'..'9' '0'..'9'
    ;
    
TIME_STRING
    : '0'..'9' ('0'..'9')? ':' '0'..'9' '0'..'9' ':' '0'..'9' '0'..'9' '.' '0'..'9'*
    ;


	 */
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMe(){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		String qs = "SELECT u FROM User AS u WHERE u.createdAt > {d '2011-01-01'} AND u.createdAt < {ts '2008-01-02 8:05:34.8837738473772627'}";
		Query q = em.createQuery(qs);
		List<User> us = q.getResultList();
		Assert.assertEquals(0, us.size());		
		em.getTransaction().commit();
		em.close();
	}
	@Test
	public void testMe1(){
//		{"fieldName":"email","operator":"iContains","value":"uuu"}
//		{"fieldName":"createdAt","operator":"lessThan","value":"2010-06-27T16:00:00"}]}
//		{"_constructor":"AdvancedCriteria","operator":"and","criteria":[{"fieldName":"birthDay","operator":"greaterThan","value":"2010-06-07"},{"fieldName":"birthDay","operator":"lessThan","value":"2010-06-21"}]}
		String ds = "{\"fieldName\":\"birthDay\",\"operator\":\"greaterThan\",\"value\":\"2010-06-02\"}";
		String ds1 = "{\"fieldName\":\"createdAt\",\"operator\":\"lessThan\",\"value\":\"2010-06-27T16:00:00\"}]}";
		String ds2 = "{\"_constructor\":\"AdvancedCriteria\",\"operator\":\"and\",\"criteria\":[{\"fieldName\":\"birthDay\",\"operator\":\"greaterThan\",\"value\":\"2010-06-07\"},{\"fieldName\":\"birthDay\",\"operator\":\"lessThan\",\"value\":\"2010-06-21\"}]}";
		String ds3 = "{\"fieldName\":\"email\",\"operator\":\"iContains\",\"value\":\"uuu\"}";
		String[] dss = new String[]{ds,ds1,ds2,ds3};
		SmartClientCriteria dc = new SmartClientCriteria(dss);
		Assert.assertEquals(2, dc.getDateFieldMap().size());
		Assert.assertEquals(1, dc.getStringFieldMap().size());
		Assert.assertEquals(3, dc.getDateFieldMap().get("birthDay").size());
		Assert.assertEquals(1, dc.getDateFieldMap().get("createdAt").size());
		Assert.assertEquals(1, dc.getStringFieldMap().size());
//		JSONObject jo = JSONObject.fromObject(ds);
//		Assert.assertEquals("birthDay", jo.get("fieldName"));
//		Assert.assertEquals("operator", jo.get("greaterThan"));
//		Assert.assertEquals("value", jo.get("2010-06-02"));
	}
	

	
	@After
	public void destroy(){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.getTransaction().commit();
		em.close();

	}
}
