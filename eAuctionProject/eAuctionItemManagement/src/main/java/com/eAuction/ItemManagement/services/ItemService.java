package com.eAuction.ItemManagement.services;

import com.eAuction.ItemManagement.domain.Item;

import java.util.List;

public interface ItemService {
	List<Item> getAllItems();

	List<Item> getItemsByKeyword(String keyword);

	List<Item> getItemsByCategory(String categoryName);

	Item getItemById(Integer id);

	int addItem(Item item);

	boolean updateItem(Item item);

	boolean deleteItem(Integer id);
}