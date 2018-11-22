package com.rabbitmq.sample;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publisher {
	
	public static final String QUEUE_NAME = "effortless_queue";

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = null;
		Channel channel = null;
		
		try {
			ConnectionFactory connectionFactory = new ConnectionFactory();
			connectionFactory.setHost("localhost");
			connection = connectionFactory.newConnection();
			
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME,false, false, false, null);
			
			int count = 0;
			while (count < 5000) {
				String message = "Message number: " + count;
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
				System.out.println("Published message: " + message);
				count++;
				Thread.currentThread().sleep(2000);
			}
		}finally {
			if( connection != null ) {
				connection.close();
			}
			
			if( channel != null ) {
				channel.close();
			}
		}

	}

}
