package com.eAuction.domain;

import java.sql.Timestamp;

/**
 * Created by DSimeon on 11/2/2017.
 */
public class PostingBuyItNow extends Posting {
	protected static final int postTypeId = 2;
	protected double price;

	public PostingBuyItNow() {
	}

	public PostingBuyItNow(int itemID, String itemName, int sellerID, String imageUrl, String createdTime, String startTime, String expirationTime, boolean isDeleted, double price,
			int quantity, String description) {
		super(itemID, itemName, sellerID, imageUrl, createdTime, startTime, expirationTime, isDeleted,quantity,description);
		this.price = price;
	}
	
	

	public PostingBuyItNow(int id, int itemID, String itemName, int sellerID, String imageUrl, String createdTime,
			String startTime, String expirationTime, String lastModifiedTime, boolean isDeleted,double price,int quantity, String description,boolean isNotified) {
		super(id, itemID, itemName, sellerID, imageUrl, createdTime, startTime, expirationTime, lastModifiedTime, isDeleted,quantity,description,isDeleted);
		this.price = price;
	}

	public static int getPostTypeId() {
		return postTypeId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getpostTypeId() {
		return postTypeId;
	}
	
	
}
