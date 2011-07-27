package com.m3958.firstgwt.mongodb;

import java.net.UnknownHostException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/*
 * {
   "name" : "MongoDB",
   "type" : "database",
   "count" : 1,
   "info" : {
               x : 203,
               y : 102
             }
}
 */

public class FirstMongo {
	
	private DB db;
	
	@Before
	public void setup() throws UnknownHostException, MongoException{
		Mongo m = new Mongo( "61.153.153.2" , 27017 );
		db = m.getDB( "mydb" );
	}
	
	@Test
	public void t1(){
		DBCollection dc = db.getCollection("dbs");
		dc.insert(getDoc());
		Assert.assertEquals(1, dc.count());
	}
	
	
	
	@After
	public void cleanup(){
		DBCollection dc1 = db.getCollection("dbs");
		dc1.remove(getDoc());
		db = null;
	}
	
	private BasicDBObject getDoc(){
        BasicDBObject doc = new BasicDBObject();

        doc.put("name", "MongoDB");
        doc.put("type", "database");
        doc.put("count", 1);

        BasicDBObject info = new BasicDBObject();

        info.put("x", 203);
        info.put("y", 102);

        doc.put("info", info);
        
        return doc;
	}

}
