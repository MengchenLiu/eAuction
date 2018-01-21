package com.eAuction.ItemManagement.repositories;

import com.eAuction.ItemManagement.domain.Category;
import com.eAuction.ItemManagement.domain.Item;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class CategoryRepository {
	private Connection con = null;

	public CategoryRepository() {
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

	public List<Category> getAllCategories(){
		List<Category> categories = new ArrayList<>();
		String sql = "SELECT * FROM category;";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Category category = new Category();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));

				categories.add(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return categories;
	}

	public Category getCategory(int id){
		String sql = "SELECT * FROM category WHERE id = " + id + ";";
		Category category = null;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				category = new Category();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return category;
	}

	public int addCategory(Category category){
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO category (name) VALUES (?,?,?,?);");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, category.getName());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public boolean updateCategory(Category category){
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE category" + "\n" +
				"SET name = ?" + "\n" +
				"WHERE id = ?;");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, category.getName());
			stmt.setInt(2, category.getId());

			stmt.executeUpdate();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean deleteCategory(int id){
		String sql = "DELETE FROM category WHERE id = ?;";

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
