package com.eAuction.ItemManagement.services;

import com.eAuction.ItemManagement.domain.Category;

import java.util.List;

public interface CategoryService {
	List<Category> listAllCategories();

	Category getCategoryById(Integer id);

	int addCategory(Category category);

	boolean updateCategory(Category category);

	boolean deleteCategory(Integer id);
}