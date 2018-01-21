package com.eAuction.Posting.domain;

import java.sql.Timestamp;

/**
 * Created by DSimeon on 11/2/2017.
 */
public class PostingAuction extends Posting {
	private static final int postTypeId = 2;
	private double startPrice;
	private double curPrice;	// same as startPrice means no bidder yet
	private int curBidder;	// -1 means no bidder yet
	private boolean isAddedToCart;

	public PostingAuction() {
		super();
	}

	public PostingAuction(int itemID, String itemName, int sellerID, String description, String imageUrl, String createdTime, String startTime, String expirationTime, boolean isNotified, boolean isDeleted, double startPrice) {
		super(itemID, itemName, 1, sellerID, description, imageUrl, createdTime, startTime, expirationTime, isNotified, isDeleted);
		this.startPrice = startPrice;
		this.curPrice = startPrice;
		this.curBidder = -1;
	}

	public static int getPostTypeId() {
		return postTypeId;
	}

	public double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(double startPrice) {
		this.startPrice = startPrice;
	}

	public double getCurPrice() {
		return curPrice;
	}

	public void setCurPrice(double curPrice) {
		this.curPrice = curPrice;
	}

	public int getCurBidder() {
		return curBidder;
	}

	public void setCurBidder(int curBidder) {
		this.curBidder = curBidder;
	}

	public boolean isAddedToCart() {
		return isAddedToCart;
	}

	public void setAddedToCart(boolean addedToCart) {
		isAddedToCart = addedToCart;
	}
}
