package com.eAuction.Notification.domain;

public class watchlist {
    private int id;
    private String email;
    private int itemId;
    private String itemName;
    private double price;
    private String userName;
    
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getItemId() {
		return itemId;
	}


	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	

	public watchlist(int id, String email, int itemId, String itemName, double price, String userName) {
		super();
		this.id = id;
		this.email = email;
		this.itemId = itemId;
		this.itemName = itemName;
		this.price = price;
		this.userName = userName;
	}

	public watchlist(String email, int itemId, String itemName, double price, String userName) {
		super();
		this.email = email;
		this.itemId = itemId;
		this.itemName = itemName;
		this.price = price;
		this.userName = userName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}



    
}
