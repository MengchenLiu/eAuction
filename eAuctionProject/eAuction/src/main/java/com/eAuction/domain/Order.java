package com.eAuction.domain;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by DSimeon on 11/2/2017.
 */
public class Order {
	private int id;
	private ArrayList<Posting> postingList;
	private int buyerId;
	private double amountDue;
	private int paymentId;
	private int shippingId;
	private int orderStatusId;
	private String orderTime;

	public Order() {
	}

	public Order(int buyerId, double amountDue, int paymentId, int shippingId, String orderTime) {
		this.postingList = new ArrayList<>();
		this.buyerId = buyerId;
		this.amountDue = amountDue;
		this.paymentId = paymentId;
		this.shippingId = shippingId;
		this.orderStatusId = 1;
		this.orderTime = orderTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Posting> getPostingList() {
		return postingList;
	}

	public void setPostingList(ArrayList<Posting> postingList) {
		this.postingList = postingList;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public double getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(double amountDue) {
		this.amountDue = amountDue;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getShippingId() {
		return shippingId;
	}

	public void setShippingId(int shippingId) {
		this.shippingId = shippingId;
	}

	public int getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(int orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
}
