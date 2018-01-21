package com.eAuction.Notification.domain;

public class UserEmail {
	private int id;
	private int userId;
	private String email;
	
	
	public UserEmail(int id, int userId, String email) {
		super();
		this.id = id;
		this.userId = userId;
		this.email = email;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
