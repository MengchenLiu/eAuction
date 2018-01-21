package com.eAuction.ItemManagement.configuration;

import com.eAuction.ItemManagement.EAuctionItemManagementApplication;
import com.eAuction.ItemManagement.controllers.Controller;
import com.eAuction.ItemManagement.domain.Item;
import com.eAuction.ItemManagement.repositories.CategoryRepository;
import com.eAuction.ItemManagement.repositories.ItemRepository;
import com.eAuction.ItemManagement.services.CategoryServiceImpl;
import com.eAuction.ItemManagement.services.ItemServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;

@WebMvcTest(EAuctionItemManagementApplication.class)
@SpringBootTest
public class EAuctionItemManagementApplicationTests {

	private Controller controller;

	@Before
	public void init(){
		ItemRepository itemRepository = new ItemRepository();
		ItemServiceImpl itemService = new ItemServiceImpl();
		itemService.setRepository(itemRepository);

		CategoryRepository categoryRepository = new CategoryRepository();
		CategoryServiceImpl categoryService = new CategoryServiceImpl();
		categoryService.setRepository(categoryRepository);

		controller = new Controller();
		controller.setService(itemService, categoryService);
	}

	private Item addDummyItem(){
		return controller.addItem("ITEM_NAME", "ITEM_DESCRIPTION", "IMAGE_URL", "CATEGORY_NAME",null);
	}

	private boolean deleteDummyItem(int id){
		return controller.deleteItem(String.valueOf(id));
	}

	@Test
	public void testAddShowItem() throws Exception {
		Item newItem = addDummyItem();
		Item comparedItem = controller.showItem(String.valueOf(newItem.getId()));
		assertEquals("ITEM_NAME", comparedItem.getName());
		assertEquals("ITEM_DESCRIPTION", comparedItem.getDescription());
		assertEquals("IMAGE_URL", comparedItem.getImageUrl());
		assertEquals("CATEGORY_NAME", comparedItem.getCategoryName());
		deleteDummyItem(newItem.getId());
	}

	@Test
	public void testAddUpdateShowItem() throws Exception {
		Item newItem = addDummyItem();
		String newDescription = "NEW_ITEM_DESCRIPTION";
		controller.updateItem(
				String.valueOf(newItem.getId())
				, String.valueOf(newItem.getName())
				, String.valueOf(newDescription)
				, String.valueOf(newItem.getImageUrl())
				, String.valueOf(newItem.getCategoryName())
				, null
		);
		Item comparedItem = controller.showItem(String.valueOf(newItem.getId()));
		assertEquals(newItem.getName(), comparedItem.getName());
		assertEquals(newDescription, comparedItem.getDescription());
		assertEquals(newItem.getImageUrl(), comparedItem.getImageUrl());
		assertEquals(newItem.getCategoryName(), comparedItem.getCategoryName());
		deleteDummyItem(newItem.getId());
	}

	@Test
	public void testAddDeleteItem() throws Exception {
		Item newItem = addDummyItem();

		boolean isDeleted = controller.deleteItem(String.valueOf(newItem.getId()));
		Item deletedItem = controller.showItem(String.valueOf(newItem.getId()));
		if(isDeleted)
			assertNull(deletedItem);
		else
			assertEquals(deletedItem.getId(), newItem.getId());

		deleteDummyItem(newItem.getId());
	}

}
