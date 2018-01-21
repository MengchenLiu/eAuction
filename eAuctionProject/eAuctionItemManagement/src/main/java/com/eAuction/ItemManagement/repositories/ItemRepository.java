package com.eAuction.ItemManagement.repositories;

import com.eAuction.ItemManagement.domain.Item;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class ItemRepository {
	private Connection con = null;

	public ItemRepository() {
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

	public List<Item> getAllItems(){
		List<Item> items = new ArrayList<>();
		String sql = "SELECT * FROM item;";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setDescription(rs.getString("description"));
				item.setImageUrl(rs.getString("imageUrl"));
				item.setCategoryName(rs.getString("categoryName"));

				items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;
	}

	public List<Item> getItemsByKeyword(String keyword){
		List<Item> items = new ArrayList<>();
		String sql = "SELECT * FROM item WHERE name LIKE '%"+ keyword + "%';";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setDescription(rs.getString("description"));
				item.setImageUrl(rs.getString("imageUrl"));
				item.setCategoryName(rs.getString("categoryName"));

				items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;
	}

	public List<Item> getItemsByCategory(String categoryName){
		List<Item> items = new ArrayList<>();
		String sql = "SELECT * FROM item WHERE categoryName LIKE '"+ categoryName + "';";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setDescription(rs.getString("description"));
				item.setImageUrl(rs.getString("imageUrl"));
				item.setCategoryName(rs.getString("categoryName"));

				items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;
	}

	public Item getItem(int id){
		String sql = "SELECT * FROM item WHERE id = " + id + ";";
		Item item = null;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				item = new Item();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setDescription(rs.getString("description"));
				item.setImageUrl(rs.getString("imageUrl"));
				item.setCategoryName(rs.getString("categoryName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	}

	public int addItem(Item item){
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO item (name, description, imageUrl, categoryName) VALUES (?,?,?,?);");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, item.getName());
			stmt.setString(2, item.getDescription());
			stmt.setString(3, item.getImageUrl());
			stmt.setString(4, item.getCategoryName());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public boolean updateItem(Item item){
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE item" + "\n" +
				"SET name = ?" + "\n" +
				", description = ?" + "\n" +
				", imageUrl = ?" + "\n" +
				", categoryName = ?" + "\n" +
				"WHERE id = ?;");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, item.getName());
			stmt.setString(2, item.getDescription());
			stmt.setString(3, item.getImageUrl());
			stmt.setString(4, item.getCategoryName());
			stmt.setInt(5, item.getId());

			stmt.executeUpdate();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean deleteItem(int id){
		String sql = "DELETE FROM item WHERE id = ?;";

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
}
