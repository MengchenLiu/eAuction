package com.eAuction.Posting.domain;

import java.sql.Timestamp;

/**
 * Created by DSimeon on 11/2/2017.
 */
public abstract class Posting {
	protected int id;
	protected int itemID;
	protected String itemName;
	protected int quantity;
	protected int sellerID;
	protected String description;
	protected String imageUrl;
	protected String createdTime;
	protected String startTime;
	protected String expirationTime;
	protected String lastModifiedTime;
	protected boolean isNotified;
	protected boolean isDeleted;

	public Posting() {
		
	}

	public Posting(int itemID, String itemName, int quantity, int sellerID, String description, String imageUrl, String createdTime, String startTime, String expirationTime, boolean isNotified, boolean isDeleted) {
		this.itemID = itemID;
		this.itemName = itemName;
		this.quantity = quantity;
		this.sellerID = sellerID;
		this.description = description;
		this.imageUrl = imageUrl;
		this.createdTime = createdTime;
		this.startTime = startTime;
		this.expirationTime = expirationTime;
		this.lastModifiedTime = new Timestamp(System.currentTimeMillis()).toString();
		this.isNotified = isNotified;
		this.isDeleted = isDeleted;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getSellerID() {
		return sellerID;
	}

	public void setSellerID(int sellerID) {
		this.sellerID = sellerID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public boolean isNotified() {
		return isNotified;
	}

	public void setNotified(boolean notified) {
		isNotified = notified;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
	}
}
