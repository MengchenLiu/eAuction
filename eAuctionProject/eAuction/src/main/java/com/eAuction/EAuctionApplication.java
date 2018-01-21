package com.eAuction;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.StaticApplicationContext;

import com.eAuction.HttpRequest.HttpRequest;
import com.eAuction.HttpRequest.HttpResponse;
import com.eAuction.config.IpConfig;
import com.eAuction.domain.PostingAuction;
import com.eAuction.domain.ShoppingCart;

@SpringBootApplication
public class EAuctionApplication {

	
	private static HttpRequest request = new HttpRequest() ;
	
	public static void main(String[] args) {
		SpringApplication.run(EAuctionApplication.class, args);
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				notifyBeforeOneHour();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				addToShoppingCartWhenFinished();
			}
		});
//		t1.start();
//		t2.start();
		
		
	}
	
	
	// the function to notify user when auction will finished in one hour//
	public static void notifyBeforeOneHour() {
		while (true) {
			try {
				String data = new String();
				Socket socket = new Socket();
				SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
				socket.connect(address);
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				Scanner scanner = new Scanner(socket.getInputStream());
				request.setPrintWriter(printWriter);
				request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/expiredPostingBidders");
				List<String> response = new HttpResponse(scanner).getRespnse();
				socket.close();
				JSONArray jsonArray = new JSONArray(response.get(6));
				StringBuilder stringBuilder = new StringBuilder();
				List<Integer> idList = new LinkedList<>();
				for(int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String temp = "(" + jsonObject.getInt("postingId") + " " + jsonObject.getInt("bidderId") + ")";
					if(i!=jsonArray.length()-1) temp = temp +",";
					stringBuilder.append(temp);
					idList.add(jsonObject.getInt("postingId"));
				}
				boolean res =  helper(stringBuilder.toString());
				if(res == true) {
					for(int i : idList) {
						updateIsNotifiedByPostingId(i);
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	}
	
	private static boolean helper(String data) {
		try {
			String datas = URLEncoder.encode("ids", "utf-8") + "=" + URLEncoder.encode(data, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(datas, InetAddress.getLocalHost().getHostAddress(),"/notifyExpire");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();	
			JSONObject jsonObject = new JSONObject(response.get(6));
			if(jsonObject.getBoolean("requestResponse") == true) {
				return  true;
			}
			else return false;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	private static void updateIsNotifiedByPostingId(int id) {
		try {
			String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id+"", "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/updateIsNotifiedByPostingId");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();	
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	

// --------------------------------helper function for the  notifyBeforeOneHour-----------------------------------//
	
	public static void addToShoppingCartWhenFinished() {
		while (true) {
			try {
				String data = new String();
				Socket socket = new Socket();
				SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
				socket.connect(address);
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				Scanner scanner = new Scanner(socket.getInputStream());
				request.setPrintWriter(printWriter);
				request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/expiredAddedToCartPostings");
				List<String> response = new HttpResponse(scanner).getRespnse();
				socket.close();
				JSONArray jsonArray = new JSONArray(response.get(6));
				List<PostingAuction> postingAuctions = new LinkedList<>();
				for(int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					addToShoppingCart(jsonObject.getInt("curBidder"), jsonObject.getInt("id"), 
							1, jsonObject.getDouble("curPrice"), jsonObject.getString("imageUrl"), jsonObject.getString("itemName"),
							2);
					updateIsAddedToCartByPostingId(jsonObject.getInt("id"));
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
			
			
			
		}
	}
	
	
	private static void addToShoppingCart(int userId, int postingId, int quantity, double price, String imageUrl,
			String itemName, int typeId) {
		try {
			String data = URLEncoder.encode("userId", "utf-8") + "=" + URLEncoder.encode(userId+"", "utf-8") 
			+ "&"
		    + URLEncoder.encode("postingId", "utf-8") + "=" + URLEncoder.encode(postingId+"", "utf-8") 
			+"&" 
			+ URLEncoder.encode("quantity", "utf-8") + "=" + URLEncoder.encode(quantity+"", "utf-8")
			+ "&"
		    + URLEncoder.encode("price", "utf-8") + "=" + URLEncoder.encode(price+"", "utf-8") 
			+"&" 
			+ URLEncoder.encode("imageUrl", "utf-8") + "=" + URLEncoder.encode(imageUrl, "utf-8")
			+"&" 
			+ URLEncoder.encode("itemName", "utf-8") + "=" + URLEncoder.encode(itemName, "utf-8")
			+"&" 
			+ URLEncoder.encode("typeId", "utf-8") + "=" + URLEncoder.encode(typeId+"", "utf-8");
			Socket socket = new Socket();
			SocketAddress  address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addToShoppingCartById");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	private static void updateIsAddedToCartByPostingId(int id) {
		try {
			String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id+"", "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/updateIsAddedToCartByPostingId");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();	
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	
	
}
