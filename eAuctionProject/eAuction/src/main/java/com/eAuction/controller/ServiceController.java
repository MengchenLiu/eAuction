package com.eAuction.controller;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.eAuction.HttpRequest.HttpRequest;
import com.eAuction.HttpRequest.HttpResponse;
import com.eAuction.config.IpConfig;
import com.eAuction.domain.Message;
import com.eAuction.domain.User;

@Controller
public class ServiceController {
	
	@RequestMapping("/askService")
	public String service(@Autowired HttpRequest request,
			HttpSession session,
			Model model) {
		User user = (User) session.getAttribute("user");
		if(user==null) {
			return "redirect:/login";
		}
		else {
			try {
				String data = URLEncoder.encode("userId", "utf-8") + "=" + URLEncoder.encode(user.getId()+"", "utf-8");                    
				Socket socket = new Socket();
				SocketAddress address = new InetSocketAddress(IpConfig.CustomerServiceIP, 8085);
				socket.connect(address);
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				Scanner scanner = new Scanner(socket.getInputStream());
				request.setPrintWriter(printWriter);
				request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/messages/"+user.getId());
				List<String> response = new HttpResponse(scanner).getRespnse();
				socket.close();
				JSONArray jsonArray = new JSONArray(response.get(6));
				List<Message> messages = new ArrayList<>();
				for(int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					System.out.println(jsonObject.get("answer"));
					messages.add(new Message(jsonObject.getInt("id"),
							jsonObject.getInt("userId"),
							jsonObject.getString("question"),
							jsonObject.getInt("adminId"),
							jsonObject.get("answer").toString().equals("null")?"":jsonObject.get("answer").toString()));
				}
				model.addAttribute("messages",messages);
			} catch (Exception e) {
				System.out.println(e);
			}
			return "usercustomservice";
		}
	}
	
	
	@RequestMapping("/addQuestion")
	public String addQuestion(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null) return "redirect:/login";
		else {
			return "newQuestion";
		}
	}
	
	
	@RequestMapping(value="/addQuestion", method=RequestMethod.POST)
	public String addQuestion(@Autowired HttpRequest request,
			HttpSession session,
			@ModelAttribute("question") String question) {
		User user = (User)session.getAttribute("user");
		try {
			String data = URLEncoder.encode("userId", "utf-8") + "=" + URLEncoder.encode(user.getId()+"", "utf-8")
			+ "&"
		    + URLEncoder.encode("question", "utf-8") + "=" + URLEncoder.encode(question, "utf-8") ;                    
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.CustomerServiceIP, 8085);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addQuestion");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/askService";
	}
	
	@RequestMapping("/provideService")
	public String prodiveService(
			@Autowired HttpRequest request,
			HttpSession session,
			Model model) {
		User user = (User) session.getAttribute("user");
		if(user == null || !user.getRole().equals("admin")) return "redirect://askService";
		try {
			String data = "" ;                    
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.CustomerServiceIP, 8085);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/messages");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			List<Message> messages = new ArrayList<>();
			JSONArray jsonArray = new JSONArray(response.get(6));
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				System.out.println(jsonObject.get("answer"));
				messages.add(new Message(jsonObject.getInt("id"),
						jsonObject.getInt("userId"),
						jsonObject.getString("question"),
						jsonObject.getInt("adminId"),
						jsonObject.get("answer").toString().equals("null")?"":jsonObject.get("answer").toString()));
			}
			model.addAttribute("messages",messages);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "admincustomservice";
	}
	
	@RequestMapping("/getUnansweredQuestion")
	public String getUnansweredQuestion(
			@Autowired HttpRequest request,
			HttpSession session,
			Model model) {
		User user = (User) session.getAttribute("user");
		if(user == null || !user.getRole().equals("admin")) return "redirect:/askService";
		try {
			String data = "" ;                    
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.CustomerServiceIP, 8085);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/unansweredmessages");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			List<Message> messages = new ArrayList<>();
			JSONArray jsonArray = new JSONArray(response.get(6));
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				System.out.println(jsonObject.get("answer"));
				messages.add(new Message(jsonObject.getInt("id"),
						jsonObject.getInt("userId"),
						jsonObject.getString("question"),
						jsonObject.getInt("adminId"),
						jsonObject.get("answer").toString().equals("null")?"":jsonObject.get("answer").toString()));
			}
			model.addAttribute("messages",messages);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "unanswered";
	}
	
	@RequestMapping("/addAnswer")
	public String addAnswer(
			HttpSession session,
			Model model,
			@Autowired HttpRequest request,
			@RequestParam("messageid") String id) {
		User user = (User) session.getAttribute("user");
		if(user == null || !user.getRole().equals("admin")) return "redirect:/askService";
		try {
			String data = "" ;                    
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.CustomerServiceIP, 8085);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/showMessage/"+id);
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			List<Message> messages = new ArrayList<>();
			JSONObject jsonObject = new JSONObject(response.get(6));
			Message message = new Message(jsonObject.getInt("id"),
					jsonObject.getInt("userId"),
					jsonObject.getString("question"),
					jsonObject.getInt("adminId"),
					jsonObject.get("answer").toString().equals("null")?"":jsonObject.get("answer").toString());
			model.addAttribute("messageid",message.getId());
		} catch (Exception e) {
			System.out.println(e);
		}
		return "newAnswer";
	}
		
	
	@RequestMapping(value="/addAnswer",method=RequestMethod.POST)
	public String addAnswer(
			@Autowired HttpRequest request,
			@ModelAttribute("answer") String answer,
			@ModelAttribute("messageid") String messageid,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		try {
			String data = URLEncoder.encode("messageId", "utf-8") + "=" + URLEncoder.encode(messageid, "utf-8")
			+ "&"
		    + URLEncoder.encode("answer", "utf-8") + "=" + URLEncoder.encode(answer, "utf-8") 
		    + "&"
		    + URLEncoder.encode("adminId", "utf-8") + "=" + URLEncoder.encode(user.getId()+"", "utf-8") ;                     
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(IpConfig.CustomerServiceIP, 8085);
			socket.connect(address);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			request.setPrintWriter(printWriter);
			request.sendRequest(data, InetAddress.getLocalHost().getHostAddress(),"/addAnswer");
			List<String> response = new HttpResponse(scanner).getRespnse();
			socket.close();
			JSONObject jsonObject = new JSONObject(response.get(6));
			Message message = new Message(jsonObject.getInt("id"),
					jsonObject.getInt("userId"),
					jsonObject.getString("question"),
					jsonObject.getInt("adminId"),
					jsonObject.get("answer").toString().equals("null")?"":jsonObject.get("answer").toString());
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/getUnansweredQuestion";
	}

	
	
	
	
}
