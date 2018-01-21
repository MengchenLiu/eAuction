package com.eAuction.Posting.configuration;

import com.eAuction.Posting.controllers.Controller;
import com.eAuction.Posting.domain.AuctionBid;
import com.eAuction.Posting.domain.PostingAuction;
import com.eAuction.Posting.domain.PostingBuyItNow;
import com.eAuction.Posting.repositories.PostingAuctionRepository;
import com.eAuction.Posting.repositories.PostingBuyItNowRepository;
import com.eAuction.Posting.services.PostingAuctionServiceImpl;
import com.eAuction.Posting.services.PostingBuyItNowServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;

@SpringBootTest
public class EAuctionPostingApplicationTests {
	private Controller controller;

	@Before
	public void init(){
		PostingAuctionRepository postingAuctionRepository = new PostingAuctionRepository();
		PostingAuctionServiceImpl postingAuctionService = new PostingAuctionServiceImpl();
		postingAuctionService.setRepository(postingAuctionRepository);

		PostingBuyItNowRepository postingBuyItNowRepository = new PostingBuyItNowRepository();
		PostingBuyItNowServiceImpl postingBuyItNowService = new PostingBuyItNowServiceImpl();
		postingBuyItNowService.setRepository(postingBuyItNowRepository);

		controller = new Controller();
		controller.setService(postingAuctionService, postingBuyItNowService);
	}


	/**************** POSTING AUCTION **************/

	private PostingAuction addDummyPostingAuction(){
		return controller.addPostingAuction(
				"-1"
				, "ITEM_NAME"
				, "-2"
				, "DESCRIPTION"
				,"IMAGE_URL"
				, "2017-11-11 11:11:11.0"
				, "2017-12-11 11:11:11.0"
				, "100.0"
				, null
		);
	}

	private boolean deleteDummyPostingAuction(int id){
		return controller.deletePostingAuction(String.valueOf(id));
	}

	@Test
	public void testAddShowPostingAuction() throws Exception {
		PostingAuction newPosting = addDummyPostingAuction();
		PostingAuction comparedPosting = controller.showPostingAuction(String.valueOf(newPosting.getId()));
		assertEquals(-1, comparedPosting.getItemID());
		assertEquals("ITEM_NAME", comparedPosting.getItemName());
		assertEquals(-2, comparedPosting.getSellerID());
		assertEquals("DESCRIPTION", comparedPosting.getDescription());
		assertEquals("IMAGE_URL", comparedPosting.getImageUrl());
		assertEquals("2017-11-11 11:11:11.0", comparedPosting.getStartTime());
		assertEquals("2017-12-11 11:11:11.0", comparedPosting.getExpirationTime());
		assertEquals(100.0, comparedPosting.getStartPrice());
		assertEquals(false, comparedPosting.isNotified());
		assertEquals(false, comparedPosting.isDeleted());

		deleteDummyPostingAuction(newPosting.getId());
	}

	@Test
	public void testAddUpdateShowPostingAuction() throws Exception {
		PostingAuction newPosting = addDummyPostingAuction();
		String newDescription = "NEW_DESCRIPTION";
		controller.updatePostingAuction(
				String.valueOf(newPosting.getId())
				, String.valueOf(newPosting.getItemID())
				, String.valueOf(newPosting.getItemName())
				, String.valueOf(newPosting.getSellerID())
				, String.valueOf(newDescription)
				, String.valueOf(newPosting.getImageUrl())
				, String.valueOf(newPosting.getStartTime())
				, String.valueOf(newPosting.getExpirationTime())
				, String.valueOf(newPosting.getStartPrice())
				, null
		);

		PostingAuction comparedPosting = controller.showPostingAuction(String.valueOf(newPosting.getId()));
		assertEquals(newPosting.getItemID(), comparedPosting.getItemID());
		assertEquals(newPosting.getItemName(), comparedPosting.getItemName());
		assertEquals(newPosting.getSellerID(), comparedPosting.getSellerID());
		assertEquals(newDescription, comparedPosting.getDescription());
		assertEquals(newPosting.getImageUrl(), comparedPosting.getImageUrl());
		assertEquals(newPosting.getStartTime(), comparedPosting.getStartTime());
		assertEquals(newPosting.getExpirationTime(), comparedPosting.getExpirationTime());
		assertEquals(newPosting.getStartPrice(), comparedPosting.getStartPrice());

		deleteDummyPostingAuction(newPosting.getId());
	}

	@Test
	public void testAddDeletePostingAuction() throws Exception {
		PostingAuction newPosting = addDummyPostingAuction();

		boolean isDeleted = controller.deletePostingAuction(String.valueOf(newPosting.getId()));
		PostingAuction deletedPosting = controller.showPostingAuction(String.valueOf(newPosting.getId()));
		if(isDeleted) {
			assertEquals(true, deletedPosting.isDeleted());
		}
		else
			assertEquals(false, deletedPosting.isDeleted());

		deleteDummyPostingAuction(newPosting.getId());
	}

	@Test
	public void testSubmitBid() throws Exception {
		PostingAuction newPosting = addDummyPostingAuction();
		AuctionBid newBid1 = controller.submitBid(String.valueOf(newPosting.getId()), "-3", "200.0", null);
		newPosting = controller.showPostingAuction(String.valueOf(newPosting.getId()));
		assertEquals(-3, newBid1.getBidderId());
		assertEquals(200.0, newBid1.getBidPrice());
		assertEquals(-3, newPosting.getCurBidder());
		assertEquals(200.0, newPosting.getCurPrice());

		AuctionBid newBid2 = controller.submitBid(String.valueOf(newPosting.getId()), "-4", "210.0", null);
		newPosting = controller.showPostingAuction(String.valueOf(newPosting.getId()));
		assertEquals(-4, newPosting.getCurBidder());
		assertEquals(210.0, newPosting.getCurPrice());

		List<AuctionBid> bidHistory = controller.showBidHistory(String.valueOf(newPosting.getId()));
		assertEquals(2, bidHistory.size());

		deleteDummyPostingAuction(newPosting.getId());
	}


	/************** POSTING BUY IT NOW ****************/

	private PostingBuyItNow addDummyPostingBuyItNow(){
		return controller.addPostingBuyItNow(
				"-1"
				, "ITEM_NAME"
				, "10"
				, "-2"
				, "DESCRIPTION"
				,"IMAGE_URL"
				, "2017-11-11 11:11:11.0"
				, "2017-12-11 11:11:11.0"
				, "100.0"
				, null
		);
	}

	private boolean deleteDummyPostingBuyItNow(int id){
		return controller.deletePostingBuyItNow(String.valueOf(id));
	}

	@Test
	public void testAddShowPostingBuyItNow() throws Exception {
		PostingBuyItNow newPosting = addDummyPostingBuyItNow();
		PostingBuyItNow comparedPosting = controller.showPostingBuyItNow(String.valueOf(newPosting.getId()));
		assertEquals(-1, comparedPosting.getItemID());
		assertEquals("ITEM_NAME", comparedPosting.getItemName());
		assertEquals(10, comparedPosting.getQuantity());
		assertEquals(-2, comparedPosting.getSellerID());
		assertEquals("DESCRIPTION", comparedPosting.getDescription());
		assertEquals("IMAGE_URL", comparedPosting.getImageUrl());
		assertEquals("2017-11-11 11:11:11.0", comparedPosting.getStartTime());
		assertEquals("2017-12-11 11:11:11.0", comparedPosting.getExpirationTime());
		assertEquals(100.0, comparedPosting.getPrice());

		deleteDummyPostingBuyItNow(newPosting.getId());
	}

	@Test
	public void testAddUpdateShowPostingBuyItNow() throws Exception {
		PostingBuyItNow newPosting = addDummyPostingBuyItNow();
		String newDescription = "NEW_DESCRIPTION";
		controller.updatePostingBuyItNow(
				String.valueOf(newPosting.getId())
				, String.valueOf(newPosting.getItemID())
				, String.valueOf(newPosting.getItemName())
				, String.valueOf(newPosting.getQuantity())
				, String.valueOf(newPosting.getSellerID())
				, String.valueOf(newDescription)
				, String.valueOf(newPosting.getImageUrl())
				, String.valueOf(newPosting.getStartTime())
				, String.valueOf(newPosting.getExpirationTime())
				, String.valueOf(newPosting.getPrice())
				, null
		);

		PostingBuyItNow comparedPosting = controller.showPostingBuyItNow(String.valueOf(newPosting.getId()));
		assertEquals(newPosting.getItemID(), comparedPosting.getItemID());
		assertEquals(newPosting.getItemName(), comparedPosting.getItemName());
		assertEquals(newPosting.getQuantity(), comparedPosting.getQuantity());
		assertEquals(newPosting.getSellerID(), comparedPosting.getSellerID());
		assertEquals(newDescription, comparedPosting.getDescription());
		assertEquals(newPosting.getImageUrl(), comparedPosting.getImageUrl());
		assertEquals(newPosting.getStartTime(), comparedPosting.getStartTime());
		assertEquals(newPosting.getExpirationTime(), comparedPosting.getExpirationTime());
		assertEquals(newPosting.getPrice(), comparedPosting.getPrice());

		deleteDummyPostingBuyItNow(newPosting.getId());
	}

	@Test
	public void testAddDeletePostingBuyItNow() throws Exception {
		PostingBuyItNow newPosting = addDummyPostingBuyItNow();

		boolean isDeleted = controller.deletePostingBuyItNow(String.valueOf(newPosting.getId()));
		PostingBuyItNow deletedPosting = controller.showPostingBuyItNow(String.valueOf(newPosting.getId()));
		if(isDeleted) {
			assertEquals(true, deletedPosting.isDeleted());
		}
		else
			assertEquals(false, deletedPosting.isDeleted());
		
		deleteDummyPostingBuyItNow(newPosting.getId());
	}


}
