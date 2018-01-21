package com.eAuction.domain;

import java.sql.Timestamp;

/**
 * Created by DSimeon on 11/2/2017.
 */
public abstract class Posting {
	protected int id;
	protected int itemID;
	protected String itemName;
	protected int sellerID;
	protected String imageUrl;
	protected String createdTime;
	protected String startTime;
	protected String expirationTime;
	protected String lastModifiedTime;
	protected boolean isDeleted;
	protected int quantity;
	protected String description;
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isNotified() {
		return isNotified;
	}

	public void setNotified(boolean isNotified) {
		this.isNotified = isNotified;
	}

	protected boolean isNotified;
	
	public Posting() {
	}

	public Posting(int itemID, String itemName, int sellerID, String imageUrl, String createdTime, String startTime, String expirationTime, boolean isDeleted,int quantity,
			String description) {
		this.itemID = itemID;
		this.itemName = itemName;
		this.sellerID = sellerID;
		this.imageUrl = imageUrl;
		this.createdTime = createdTime;
		this.startTime = startTime;
		this.expirationTime = expirationTime;
		this.isDeleted = isDeleted;
		this.quantity = quantity;
		this.description = description;
	}
	
	
	public Posting(int id, int itemID, String itemName, int sellerID, String imageUrl, String createdTime,
			String startTime, String expirationTime, String lastModifiedTime, boolean isDeleted,
			int quantity, String description,boolean isNotified) {
		this.id = id;
		this.itemID = itemID;
		this.itemName = itemName;
		this.sellerID = sellerID;
		this.imageUrl = imageUrl;
		this.createdTime = createdTime;
		this.startTime = startTime;
		this.expirationTime = expirationTime;
		this.lastModifiedTime = lastModifiedTime;
		this.isDeleted = isDeleted;
		this.quantity = quantity;
		this.description = description;
		this.isNotified = isNotified;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getSellerID() {
		return sellerID;
	}

	public void setSellerID(int sellerID) {
		this.sellerID = sellerID;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
	}
}
