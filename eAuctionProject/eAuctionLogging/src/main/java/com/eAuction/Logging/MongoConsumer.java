package com.eAuction.Logging;

import java.util.*;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.tools.json.JSONWriter;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

public class MongoConsumer extends DefaultConsumer {

	public MongoConsumer(Channel channel) {
		super(channel);
	}

	@Override
	public void handleDelivery(
			String consumerTag,
			Envelope envelope,
			BasicProperties properties,
			byte[] body) throws java.io.IOException {
		String message = new String(body);
		System.out.println("Received from rabbitmq: " + message);	// DEBUG
		LoggingMessage logMsg = LoggingMessage.loadFromJSON(message);

		System.out.println("Message Information Received:");
		System.out.println("UserID: " + logMsg.getUserID());
		System.out.println("Message Text: " + logMsg.getMsgText());
		System.out.println("Address: " + logMsg.getContainerName());
		System.out.println("DateTimeStamp: " + logMsg.getDateTimeStamp());

		//Use a JSONWriter to build a JSON string.
		JSONWriter rabbitmqJson = new JSONWriter();
		String messageToLog = rabbitmqJson.write(logMsg);

		//NOW WRITE IT TO MONGODB
		System.out.println("MONGODB: creating new MongoClient\n");
		//MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:password@localhost/"));

		System.out.println("MONGODB:  New MongoClient created, now trying to getDB loggingServce\n");
		//the following call will create "logginService" if it doesn't already exist....
		DB database = mongoClient.getDB("loggingService");

		System.out.println("MONGODB:  got loggingService...now trying to get database names...\n");

		// let's play around a bit and print existing MongoDB databases

		// IF YOU ARE USING JAVA 1.7...
		for(Iterator<String> i = mongoClient.getDatabaseNames().iterator(); i.hasNext(); )
			System.out.println(i.next());
		//ELSE IF YOU ARE USING JAVA 1.8...
		///mongoClient.getDatabaseNames().forEach(System.out::println);

		database.createCollection("logging", null);

		// print all collections in customers database
		System.out.println("\nMONGODB:  got database names, now printing collections\n");

		// IF YOU ARE USING JAVA 1.7...
		for(Iterator<String> i2 = database.getCollectionNames().iterator(); i2.hasNext(); )
			System.out.println(i2.next());
		//ELSE IF YOU ARE USING JAVA 1.8...
		//database.getCollectionNames().forEach(System.out::println);

		DBCollection dbCollection = database.getCollection("logging");
		System.out.println("MONGODB:  got the logging collection...\n");

		System.out.println("Message To Log: " + messageToLog);

		DBObject dbObject = (DBObject) JSON.parse(messageToLog);
		dbCollection.insert(dbObject);

		// Print content of collection
		for(DBObject doc : dbCollection.find()) {
			System.out.println(doc);
		}
	}
}
