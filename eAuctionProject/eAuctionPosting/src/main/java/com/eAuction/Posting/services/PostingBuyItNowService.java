package com.eAuction.Posting.services;

import com.eAuction.Posting.domain.PostingBidder;
import com.eAuction.Posting.domain.PostingBuyItNow;

import java.util.List;

public interface PostingBuyItNowService {
	List<PostingBuyItNow> getAllPostings();

	List<PostingBuyItNow> getPostingsBySellerId(Integer userId);

	List<PostingBuyItNow> getAllPostingsByDate(String startDate, String endDate);

	PostingBuyItNow getPostingById(Integer id);

	int addPosting(PostingBuyItNow posting);

	boolean updatePosting(PostingBuyItNow posting);

	boolean deletePosting(Integer id);

	boolean reducePostingQuantity(Integer postingId, Integer reduceQuantity);
}