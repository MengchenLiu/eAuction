package com.eAuction.Notification.domain;

public class Response {
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
