package com.eAuction.ItemManagement.controllers;

import javax.servlet.http.HttpServletResponse;

import com.eAuction.ItemManagement.domain.Category;
import com.eAuction.ItemManagement.domain.Item;
import com.eAuction.ItemManagement.services.CategoryService;
import com.eAuction.ItemManagement.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
	private ItemService itemService;
	private CategoryService categoryService;

	@Autowired
	public void setService(ItemService itemService, CategoryService categoryService) {
		this.itemService = itemService;
		this.categoryService = categoryService;
	}


	/***************** ITEM *****************/
	@RequestMapping(value = "/items", method=RequestMethod.POST)
	public List<Item> getAllItems() {
		return itemService.getAllItems();
	}

	@RequestMapping(value = "/itemsByKeyword", method=RequestMethod.POST)
	public List<Item> getItemsByKeyword(@RequestParam("keyword") String keyword) {
		return itemService.getItemsByKeyword(keyword);
	}

	@RequestMapping(value = "/itemsByCategory", method=RequestMethod.POST)
	public List<Item> getItemsByCategory(@RequestParam("categoryName") String categoryName) {
		return itemService.getItemsByCategory(categoryName);
	}

	@RequestMapping(value = "/addItem", method=RequestMethod.POST)
	public Item addItem(
			@RequestParam("itemName") String itemName
			, @RequestParam("itemDescription") String itemDescription
			, @RequestParam("imageUrl") String imageUrl
			, @RequestParam("categoryName") String categoryName
			, HttpServletResponse response
	) {
		Item item = new Item(itemName, itemDescription, imageUrl, categoryName);
		int itemID = itemService.addItem(item);
		item.setId(itemID);

		return item;
	}

	@RequestMapping(value = "/showItem/{id}", method=RequestMethod.POST)
	public Item showItem(@PathVariable String id) {
		return itemService.getItemById(Integer.parseInt(id));
	}

	/**
	 * Deletes item from repository
	 * @param id: itemID
	 * @return true if delete is successful, false otherwise
	 */
	@RequestMapping(value = "/deleteItem/{id}", method=RequestMethod.POST)
	public boolean deleteItem(@PathVariable String id) {
		return itemService.deleteItem(Integer.parseInt(id));
	}

	/**
	 * Updates item
	 * @param itemId itemId
	 * @param itemName itemName
	 * @param itemDescription itemDescription
	 * @param imageUrl imageUrl
	 * @param categoryName categoryName
	 * @param response response
	 * @return edited item if successful, null otherwise
	 */
	@RequestMapping(value = "/updateItem", method=RequestMethod.POST)
	public Item updateItem(
			@RequestParam("itemId") String itemId
			, @RequestParam("itemName") String itemName
			, @RequestParam("itemDescription") String itemDescription
			, @RequestParam("imageUrl") String imageUrl
			, @RequestParam("categoryName") String categoryName
			, HttpServletResponse response
	) {
		Item item = itemService.getItemById(Integer.parseInt(itemId));
		item.setName(itemName);
		item.setDescription(itemDescription);
		item.setImageUrl(imageUrl);
		item.setCategoryName(categoryName);

		if(itemService.updateItem(item))
			return item;
		else
			return null;
	}

	/***************** CATEGORY *****************/
	@RequestMapping(value = "/categories", method=RequestMethod.POST)
	public List<Category> getAllCategories() {
		return categoryService.listAllCategories();
	}

	@RequestMapping(value = "/addCategory", method=RequestMethod.POST)
	public Category addCategory(
			@RequestParam("categoryName") String categoryName
			, HttpServletResponse response
	) {
		Category category = new Category(categoryName);
		int categoryID = categoryService.addCategory(category);
		category.setId(categoryID);

		return category;
	}

	@RequestMapping(value = "/showCategory/{id}", method=RequestMethod.POST)
	public Category showCategory(@PathVariable String id) {
		return categoryService.getCategoryById(Integer.parseInt(id));
	}

	/**
	 * Deletes category from repository
	 * @param id: categoryID
	 * @return true if delete is successful, false otherwise
	 */
	@RequestMapping(value = "/deleteCategory/{id}", method=RequestMethod.POST)
	public boolean deleteCategory(@PathVariable String id) {
		return categoryService.deleteCategory(Integer.parseInt(id));
	}

	/**
	 * Updates a category
	 * @param categoryId categoryId
	 * @param categoryName categoryName
	 * @return edited category if successful, null otherwise
	 */
	@RequestMapping(value = "/updateCategory", method=RequestMethod.POST)
	public Category updateCategory(
			@RequestParam("categoryId") String categoryId
			, @RequestParam("categoryName") String categoryName
			, HttpServletResponse response
	) {
		Category category = categoryService.getCategoryById(Integer.parseInt(categoryId));
		category.setName(categoryName);

		if(categoryService.updateCategory(category))
			return category;
		else
			return null;
	}
}
