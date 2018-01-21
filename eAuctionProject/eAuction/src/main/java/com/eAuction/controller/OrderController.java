package com.eAuction.controller;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eAuction.HttpRequest.HttpRequest;
import com.eAuction.HttpRequest.HttpResponse;
import com.eAuction.config.IpConfig;
import com.eAuction.domain.Address;
import com.eAuction.domain.Order;
import com.eAuction.domain.Payment;
import com.eAuction.domain.PostingAuction;
import com.eAuction.domain.PostingBuyItNow;
import com.eAuction.domain.ShoppingCart;
import com.eAuction.domain.User;

@Controller
public class OrderController {
	
	@Autowired HttpRequest request;
	
	@RequestMapping("/checkout")
	public String checkout(HttpSession session
			,Model model) {
		try {
			String userName = (String) session.getAttribute("currentUser");
			if(userName == null) return "redirect:/login";
			String data = URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode((String)session.getAttribute("currentUser"), "utf-8");
			Socket socket = new Socket();
			SocketAddress  address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/shoppingCarts");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			List<ShoppingCart> auctions = new ArrayList<>();
			List<ShoppingCart> buyitnows = new ArrayList<>();
			JSONArray jsonArray = new JSONArray(response.get(6));
			double price = 0;
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(jsonObject.getInt("typeId") == 1) {
					auctions.add(new ShoppingCart(jsonObject.getInt("id"), jsonObject.getString("userName"), 
							jsonObject.getInt("postingId"), jsonObject.getInt("quantity"),jsonObject.getDouble("price"),
							jsonObject.getString("imageUrl"),jsonObject.getString("itemName"),jsonObject.getInt("typeId")));	
					
				}
				else {
					buyitnows.add(new ShoppingCart(jsonObject.getInt("id"), jsonObject.getString("userName"), 
							jsonObject.getInt("postingId"), jsonObject.getInt("quantity"),jsonObject.getDouble("price"),
							jsonObject.getString("imageUrl"),jsonObject.getString("itemName"),jsonObject.getInt("typeId")));
					
				}
				price += jsonObject.getInt("quantity") * jsonObject.getDouble("price");
			}
			model.addAttribute("auctions",auctions);
			model.addAttribute("buyitnows",buyitnows);
			List<Payment> payments = getPayment(userName);
			List<Address> addresses = getAddress(userName);
			model.addAttribute("addresses",addresses);
			model.addAttribute("payments",payments);
			model.addAttribute("totalPrice",price);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "checkout";
	}
	
	// list the user's payments 
	private List<Payment> getPayment(String userName){
		List<Payment> payments = new ArrayList<>();
		try {
			String data = URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode(userName, "utf-8");                    
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/payments");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONArray jsonArray = new JSONArray(response.get(6));
			
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				payments.add(new Payment((int)jsonObject.getInt("id"), jsonObject.getString("cardNumber"), jsonObject.getString("holderName"), 
						jsonObject.getString("cvs"), jsonObject.getString("expiryMonth"), jsonObject.getString("expiryYear"), jsonObject.getString("userName")));
			}		
		} catch (Exception e) {
			System.out.println(e);
		}
		return payments;
	}
	
	// list the user's address
	public List<Address> getAddress(String userName){
		List<Address> addresses = new ArrayList<>();
		try {
			String data = URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode(userName, "utf-8") ;
			Socket socket = new Socket();
			SocketAddress  address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addresses");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONArray jsonArray = new JSONArray(response.get(6));
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				addresses.add(new Address(jsonObject.getInt("id"), jsonObject.getString("country"), jsonObject.getString("state"), jsonObject.getString("addressStreet1")
						, jsonObject.getString("addressStreet2"), jsonObject.getString("city"), jsonObject.getInt("zip"), jsonObject.getString("userName")));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return addresses;
	}
	
	@RequestMapping(value="/addOrder",method=RequestMethod.POST)
	public String addOrder(@ModelAttribute("addressid") String addressId, 
			@ModelAttribute("paymentid") String paymentId,
			@Autowired HttpRequest request,
			HttpSession session,
			Model model
			) {
		User user = (User) session.getAttribute("user");
		double price = 0;
		List<ShoppingCart> carts = getShoopingCart((String)session.getAttribute("currentUser"));
		for(ShoppingCart cart : carts) {
			int postingId = cart.getPostingId();
			int typeId = cart.getTypeId();
			if(typeId == 2) {
				PostingAuction postingAuction = getPostingAuction(postingId);
				if(cart.getQuantity() > postingAuction.getQuantity()) {
					return "redirect:/checkout";
				}
			}
			else {
				PostingBuyItNow postingBuyItNow = getPostingBuyItNow(postingId);
				if(cart.getQuantity() > postingBuyItNow.getQuantity()) {
					return "redirect:/checkout";
				}
			}
			price += cart.getQuantity() * cart.getPrice();
		}
		int  id = addToOrder(user.getId(), price, Integer.parseInt(paymentId), Integer.parseInt(addressId));
		for(ShoppingCart cart : carts) {
			addPostingToOrder(id, cart.getPostingId(), cart.getTypeId());
			reducePostingQuantity(cart.getPostingId(), cart.getTypeId(), cart.getQuantity());
			deleteShoppingCart(cart.getId());
		}
		model.addAttribute("messageType","Check out Successfully");
		return "message";
	}

	
	//-------------------------------- Helper function for adding order-----------------------------------------//
	private List<ShoppingCart> getShoopingCart(String userName){
		List<ShoppingCart> postings = new ArrayList<>();
		try {
			String data = URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode(userName, "utf-8");
			Socket socket = new Socket();
			SocketAddress  address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/shoppingCarts");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONArray jsonArray = new JSONArray(response.get(6));
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				postings.add(new ShoppingCart(jsonObject.getInt("id"), jsonObject.getString("userName"), 
							jsonObject.getInt("postingId"), jsonObject.getInt("quantity"),jsonObject.getDouble("price"),
							jsonObject.getString("imageUrl"),jsonObject.getString("itemName"),jsonObject.getInt("typeId")));	
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return postings;
	}
	
	
	public PostingAuction getPostingAuction(int postingId) {
		PostingAuction postingAuction = null;
		try {
			String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(postingId+"", "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/showPostingAuction/"+postingId);
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			 postingAuction = new PostingAuction(jsonObject.getInt("id"), jsonObject.getInt("itemID"), 
					jsonObject.getString("itemName"), jsonObject.getInt("sellerID"), 
					jsonObject.getString("imageUrl"), jsonObject.getString("createdTime"), jsonObject.getString("startTime"),
					jsonObject.getString("expirationTime"), jsonObject.getString("lastModifiedTime"),
					jsonObject.getBoolean("deleted"), jsonObject.getDouble("startPrice"), jsonObject.getDouble("curPrice"), jsonObject.getInt("curBidder"),jsonObject.getInt("quantity"),jsonObject.getString("description"),jsonObject.getBoolean("notified"));
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return postingAuction;
	}
	
	
	public PostingBuyItNow getPostingBuyItNow(int id) {
		PostingBuyItNow postingBuyItNow = null;
		try {
			String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id+"", "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/showPostingBuyItNow/"+id);
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			postingBuyItNow = new PostingBuyItNow(jsonObject.getInt("id"), jsonObject.getInt("itemID"), 
					jsonObject.getString("itemName"), jsonObject.getInt("sellerID"), 
					jsonObject.getString("imageUrl"), jsonObject.getString("createdTime"), jsonObject.getString("startTime"),
					jsonObject.getString("expirationTime"), jsonObject.getString("lastModifiedTime"),
					jsonObject.getBoolean("deleted"),jsonObject.getDouble("price"),jsonObject.getInt("quantity"),jsonObject.getString("description"),jsonObject.getBoolean("notified"));
		} catch (Exception e) {
			System.out.println(e);
		}
		return postingBuyItNow;
	}
	
	public int addToOrder(int buyerId, double amountDue, int paymentId, int shippingId) {
		int id = 0;
		try {
			String data = URLEncoder.encode("buyerId", "utf-8") + "=" + URLEncoder.encode(buyerId+"", "utf-8")
			+ "&"
		    + URLEncoder.encode("amountDue", "utf-8") + "=" + URLEncoder.encode(amountDue+"", "utf-8") 
			+"&" 
			+ URLEncoder.encode("paymentId", "utf-8") + "=" + URLEncoder.encode(paymentId+"", "utf-8")
			+ "&"
		    + URLEncoder.encode("shippingId", "utf-8") + "=" + URLEncoder.encode(shippingId+"", "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.OrderIP, 8084);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addOrder");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			id = jsonObject.getInt("id");
		} catch (Exception e) {
			System.out.println(e);
		}
		return id;
	}
	

	public void addPostingToOrder(int orderId, int postingId, int postingTypeId) {
		try {
			String data = URLEncoder.encode("orderId", "utf-8") + "=" + URLEncoder.encode(orderId+"", "utf-8")
			+ "&"
		    + URLEncoder.encode("postingId", "utf-8") + "=" + URLEncoder.encode(postingId+"", "utf-8") 
			+"&" 
			+ URLEncoder.encode("postingTypeId", "utf-8") + "=" + URLEncoder.encode(postingTypeId+"", "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.OrderIP, 8084);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addPostingToOrder");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void reducePostingQuantity(int postingID, int postingTypeID, int reduceQuantity) {
		try {
			String data = URLEncoder.encode("postingID", "utf-8") + "=" + URLEncoder.encode(postingID+"", "utf-8")
			+ "&"
		    + URLEncoder.encode("postingTypeID", "utf-8") + "=" + URLEncoder.encode(postingTypeID+"", "utf-8") 
			+"&" 
			+ URLEncoder.encode("reduceQuantity", "utf-8") + "=" + URLEncoder.encode(reduceQuantity+"", "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/reducePostingQuantity");
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	private void deleteShoppingCart(int id) {
		try {
			String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id+"", "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/deleteShoppingCart");
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//--------------------------------------------All helper function for adding order------------------------//
	
	@RequestMapping("/past")
	public String pastOrder(
			HttpSession session,Model model) {
		User user = (User) session.getAttribute("user");
		try {
			String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(user.getId()+"", "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.OrderIP, 8084);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/orders/"+user.getId());
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONArray jsonArray = new JSONArray(response.get(6));
			List<Order> orders = new ArrayList<>();
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Order order = new Order(jsonObject.getInt("buyerId"), jsonObject.getDouble("amountDue"), jsonObject.getInt("paymentId"), jsonObject.getInt("shippingId"), 
						jsonObject.getString("orderTime"));
				orders.add(order);
			}
			model.addAttribute("orders",orders);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "past";
	}
	
	
	
	
	
	
}
