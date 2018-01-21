package com.eAuction.Logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.lang.String;
import java.util.Date;

import com.rabbitmq.tools.json.JSONReader;

public class LoggingMessage {

	private int userID;
	private String msgText;				// itemId: 1, itemName: ABC, is viewed
	private String containerName;	// container name
	private String dateTimeStamp;

	public String getDateTimeStamp() {
		return dateTimeStamp.toString();
	}

	public void setDateTimeStamp(Date d) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		this.dateTimeStamp = df.format(d);
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setMsgText(String message) {
		this.msgText = message;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String service) {
		this.containerName = service;
	}

	public static LoggingMessage loadFromJSON(String JSONString) {
		JSONReader jsonreader = new JSONReader();
		@SuppressWarnings("unchecked")
		HashMap<String, Object> hash = (HashMap<String, Object>) jsonreader.read(JSONString);

		LoggingMessage mymsg = new LoggingMessage();
		mymsg.msgText = String.valueOf(hash.get("msgText").toString());
		mymsg.userID = Integer.valueOf(hash.get("userID").toString());
		mymsg.containerName = hash.get("containerName").toString();
		mymsg.dateTimeStamp = String.valueOf(hash.get("dateTimeStamp").toString());
		return mymsg;
	}

	/*
	{
		"msgText": "MESSAGE_TEXT"
		, "userID": 1
		, "containerName": "eAuction"
		, "dateTimeStamp": "2017-11-18T10:01:01.000Z"
	}
	Header Attributes:
	The message was created by guest
	The message was sent at Sat Nov 18 20:00:16 CST 2017
	The producer's IP Address is: 172.17.0.4
	Message Information Received:
	UserID: 12345
	Message Text: OMG It Worked!!!
	Address: UserManagement
	UserName: guest
	DateTimeStamp: 2017-11-18T20:00:16.000Z
	ProducerIP: 172.17.0.4
	LogLevel: INFO
	NewUserName: dsimeon
	NewPassword: 12
	*/
	
	
}
