package com.eAuction.Posting.controllers;

import javax.servlet.http.HttpServletResponse;

import com.eAuction.Posting.domain.*;
import com.eAuction.Posting.services.PostingBuyItNowService;
import com.eAuction.Posting.services.PostingAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
public class Controller {
	private PostingAuctionService postingAuctionService;
	private PostingBuyItNowService postingBuyItNowService;

	@Autowired
	public void setService(PostingAuctionService postingAuctionService, PostingBuyItNowService postingBuyItNowService) {
		this.postingAuctionService = postingAuctionService;
		this.postingBuyItNowService = postingBuyItNowService;
	}

	/***************** Posting *****************/
	@RequestMapping(value = "/postings", method=RequestMethod.POST)
	public List<Posting> getAllPostings() {
		ArrayList<Posting> list = new ArrayList<>();
		list.addAll(postingAuctionService.getAllPostings());
		list.addAll(postingBuyItNowService.getAllPostings());
		return list;
	}

	@RequestMapping(value = "/postingsBySeller/{sellerId}", method=RequestMethod.POST)
	public List<Posting> getPostingsBySellerId(@PathVariable String sellerId) {
		ArrayList<Posting> list = new ArrayList<>();
		list.addAll(postingAuctionService.getPostingsBySellerId(Integer.parseInt(sellerId)));
		list.addAll(postingBuyItNowService.getPostingsBySellerId(Integer.parseInt(sellerId)));
		return list;
	}

	@RequestMapping(value = "/postingsByDate", method=RequestMethod.POST)
	public List<Posting> getAllPostingsByDate(
			@RequestParam("startDate") String startDate
			, @RequestParam("endDate") String endDate) {
		ArrayList<Posting> list = new ArrayList<>();
		list.addAll(postingAuctionService.getAllPostingsByDate(startDate, endDate));
		list.addAll(postingBuyItNowService.getAllPostingsByDate(startDate, endDate));
		return list;
	}

	/**
	 * Gets postings and its bidders that were about to expire (1 hour from now)
	 * @return List of PostingBidders
	 */
	@RequestMapping(value = "/expiredPostingBidders", method=RequestMethod.POST)
	public List<PostingBidder> getExpiredPostingBidders() {
		Timestamp now = new Timestamp(System.currentTimeMillis()+(60*60*1000));	// 1 hour from now
		return postingAuctionService.getExpiredPostingBidders(now.toString());
	}

	/**
	 * Gets postings that just got expired but the item has not been added to the winner's cart
	 * @return List of PostingAuction
	 */
	@RequestMapping(value = "/expiredAddedToCartPostings", method=RequestMethod.POST)
	public List<PostingAuction> getExpiredAddedToCartPostings() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		return postingAuctionService.getExpiredAddedToCartPostings(now.toString());
	}

	/**
	 * Reduces quantity by reduceQuantity parameter. If quantity falls to 0, delete the posting.
	 * Keep in mind, PostingAuction always has quantity = 1. So it simply removes it.
	 * @param postingID postingID
	 * @param postingTypeID 1: PostingBuyItNow, 2: PostingAuction
	 * @param reduceQuantity quantity to reduce
	 * @return true if successful, false otherwise
	 */
	@RequestMapping(value = "/reducePostingQuantity", method=RequestMethod.POST)
	public boolean reducePostingQuantity(
			@RequestParam("postingID") String postingID
			, @RequestParam("postingTypeID") String postingTypeID
			, @RequestParam("reduceQuantity") String reduceQuantity
	) {

		if(Integer.parseInt(postingTypeID) == PostingAuction.getPostTypeId())
			return postingAuctionService.deletePosting(Integer.parseInt(postingID));
		else {
			if(postingBuyItNowService.reducePostingQuantity(Integer.parseInt(postingID), Integer.parseInt(reduceQuantity))) {
				PostingBuyItNow posting = postingBuyItNowService.getPostingById(Integer.parseInt(postingID));
				if(posting.getQuantity() == 0) {
					return postingBuyItNowService.deletePosting(posting.getId());
				}
			}
		}

		return false;
	}

	/************* BuyItNow **************/
	@RequestMapping(value = "/addPostingBuyItNow", method=RequestMethod.POST)
	public PostingBuyItNow addPostingBuyItNow(
			@RequestParam("itemID") String itemID
			, @RequestParam("itemName") String itemName
			, @RequestParam("quantity") String quantity
			, @RequestParam("sellerID") String sellerID
			, @RequestParam("description") String description
			, @RequestParam("imageUrl") String imageUrl
			, @RequestParam("startTime") String startTime
			, @RequestParam("expirationTime") String expirationTime
			, @RequestParam("startPrice") String price
			, HttpServletResponse response
	) {
		PostingBuyItNow posting = new PostingBuyItNow(Integer.parseInt(itemID), itemName, Integer.parseInt(quantity)
				, Integer.parseInt(sellerID), description, imageUrl, new Timestamp(System.currentTimeMillis()).toString()
				, startTime, expirationTime, false, false, Double.parseDouble(price));
		int postingID = postingBuyItNowService.addPosting(posting);
		posting.setId(postingID);

		return posting;
	}

	@RequestMapping(value = "/showPostingBuyItNow/{id}", method=RequestMethod.POST)
	public PostingBuyItNow showPostingBuyItNow(@PathVariable String id) {
		return postingBuyItNowService.getPostingById(Integer.parseInt(id));
	}

	/**
	 * Updates existing PostingBuyItNow
	 * @param postingID postingID
	 * @param itemID itemID
	 * @param quantity quantity
	 * @param sellerID sellerID
	 * @param description description
	 * @param imageUrl imageUrl
	 * @param startTime startTime
	 * @param expirationTime expirationTime
	 * @param price price
	 * @param response response
	 * @return edited item if successful, null otherwise
	 */
	@RequestMapping(value = "/updatePostingBuyItNow", method=RequestMethod.POST)
	public PostingBuyItNow updatePostingBuyItNow(
			@RequestParam("postingID") String postingID
			, @RequestParam("itemID") String itemID
			, @RequestParam("itemName") String itemName
			, @RequestParam("quantity") String quantity
			, @RequestParam("sellerID") String sellerID
			, @RequestParam("description") String description
			, @RequestParam("imageUrl") String imageUrl
			, @RequestParam("startTime") String startTime
			, @RequestParam("expirationTime") String expirationTime
			, @RequestParam("price") String price
			, HttpServletResponse response
	) {
		PostingBuyItNow posting = postingBuyItNowService.getPostingById(Integer.parseInt(postingID));
		posting.setItemID(Integer.parseInt(itemID));
		posting.setItemName(itemName);
		posting.setQuantity(Integer.parseInt(quantity));
		posting.setSellerID(Integer.parseInt(sellerID));
		posting.setDescription(description);
		posting.setImageUrl(imageUrl);
		posting.setStartTime(startTime);
		posting.setExpirationTime(expirationTime);
		posting.setPrice(Double.parseDouble(price));

		if(postingBuyItNowService.updatePosting(posting))
			return posting;
		else
			return null;
	}

	/**
	 * Deletes PostingBuyItNow from repository
	 * @param id: postingID
	 * @return true if delete is successful, false otherwise
	 */
	@RequestMapping(value = "/deletePostingBuyItNow/{id}", method=RequestMethod.POST)
	public boolean deletePostingBuyItNow(@PathVariable String id) {
		return postingBuyItNowService.deletePosting(Integer.parseInt(id));
	}


	/************* Auction **************/
	@RequestMapping(value = "/addPostingAuction", method=RequestMethod.POST)
	public PostingAuction addPostingAuction(
			@RequestParam("itemID") String itemID
			, @RequestParam("itemName") String itemName
			, @RequestParam("sellerID") String sellerID
			, @RequestParam("description") String description
			, @RequestParam("imageUrl") String imageUrl
			, @RequestParam("startTime") String startTime
			, @RequestParam("expirationTime") String expirationTime
			, @RequestParam("startPrice") String startPrice
			, HttpServletResponse response
	) {
		PostingAuction posting = new PostingAuction(Integer.parseInt(itemID), itemName, Integer.parseInt(sellerID)
				, description, imageUrl,  new Timestamp(System.currentTimeMillis()).toString(), startTime
				, expirationTime, false, false, Double.parseDouble(startPrice));
		int postingID = postingAuctionService.addPosting(posting);
		posting.setId(postingID);

		return posting;
	}

	@RequestMapping(value = "/showPostingAuction/{id}", method=RequestMethod.POST)
	public PostingAuction showPostingAuction(@PathVariable String id) {
		return postingAuctionService.getPostingById(Integer.parseInt(id));
	}

	@RequestMapping(value = "/getPostingsByBidderId/{id}", method=RequestMethod.POST)
	public List<PostingAuction> getPostingsByBidderId(@PathVariable String id) {
		return postingAuctionService.getPostingsByBidderId(Integer.parseInt(id));
	}

	/**
	 * Deletes postingAuction from repository
	 * @param id: postingID
	 * @return true if delete is successful, false otherwise
	 */
	@RequestMapping(value = "/deletePostingAuction/{id}", method=RequestMethod.POST)
	public boolean deletePostingAuction(@PathVariable String id) {
		return postingAuctionService.deletePosting(Integer.parseInt(id));
	}

	/**
	 * Updates existing PostingAuction
	 * @param postingID postingID
	 * @param itemID itemID
	 * @param sellerID sellerID
	 * @param imageUrl imageUrl
	 * @param startTime startTime
	 * @param expirationTime expirationTime
	 * @param startPrice startPrice
	 * @param response response
	 * @return edited item if successful, null otherwise
	 */
	@RequestMapping(value = "/updatePostingAuction", method=RequestMethod.POST)
	public PostingAuction updatePostingAuction(
			@RequestParam("postingID") String postingID
			, @RequestParam("itemID") String itemID
			, @RequestParam("itemName") String itemName
			, @RequestParam("sellerID") String sellerID
			, @RequestParam("description") String description
			, @RequestParam("imageUrl") String imageUrl
			, @RequestParam("startTime") String startTime
			, @RequestParam("expirationTime") String expirationTime
			, @RequestParam("startPrice") String startPrice
			, HttpServletResponse response
	) {
		PostingAuction posting = postingAuctionService.getPostingById(Integer.parseInt(postingID));
		posting.setItemID(Integer.parseInt(itemID));
		posting.setItemName(itemName);
		posting.setSellerID(Integer.parseInt(sellerID));
		posting.setDescription(description);
		posting.setImageUrl(imageUrl);
		posting.setStartTime(startTime);
		posting.setExpirationTime(expirationTime);
		posting.setStartPrice(Double.parseDouble(startPrice));

		if(postingAuctionService.updatePosting(posting))
			return posting;
		else
			return null;
	}

	@RequestMapping(value = "/updateIsNotifiedByPostingId", method=RequestMethod.POST)
	public boolean updateIsNotifiedByPostingId(@RequestParam("id") String postingID, HttpServletResponse response) {
		return postingAuctionService.updateIsNotifiedByPostingId(Integer.parseInt(postingID));
	}

	@RequestMapping(value = "/updateIsAddedToCartByPostingId", method=RequestMethod.POST)
	public boolean updateIsAddedToCartByPostingId(@RequestParam("id") String postingID, HttpServletResponse response) {
		return postingAuctionService.updateIsAddedToCartByPostingId(Integer.parseInt(postingID));
	}


	/***************** Auction History ******************/
	@RequestMapping(value = "/submitBid", method=RequestMethod.POST)
	public AuctionBid submitBid(
			@RequestParam("postingAuctionId") String postingAuctionId
			, @RequestParam("bidderId") String bidderId
			, @RequestParam("price") String price
			, HttpServletResponse response
	) {
		AuctionBid bid = new AuctionBid(Integer.parseInt(postingAuctionId), Integer.parseInt(bidderId), Double.parseDouble(price));
		int historyId = postingAuctionService.submitBid(bid);
		bid.setHistoryId(historyId);
		return bid;
	}

	/**
	 * Returns bid history of a particular auction
	 * @param id: PostingAuctionID
	 * @return list of AuctionBids sorted by the latest first
	 */
	@RequestMapping(value = "/showBidHistory/{id}", method=RequestMethod.POST)
	public List<AuctionBid> showBidHistory(@PathVariable String id) {
		return postingAuctionService.getBidHistory(Integer.parseInt(id));
	}


}
