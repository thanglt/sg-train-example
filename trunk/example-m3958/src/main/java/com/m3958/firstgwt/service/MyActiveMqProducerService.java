package com.m3958.firstgwt.service;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.ActiveMqSubject;


@Singleton
public class MyActiveMqProducerService{
	
	@Inject
	Injector injector;
	
    private  String user = ActiveMQConnection.DEFAULT_USER; //"defaultUser"
    private  String password = ActiveMQConnection.DEFAULT_PASSWORD; //"defaultPassword"
//    private  String url = ActiveMQConnection.DEFAULT_BROKER_URL; //tcp://localhost:61616
    
    private  String url = "tcp://0.0.0.0:61616?trace=false&soTimeout=60000";
    
    private boolean transacted = false;
    private int ackMode = Session.AUTO_ACKNOWLEDGE;
    

	public void sendMessage(Serializable am){
		SendMessageThread smt = new SendMessageThread(am);
		smt.start();
	}
	
	
	public void sendTextMessage(String queenName,String msg){
		Session session = null;
		Connection connection = null;
		MessageProducer producer = null;
        try {
        	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(transacted, ackMode);
            Destination destination = session.createQueue(queenName);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			TextMessage message = session.createTextMessage(msg);
			producer.send(message);
			
			//clean up
			producer.close();
			session.close();
			connection.close();
			
        } catch (JMSException e) {
            e.printStackTrace();
        }finally{
            try {
                producer.close();
            } catch (Throwable ignore) {
            }
            try {
                session.close();
            } catch (Throwable ignore) {
            }
            try {
                connection.close();
            } catch (Throwable ignore) {
            }
        }
	}
	
	private static class SendMessageThread extends Thread{
		
	    private  String user = ActiveMQConnection.DEFAULT_USER; //"defaultUser"
	    private  String password = ActiveMQConnection.DEFAULT_PASSWORD; //"defaultPassword"
	    private  String url = ActiveMQConnection.DEFAULT_BROKER_URL; //tcp://localhost:61616
	    
	    private boolean transacted = false;
	    private int ackMode = Session.AUTO_ACKNOWLEDGE;
	    
	    private Serializable msg;
	    
	    public SendMessageThread(Serializable msg){
	    	this.msg = msg;
	    }
	    
		@Override
		public void run() {
			Session session = null;
			Connection connection = null;
			MessageProducer producer = null;
	        try {
	        	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
	            connection = connectionFactory.createConnection();
	            connection.start();
	            session = connection.createSession(transacted, ackMode);
	            Destination destination = session.createQueue(ActiveMqSubject.ASSET_TRANSFORM);
	            producer = session.createProducer(destination);
	            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
				ObjectMessage message = session.createObjectMessage(msg);
				producer.send(message);
				
				//clean up
				producer.close();
				session.close();
				connection.close();
				
	        } catch (JMSException e) {
	            e.printStackTrace();
	        }finally{
	            try {
	                producer.close();
	            } catch (Throwable ignore) {
	            }
	            try {
	                session.close();
	            } catch (Throwable ignore) {
	            }
	            try {
	                connection.close();
	            } catch (Throwable ignore) {
	            }
	        }
		}
	}
}
