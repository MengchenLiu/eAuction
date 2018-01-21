package com.eAuction.Account.configuration;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import com.eAuction.Account.controller.Controller;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EAuctionAccountApplicationTests {
	
	private Controller controller;
	
	@Before
	public void init() {
		controller = new Controller();
	}
	
	
	@Test
	public void contextLoads() {
	}

}
