package com.eAuction.Account.domain;

import java.io.Serializable;

import com.eAuction.Account.objectInterface.UserInterface;

public class User implements UserInterface,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String role;
	private String email;
	
	public User (int id, String userName, String password, String firstName, String lastName, String role, String email) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}
	
	
	public User (String userName, String password, String firstName, String lastName, String role, String email) {
		this.email = email;
		this.password = password;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
