package com.eAuction.CustomerService.configuration;

import com.eAuction.CustomerService.controllers.Controller;
import com.eAuction.CustomerService.domain.Message;
import com.eAuction.CustomerService.repositories.MessageRepository;
import com.eAuction.CustomerService.services.MessageServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
public class EAuctionCustomerServiceApplicationTests {
	private Controller controller;

	@Before
	public void init(){
		MessageRepository messageRepository = new MessageRepository();

		MessageServiceImpl messageService = new MessageServiceImpl();
		messageService.setRepository(messageRepository);

		controller = new Controller();
		controller.setService(messageService);
	}

	private Message addDummyData(){
		return controller.addQuestion("-1", "QUESTION",null);
	}


	@Test
	public void testAskShowMessage() throws Exception {
		Message newMessage = addDummyData();
		Message comparedMessage = controller.showMessage(String.valueOf(newMessage.getId()));
		assertEquals(-1, comparedMessage.getUserId());
		assertEquals("QUESTION", comparedMessage.getQuestion());
	}

	@Test
	public void testAnswerShowMessage() throws Exception {
		Message newMessage = addDummyData();
		controller.addAnswer(
				String.valueOf(newMessage.getId())
				, "0"
				, "ANSWER"
				, null
		);
		Message comparedMessage = controller.showMessage(String.valueOf(newMessage.getId()));
		assertEquals(0, comparedMessage.getAdminId());
		assertEquals("ANSWER", comparedMessage.getAnswer());
	}

}
