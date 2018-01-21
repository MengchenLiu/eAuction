package com.eAuction.Posting.services;

import com.eAuction.Posting.domain.AuctionBid;
import com.eAuction.Posting.domain.PostingAuction;
import com.eAuction.Posting.domain.PostingBidder;

import java.util.List;

public interface PostingAuctionService {
	List<PostingAuction> getAllPostings();

	List<PostingAuction> getPostingsBySellerId(Integer userId);

	List<PostingAuction> getAllPostingsByDate(String startDate, String endDate);

	List<PostingBidder> getExpiredPostingBidders(String compareTime);

	List<PostingAuction> getExpiredAddedToCartPostings(String compareTime);

	List<PostingAuction> getPostingsByBidderId(Integer id);

	PostingAuction getPostingById(Integer id);

	int addPosting(PostingAuction posting);

	boolean updatePosting(PostingAuction posting);

	boolean updateIsNotifiedByPostingId(Integer id);

	boolean updateIsAddedToCartByPostingId(Integer id);

	boolean deletePosting(Integer id);

	int submitBid(AuctionBid bid);

	List<AuctionBid> getBidHistory(Integer id);
}