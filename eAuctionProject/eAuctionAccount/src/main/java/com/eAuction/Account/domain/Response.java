package com.eAuction.Account.domain;

import com.eAuction.Account.objectInterface.ResponseInterface;
import com.eAuction.Account.objectInterface.UserInterface;

public class Response implements UserInterface,ResponseInterface{
	private boolean requestResponse;
	private String error;
	
	public Response(boolean flag,String error) {
		this.requestResponse = flag;
		this.error = error;
	}

	public boolean isRequestResponse() {
		return requestResponse;
	}

	public void setRequestResponse(boolean requestResponse) {
		this.requestResponse = requestResponse;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	
	
}
