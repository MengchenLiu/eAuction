package com.eAuction.Posting.domain;

import java.sql.Timestamp;

/**
 * Created by DSimeon on 11/2/2017.
 */
public class PostingBuyItNow extends Posting {
	private static final int postTypeId = 2;
	private double price;

	public PostingBuyItNow() {
	}

	public PostingBuyItNow(int itemID, String itemName, int quantity, int sellerID, String description, String imageUrl, String createdTime, String startTime, String expirationTime, boolean isNotified, boolean isDeleted, double price) {
		super(itemID, itemName, quantity, sellerID, description, imageUrl, createdTime, startTime, expirationTime, isNotified, isDeleted);
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
}
