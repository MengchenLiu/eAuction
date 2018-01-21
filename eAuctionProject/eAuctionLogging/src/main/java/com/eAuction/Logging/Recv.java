package com.eAuction.Logging;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

public class Recv {

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(Constants.rabbitMQIP);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		//channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.queueDeclare(Constants.queue, true, false, false, null);
		channel.exchangeDeclare(Constants.queue, BuiltinExchangeType.DIRECT);

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new MongoConsumer(channel);
		String consumerTag = channel.basicConsume(Constants.queue, true, consumer);
		
/*    		new DefaultConsumer(channel) {
	  @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
*/
		channel.basicConsume(Constants.queue, true, consumer);
	}
}

