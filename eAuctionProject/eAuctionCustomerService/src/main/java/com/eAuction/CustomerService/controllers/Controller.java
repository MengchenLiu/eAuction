package com.eAuction.CustomerService.controllers;

import javax.servlet.http.HttpServletResponse;

import com.eAuction.CustomerService.domain.Message;
import com.eAuction.CustomerService.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
public class Controller {
	private MessageService messageService;

	@Autowired
	public void setService(MessageService messageService) {
		this.messageService = messageService;
	}

	/***************** CustomerService *****************/
	@RequestMapping(value = "/messages", method=RequestMethod.POST)
	public List<Message> listAllMessages() {
		return messageService.listAllMessages();
	}

	@RequestMapping(value = "/messages/{userId}", method=RequestMethod.POST)
	public List<Message> getMessagesByUser(@PathVariable String userId) {
		return messageService.getMessagesByUser(Integer.parseInt(userId));
	}

	@RequestMapping(value = "/unansweredmessages", method=RequestMethod.POST)
	public List<Message> listUnansweredMessages() {
		return messageService.listUnansweredMessages();
	}

	@RequestMapping(value = "/addQuestion", method=RequestMethod.POST)
	public Message addQuestion(
			@RequestParam("userId") String userId
			, @RequestParam("question") String question
			, HttpServletResponse response
	) {
		Message message = new Message(Integer.parseInt(userId), question, new Timestamp(System.currentTimeMillis()));
		int messageId = messageService.addQuestion(message);
		message.setId(messageId);

		return message;
	}

	@RequestMapping(value = "/addAnswer", method=RequestMethod.POST)
	public Message addAnswer(
			@RequestParam("messageId") String messageId
			, @RequestParam("adminId") String adminId
			, @RequestParam("answer") String answer
			, HttpServletResponse response
	) {
		Message message = messageService.getMessageById(Integer.parseInt(messageId));
		message.setAdminId(Integer.parseInt(adminId));
		message.setAnswer(answer);

		if(messageService.addAnswer(message))
			return message;
		else
			return null;
	}

	@RequestMapping(value = "/showMessage/{id}", method=RequestMethod.POST)
	public Message showMessage(@PathVariable String id) {
		return messageService.getMessageById(Integer.parseInt(id));
	}

	@RequestMapping(value = "/deleteMessage/{id}", method=RequestMethod.POST)
	public boolean deleteMessage(@PathVariable String id) {
		return messageService.deleteMessage(Integer.parseInt(id));
	}

}
