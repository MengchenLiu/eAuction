package com.eAuction.Posting.domain;

public class PostingBidder {
    private int postingId;
    private int postingTypeId;
    private int bidderId;

    public PostingBidder() {
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

    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }
}
