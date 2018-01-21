package com.eAuction.Account.domain;

import com.eAuction.Account.objectInterface.ResponseInterface;

public class Payment implements ResponseInterface{
	private int id;
	private String cardNumber;
	private String holderName;
	private String cvs;
	private String expiryMonth;
	private String expiryYear;
	private String userName;
	
	
	public Payment(int id, String cardNumber, String holderName, String cvs, String expiryMonth, String expiryYear, String userName) {
		this.cardNumber =cardNumber;
		this.id = id;
		this.holderName = holderName;
		this.cvs = cvs;
		this.expiryYear = expiryYear;
		this.expiryMonth = expiryMonth;
		this.userName = userName;
	}
	
	public Payment( String cardNumber, String holderName, String cvs, String expiryMonth, String expiryYear, String userName) {
		this.cardNumber =cardNumber;
		this.holderName = holderName;
		this.cvs = cvs;
		this.expiryYear = expiryYear;
		this.expiryMonth = expiryMonth;
		this.userName = userName;
	}
	
	public Payment() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getCvs() {
		return cvs;
	}
	public void setCvs(String cvs) {
		this.cvs = cvs;
	}
	public String getExpiryMonth() {
		return expiryMonth;
	}
	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}
	public String getExpiryYear() {
		return expiryYear;
	}
	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
