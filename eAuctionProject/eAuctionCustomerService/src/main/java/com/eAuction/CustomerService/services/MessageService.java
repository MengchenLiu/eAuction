package com.eAuction.CustomerService.services;

import com.eAuction.CustomerService.domain.Message;

import java.util.List;

public interface MessageService {
	List<Message> listAllMessages();

	List<Message> getMessagesByUser(Integer userId);

	List<Message> listUnansweredMessages();

	Message getMessageById(Integer id);

	int addQuestion(Message message);

	boolean addAnswer(Message message);

	boolean deleteMessage(Integer id);
}