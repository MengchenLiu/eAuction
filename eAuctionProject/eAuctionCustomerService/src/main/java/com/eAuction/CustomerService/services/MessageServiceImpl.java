package com.eAuction.CustomerService.services;

import com.eAuction.CustomerService.domain.Message;
import com.eAuction.CustomerService.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
	private MessageRepository repository;

	@Autowired
	public void setRepository(MessageRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Message> listAllMessages() {
		return repository.getAllMessages();
	}

	@Override
	public List<Message> getMessagesByUser(Integer userId) {
		return repository.getMessagesByUser(userId);
	}

	@Override
	public List<Message> listUnansweredMessages() {
		return repository.getUnansweredMessages();
	}

	@Override
	public Message getMessageById(Integer id) {
		return repository.getMessage(id);
	}

	@Override
	public int addQuestion(Message message) {
		return repository.addQuestion(message);
	}

	@Override
	public boolean addAnswer(Message message) {
		return repository.addAnswer(message);
	}

	@Override
	public boolean deleteMessage(Integer id) {
		return repository.deleteMessage(id);
	}
}
