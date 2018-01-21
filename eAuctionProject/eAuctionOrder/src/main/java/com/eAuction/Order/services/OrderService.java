package com.eAuction.Order.services;

import com.eAuction.Order.domain.Order;
import com.eAuction.Order.domain.Posting;

import java.util.List;

public interface OrderService {
	List<Order> listAllOrders();

	List<Order> getOrdersByBuyer(Integer buyerId);

	Order getOrderById(Integer id);

	int addOrder(Order order);

	boolean updateOrder(Order order);

	boolean deleteOrder(Integer id);

	Posting addPostingToOrder(Posting posting);
}