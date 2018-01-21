package com.eAuction.Posting.services;

import com.eAuction.Posting.domain.AuctionBid;
import com.eAuction.Posting.domain.PostingAuction;
import com.eAuction.Posting.domain.PostingBidder;
import com.eAuction.Posting.repositories.PostingAuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostingAuctionServiceImpl implements PostingAuctionService {
	private PostingAuctionRepository repository;

	@Autowired
	public void setRepository(PostingAuctionRepository repository) {
		this.repository = repository;
	}

	/**
	 * List all postings that exist in the database
	 * @return list of PostingAuctions
     */
	@Override
	public List<PostingAuction> getAllPostings() {
		return repository.getAllPostings();
	}

	@Override
	public List<PostingAuction> getPostingsBySellerId(Integer userId) {
		return repository.getPostingsBySellerId(userId);
	}

	@Override
	public List<PostingAuction> getAllPostingsByDate(String startDate, String endDate) {
		return repository.getAllPostingsByDate(startDate, endDate);
	}

	@Override
	public List<PostingBidder> getExpiredPostingBidders(String compareTime) {
		return repository.getExpiredPostingBidders(compareTime);
	}

	@Override
	public List<PostingAuction> getExpiredAddedToCartPostings(String compareTime) {
		return repository.getExpiredAddedToCartPostings(compareTime);
	}

	@Override
	public List<PostingAuction> getPostingsByBidderId(Integer id) {
		return repository.getPostingsByBidderId(id);
	}

	/**
	 * Returns an posting by a given postingID
	 * @param id: postingID
	 * @return PostingAuction
     */
	@Override
	public PostingAuction getPostingById(Integer id) {
		return repository.getPosting(id);
	}

	/**
	 * Saves a new posting and returns the new ID
	 * @param posting: new posting
	 * @return postingID
     */
	@Override
	public int addPosting(PostingAuction posting) {
		return repository.addPosting(posting);
	}

	@Override
	public boolean updatePosting(PostingAuction posting) {
		return repository.updatePosting(posting);
	}

	@Override
	public boolean updateIsNotifiedByPostingId(Integer id) {
		return repository.updateIsNotifiedByPostingId(id);
	}

	@Override
	public boolean updateIsAddedToCartByPostingId(Integer id) {
		return repository.updateIsAddedToCartByPostingId(id);
	}

	/**
	 * Deletes an posting by a given postingID
	 * @param id: postingID
     */
	@Override
	public boolean deletePosting(Integer id) {
		return repository.deletePosting(id);
	}

	@Override
	public int submitBid(AuctionBid bid) {
		return repository.submitBid(bid);
	}

	@Override
	public List<AuctionBid> getBidHistory(Integer id) {
		return repository.getBidHistory(id);
	}
}
