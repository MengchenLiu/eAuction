package com.eAuction.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.SetOperations;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eAuction.domain.Address;
import com.eAuction.domain.Item;
import com.eAuction.domain.Payment;
import com.eAuction.domain.ShoppingCart;
import com.eAuction.domain.User;
import com.eAuction.domain.watchlist;
import com.eAuction.util.LoggingMessage;

import ch.qos.logback.core.joran.conditional.IfAction;
import javassist.expr.NewArray;

import com.eAuction.HttpRequest.HttpRequest;
import com.eAuction.HttpRequest.HttpResponse;
import com.eAuction.config.IpConfig;


@Controller
public class HomeController {
	
	@Autowired
	Environment env;
	
	
//	@Qualifier("redisTemplate")
//	@Autowired
//	RedisTemplate<String,Object> redisTemplate;
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String login() {	
		return "login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@ModelAttribute("userName") String userName, @ModelAttribute("password") String password,
			@Autowired HttpRequest request, HttpServletResponse servletResponse, HttpServletRequest servlerRequest
			,Model model,HttpSession session ) {
		try {	
			/**
			ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
			boolean hasKey = redisTemplate.hasKey(userName);
			if(hasKey) {
				System.out.println("Redis has this key");
				User user = (User) valueOperations.get(userName);
				if(!(PasswordEncode.getMD5(password, userName)).equals(user.getPassword())) {
					model.addAttribute("ifSuccess","false");
					model.addAttribute("reason","Password or UserName is wrong");
					return "login";
				}
				else {
					session.setMaxInactiveInterval(60*60);
					session.setAttribute("currentUser", userName);
					session.setAttribute("role", user.getRole());
					return "redirect:/index";
				}
			}
			**/
			String data = URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode(userName, "utf-8") + "&" +  
                    URLEncoder.encode("password", "utf-8") + "=" + URLEncoder.encode(password, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/login");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			if(jsonObject.has("requestResponse")) {
				model.addAttribute("ifSuccess","false");
			}
			else {
				User user = new User((int)jsonObject.get("id"), (String)jsonObject.get("userName"), (String)jsonObject.get("password"),(String)jsonObject.get("firstName"),(String)jsonObject.get("lastName"),
						(String)jsonObject.get("role"), (String)jsonObject.get("email"));
				//valueOperations.set(userName, user);
				session.setMaxInactiveInterval(60*60);
				session.setAttribute("currentUser", jsonObject.get("userName"));
				session.setAttribute("role", jsonObject.get("role"));
				session.setAttribute("user", user);
				return "redirect:/index";
			}
		} catch (Exception e) {
			System.out.println(e);
		}	
		return "login";
	}	
	
	@RequestMapping(value="/signup",method=RequestMethod.GET)
	public String signup() {
		return "signup";
	}
	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public String signup(@ModelAttribute("userName") String userName,@ModelAttribute("firstname") String firstName, 
			@ModelAttribute("lastname")String lastName, @ModelAttribute("email") String email, 
			@ModelAttribute("password") String password,Model model,@Autowired HttpRequest request,HttpServletRequest servlerRequest,
			@ModelAttribute("type") String type) {
		try {
			/**
			ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
			boolean hasKey = redisTemplate.hasKey(userName);
			User user = null;
			if(hasKey) {
				System.out.println("Redis has the key during login");
				user = (User)valueOperations.get(userName);
				
			}
			**/
			String data = URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode(userName, "utf-8") + "&" +  
                    URLEncoder.encode("password", "utf-8") + "=" + URLEncoder.encode(password, "utf-8")+ "&" +  
                      URLEncoder.encode("firstName", "utf-8") + "=" + URLEncoder.encode(firstName, "utf-8")
                            + "&" + URLEncoder.encode("lastName", "utf-8") + "=" + URLEncoder.encode(lastName, "utf-8")
                            + "&" +  URLEncoder.encode("email", "utf-8") + "=" + URLEncoder.encode(email, "utf-8")
                            + "&" +  URLEncoder.encode("role", "utf-8") + "=" + URLEncoder.encode((Integer.parseInt(type)==1)?"admin":"normal", "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/signup");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			if(jsonObject.has("requestResponse")) {
				model.addAttribute("ifSuccess","false");
				model.addAttribute("errorReason","UserName has already existed");
			}
			else {
				sendEmailToNotification(request, email, jsonObject.getInt("id")+"");
				return "redirect:/login";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return "signup";
	}
	
	private void sendEmailToNotification(HttpRequest request, String email, String id)  {
		try {
			String data = URLEncoder.encode("email", "utf-8") + "=" + URLEncoder.encode(email, "utf-8") + "&" +  
	                URLEncoder.encode("userid", "utf-8") + "=" + URLEncoder.encode(id, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.NotificationIP, 8086);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addEmail");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}	
	}
	
	@RequestMapping("/profile")
	public String profile(HttpSession session, @Autowired HttpRequest request,Model model) {
		try {
			String userName = (String) session.getAttribute("currentUser");
			if(userName == null) {
				return "redirect:/login";
			}
			else {			
//				ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//				if(redisTemplate.hasKey(userName)) {
//					User user = (User) valueOperations.get(userName);
//					model.addAttribute("user",user);
//					return "/profile";
//				}
				String data = URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode(userName, "utf-8");                    
				Socket socket = new Socket();
				SocketAddress address = new InetSocketAddress(IpConfig.AccountIP, 8081);
				socket.connect(address);
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				Scanner scanner = new Scanner(socket.getInputStream());
				request.setPrintWriter(printWriter);
				request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/profile");
				List<String> response = new HttpResponse(scanner).getRespnse();
				socket.close();
				JSONObject jsonObject = new JSONObject(response.get(6));
				User user = new User((int)jsonObject.get("id"), (String)jsonObject.get("userName"), (String)jsonObject.get("password"),(String)jsonObject.get("firstName"),(String)jsonObject.get("lastName"),
						(String)jsonObject.get("role"), (String)jsonObject.get("email"));
				model.addAttribute("user",user);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "profile";
	}
	
	@RequestMapping("/deleteUser")
	public String deleteUser() {
		return "deleteUser";
	}
	
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST)
	public String deleteUser(HttpSession session,@Autowired HttpRequest request,@ModelAttribute("userName") String name,
			Model model) {
		try {
			String userName = (String)session.getAttribute("currentUser");
			if(userName == null) return "redirect:/login";
			else if(!userName.equals(name)) {
				String role = (String)session.getAttribute("role");
				if(role.equals("admin")) {
					deleteUserHelper(request, name, session);
				}
				else {
					model.addAttribute("messageType","You do not have the authority to delete the user");
					return "message";
				}
			}
			else {
				deleteUserHelper(request, userName, session);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/index";
	}
	
	@RequestMapping(value="/deleteSelf",method=RequestMethod.POST)
	public String deleteSelf(HttpSession session,
			@Autowired HttpRequest request) {
		try {
			deleteUserHelper(request, (String)session.getAttribute("currentUser"), session);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/login";
	}
	
	private void deleteUserHelper(HttpRequest request, String userName,HttpSession session) {
		try {
			String data = URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode(userName, "utf-8");                    
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/deleteUser");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			session.invalidate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@RequestMapping("/blockUser")
	public String blockUser(HttpSession session, Model model) {
		String role = (String)session.getAttribute("role");
		if(role == null || !role.equals("admin")) {
			model.addAttribute("ifSuccess","fail");
			model.addAttribute("error","Do not have permission");
			return "profile";
		}
		return "blockuser";
	}
	
	@RequestMapping(value="/blockUser",method=RequestMethod.POST)
	public String blockUser(@ModelAttribute("userName") String userName, Model model, 
			HttpSession session,@Autowired HttpRequest request) {
		try {
			String role = (String)session.getAttribute("role");
			if(role == null || !role.equals("admin")) {
				model.addAttribute("ifSuccess","fail");
				model.addAttribute("error","Do not have permission");
				return "profile";
			}
			else {
				String data = URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode(userName, "utf-8") +"&" 
				+ URLEncoder.encode("role", "utf-8") + "=" + URLEncoder.encode("block", "utf-8");                    
				Socket socket = new Socket();
				SocketAddress address = new InetSocketAddress(IpConfig.AccountIP, 8081);
				socket.connect(address);
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				Scanner scanner = new Scanner(socket.getInputStream());
				request.setPrintWriter(printWriter);
				request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/blockUser");
				List<String> response = new HttpResponse(scanner).getRespnse();
				socket.close();
				JSONObject jsonObject = new JSONObject(response.get(6));
				if(jsonObject.has("requestResponse")) {
					model.addAttribute("ifSuccess",jsonObject.get("requestResponse"));
					model.addAttribute("error",jsonObject.get("error"));
				}
				//if(redisTemplate.hasKey(userName)) redisTemplate.delete(userName);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/profile";
	}
	
	@RequestMapping("/updateInformation")
	public String updateInformation(@Autowired HttpRequest request, HttpSession session,Model model) {
		try {
			String currUser = (String)session.getAttribute("currentUser");
			if(currUser == null) {
				return "redirect:/login";
			}
			else {
				String data = URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode(currUser, "utf-8");                    
				Socket socket = new Socket();
				SocketAddress address = new InetSocketAddress(IpConfig.AccountIP, 8081);
				socket.connect(address);
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				Scanner scanner = new Scanner(socket.getInputStream());
				request.setPrintWriter(printWriter);
				request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/profile");
				List<String> response = new HttpResponse(scanner).getRespnse();
				socket.close();
				JSONObject jsonObject = new JSONObject(response.get(6));
				User user = new User(jsonObject.getInt("id"), (String)jsonObject.get("userName"), (String)jsonObject.get("password"),(String)jsonObject.get("firstName"),(String)jsonObject.get("lastName"),
						(String)jsonObject.get("role"), (String)jsonObject.get("email"));
				model.addAttribute("user",user);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "updateInformation";
	}
	
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	public String updateInformation(@Autowired HttpRequest request,
			@ModelAttribute("firstname") String firstName, @ModelAttribute("lastname") String lastName,
			@ModelAttribute("email") String email,
			HttpSession session,
			Model model) {
		User user = (User)session.getAttribute("user");
		if(user == null) return "redirect:/login";
		try {
			String data = URLEncoder.encode("userId", "utf-8") + "=" + URLEncoder.encode(user.getId()+"", "utf-8")
			+"&" 
			+ URLEncoder.encode("firstName", "utf-8") + "=" + URLEncoder.encode(firstName, "utf-8")
			+"&" 
			+ URLEncoder.encode("lastName", "utf-8") + "=" + URLEncoder.encode(lastName, "utf-8")
			+"&" 
			+ URLEncoder.encode("email", "utf-8") + "=" + URLEncoder.encode(email, "utf-8");                    
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/updateInformation");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			User newuser = new User(jsonObject.getInt("id"), (String)jsonObject.get("userName"), (String)jsonObject.get("password"),(String)jsonObject.get("firstName"),(String)jsonObject.get("lastName"),
					(String)jsonObject.get("role"), (String)jsonObject.get("email"));
			model.addAttribute("user",newuser);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/profile";
	}
	
	@RequestMapping("/payment")
	public String payment(HttpSession session,@Autowired HttpRequest request,Model model) {
		try {
			String currUser = (String) session.getAttribute("currentUser");
			if(currUser == null) return "redirect:/login";
			else {
				String data = URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode(currUser, "utf-8");                    
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
				List<Payment> payments = new ArrayList<>();
				for(int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					payments.add(new Payment((int)jsonObject.getInt("id"), jsonObject.getString("cardNumber"), jsonObject.getString("holderName"), 
							jsonObject.getString("cvs"), jsonObject.getString("expiryMonth"), jsonObject.getString("expiryYear"), jsonObject.getString("userName")));
				}
				model.addAttribute("payments",payments);
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "payment";
	}
	
	@RequestMapping(value="/payment",method=RequestMethod.POST)
	public String payment(HttpSession session,@Autowired HttpRequest request,
			@ModelAttribute("name") String holderName, @ModelAttribute("cardNum") String cardNumber,@ModelAttribute("month") String expiryMonth,
			@ModelAttribute("year") String expiryYear, @ModelAttribute("cvv") String cvs) {
		try {
			String currUser = (String) session.getAttribute("currentUser");
			if(currUser == null) return "redirect:/login";
			else {
				String data = URLEncoder.encode("holderName", "utf-8") + "=" + URLEncoder.encode(holderName, "utf-8") 
				+"&" 
				+ URLEncoder.encode("cardNumber", "utf-8") + "=" + URLEncoder.encode(cardNumber, "utf-8")
				+"&" 
				+ URLEncoder.encode("expiryMonth", "utf-8") + "=" + URLEncoder.encode(expiryMonth, "utf-8")
				+"&" 
				+ URLEncoder.encode("expiryYear", "utf-8") + "=" + URLEncoder.encode(expiryYear, "utf-8")
				+"&" 
				+ URLEncoder.encode("cvs", "utf-8") + "=" + URLEncoder.encode(cvs, "utf-8")
				+"&" 
				+ URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode(currUser, "utf-8");                    
				Socket socket = new Socket();
				SocketAddress address = new InetSocketAddress(IpConfig.AccountIP, 8081);
				socket.connect(address);
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				Scanner scanner = new Scanner(socket.getInputStream());
				request.setPrintWriter(printWriter);
				request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addPayment");
				List<String> response = new HttpResponse(scanner).getRespnse();
				socket.close();
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/payment";
	}
	
	
	@RequestMapping(value="/items", method=RequestMethod.GET)
	public String items(@Autowired HttpRequest request,Model model) {
		try {
			
			String data = new String();
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.ItemManagementIP, 8082);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/items");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONArray jsonArray = new JSONArray(response.get(6));
			List<Item> list = new ArrayList<>();
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject =jsonArray.getJSONObject(i);
				list.add(new Item((int)jsonObject.get("id"), (String)jsonObject.get("name"),(String)jsonObject.get("description"),(String)jsonObject.get("imageUrl"),
						(String)jsonObject.get("categoryName")));
			}
			model.addAttribute("items",list);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "items";
	}
	
	@RequestMapping("/addItem")
	public String addItem(HttpSession session,Model model) {
		String role = (String)session.getAttribute("role");
		if(role == null || !role.equals("admin")) {
			model.addAttribute("error","You do not have authority to add a new item");
			return "redirect:/index";
		}
		else return "newItem";
	}
	
	@RequestMapping(value="/addItem",method=RequestMethod.POST)
	public String addItem(@Autowired HttpRequest request,@ModelAttribute("name") String itemName, @ModelAttribute("categoryName") String 
			categoryName, @ModelAttribute("imageUrl") String imageUrl, @ModelAttribute("description") String description,
			HttpSession session,Model model) {
		try {
			String data = URLEncoder.encode("itemName", "utf-8") + "=" + URLEncoder.encode(itemName, "utf-8") 
			+"&" 
			+ URLEncoder.encode("categoryName", "utf-8") + "=" + URLEncoder.encode(categoryName, "utf-8")
			+"&" 
			+ URLEncoder.encode("imageUrl", "utf-8") + "=" + URLEncoder.encode(imageUrl, "utf-8")
			+"&" 
			+ URLEncoder.encode("itemDescription", "utf-8") + "=" + URLEncoder.encode(description, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.ItemManagementIP, 8082);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addItem");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			Item item = new Item((int)jsonObject.get("id"), (String)jsonObject.get("name"),(String)jsonObject.get("description"),(String)jsonObject.get("imageUrl"),
					(String)jsonObject.get("categoryName"));
			model.addAttribute("item",item);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/items";
	}
	
	@RequestMapping("/showItem/{id}")
	public String showItem(@PathVariable("id") String id,@Autowired HttpRequest request,Model model,HttpSession session) {
		try {
			String data  = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.ItemManagementIP, 8082);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/showItem/"+id);
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			Item item = new Item((int)jsonObject.get("id"), (String)jsonObject.get("name"),(String)jsonObject.get("description"),(String)jsonObject.get("imageUrl"),
					(String)jsonObject.get("categoryName"));
			model.addAttribute("item",item);
			User user = (User) session.getAttribute("user");
			if(user != null) {
				String msgText = "userName " + user.getUserName() + " " + " itemId " + item.getId() + " " 
						+ "itemName " + item.getName();
				String message = LoggingMessage.getMessage(user.getId(), msgText, "itemManagement", new Timestamp(System.currentTimeMillis())+"");
				rabbitTemplate.convertAndSend("logging.queue",message);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return "itemDetail";
	}
	
	@RequestMapping(value = "/deleteItem/{id}", method = RequestMethod.GET)
	public String deleteItem(@PathVariable("id") String id, @Autowired HttpRequest request, Model model,HttpSession session) {
		String role = (String)session.getAttribute("role");
		if(role == null || !role.equals("admin")) return "items";
		try {
			String data  = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.ItemManagementIP, 8082);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/deleteItem/"+id);
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/items";
	}
	
	@RequestMapping("/updateItem/{id}")
	public String updateItem(HttpSession session,Model model,
			@PathVariable("id") String id,
			@Autowired HttpRequest request) {
		String role = (String)session.getAttribute("role");
		if(!role.equals("admin")) {
			model.addAttribute("messageType","You do not have authority to update item");
			return "redirect:/login";
		}
		try {
			String data  = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.ItemManagementIP, 8082);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/showItem/"+id);
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			Item item = new Item((int)jsonObject.get("id"), (String)jsonObject.get("name"),(String)jsonObject.get("description"),(String)jsonObject.get("imageUrl"),
					(String)jsonObject.get("categoryName"));
			model.addAttribute("item",item);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "updateItem";
	}

	
	@RequestMapping(value="/updateItem", method = RequestMethod.POST)
	public String updateItem(@Autowired HttpRequest request, @ModelAttribute("id") String id, @ModelAttribute("itemName") String itemName,
			@ModelAttribute("categoryName") String categoryName, @ModelAttribute("imageUrl") String imageUrl, 
			@ModelAttribute("description") String description,Model model) {
		try {
			String data = URLEncoder.encode("itemId", "utf-8") + "=" + URLEncoder.encode(id, "utf-8") 
			+ "&"
		    + URLEncoder.encode("itemName", "utf-8") + "=" + URLEncoder.encode(itemName, "utf-8") 
			+"&" 
			+ URLEncoder.encode("categoryName", "utf-8") + "=" + URLEncoder.encode(categoryName, "utf-8")
			+"&" 
			+ URLEncoder.encode("imageUrl", "utf-8") + "=" + URLEncoder.encode(imageUrl, "utf-8")
			+"&" 
			+ URLEncoder.encode("itemDescription", "utf-8") + "=" + URLEncoder.encode(description, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.ItemManagementIP, 8082);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/updateItem");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			Item item = new Item(Integer.parseInt((String)jsonObject.get("id")), (String)jsonObject.get("name"),(String)jsonObject.get("description"),(String)jsonObject.get("imageUrl"),
					(String)jsonObject.get("categoryName"));
			model.addAttribute("item",item);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/showItem/"+id;
	}
	

	
	@RequestMapping("/addToCart")
	public String addToShoppingCart(HttpSession session, 
			@ModelAttribute("id") String postingId, @ModelAttribute("quantity") String quantity,
			HttpRequest request,Model model,@ModelAttribute("price") String price, @ModelAttribute("imageUrl") String imageUrl,
			@ModelAttribute("itemName") String itemName,@ModelAttribute("typeId") String typeId) {
		String role = (String)session.getAttribute("currentUser");
		if(role == null) return "redirect:/login";
		try {
			String data = URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode((String)session.getAttribute("currentUser"), "utf-8") 
			+ "&"
		    + URLEncoder.encode("postingId", "utf-8") + "=" + URLEncoder.encode(postingId, "utf-8") 
			+"&" 
			+ URLEncoder.encode("quantity", "utf-8") + "=" + URLEncoder.encode(quantity, "utf-8")
			+ "&"
		    + URLEncoder.encode("price", "utf-8") + "=" + URLEncoder.encode(price, "utf-8") 
			+"&" 
			+ URLEncoder.encode("imageUrl", "utf-8") + "=" + URLEncoder.encode(imageUrl, "utf-8")
			+"&" 
			+ URLEncoder.encode("itemName", "utf-8") + "=" + URLEncoder.encode(itemName, "utf-8")
			+"&" 
			+ URLEncoder.encode("typeId", "utf-8") + "=" + URLEncoder.encode(typeId, "utf-8");
			Socket socket = new Socket();
			SocketAddress  address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addToShoppingCart");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			ShoppingCart shoppingCart = new ShoppingCart(jsonObject.getString("userName"), jsonObject.getInt("postingId"), 
					jsonObject.getInt("postingId"),jsonObject.getDouble("price"),jsonObject.getString("imageUrl"),jsonObject.getString("itemName"),
					jsonObject.getInt("typeId"));
			model.addAttribute("shoppingCart",shoppingCart);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/showPostingBuyItNow/" + postingId;
	}
	
	@RequestMapping("/address")
	public String showAddress(HttpSession session,Model model,@Autowired HttpRequest request) {
		String userName = (String)session.getAttribute("currentUser");
		if(userName == null) return "redirect:/login";
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
			List<Address> addresses = new ArrayList<>();
			JSONArray jsonArray = new JSONArray(response.get(6));
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				addresses.add(new Address(jsonObject.getInt("id"), jsonObject.getString("country"), jsonObject.getString("state"), jsonObject.getString("addressStreet1")
						, jsonObject.getString("addressStreet2"), jsonObject.getString("city"), jsonObject.getInt("zip"), jsonObject.getString("userName")));
			}
			model.addAttribute("addresses",addresses);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "address";
	}
	
	
	@RequestMapping("/addAddress")
	public String addAddress(HttpSession session, @Autowired HttpRequest request, @ModelAttribute("addrss1") String addressStreet1,
			@ModelAttribute("addrss2") String addressStreet2, @ModelAttribute("country") String country,
			@ModelAttribute("city") String city, @ModelAttribute("state") String state, @ModelAttribute("zip") String zip) {
		try {
			String data = URLEncoder.encode("addressStreet1", "utf-8") + "=" + URLEncoder.encode(addressStreet1, "utf-8") 
			+ "&"
		    + URLEncoder.encode("addressStreet2", "utf-8") + "=" + URLEncoder.encode(addressStreet2, "utf-8") 
			+"&" 
			+ URLEncoder.encode("country", "utf-8") + "=" + URLEncoder.encode(country, "utf-8")
			+ "&"
		    + URLEncoder.encode("city", "utf-8") + "=" + URLEncoder.encode(city, "utf-8") 
			+"&" 
			+ URLEncoder.encode("state", "utf-8") + "=" + URLEncoder.encode(state, "utf-8")
			+ "&"
		    + URLEncoder.encode("zip", "utf-8") + "=" + URLEncoder.encode(zip, "utf-8") 
			+"&" 
			+ URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode((String)session.getAttribute("currentUser"), "utf-8");
			Socket socket = new Socket();
			SocketAddress  address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addAddress");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/address";
	}
	
	@RequestMapping("/deleteAddress/{id}")
	public String deleteAddress(@Autowired HttpRequest request, @PathVariable("id") String id) {
		try {
			
			String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id, "utf-8");
			Socket socket = new Socket();
			SocketAddress  address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/deleteAddress");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/address";
	}
	
	
	@RequestMapping("/deletePayment/{id}")
	public String deletePayment(@Autowired HttpRequest request, @PathVariable("id") String id,HttpSession session) {
		try {
			String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id, "utf-8")
			+"&" 
			+ URLEncoder.encode("userName", "utf-8") + "=" + URLEncoder.encode((String)session.getAttribute("currentUser"), "utf-8");
			Socket socket = new Socket();
			SocketAddress  address = new InetSocketAddress(IpConfig.AccountIP, 8081);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/deletePayment");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/payment";
	}
	
	
	@RequestMapping("/shoppintCarts")
	public String shoppingCart(@Autowired HttpRequest request,Model model,HttpSession session) {
		String userName = (String)session.getAttribute("currentUser");
		if(userName == null) {
			return "redirect:/login";
		}
		else {
			try {
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
				for(int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					if(jsonObject.getInt("typeId") == 2) {
						auctions.add(new ShoppingCart(jsonObject.getInt("id"), jsonObject.getString("userName"), 
								jsonObject.getInt("postingId"), jsonObject.getInt("quantity"),jsonObject.getDouble("price"),
								jsonObject.getString("imageUrl"),jsonObject.getString("itemName"),jsonObject.getInt("typeId")));	
					}
					else {
						buyitnows.add(new ShoppingCart(jsonObject.getInt("id"), jsonObject.getString("userName"), 
								jsonObject.getInt("postingId"), jsonObject.getInt("quantity"),jsonObject.getDouble("price"),
								jsonObject.getString("imageUrl"),jsonObject.getString("itemName"),jsonObject.getInt("typeId")));
					}
					
				}
				model.addAttribute("auctions",auctions);
				model.addAttribute("buyitnows",buyitnows);
			} catch (Exception e) {
				System.out.println(e);
			}
			return "cart";
		}	
	}

	
	@RequestMapping(value="/watchlist")
	public String watchlist(HttpSession session, @Autowired HttpRequest request,Model model) {
		String userName = (String)session.getAttribute("currentUser");
		User user = (User) session.getAttribute("user");
		if(userName == null) {
			return "redirect:/login";
		}
		try {
			String data = URLEncoder.encode("userid", "utf-8") + "=" + URLEncoder.encode(user.getId()+"", "utf-8");
			Socket socket = new Socket();
			SocketAddress  address = new InetSocketAddress(IpConfig.NotificationIP, 8086);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/watchlist");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONArray jsonArray = new JSONArray(response.get(6));
			List<watchlist> list = new ArrayList<>();
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				list.add(new watchlist(jsonObject.getInt("id"), jsonObject.getString("email"), 
						jsonObject.getInt("itemId"), jsonObject.getString("itemName"), jsonObject.getDouble("price"), 
						jsonObject.getString("userName")));	
			}
			model.addAttribute("watchlist",list);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return "watchlist";
	}
	
	@RequestMapping(value="/deleteInWatchlist",method=RequestMethod.POST)
	public String deleteWatchList(@ModelAttribute("id") String id
			, @Autowired HttpRequest request
			,@ModelAttribute("userName") String name
			,HttpSession session) {
		System.out.println(name);
		User user = (User) session.getAttribute("user");
		System.out.println(user.getId());
		if(user.getId() != Integer.parseInt(name)) return "redirect:/watchlist";
		try {
			String data =  URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.NotificationIP, 8086);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/deleteWatchlist");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/watchlist";
	}
	
	@RequestMapping(value="/addWatchList",method=RequestMethod.POST)
	public String addToWathchList(@ModelAttribute("price") String price, @ModelAttribute("itemId") String itemId, 
			@ModelAttribute("itemName") String itemName,HttpSession session, @Autowired HttpRequest request) {
		User user = (User) session.getAttribute("user");
		if(user == null) return "redirect:/login";
		try {
			String data = URLEncoder.encode("userid", "utf-8") + "=" + URLEncoder.encode(user.getId()+"", "utf-8")
			+ "&"
		    + URLEncoder.encode("itemId", "utf-8") + "=" + URLEncoder.encode(itemId, "utf-8") 
			+"&" 
			+ URLEncoder.encode("itemName", "utf-8") + "=" + URLEncoder.encode(itemName, "utf-8")
			+ "&"
		    + URLEncoder.encode("price", "utf-8") + "=" + URLEncoder.encode(price, "utf-8")
		    ;
			Socket socket = new Socket();
			SocketAddress  address = new InetSocketAddress(IpConfig.NotificationIP, 8086);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addWatchlist");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/showItem/" + itemId;
	}	
	
	
	@RequestMapping(value="/deleteInCart")
	public String deleteFromShoppingCart(@Autowired HttpRequest request, 
			@ModelAttribute("id") String id, 
			HttpSession session,
			@ModelAttribute("userName") String userName) {
		String user = (String) session.getAttribute("currentUser");
		if(!user.equals(userName)) return "redirect:/shoppintCarts";
		else {
			try {
				String data = URLEncoder.encode("id", "utf-8") + "=" + URLEncoder.encode(id, "utf-8");
				Socket socket = new Socket();
				SocketAddress  address = new InetSocketAddress(IpConfig.AccountIP, 8081);
				socket.connect(address);
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				Scanner scanner = new Scanner(socket.getInputStream());
				request.setPrintWriter(printWriter);
				request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/deleteShoppingCart");
				List<String> response = new HttpResponse(scanner).getRespnse();
				socket.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			return "redirect:/shoppintCarts";
		}
	}
	
	@RequestMapping(value="/searchItem",method=RequestMethod.POST)
	public String searchItem(@Autowired HttpRequest request,
			@ModelAttribute("key") String key,Model model) {
		try {	
			String data = URLEncoder.encode("keyword", "utf-8") + "=" + URLEncoder.encode(key, "utf-8");
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.ItemManagementIP, 8082);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/itemsByKeyword");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONArray jsonArray = new JSONArray(response.get(6));
			List<Item> list = new ArrayList<>();
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject =jsonArray.getJSONObject(i);
				list.add(new Item((int)jsonObject.get("id"), (String)jsonObject.get("name"),(String)jsonObject.get("description"),(String)jsonObject.get("imageUrl"),
						(String)jsonObject.get("categoryName")));
			}
			model.addAttribute("items",list);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "items";
	}
	
	
}
