package com.eAuction.ItemManagement.services;

import com.eAuction.ItemManagement.domain.Category;
import com.eAuction.ItemManagement.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	private CategoryRepository repository;

	@Autowired
	public void setRepository(CategoryRepository repository) {
		this.repository = repository;
	}

	/**
	 * List all categories that exist in the database
	 * @return
     */
	@Override
	public List<Category> listAllCategories() {
		return repository.getAllCategories();
	}

	/**
	 * Returns a category by a given categoryID
	 * @param id: categoryID
	 * @return Category
     */
	@Override
	public Category getCategoryById(Integer id) {
		return repository.getCategory(id);
	}

	/**
	 * Saves a new category and returns it
	 * @param category: new category
	 * @return Category
     */
	@Override
	public int addCategory(Category category) {
		return repository.addCategory(category);
	}

	@Override
	public boolean updateCategory(Category category) {
		return repository.updateCategory(category);
	}

	/**
	 * Deletes a category by a given categoryID
	 * @param id: categoryID
     */
	@Override
	public boolean deleteCategory(Integer id) {
		return repository.deleteCategory(id);
	}
}
