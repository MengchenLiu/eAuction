package com.eAuction.Order.repositories;

import com.eAuction.Order.domain.Order;
import com.eAuction.Order.domain.Posting;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class OrderRepository {
	private Connection con = null;

	public OrderRepository() {
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

	public List<Order> getAllOrders(){
		List<Order> orders = new ArrayList<>();
		String sql = "SELECT * FROM `order`;";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Order order = new Order();
				order.setId(rs.getInt("id"));
				order.setBuyerId(rs.getInt("buyerId"));
				order.setAmountDue(rs.getDouble("amountDue"));
				order.setPaymentId(rs.getInt("paymentId"));
				order.setShippingId(rs.getInt("shippingId"));
				order.setOrderStatusId(rs.getInt("orderStatusId"));
				order.setOrderTime(rs.getString("orderTime"));
				order.setPostingList(getPostingsByOrderId(order.getId()));

				orders.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return orders;
	}

	public List<Order> getOrdersByBuyer(int buyerId){
		List<Order> orders = new ArrayList<>();
		String sql = "SELECT * FROM `order` WHERE buyerId = " + buyerId + ";";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Order order = new Order();
				order.setId(rs.getInt("id"));
				order.setBuyerId(rs.getInt("buyerId"));
				order.setAmountDue(rs.getDouble("amountDue"));
				order.setPaymentId(rs.getInt("paymentId"));
				order.setShippingId(rs.getInt("shippingId"));
				order.setOrderStatusId(rs.getInt("orderStatusId"));
				order.setOrderTime(rs.getString("orderTime"));
				order.setPostingList(getPostingsByOrderId(order.getId()));
				System.out.println(order.toString());
				orders.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return orders;
	}

	/**
	 * Gets postings related to given orderId
	 * @param orderId orderId
	 * @return ArrayList<Posting>
	 */
	private ArrayList<Posting> getPostingsByOrderId(int orderId) {
		String sql = "SELECT * FROM `order_posting` WHERE orderId = " + orderId + ";";
		ArrayList<Posting> postings = new ArrayList<>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Posting posting = new Posting();
				posting.setOrderId(rs.getInt("orderId"));
				posting.setPostingId(rs.getInt("postingId"));
				posting.setPostingTypeId(rs.getInt("postingTypeId"));

				postings.add(posting);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return postings;
	}

	public Order getOrder(int id){
		String sql = "SELECT * FROM `order` WHERE id = " + id + ";";
		Order order = null;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				order = new Order();
				order.setId(rs.getInt("id"));
				order.setBuyerId(rs.getInt("buyerId"));
				order.setAmountDue(rs.getDouble("amountDue"));
				order.setPaymentId(rs.getInt("paymentId"));
				order.setShippingId(rs.getInt("shippingId"));
				order.setOrderStatusId(rs.getInt("orderStatusId"));
				order.setOrderTime(rs.getString("orderTime"));
				order.setPostingList(getPostingsByOrderId(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return order;
	}

	public int addOrder(Order order){
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO `order`").append("\n");
		sb.append("(buyerId, amountDue, paymentId, shippingId, orderStatusId, orderTime)").append("\n");
		sb.append("VALUES (?,?,?,?,?,?);");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, order.getBuyerId());
			stmt.setDouble(2, order.getAmountDue());
			stmt.setInt(3, order.getPaymentId());
			stmt.setInt(4, order.getShippingId());
			stmt.setInt(5, order.getOrderStatusId());
			stmt.setString(6, order.getOrderTime());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public Posting addPostingToOrder(Posting posting){
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO `order_posting`").append("\n");
		sb.append("(orderId, postingId, postingTypeId)").append("\n");
		sb.append("VALUES (?,?,?);");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, posting.getOrderId());
			stmt.setInt(2, posting.getPostingId());
			stmt.setInt(3, posting.getPostingTypeId());
			stmt.executeUpdate();

			return posting;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean updateOrder(Order order){
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE `order`" + "\n" +
				"SET buyerId = ?" + "\n" +
				", amountDue = ?" + "\n" +
				", paymentId = ?" + "\n" +
				", shippingId = ?" + "\n" +
				", orderStatusId = ?" + "\n" +
				"WHERE id = ?;");

		String sql = sb.toString();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, order.getBuyerId());
			stmt.setDouble(2, order.getAmountDue());
			stmt.setInt(3, order.getPaymentId());
			stmt.setInt(4, order.getShippingId());
			stmt.setInt(5, order.getOrderStatusId());
			stmt.setInt(6, order.getId());

			stmt.executeUpdate();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean deleteOrder(int id){
		String sql = "DELETE FROM `order` WHERE id = ?;";

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
