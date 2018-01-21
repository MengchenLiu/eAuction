package com.eAuction.Account.domain;

public class Address {
	private int id;
	private String country;
	private String state;
	private String addressStreet1;
	private String addressStreet2;
	private String city;
	private int zip;
	private String userName;
	

	public Address(int id, String country, String state, String addressStreet1, String addressStreet2, String city,
			int zip, String userName) {
		super();
		this.id = id;
		this.country = country;
		this.state = state;
		this.addressStreet1 = addressStreet1;
		this.addressStreet2 = addressStreet2;
		this.city = city;
		this.zip = zip;
		this.userName = userName;
	}

	public Address(int id, String country, String state, String addressStreet1, String addressStreet2, String city,
			 String userName,int zip) {
		super();
		this.id = id;
		this.country = country;
		this.state = state;
		this.addressStreet1 = addressStreet1;
		this.addressStreet2 = addressStreet2;
		this.city = city;
		this.zip = zip;
		this.userName = userName;
	}
	
	public Address(String country, String state, String addressStreet1, String addressStreet2, String city, int zip,
			String userName) {
		super();
		this.country = country;
		this.state = state;
		this.addressStreet1 = addressStreet1;
		this.addressStreet2 = addressStreet2;
		this.city = city;
		this.zip = zip;
		this.userName = userName;
	}


	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddressStreet1() {
		return addressStreet1;
	}

	public void setAddressStreet1(String addressStreet1) {
		this.addressStreet1 = addressStreet1;
	}

	public String getAddressStreet2() {
		return addressStreet2;
	}

	public void setAddressStreet2(String addressStreet2) {
		this.addressStreet2 = addressStreet2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
