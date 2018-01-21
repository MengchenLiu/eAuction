package com.eAuction.controller;


import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.eAuction.HttpRequest.HttpRequest;
import com.eAuction.HttpRequest.HttpResponse;
import com.eAuction.config.IpConfig;
import com.eAuction.domain.PostingAuction;
import com.eAuction.domain.PostingBuyItNow;
import com.eAuction.domain.User;

@Controller
public class PostingController {
	
	
	@RequestMapping("/postings")
	public String postings(Model model,@Autowired HttpRequest request) {
		try {	
			String data = new String();
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/postings");
			//else request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(), "/postingsBySeller/"+bidderId);
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONArray jsonArray = new JSONArray(response.get(6));
			List<PostingAuction> auctions = new ArrayList<>();
			List<PostingBuyItNow> buyitnows = new ArrayList<>();
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject =jsonArray.getJSONObject(i);
				if(jsonObject.has("startPrice")) {
					auctions.add(new PostingAuction(jsonObject.getInt("id"), jsonObject.getInt("itemID"), 
							jsonObject.getString("itemName"), jsonObject.getInt("sellerID"), 
							jsonObject.getString("imageUrl"), jsonObject.getString("createdTime"),jsonObject.getString("startTime"),
							jsonObject.getString("expirationTime"), jsonObject.getString("lastModifiedTime"),
							jsonObject.getBoolean("deleted"), jsonObject.getDouble("startPrice"), jsonObject.getDouble("curPrice"), jsonObject.getInt("curBidder"),jsonObject.getInt("quantity"),jsonObject.getString("description"),jsonObject.getBoolean("notified")));
					
				}
				else {
					buyitnows.add(new PostingBuyItNow(jsonObject.getInt("id"), jsonObject.getInt("itemID"), 
								jsonObject.getString("itemName"), jsonObject.getInt("sellerID"), 
								jsonObject.getString("imageUrl"), jsonObject.getString("createdTime"), jsonObject.getString("startTime"),
								jsonObject.getString("expirationTime"), jsonObject.getString("lastModifiedTime"),
								jsonObject.getBoolean("deleted"),jsonObject.getDouble("price"),jsonObject.getInt("quantity"),jsonObject.getString("description"),jsonObject.getBoolean("notified")));
				}
			}
			Collections.sort(auctions,new Comparator<PostingAuction>() {
				public int compare(PostingAuction o1, PostingAuction o2) {
					return o1.getExpirationTime().compareTo(o2.getExpirationTime());
				}
			});
			
			Collections.sort(buyitnows,new Comparator<PostingBuyItNow>() {
				public int compare(PostingBuyItNow o1, PostingBuyItNow o2) {
					return o1.getExpirationTime().compareTo(o2.getExpirationTime());
				}
			});
			model.addAttribute("auctions",auctions);
			model.addAttribute("buyitnows",buyitnows);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "postings";
	}
	
	@RequestMapping("/deletePostingNow/{id}")
	public String deletePostingNow(HttpSession session,
			@Autowired HttpRequest request,
			@PathVariable("id") String id,Model model) {
		User user = (User) session.getAttribute("user");
		if(!user.getRole().equals("admin")) {
			model.addAttribute("messageType","You do not have authority to delete user");
			return "message";
		}
		try {
			String data = "";
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/deletePostingBuyItNow/"+id);
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/postings";
	}
	
	@RequestMapping("/deletePostingAuction/{id}")
	public String deletePostingAuction(HttpSession session,
			@Autowired HttpRequest request,
			@PathVariable("id") String id,Model model) {
		User user = (User) session.getAttribute("user");
		if(!user.getRole().equals("admin")) {
			model.addAttribute("messageType","You do not have authority to delete user");
			return "message";
		}
		try {
			String data = "";
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/deletePostingAuction/"+id);
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/postings";
	}
	
	@RequestMapping("/addPosting")
	public String addPosting(HttpSession session, Model model,@RequestParam("item") String id, @RequestParam("name") String name,
			@RequestParam("url") String imageUrl) {
		String user = (String)session.getAttribute("currentUser");
		if(user == null) {
			return "redirect:/login";
		}
		else {
			model.addAttribute("name",name);
			model.addAttribute("id",id);
			model.addAttribute("url",imageUrl);
			return "newPosting";
		}
	}
	
	@RequestMapping(value="/addPosting",method = RequestMethod.POST)
	public String addPosting(@Autowired HttpRequest request, Model model, 
			@ModelAttribute("price") String startPrice, @ModelAttribute("startTime") String startTime, 
			@ModelAttribute("endTime") String expirationTime,@ModelAttribute("id") String itemID, 
			@ModelAttribute("name") String itemName, HttpSession session,@ModelAttribute("imageUrl") String imageUrl,
			@ModelAttribute("type") String type,@ModelAttribute("description") String description, 
			@ModelAttribute("quantity") String quantity) {
		int  id = 0;
		try {
			
			String data = URLEncoder.encode("itemID", "utf-8") + "=" + URLEncoder.encode(itemID, "utf-8") 
			+ "&"
		    + URLEncoder.encode("itemName", "utf-8") + "=" + URLEncoder.encode(itemName, "utf-8") 
			+"&" 
			+ URLEncoder.encode("sellerID", "utf-8") + "=" + URLEncoder.encode(((User)session.getAttribute("user")).getId()+"", "utf-8")
			+"&" 
			+ URLEncoder.encode("imageUrl", "utf-8") + "=" + URLEncoder.encode(imageUrl, "utf-8")
			+"&" 
			+ URLEncoder.encode("startTime", "utf-8") + "=" + URLEncoder.encode(startTime, "utf-8")
			+"&" 
			+ URLEncoder.encode("expirationTime", "utf-8") + "=" + URLEncoder.encode(expirationTime, "utf-8")
			+"&" 
			+ URLEncoder.encode("startPrice", "utf-8") + "=" + URLEncoder.encode(startPrice, "utf-8")
			+"&" 
			+ URLEncoder.encode("description", "utf-8") + "=" + URLEncoder.encode(description, "utf-8")
			+"&" 
			+ URLEncoder.encode("quantity", "utf-8") + "=" + URLEncoder.encode(quantity, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			if(type.equals("2"))request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addPostingBuyItNow");
			else request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addPostingAuction");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			if(type.equals("1")) {
				PostingAuction postingAuction = new PostingAuction(jsonObject.getInt("id"), jsonObject.getInt("itemID"), 
						jsonObject.getString("itemName"), jsonObject.getInt("sellerID"), 
						jsonObject.getString("imageUrl"), jsonObject.getString("createdTime"),jsonObject.getString("startTime"),
						jsonObject.getString("expirationTime"), jsonObject.getString("lastModifiedTime"),
						jsonObject.getBoolean("deleted"), jsonObject.getDouble("startPrice"), jsonObject.getDouble("curPrice"), jsonObject.getInt("curBidder"),jsonObject.getInt("quantity"),jsonObject.getString("description"),jsonObject.getBoolean("notified"));
				model.addAttribute("type","1");
				id = postingAuction.getId();
			}else {
				PostingBuyItNow postingBuyItNow = new PostingBuyItNow(jsonObject.getInt("id"), jsonObject.getInt("itemID"), 
						jsonObject.getString("itemName"), jsonObject.getInt("sellerID"), 
						jsonObject.getString("imageUrl"), jsonObject.getString("createdTime"), jsonObject.getString("startTime"),
						jsonObject.getString("expirationTime"), jsonObject.getString("lastModifiedTime"),
						jsonObject.getBoolean("deleted"),jsonObject.getDouble("price"),jsonObject.getInt("quantity"),jsonObject.getString("description"),jsonObject.getBoolean("notified"));
				model.addAttribute("type","2");
				id = postingBuyItNow.getId();
			}
			addToWatchList(request,startPrice,itemID,id+"");
		} catch (Exception e) {
			System.out.println(e);
		}
		if(type.equals("1")) return "redirect:/showPostingAuction/" + id;
		else return "redirect:/showPostingBuyItNow/" + id; 
	}
	
	private void addToWatchList(HttpRequest request, String price, String itemId,
			String postingID) {
		try {
			String data = URLEncoder.encode("price", "utf-8") + "=" + URLEncoder.encode(price, "utf-8")
			+ "&"
		    + URLEncoder.encode("itemId", "utf-8") + "=" + URLEncoder.encode(itemId, "utf-8") 
			+"&" 
			+ URLEncoder.encode("postingID", "utf-8") + "=" + URLEncoder.encode(postingID+"", "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.NotificationIP, 8086);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/notifyWatchlist");
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}	
	}
	
	
	@RequestMapping("/showPostingAuction/{id}")
	public String showPosting(@PathVariable("id") String id, @Autowired HttpRequest request, Model model,HttpSession session) {
		try {
			String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/showPostingAuction/"+id);
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			PostingAuction postingAuction = new PostingAuction(jsonObject.getInt("id"), jsonObject.getInt("itemID"), 
					jsonObject.getString("itemName"), jsonObject.getInt("sellerID"), 
					jsonObject.getString("imageUrl"), jsonObject.getString("createdTime"), jsonObject.getString("startTime"),
					jsonObject.getString("expirationTime"), jsonObject.getString("lastModifiedTime"),
					jsonObject.getBoolean("deleted"), jsonObject.getDouble("startPrice"), jsonObject.getDouble("curPrice"), jsonObject.getInt("curBidder"),jsonObject.getInt("quantity"),jsonObject.getString("description"),jsonObject.getBoolean("notified"));
			model.addAttribute("posting",postingAuction);
			User user = (User) session.getAttribute("user");
			if(user.getId() == postingAuction.getSellerID()) model.addAttribute("isSeller",true);
			else model.addAttribute("isSeller",false);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return "postingDetailsAuction";
	}
	
	@RequestMapping("/showPostingBuyItNow/{id}")
	public String showPostingBuyItKnow(@PathVariable("id") String id, @Autowired HttpRequest request, Model model,HttpSession session) {
		try {
			String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id, "utf-8");
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
			PostingBuyItNow postingBuyItNow = new PostingBuyItNow(jsonObject.getInt("id"), jsonObject.getInt("itemID"), 
					jsonObject.getString("itemName"), jsonObject.getInt("sellerID"), 
					jsonObject.getString("imageUrl"), jsonObject.getString("createdTime"), jsonObject.getString("startTime"),
					jsonObject.getString("expirationTime"), jsonObject.getString("lastModifiedTime"),
					jsonObject.getBoolean("deleted"),jsonObject.getDouble("price"),jsonObject.getInt("quantity"),jsonObject.getString("description"),jsonObject.getBoolean("notified"));
			model.addAttribute("posting",postingBuyItNow);
			User user = (User) session.getAttribute("user");
			if(user.getId() == postingBuyItNow.getSellerID()) model.addAttribute("isSeller",true);
			else model.addAttribute("isSeller",false);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "postingDetailsNow";
	}
	
	
	@RequestMapping("/updatePostingAuction/{id}")
	public String updatePostingAuction(@Autowired HttpRequest request, @PathVariable("id") String id,  Model model, HttpSession session) {
		try {			
		String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id, "utf-8");
		Socket socket = new Socket();
		SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
		socket.connect(address);
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
		Scanner scanner = new Scanner(socket.getInputStream());
		request.setPrintWriter(printWriter);
		request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/showPostingAuction/"+id);
		List<String> response = new HttpResponse(scanner).getRespnse();
		socket.close();
		JSONObject jsonObject = new JSONObject(response.get(6));
		if(jsonObject.getInt("sellerID") != ((User)session.getAttribute("user")).getId()) {
			return "redirect:/showPostingAuction/" + id;
		}
		PostingAuction postingAuction = new PostingAuction(jsonObject.getInt("id"), jsonObject.getInt("itemID"), 
				jsonObject.getString("itemName"), jsonObject.getInt("sellerID"), 
				jsonObject.getString("imageUrl"), jsonObject.getString("createdTime"), jsonObject.getString("startTime"),
				jsonObject.getString("expirationTime"), jsonObject.getString("lastModifiedTime"),
				jsonObject.getBoolean("deleted"), jsonObject.getDouble("startPrice"), jsonObject.getDouble("curPrice"), jsonObject.getInt("curBidder"),jsonObject.getInt("quantity"),jsonObject.getString("description"),jsonObject.getBoolean("notified"));
		model.addAttribute("posting",postingAuction);
		}catch (Exception e) {
			System.out.println(e);
		}
		return "updatePostingAuction";
	}
	
	@RequestMapping("/updatePostingNow/{id}")
	public String updatePostingNow(@Autowired HttpRequest request,
			@PathVariable("id") String id, Model model,
			HttpSession session) {
		try {
			String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id, "utf-8");
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
			if(jsonObject.getInt("sellerID") != ((User)session.getAttribute("user")).getId()) {
				return "redirect:/showPostingBuyItNow/" + id;
			}
			PostingBuyItNow postingAuction = new PostingBuyItNow(jsonObject.getInt("id"), jsonObject.getInt("itemID"), 
					jsonObject.getString("itemName"), jsonObject.getInt("sellerID"), 
					jsonObject.getString("imageUrl"), jsonObject.getString("createdTime"), jsonObject.getString("startTime"),
					jsonObject.getString("expirationTime"), jsonObject.getString("lastModifiedTime"),
					jsonObject.getBoolean("deleted"),jsonObject.getDouble("price"),jsonObject.getInt("quantity"),jsonObject.getString("description"),jsonObject.getBoolean("notified"));
			model.addAttribute("posting",postingAuction);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "updatePostingNow";
	}
	

	@RequestMapping(value="/updatePostingNow",method=RequestMethod.POST)
	public String updatePostingNow(@Autowired HttpRequest request, @ModelAttribute("postingID") String postingID,
			@ModelAttribute("itemID") String itemID, @ModelAttribute("itemName") String itemName,  @ModelAttribute("sellerID") String 
			sellerID, @ModelAttribute("description") String description, @ModelAttribute("imageUrl") String imageUrl,
			@ModelAttribute("startTime") String startTime, @ModelAttribute("endTime") String expirationTime,
			@ModelAttribute("price") String price, @ModelAttribute("quantity") String quantity
			) {
		try {
			String data = URLEncoder.encode("itemID", "utf-8") + "=" + URLEncoder.encode(itemID, "utf-8") 
			+ "&"
		    + URLEncoder.encode("itemName", "utf-8") + "=" + URLEncoder.encode(itemName, "utf-8") 
			+"&" 
			+ URLEncoder.encode("sellerID", "utf-8") + "=" + URLEncoder.encode(sellerID, "utf-8")
			+"&" 
			+ URLEncoder.encode("imageUrl", "utf-8") + "=" + URLEncoder.encode(imageUrl, "utf-8")
			+"&" 
			+ URLEncoder.encode("startTime", "utf-8") + "=" + URLEncoder.encode(startTime, "utf-8")
			+"&" 
			+ URLEncoder.encode("expirationTime", "utf-8") + "=" + URLEncoder.encode(expirationTime, "utf-8")
			+"&" 
			+ URLEncoder.encode("price", "utf-8") + "=" + URLEncoder.encode(price, "utf-8")
			+"&" 
			+ URLEncoder.encode("description", "utf-8") + "=" + URLEncoder.encode(description, "utf-8")
			+"&" 
			+ URLEncoder.encode("postingID", "utf-8") + "=" + URLEncoder.encode(postingID, "utf-8")
			+"&" 
			+ URLEncoder.encode("quantity", "utf-8") + "=" + URLEncoder.encode(quantity, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/updatePostingBuyItNow");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/showPostingBuyItNow/" + postingID;
	}
	
	@RequestMapping(value="/updatePostingAuction",method=RequestMethod.POST)
	public String updatePostingAuction(@Autowired HttpRequest request, @ModelAttribute("postingID") String postingID,
			@ModelAttribute("itemID") String itemID, @ModelAttribute("itemName") String itemName,  @ModelAttribute("sellerID") String 
			sellerID, @ModelAttribute("description") String description, @ModelAttribute("imageUrl") String imageUrl,
			@ModelAttribute("startTime") String startTime, @ModelAttribute("endTime") String expirationTime,
			@ModelAttribute("startPrice") String startPrice) {
		try {
			String data = URLEncoder.encode("itemID", "utf-8") + "=" + URLEncoder.encode(itemID, "utf-8") 
			+ "&"
		    + URLEncoder.encode("itemName", "utf-8") + "=" + URLEncoder.encode(itemName, "utf-8") 
			+"&" 
			+ URLEncoder.encode("sellerID", "utf-8") + "=" + URLEncoder.encode(sellerID, "utf-8")
			+"&" 
			+ URLEncoder.encode("imageUrl", "utf-8") + "=" + URLEncoder.encode(imageUrl, "utf-8")
			+"&" 
			+ URLEncoder.encode("startTime", "utf-8") + "=" + URLEncoder.encode(startTime, "utf-8")
			+"&" 
			+ URLEncoder.encode("expirationTime", "utf-8") + "=" + URLEncoder.encode(expirationTime, "utf-8")
			+"&" 
			+ URLEncoder.encode("startPrice", "utf-8") + "=" + URLEncoder.encode(startPrice, "utf-8")
			+"&" 
			+ URLEncoder.encode("description", "utf-8") + "=" + URLEncoder.encode(description, "utf-8")
			+"&" 
			+ URLEncoder.encode("postingID", "utf-8") + "=" + URLEncoder.encode(postingID, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/updatePostingAuction");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/showPostingAuction/" + postingID;
	}
	
	
	@RequestMapping(value="/addBid",method=RequestMethod.POST)
	public String addBid(@ModelAttribute("price") String price, @ModelAttribute("id") String postingId,@Autowired HttpRequest request,
			HttpSession session,Model model) {
		try {
			String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(postingId, "utf-8");
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
			PostingAuction postingAuction = new PostingAuction(jsonObject.getInt("id"), jsonObject.getInt("itemID"), 
					jsonObject.getString("itemName"), jsonObject.getInt("sellerID"), 
					jsonObject.getString("imageUrl"), jsonObject.getString("createdTime"), jsonObject.getString("startTime"),
					jsonObject.getString("expirationTime"), jsonObject.getString("lastModifiedTime"),
					jsonObject.getBoolean("deleted"), jsonObject.getDouble("startPrice"), jsonObject.getDouble("curPrice"), jsonObject.getInt("curBidder"),jsonObject.getInt("quantity"),jsonObject.getString("description"),jsonObject.getBoolean("notified"));
			if(postingAuction.getCurPrice() > Double.parseDouble(price)) {
				model.addAttribute("messageType","Your price is lower than the current price");
				return "message";
			}
			else {
				if(postingAuction.getCurBidder() == -1) {
					try {
						String dataNotification = URLEncoder.encode("postingID", "utf-8") + "=" + URLEncoder.encode(postingId, "utf-8")
						+ "&"
					    + URLEncoder.encode("itemName", "utf-8") + "=" + URLEncoder.encode(postingAuction.getItemName(), "utf-8") 
						+"&" 
						+ URLEncoder.encode("sellerID", "utf-8") + "=" + URLEncoder.encode(postingAuction.getSellerID()+"", "utf-8");
						Socket socketNotification = new Socket();
						SocketAddress addressNotification = new InetSocketAddress(IpConfig.NotificationIP, 8086);
						socketNotification.connect(addressNotification);
						PrintWriter printWriterNotification = new PrintWriter(socketNotification.getOutputStream());
						Scanner scannerNotification = new Scanner(socketNotification.getInputStream());
						request.setPrintWriter(printWriterNotification);
						request.sendRequest(dataNotification, InetAddress.getLocalHost().getHostAddress(),"/notifyNewBid");
						socketNotification.close();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				else {
					try {
						String dataNotification = URLEncoder.encode("postingID", "utf-8") + "=" + URLEncoder.encode(postingId, "utf-8")
						+ "&"
					    + URLEncoder.encode("itemName", "utf-8") + "=" + URLEncoder.encode(postingAuction.getItemName(), "utf-8") 
						+"&" 
						+ URLEncoder.encode("bidderId", "utf-8") + "=" + URLEncoder.encode(postingAuction.getCurBidder()+"", "utf-8");
						Socket socketNotification = new Socket();
						SocketAddress addressNotification = new InetSocketAddress(IpConfig.NotificationIP, 8086);
						socketNotification.connect(addressNotification);
						PrintWriter printWriterNotification = new PrintWriter(socketNotification.getOutputStream());
						Scanner scannerNotification = new Scanner(socketNotification.getInputStream());
						request.setPrintWriter(printWriterNotification);
						request.sendRequest(dataNotification, InetAddress.getLocalHost().getHostAddress(),"/notifyLostBid");
						socketNotification.close();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				updatePostingAuction(request,postingId,((User)session.getAttribute("user")).getId()+"", price+"");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/showPostingAuction/" + postingId;
	}

	private void updatePostingAuction(HttpRequest request, String postingAuctionId, String bidderId,
			String price) {
		try {
			String data = URLEncoder.encode("postingAuctionId", "utf-8") + "=" + URLEncoder.encode(postingAuctionId, "utf-8")
			+ "&"
		    + URLEncoder.encode("bidderId", "utf-8") + "=" + URLEncoder.encode(bidderId, "utf-8") 
			+"&" 
			+ URLEncoder.encode("price", "utf-8") + "=" + URLEncoder.encode(price+"", "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.PostingIP, 8083);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/submitBid");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}	
	}
	
	
	
	
	
}
