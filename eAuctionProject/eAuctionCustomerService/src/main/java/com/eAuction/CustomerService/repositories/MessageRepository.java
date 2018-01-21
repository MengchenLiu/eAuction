package com.eAuction.CustomerService.repositories;

import com.eAuction.CustomerService.domain.Message;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class MessageRepository {
	private Connection con = null;

	public MessageRepository() {
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

	public List<Message> getAllMessages(){
		List<Message> messages = new ArrayList<>();
		String sql = "SELECT * FROM `message`;";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Message message = new Message();
				message.setId(rs.getInt("id"));
				message.setUserId(rs.getInt("userId"));
				message.setQuestion(rs.getString("question"));
				message.setAdminId(rs.getInt("adminId"));
				message.setAnswer(rs.getString("answer"));
				message.setLastModifiedTime(rs.getTimestamp("lastModifiedTime"));

				messages.add(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return messages;
	}

	public List<Message> getMessagesByUser(int userId){
		List<Message> messages = new ArrayList<>();
		String sql = "SELECT * FROM `message` WHERE userId = " + userId + ";";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Message message = new Message();
				message.setId(rs.getInt("id"));
				message.setUserId(rs.getInt("userId"));
				message.setQuestion(rs.getString("question"));
				message.setAdminId(rs.getInt("adminId"));
				message.setAnswer(rs.getString("answer"));
				message.setLastModifiedTime(rs.getTimestamp("lastModifiedTime"));

				messages.add(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return messages;
	}

	public List<Message> getUnansweredMessages(){
		List<Message> messages = new ArrayList<>();
		String sql = "SELECT * FROM `message` WHERE adminId IS NOT 0;";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Message message = new Message();
				message.setId(rs.getInt("id"));
				message.setUserId(rs.getInt("userId"));
				message.setQuestion(rs.getString("question"));
				message.setAdminId(rs.getInt("adminId"));
				message.setAnswer(rs.getString("answer"));
				message.setLastModifiedTime(rs.getTimestamp("lastModifiedTime"));

				messages.add(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return messages;
	}

	public Message getMessage(int id){
		String sql = "SELECT * FROM `message` WHERE id = " + id + ";";
		Message message = null;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				message = new Message();
				message.setId(rs.getInt("id"));
				message.setUserId(rs.getInt("userId"));
				message.setQuestion(rs.getString("question"));
				message.setAdminId(rs.getInt("adminId"));
				message.setAnswer(rs.getString("answer"));
				message.setLastModifiedTime(rs.getTimestamp("lastModifiedTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return message;
	}

	public int addQuestion(Message message){
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO `message`").append("\n");
		sb.append("(userId, question, adminId, answer, lastModifiedTime)").append("\n");
		sb.append("VALUES (?,?,?,?,?);");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, message.getUserId());
			stmt.setString(2, message.getQuestion());
			stmt.setInt(3, 0);
			stmt.setString(4, "");
			stmt.setTimestamp(5, message.getLastModifiedTime());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public boolean addAnswer(Message message){
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE `message`" + "\n" +
				"SET adminId = ?" + "\n" +
				", answer = ?" + "\n" +
				", lastModifiedTime = ?" + "\n" +
				"WHERE id = ?;");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, message.getAdminId());
			stmt.setString(2, message.getAnswer());
			stmt.setTimestamp(3, message.getLastModifiedTime());
			stmt.setInt(4, message.getId());
			stmt.executeUpdate();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean deleteMessage(int id){
		String sql = "DELETE FROM `message` WHERE id = ?;";

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
