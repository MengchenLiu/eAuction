package com.eAuction.CustomerService.domain;

import java.sql.Timestamp;

/**
 * Created by DSimeon on 11/2/2017.
 */
public class Message {
	private int id;
	private int userId;
	private String question;
	private int adminId;
	private String answer;
	private Timestamp lastModifiedTime;

	public Message() {
	}

	public Message(int userId, String question, Timestamp lastModifiedTime) {
		this.userId = userId;
		this.question = question;
		this.lastModifiedTime = lastModifiedTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Timestamp getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Timestamp lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
}
