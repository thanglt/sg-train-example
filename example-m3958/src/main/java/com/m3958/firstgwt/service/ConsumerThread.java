package com.m3958.firstgwt.service;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.m3958.firstgwt.client.types.ActiveMqSubject;

public class ConsumerThread  extends Thread{
	
    private String user = ActiveMQConnection.DEFAULT_USER;
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;
    private String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private boolean transacted = false;
    private int ackMode = Session.AUTO_ACKNOWLEDGE;
    
    private MyActiveMqConsumer mmc;
    
    public ConsumerThread(MyActiveMqConsumer mmc){
    	this.mmc = mmc;
    }
    
    
	@Override
	public void run() {
//		System.out.println("start consumer");
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;
        try {
        	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        	connection = connectionFactory.createConnection();
        	connection.start();
        	session = connection.createSession(transacted, ackMode);
        	Destination destination = session.createQueue(ActiveMqSubject.ASSET_TRANSFORM);
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(mmc);
            //clean up
            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                consumer.close();
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
