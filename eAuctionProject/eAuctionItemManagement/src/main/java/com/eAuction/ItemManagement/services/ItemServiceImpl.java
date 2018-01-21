package com.eAuction.ItemManagement.services;

import com.eAuction.ItemManagement.domain.Item;
import com.eAuction.ItemManagement.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	private ItemRepository repository;

	@Autowired
	public void setRepository(ItemRepository repository) {
		this.repository = repository;
	}

	/**
	 * List all items that exist in the database
	 * @return
     */
	@Override
	public List<Item> getAllItems() {
		return repository.getAllItems();
	}

	@Override
	public List<Item> getItemsByKeyword(String keyword) {
		return repository.getItemsByKeyword(keyword);
	}

	@Override
	public List<Item> getItemsByCategory(String categoryName) {
		return repository.getItemsByCategory(categoryName);
	}

	/**
	 * Returns an item by a given itemID
	 * @param id: itemID
	 * @return Item
     */
	@Override
	public Item getItemById(Integer id) {
		return repository.getItem(id);
	}

	/**
	 * Saves a new item and returns the new ID
	 * @param item: new Item
	 * @return itemID
     */
	@Override
	public int addItem(Item item) {
		return repository.addItem(item);
	}

	@Override
	public boolean updateItem(Item item) {
		return repository.updateItem(item);
	}

	/**
	 * Deletes an item by a given itemID
	 * @param id: itemID
     */
	@Override
	public boolean deleteItem(Integer id) {
		return repository.deleteItem(id);
	}
}
