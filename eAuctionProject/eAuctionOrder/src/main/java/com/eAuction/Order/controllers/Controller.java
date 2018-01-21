package com.eAuction.Order.controllers;

import javax.servlet.http.HttpServletResponse;

import com.eAuction.Order.domain.Order;
import com.eAuction.Order.domain.Posting;
import com.eAuction.Order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
public class Controller {
	private OrderService orderService;

	@Autowired
	public void setService(OrderService orderService) {
		this.orderService = orderService;
	}

	/***************** Order *****************/
	@RequestMapping(value = "/orders", method=RequestMethod.POST)
	public List<Order> listAllOrders() {
		return orderService.listAllOrders();
	}

	@RequestMapping(value = "/orders/{buyerId}", method=RequestMethod.POST)
	public List<Order> getOrdersByBuyer(@PathVariable String buyerId) {
		return orderService.getOrdersByBuyer(Integer.parseInt(buyerId));
	}

	@RequestMapping(value = "/addOrder", method=RequestMethod.POST)
	public Order addOrder(
			@RequestParam("buyerId") String buyerId
			, @RequestParam("amountDue") String amountDue
			, @RequestParam("paymentId") String paymentId
			, @RequestParam("shippingId") String shippingId
			, HttpServletResponse response
	) {
		Order order = new Order(Integer.parseInt(buyerId), Double.parseDouble(amountDue), Integer.parseInt(paymentId)
				, Integer.parseInt(shippingId), new Timestamp(System.currentTimeMillis()).toString()
		);
		int orderID = orderService.addOrder(order);
		order.setId(orderID);

		return order;
	}

	@RequestMapping(value = "/addPostingToOrder", method=RequestMethod.POST)
	public Posting addPostingToOrder(
			@RequestParam("orderId") String orderId
			, @RequestParam("postingId") String postingId
			, @RequestParam("postingTypeId") String postingTypeId
	){
		Posting posting = new Posting(Integer.parseInt(orderId), Integer.parseInt(postingId), Integer.parseInt(postingTypeId));

		return orderService.addPostingToOrder(posting);
	}

	@RequestMapping(value = "/showOrder", method=RequestMethod.POST)
	public Order showOrder(@RequestParam String id) {
		return orderService.getOrderById(Integer.parseInt(id));
	}

	@RequestMapping(value = "/deleteOrder/{id}", method=RequestMethod.POST)
	public boolean deleteOrder(@PathVariable String id) {
		return orderService.deleteOrder(Integer.parseInt(id));
	}

	@RequestMapping(value = "/updateOrder", method=RequestMethod.POST)
	public Order updateOrder(
			@RequestParam("orderId") String orderId
			, @RequestParam("buyerId") String buyerId
			, @RequestParam("amountDue") String amountDue
			, @RequestParam("paymentId") String paymentId
			, @RequestParam("shippingId") String shippingId
			, @RequestParam("orderStatusId") String orderStatusId
			, HttpServletResponse response
	) {
		Order order = orderService.getOrderById(Integer.parseInt(orderId));
		order.setBuyerId(Integer.parseInt(buyerId));
		order.setAmountDue(Double.parseDouble(amountDue));
		order.setPaymentId(Integer.parseInt(paymentId));
		order.setShippingId(Integer.parseInt(shippingId));
		order.setOrderStatusId(Integer.parseInt(orderStatusId));

		if(orderService.updateOrder(order))
			return order;
		else
			return null;
	}

}
