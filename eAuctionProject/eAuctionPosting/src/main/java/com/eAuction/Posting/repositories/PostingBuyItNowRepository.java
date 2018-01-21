package com.eAuction.Posting.repositories;

import com.eAuction.Posting.domain.PostingBidder;
import com.eAuction.Posting.domain.PostingBuyItNow;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class PostingBuyItNowRepository {
	private Connection con = null;

	public PostingBuyItNowRepository() {
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

	public List<PostingBuyItNow> getAllPostings(){
		List<PostingBuyItNow> postings = new ArrayList<>();
		String sql = "SELECT * FROM posting_buy_it_now WHERE isDeleted = 0 AND expirationTime > '" + new Timestamp(System.currentTimeMillis())+ "';";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				PostingBuyItNow posting = new PostingBuyItNow();
				posting.setId(rs.getInt("id"));
				posting.setItemID(rs.getInt("itemId"));
				posting.setItemName(rs.getString("itemName"));
				posting.setQuantity(rs.getInt("quantity"));
				posting.setSellerID(rs.getInt("sellerID"));
				posting.setDescription(rs.getString("description"));
				posting.setPrice(rs.getDouble("price"));
				posting.setImageUrl(rs.getString("imageUrl"));
				posting.setCreatedTime(rs.getString("createdTime"));
				posting.setStartTime(rs.getString("startTime"));
				posting.setExpirationTime(rs.getString("expirationTime"));
				posting.setLastModifiedTime(rs.getString("lastModifiedTime"));
				posting.setNotified((rs.getInt("isNotified") == 1));
				posting.setDeleted((rs.getInt("isDeleted") == 1));

				postings.add(posting);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return postings;
	}

	public List<PostingBuyItNow> getPostingsBySellerId(int sellerId){
		List<PostingBuyItNow> postings = new ArrayList<>();
		String sql = "SELECT * FROM posting_buy_it_now WHERE isDeleted = 0 AND sellerID = " + sellerId + ";";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				PostingBuyItNow posting = new PostingBuyItNow();
				posting.setId(rs.getInt("id"));
				posting.setItemID(rs.getInt("itemId"));
				posting.setItemName(rs.getString("itemName"));
				posting.setQuantity(rs.getInt("quantity"));
				posting.setSellerID(rs.getInt("sellerID"));
				posting.setDescription(rs.getString("description"));
				posting.setPrice(rs.getDouble("price"));
				posting.setImageUrl(rs.getString("imageUrl"));
				posting.setCreatedTime(rs.getString("createdTime"));
				posting.setStartTime(rs.getString("startTime"));
				posting.setExpirationTime(rs.getString("expirationTime"));
				posting.setLastModifiedTime(rs.getString("lastModifiedTime"));
				posting.setNotified((rs.getInt("isNotified") == 1));
				posting.setDeleted((rs.getInt("isDeleted") == 1));

				postings.add(posting);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return postings;
	}

	public List<PostingBuyItNow> getAllPostingsByDate(String startDate, String endDate){
		List<PostingBuyItNow> postings = new ArrayList<>();
		String sql = "SELECT * FROM posting_buy_it_now WHERE isDeleted = 0";
		if(startDate != null)
			sql += " AND startTime >= '" + startDate + "'";
		if(endDate != null)
			sql += " AND startTime <= '" + endDate + "'";
		sql += ";";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				PostingBuyItNow posting = new PostingBuyItNow();
				posting.setId(rs.getInt("id"));
				posting.setItemID(rs.getInt("itemId"));
				posting.setItemName(rs.getString("itemName"));
				posting.setQuantity(rs.getInt("quantity"));
				posting.setSellerID(rs.getInt("sellerID"));
				posting.setDescription(rs.getString("description"));
				posting.setPrice(rs.getDouble("price"));
				posting.setImageUrl(rs.getString("imageUrl"));
				posting.setCreatedTime(rs.getString("createdTime"));
				posting.setStartTime(rs.getString("startTime"));
				posting.setExpirationTime(rs.getString("expirationTime"));
				posting.setLastModifiedTime(rs.getString("lastModifiedTime"));
				posting.setNotified((rs.getInt("isNotified") == 1));
				posting.setDeleted((rs.getInt("isDeleted") == 1));

				postings.add(posting);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return postings;
	}

	public PostingBuyItNow getPosting(int id){
		String sql = "SELECT * FROM posting_buy_it_now WHERE id = " + id + ";";
		PostingBuyItNow posting = null;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				posting = new PostingBuyItNow();
				posting.setId(rs.getInt("id"));
				posting.setItemID(rs.getInt("itemId"));
				posting.setItemName(rs.getString("itemName"));
				posting.setQuantity(rs.getInt("quantity"));
				posting.setSellerID(rs.getInt("sellerID"));
				posting.setDescription(rs.getString("description"));
				posting.setPrice(rs.getDouble("price"));
				posting.setImageUrl(rs.getString("imageUrl"));
				posting.setCreatedTime(rs.getString("createdTime"));
				posting.setStartTime(rs.getString("startTime"));
				posting.setExpirationTime(rs.getString("expirationTime"));
				posting.setLastModifiedTime(rs.getString("lastModifiedTime"));
				posting.setNotified((rs.getInt("isNotified") == 1));
				posting.setDeleted((rs.getInt("isDeleted") == 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return posting;
	}

	public int addPosting(PostingBuyItNow posting){
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO posting_buy_it_now").append("\n");
		sb.append("(itemId, itemName, quantity, sellerId, price, description, imageUrl, startTime, expirationTime)").append("\n");
		sb.append("VALUES (?,?,?,?,?,?,?,?,?);");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, posting.getItemID());
			stmt.setString(2, posting.getItemName());
			stmt.setInt(3, posting.getQuantity());
			stmt.setInt(4, posting.getSellerID());
			stmt.setDouble(5, posting.getPrice());
			stmt.setString(6, posting.getDescription());
			stmt.setString(7, posting.getImageUrl());
			stmt.setString(8, posting.getStartTime());
			stmt.setString(9, posting.getExpirationTime());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public boolean updatePosting(PostingBuyItNow posting){
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE posting_buy_it_now" + "\n" +
				"SET itemId = ?" + "\n" +
				", itemName = ?" + "\n" +
				", quantity = ?" + "\n" +
				", sellerId = ?" + "\n" +
				", price = ?" + "\n" +
				", description = ?" + "\n" +
				", imageUrl = ?" + "\n" +
				", startTime = ?" + "\n" +
				", expirationTime = ?" + "\n" +
				", isNotified = ?" + "\n" +
				", isDeleted = ?" + "\n" +
				"WHERE id = ?;");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, posting.getItemID());
			stmt.setString(2, posting.getItemName());
			stmt.setInt(3, posting.getQuantity());
			stmt.setInt(4, posting.getSellerID());
			stmt.setDouble(5, posting.getPrice());
			stmt.setString(6, posting.getDescription());
			stmt.setString(7, posting.getImageUrl());
			stmt.setString(8, posting.getStartTime());
			stmt.setString(9, posting.getExpirationTime());
			stmt.setInt(10, (posting.isNotified() ? 1 : 0));
			stmt.setInt(11, (posting.isDeleted() ? 1 : 0));
			stmt.setInt(12, posting.getId());

			stmt.executeUpdate();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean deletePosting(int id){
		String sql = "UPDATE posting_buy_it_now SET isDeleted = 1 WHERE id = ?;";

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

	public boolean reducePostingQuantity(int postingId, int reduceQuantity){
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE posting_buy_it_now" + "\n" +
				"SET quantity = quantity - ?" + "\n" +
				"WHERE id = ?;");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, reduceQuantity);
			stmt.setInt(2, postingId);

			stmt.executeUpdate();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
