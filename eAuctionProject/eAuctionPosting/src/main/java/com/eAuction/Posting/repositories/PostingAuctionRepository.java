package com.eAuction.Posting.repositories;

import com.eAuction.Posting.domain.AuctionBid;
import com.eAuction.Posting.domain.PostingAuction;
import com.eAuction.Posting.domain.PostingBidder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class PostingAuctionRepository {
	private Connection con = null;

	public PostingAuctionRepository() {
		String url = "jdbc:mysql://localhost:3306/eAuction";	// eAuction is the DB name
		String username = "root";
		String password = "password";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<PostingAuction> getAllPostings(){
		List<PostingAuction> postings = new ArrayList<>();
		String sql = "SELECT * FROM posting_auction WHERE isDeleted = 0 AND expirationTime > '" + new Timestamp(System.currentTimeMillis())+ "';";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				PostingAuction posting = new PostingAuction();
				posting.setId(Integer.parseInt(rs.getString("id")));
				posting.setItemID(rs.getInt("itemID"));
				posting.setItemName(rs.getString("itemName"));
				posting.setQuantity(rs.getInt("quantity"));
				posting.setSellerID(rs.getInt("sellerID"));
				posting.setDescription(rs.getString("description"));
				posting.setImageUrl(rs.getString("imageUrl"));
				posting.setCreatedTime(rs.getString("createdTime"));
				posting.setStartTime(rs.getString("startTime"));
				posting.setExpirationTime(rs.getString("expirationTime"));
				posting.setLastModifiedTime(rs.getString("lastModifiedTime"));
				posting.setStartPrice(rs.getDouble("startPrice"));
				posting.setCurPrice(rs.getDouble("curPrice"));
				posting.setCurBidder(rs.getInt("curBidder"));
				posting.setNotified((rs.getInt("isNotified") == 1));
				posting.setNotified((rs.getInt("isAddedToCart") == 1));
				posting.setDeleted((rs.getInt("isDeleted") == 1));

				postings.add(posting);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return postings;
	}

	public List<PostingAuction> getPostingsBySellerId(int sellerId){
		List<PostingAuction> postings = new ArrayList<>();
		String sql = "SELECT * FROM posting_auction WHERE isDeleted = 0 AND sellerID = " + sellerId + ";";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				PostingAuction posting = new PostingAuction();
				posting.setId(Integer.parseInt(rs.getString("id")));
				posting.setItemID(rs.getInt("itemID"));
				posting.setItemName(rs.getString("itemName"));
				posting.setQuantity(rs.getInt("quantity"));
				posting.setSellerID(rs.getInt("sellerID"));
				posting.setDescription(rs.getString("description"));
				posting.setImageUrl(rs.getString("imageUrl"));
				posting.setCreatedTime(rs.getString("createdTime"));
				posting.setStartTime(rs.getString("startTime"));
				posting.setExpirationTime(rs.getString("expirationTime"));
				posting.setLastModifiedTime(rs.getString("lastModifiedTime"));
				posting.setStartPrice(rs.getDouble("startPrice"));
				posting.setCurPrice(rs.getDouble("curPrice"));
				posting.setCurBidder(rs.getInt("curBidder"));
				posting.setNotified((rs.getInt("isNotified") == 1));
				posting.setNotified((rs.getInt("isAddedToCart") == 1));
				posting.setDeleted((rs.getInt("isDeleted") == 1));

				postings.add(posting);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return postings;
	}

	public List<PostingAuction> getAllPostingsByDate(String startDate, String endDate){
		List<PostingAuction> postings = new ArrayList<>();
		String sql = "SELECT * FROM posting_auction WHERE isDeleted = 0";
		if(startDate != null)
			sql += " AND startTime >= '" + startDate + "'";
		if(endDate != null)
			sql += " AND startTime <= '" + endDate + "'";
		sql += ";";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				PostingAuction posting = new PostingAuction();
				posting.setId(Integer.parseInt(rs.getString("id")));
				posting.setItemID(rs.getInt("itemID"));
				posting.setItemName(rs.getString("itemName"));
				posting.setQuantity(rs.getInt("quantity"));
				posting.setSellerID(rs.getInt("sellerID"));
				posting.setDescription(rs.getString("description"));
				posting.setImageUrl(rs.getString("imageUrl"));
				posting.setCreatedTime(rs.getString("createdTime"));
				posting.setStartTime(rs.getString("startTime"));
				posting.setExpirationTime(rs.getString("expirationTime"));
				posting.setLastModifiedTime(rs.getString("lastModifiedTime"));
				posting.setStartPrice(rs.getDouble("startPrice"));
				posting.setCurPrice(rs.getDouble("curPrice"));
				posting.setCurBidder(rs.getInt("curBidder"));
				posting.setNotified((rs.getInt("isNotified") == 1));
				posting.setNotified((rs.getInt("isAddedToCart") == 1));
				posting.setDeleted((rs.getInt("isDeleted") == 1));

				postings.add(posting);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return postings;
	}

	public List<PostingBidder> getExpiredPostingBidders(String compareTime){
		List<PostingBidder> postingBidders = new ArrayList<>();
		String sql = "SELECT * FROM posting_auction WHERE isDeleted = 0 AND isNotified = 0 AND expirationTime < '"+compareTime+"';";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				PostingAuction posting = new PostingAuction();
				posting.setId(Integer.parseInt(rs.getString("id")));
				posting.setItemID(rs.getInt("itemID"));
				posting.setItemName(rs.getString("itemName"));
				posting.setQuantity(rs.getInt("quantity"));
				posting.setSellerID(rs.getInt("sellerID"));
				posting.setDescription(rs.getString("description"));
				posting.setImageUrl(rs.getString("imageUrl"));
				posting.setCreatedTime(rs.getString("createdTime"));
				posting.setStartTime(rs.getString("startTime"));
				posting.setExpirationTime(rs.getString("expirationTime"));
				posting.setLastModifiedTime(rs.getString("lastModifiedTime"));
				posting.setStartPrice(rs.getDouble("startPrice"));
				posting.setCurPrice(rs.getDouble("curPrice"));
				posting.setCurBidder(rs.getInt("curBidder"));
				posting.setNotified((rs.getInt("isNotified") == 1));
				posting.setNotified((rs.getInt("isAddedToCart") == 1));
				posting.setDeleted((rs.getInt("isDeleted") == 1));

				String sql2 = "SELECT DISTINCT postingAuctionId, bidderId FROM auction_bid_history WHERE postingAuctionId = " + posting.getId() + ";";
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2.executeQuery(sql2);
				while(rs2.next()) {
					PostingBidder postingBidder = new PostingBidder();
					postingBidder.setPostingId(posting.getId());
					postingBidder.setPostingTypeId(posting.getPostTypeId());
					postingBidder.setBidderId(rs2.getInt("bidderId"));

					postingBidders.add(postingBidder);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return postingBidders;
	}

	public List<PostingAuction> getExpiredAddedToCartPostings(String compareTime){
		List<PostingAuction> postings = new ArrayList<>();
		String sql = "SELECT * FROM posting_auction WHERE isDeleted = 0 AND isAddedToCart = 0 AND expirationTime < '"+compareTime+"';";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				PostingAuction posting = new PostingAuction();
				posting.setId(Integer.parseInt(rs.getString("id")));
				posting.setItemID(rs.getInt("itemID"));
				posting.setItemName(rs.getString("itemName"));
				posting.setQuantity(rs.getInt("quantity"));
				posting.setSellerID(rs.getInt("sellerID"));
				posting.setDescription(rs.getString("description"));
				posting.setImageUrl(rs.getString("imageUrl"));
				posting.setCreatedTime(rs.getString("createdTime"));
				posting.setStartTime(rs.getString("startTime"));
				posting.setExpirationTime(rs.getString("expirationTime"));
				posting.setLastModifiedTime(rs.getString("lastModifiedTime"));
				posting.setStartPrice(rs.getDouble("startPrice"));
				posting.setCurPrice(rs.getDouble("curPrice"));
				posting.setCurBidder(rs.getInt("curBidder"));
				posting.setNotified((rs.getInt("isNotified") == 1));
				posting.setNotified((rs.getInt("isAddedToCart") == 1));
				posting.setDeleted((rs.getInt("isDeleted") == 1));

				postings.add(posting);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return postings;
	}

	public List<PostingAuction> getPostingsByBidderId(int id){
		List<PostingAuction> postings = new ArrayList<>();
		String sql = "SELECT pa.* FROM posting_auction pa INNER JOIN auction_bid_history abh ON pa.id = abh.postingAuctionId WHERE abh.bidderId = " + id + ";";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				PostingAuction posting = new PostingAuction();
				posting.setId(Integer.parseInt(rs.getString("id")));
				posting.setItemID(rs.getInt("itemID"));
				posting.setItemName(rs.getString("itemName"));
				posting.setQuantity(rs.getInt("quantity"));
				posting.setSellerID(rs.getInt("sellerID"));
				posting.setDescription(rs.getString("description"));
				posting.setImageUrl(rs.getString("imageUrl"));
				posting.setCreatedTime(rs.getString("createdTime"));
				posting.setStartTime(rs.getString("startTime"));
				posting.setExpirationTime(rs.getString("expirationTime"));
				posting.setLastModifiedTime(rs.getString("lastModifiedTime"));
				posting.setStartPrice(rs.getDouble("startPrice"));
				posting.setCurPrice(rs.getDouble("curPrice"));
				posting.setCurBidder(rs.getInt("curBidder"));
				posting.setNotified((rs.getInt("isNotified") == 1));
				posting.setNotified((rs.getInt("isAddedToCart") == 1));
				posting.setDeleted((rs.getInt("isDeleted") == 1));

				postings.add(posting);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return postings;
	}

	public PostingAuction getPosting(int id){
		String sql = "SELECT * FROM posting_auction WHERE id = " + id + ";";
		PostingAuction posting = null;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				posting = new PostingAuction();
				posting.setId(rs.getInt("id"));
				posting.setItemID(rs.getInt("itemID"));
				posting.setItemName(rs.getString("itemName"));
				posting.setQuantity(rs.getInt("quantity"));
				posting.setSellerID(rs.getInt("sellerID"));
				posting.setDescription(rs.getString("description"));
				posting.setImageUrl(rs.getString("imageUrl"));
				posting.setCreatedTime(rs.getString("createdTime"));
				posting.setStartTime(rs.getString("startTime"));
				posting.setExpirationTime(rs.getString("expirationTime"));
				posting.setLastModifiedTime(rs.getString("lastModifiedTime"));
				posting.setStartPrice(rs.getDouble("startPrice"));
				posting.setCurPrice(rs.getDouble("curPrice"));
				posting.setCurBidder(rs.getInt("curBidder"));
				posting.setNotified((rs.getInt("isNotified") == 1));
				posting.setNotified((rs.getInt("isAddedToCart") == 1));
				posting.setDeleted((rs.getInt("isDeleted") == 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return posting;
	}

	public int addPosting(PostingAuction posting){
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO posting_auction").append("\n");
		sb.append("(itemId, itemName, quantity, sellerId, startPrice, curPrice, curBidder, description, imageUrl, startTime, expirationTime)").append("\n");
		sb.append("VALUES (?,?,?,?,?,?,?,?,?,?,?);");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, posting.getItemID());
			stmt.setString(2, posting.getItemName());
			stmt.setInt(3, posting.getQuantity());
			stmt.setInt(4, posting.getSellerID());
			stmt.setDouble(5, posting.getStartPrice());
			stmt.setDouble(6, posting.getCurPrice());
			stmt.setDouble(7, posting.getCurBidder());
			stmt.setString(8, posting.getDescription());
			stmt.setString(9, posting.getImageUrl());
			stmt.setString(10, posting.getStartTime());
			stmt.setString(11, posting.getExpirationTime());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public boolean updatePosting(PostingAuction posting){
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE posting_auction" + "\n" +
				"SET itemId = ?" + "\n" +
				", itemName = ?" + "\n" +
				", quantity = ?" + "\n" +
				", sellerId = ?" + "\n" +
				", startPrice = ?" + "\n" +
				", curPrice = ?" + "\n" +
				", curBidder = ?" + "\n" +
				", description = ?" + "\n" +
				", imageUrl = ?" + "\n" +
				", startTime = ?" + "\n" +
				", expirationTime = ?" + "\n" +
				", isNotified = ?" + "\n" +
				", isAddedToCart = ?" + "\n" +
				", isDeleted = ?" + "\n" +
				"WHERE id = ?;");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, posting.getItemID());
			stmt.setString(2, posting.getItemName());
			stmt.setInt(3, posting.getQuantity());
			stmt.setInt(4, posting.getSellerID());
			stmt.setDouble(5, posting.getStartPrice());
			stmt.setDouble(6, posting.getCurPrice());
			stmt.setDouble(7, posting.getCurBidder());
			stmt.setString(8, posting.getDescription());
			stmt.setString(9, posting.getImageUrl());
			stmt.setString(10, posting.getStartTime());
			stmt.setString(11, posting.getExpirationTime());
			stmt.setInt(12, (posting.isNotified() ? 1 : 0));
			stmt.setInt(13, (posting.isAddedToCart() ? 1 : 0));
			stmt.setInt(14, (posting.isDeleted() ? 1 : 0));
			stmt.setInt(15, posting.getId());

			stmt.executeUpdate();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateIsNotifiedByPostingId(int id){
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE posting_auction" + "\n" +
				"SET isNotified = 1" + "\n" +
				"WHERE id = ?;");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);

			stmt.executeUpdate();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateIsAddedToCartByPostingId(int id){
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE posting_auction" + "\n" +
				"SET isAddedToCart = 1" + "\n" +
				"WHERE id = ?;");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);

			stmt.executeUpdate();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean deletePosting(int id){
		String sql = "UPDATE posting_auction SET isDeleted = 1 WHERE id = ?;";

		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public int submitBid(AuctionBid bid){
		// Updates posting_auction table
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE posting_auction" + "\n" +
				"SET curPrice = ?" + "\n" +
				", curBidder = ?" + "\n" +
				"WHERE id = ?;");
		String sql = sb.toString();

		boolean flag = false;
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setDouble(1, bid.getBidPrice());
			stmt.setInt(2, bid.getBidderId());
			stmt.setInt(3, bid.getPostingAuctionId());

			stmt.executeUpdate();

			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(!flag)
			return -1;

		// Inserts into history
		sb = new StringBuilder();
		sb.append("INSERT INTO auction_bid_history").append("\n");
		sb.append("(postingAuctionId, bidderId, bidPrice)").append("\n");
		sb.append("VALUES (?,?,?);");

		sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, bid.getPostingAuctionId());
			stmt.setInt(2, bid.getBidderId());
			stmt.setDouble(3, bid.getBidPrice());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public List<AuctionBid> getBidHistory(int id){
		List<AuctionBid> bidHistory = new ArrayList<>();
		String sql = "SELECT * FROM auction_bid_history WHERE postingAuctionId = " + id + " ORDER BY historyId DESC;";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				AuctionBid auctionBid = new AuctionBid();
				auctionBid.setHistoryId(rs.getInt("historyId"));
				auctionBid.setPostingAuctionId(rs.getInt("postingAuctionId"));
				auctionBid.setBidderId(rs.getInt("bidderId"));
				auctionBid.setBidPrice(rs.getDouble("bidPrice"));
				auctionBid.setBidTime(rs.getString("bidTime"));

				bidHistory.add(auctionBid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bidHistory;
	}
}
