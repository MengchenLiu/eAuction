package com.eAuction.Posting.domain;

import java.sql.Timestamp;

/**
 * Created by DSimeon on 11/4/2017.
 */
public class AuctionBid {
	private int historyId;
	private int postingAuctionId;
	private int bidderId;
	private double bidPrice;
	private String bidTime;
	
	public AuctionBid() {
	}

	public AuctionBid(int postingAuctionId, int bidderId, double bidPrice) {
		this.postingAuctionId = postingAuctionId;
		this.bidderId = bidderId;
		this.bidPrice = bidPrice;
	}

	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	public int getPostingAuctionId() {
		return postingAuctionId;
	}

	public void setPostingAuctionId(int postingAuctionId) {
		this.postingAuctionId = postingAuctionId;
	}

	public int getBidderId() {
		return bidderId;
	}

	public void setBidderId(int bidderId) {
		this.bidderId = bidderId;
	}

	public double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public String getBidTime() {
		return bidTime;
	}

	public void setBidTime(String bidTime) {
		this.bidTime = bidTime;
	}
}
