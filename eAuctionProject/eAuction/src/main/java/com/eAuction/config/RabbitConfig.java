package com.eAuction.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;





@Configuration
public class RabbitConfig {
	
	@Bean
	public Queue queue() {
		return new Queue("logging.queue");
	}
	
	
	@Bean  
    public CachingConnectionFactory connectionFactory() {  
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();  
        connectionFactory.setAddresses(IpConfig.RabbitMQIP + ":5672");  
        connectionFactory.setUsername("guest");  
        connectionFactory.setPassword("guest");  
        connectionFactory.setVirtualHost("/");  
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;  
    }   

	
}
