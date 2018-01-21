package com.eAuction.Posting.services;

import com.eAuction.Posting.domain.PostingBuyItNow;
import com.eAuction.Posting.repositories.PostingBuyItNowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostingBuyItNowServiceImpl implements PostingBuyItNowService {
	private PostingBuyItNowRepository repository;

	@Autowired
	public void setRepository(PostingBuyItNowRepository repository) {
		this.repository = repository;
	}

	/**
	 * List all categories that exist in the database
	 * @return list of PostingBuyItNows
     */
	@Override
	public List<PostingBuyItNow> getAllPostings() {
		return repository.getAllPostings();
	}

	@Override
	public List<PostingBuyItNow> getPostingsBySellerId(Integer userId) {
		return repository.getPostingsBySellerId(userId);
	}

	@Override
	public List<PostingBuyItNow> getAllPostingsByDate(String startDate, String endDate) {
		return repository.getAllPostingsByDate(startDate, endDate);
	}

	/**
	 * Returns a posting by a given postingID
	 * @param id: postingID
	 * @return PostingBuyItNow
     */
	@Override
	public PostingBuyItNow getPostingById(Integer id) {
		return repository.getPosting(id);
	}

	/**
	 * Saves a new posting and returns it
	 * @param posting: new posting
	 * @return postingID
     */
	@Override
	public int addPosting(PostingBuyItNow posting) {
		return repository.addPosting(posting);
	}

	@Override
	public boolean updatePosting(PostingBuyItNow posting) {
		return repository.updatePosting(posting);
	}

	/**
	 * Deletes a posting by a given postingID
	 * @param id: postingID
     */
	@Override
	public boolean deletePosting(Integer id) {
		return repository.deletePosting(id);
	}
	@Override

	public boolean reducePostingQuantity(Integer postingId, Integer reduceQuantity){
		return repository.reducePostingQuantity(postingId, reduceQuantity);
	}
}
