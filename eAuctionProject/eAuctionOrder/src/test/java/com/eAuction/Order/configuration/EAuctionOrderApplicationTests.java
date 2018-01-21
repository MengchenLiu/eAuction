package com.eAuction.Order.configuration;

import com.eAuction.Order.EAuctionOrderApplication;
import com.eAuction.Order.controllers.Controller;
import com.eAuction.Order.domain.Order;
import com.eAuction.Order.domain.Posting;
import com.eAuction.Order.repositories.OrderRepository;
import com.eAuction.Order.services.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;

@WebMvcTest(EAuctionOrderApplication.class)
@SpringBootTest
public class EAuctionOrderApplicationTests {

	private Controller controller;

	@Before
	public void init(){
		OrderRepository orderRepository = new OrderRepository();

		OrderServiceImpl orderService = new OrderServiceImpl();
		orderService.setRepository(orderRepository);

		controller = new Controller();
		controller.setService(orderService);
	}

	private Order addDummyData(){
		Order order = controller.addOrder("-1", "-1.0", "-1", "-1",null);
		ArrayList<Posting> postings = new ArrayList<>();
		Posting posting = controller.addPostingToOrder(String.valueOf(order.getId()), "-1", "-1");
		postings.add(posting);
		order.setPostingList(postings);

		return order;
	}

	@Test
	public void testAddShowOder() throws Exception {
		Order newOrder = addDummyData();
		Order comparedOrder = controller.showOrder(String.valueOf(newOrder.getId()));
		assertEquals(-1, comparedOrder.getBuyerId());
		assertEquals(-1.0, comparedOrder.getAmountDue());
		assertEquals(-1, comparedOrder.getPaymentId());
		assertEquals(-1, comparedOrder.getShippingId());
		assertEquals(1, comparedOrder.getOrderStatusId());
		assertEquals(-1, comparedOrder.getPostingList().get(0).getPostingId());
		assertEquals(-1, comparedOrder.getPostingList().get(0).getPostingTypeId());
	}

	@Test
	public void testAddUpdateShowOder() throws Exception {
		Order newOrder = addDummyData();
		Double newAmountDue = newOrder.getAmountDue() * 2;
		controller.updateOrder(
				String.valueOf(newOrder.getId())
				, String.valueOf(newOrder.getBuyerId())
				, String.valueOf(newAmountDue)
				, String.valueOf(newOrder.getPaymentId())
				, String.valueOf(newOrder.getShippingId())
				, String.valueOf(newOrder.getOrderStatusId())
				, null
		);
		Order comparedOrder = controller.showOrder(String.valueOf(newOrder.getId()));
		assertEquals(newOrder.getBuyerId(), comparedOrder.getBuyerId());
		assertEquals(newAmountDue, comparedOrder.getAmountDue());
		assertEquals(newOrder.getPaymentId(), comparedOrder.getPaymentId());
		assertEquals(newOrder.getShippingId(), comparedOrder.getShippingId());
		assertEquals(newOrder.getOrderStatusId(), comparedOrder.getOrderStatusId());
		assertEquals(newOrder.getPostingList().get(0).getPostingId(), comparedOrder.getPostingList().get(0).getPostingId());
		assertEquals(newOrder.getPostingList().get(0).getPostingTypeId(), comparedOrder.getPostingList().get(0).getPostingTypeId());
	}

	@Test
	public void testAddDeleteOder() throws Exception {
		Order newOrder = addDummyData();

		boolean isDeleted = controller.deleteOrder(String.valueOf(newOrder.getId()));
		Order deletedOrder = controller.showOrder(String.valueOf(newOrder.getId()));
		if(isDeleted)
			assertNull(deletedOrder);
		else
			assertEquals(deletedOrder.getId(), newOrder.getId());
	}

}
