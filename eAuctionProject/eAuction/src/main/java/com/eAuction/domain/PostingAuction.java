package com.eAuction.domain;

import java.sql.Timestamp;

/**
 * Created by DSimeon on 11/2/2017.
 */
public class PostingAuction extends Posting {
	protected static final int postTypeId = 2;
	protected double startPrice;
	protected double curPrice;	// same as startPrice means no bidder yet
	protected int curBidder;	// -1 means no bidder yet

	public PostingAuction() {
		super();
	}

	public PostingAuction(int itemID, String itemName, int sellerID, String imageUrl, String createdTime, String startTime, String expirationTime, boolean isDeleted, double startPrice,
			int quantity, String description) {
		super(itemID, itemName, sellerID, imageUrl, createdTime, startTime, expirationTime, isDeleted,quantity,description);
		this.startPrice = startPrice;
		this.curPrice = startPrice;
		this.curBidder = -1;
	}

	public PostingAuction(int id, int itemID, String itemName, int sellerID, String imageUrl, String createdTime,
			String startTime, String expirationTime,String lastModifiedTime, boolean isDeleted,double startPrice,double curPrice, int curBidder,
			int quantity, String description,boolean isNotified) {
		super(id, itemID, itemName, sellerID, imageUrl, createdTime, startTime, expirationTime,lastModifiedTime,isDeleted,quantity,description,isNotified);
		this.startPrice = startPrice;
		this.curPrice = curPrice;
		this.curBidder = curBidder;
	}

	public PostingAuction(double startPrice, double curPrice, int curBidder) {
		super();
		this.startPrice = startPrice;
		this.curPrice = curPrice;
		this.curBidder = curBidder;
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
	
	public int getpostTypeId() {
		return postTypeId;
	}
}
