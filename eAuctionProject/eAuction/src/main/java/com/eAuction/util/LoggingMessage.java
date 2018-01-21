package com.eAuction.util;



public class LoggingMessage {
	
	public static String getMessage(int userID, String msgText, String containerName, String dateTimeStamp ) {
		
		StringBuilder sb  = new StringBuilder();
		sb.append("{" + "\"userID" + "\"" +":"  + userID  + "," + "\"" + "msgText" + "\"" +":" + "\"" + msgText + "\"" + "," + "\"" +  "containerName" + "\"" +":" + "\"" +  containerName + "\""  + ","
				+ "\"" +"dateTimeStamp"  + "\"" +":" + "\"" + dateTimeStamp + "\"" + "}");
		return sb.toString();
	}
}
