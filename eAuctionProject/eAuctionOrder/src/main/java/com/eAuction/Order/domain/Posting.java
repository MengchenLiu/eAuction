package com.eAuction.Order.domain;

public class Posting {
    private int orderId;
    private int postingId;
    private int postingTypeId;

    public Posting() {
    }

    public Posting(int orderId, int postingId, int postingTypeId) {
        this.orderId = orderId;
        this.postingId = postingId;
        this.postingTypeId = postingTypeId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPostingId() {
        return postingId;
    }

    public void setPostingId(int postingId) {
        this.postingId = postingId;
    }

    public int getPostingTypeId() {
        return postingTypeId;
    }

    public void setPostingTypeId(int postingTypeId) {
        this.postingTypeId = postingTypeId;
    }
}
