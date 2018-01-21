package com.eAuction.Account.domain;

public class ShoppingCart {
	private int id;
	private String userName;
	private int postingId;
	private int quantity;
	private double price;
	private String imageUrl;
	private String itemName;
	private int typeId;


	public ShoppingCart(int id, String userName, int postingId, int quantity, double price, String imageUrl,
			String itemName,int typeId) {
		super();
		this.id = id;
		this.userName = userName;
		this.postingId = postingId;
		this.quantity = quantity;
		this.price = price;
		this.imageUrl = imageUrl;
		this.itemName = itemName;
		this.setTypeId(typeId);
	}
	
	public ShoppingCart(String userName, int postingId, int quantity, double price, String imageUrl, String itemName,int typeId) {
		super();
		this.userName = userName;
		this.postingId = postingId;
		this.quantity = quantity;
		this.price = price;
		this.imageUrl = imageUrl;
		this.itemName = itemName;
		this.setTypeId(typeId);
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getPostingId() {
		return postingId;
	}
	public void setPostingId(int postingId) {
		this.postingId = postingId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	
}
