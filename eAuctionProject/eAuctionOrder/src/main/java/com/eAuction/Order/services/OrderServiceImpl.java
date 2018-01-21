package com.eAuction.Order.services;

import com.eAuction.Order.domain.Order;
import com.eAuction.Order.domain.Posting;
import com.eAuction.Order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
	private OrderRepository repository;

	@Autowired
	public void setRepository(OrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Order> listAllOrders() {
		return repository.getAllOrders();
	}

	@Override
	public List<Order> getOrdersByBuyer(Integer buyerId) {
		return repository.getOrdersByBuyer(buyerId);
	}

	@Override
	public Order getOrderById(Integer id) {
		return repository.getOrder(id);
	}

	@Override
	public int addOrder(Order order) {
		return repository.addOrder(order);
	}

	@Override
	public Posting addPostingToOrder(Posting posting) {
		return repository.addPostingToOrder(posting);
	}

	@Override
	public boolean updateOrder(Order order) {
		return repository.updateOrder(order);
	}

	@Override
	public boolean deleteOrder(Integer id) {
		return repository.deleteOrder(id);
	}
}
