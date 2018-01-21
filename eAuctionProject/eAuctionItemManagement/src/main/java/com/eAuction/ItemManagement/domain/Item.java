package com.eAuction.ItemManagement.domain;

public class Item {
	private int id;
	private String name;
	private String description;
	private String imageUrl;
	private String categoryName;

	public Item() {
	}

	public Item(String name, String description, String imageUrl, String categoryName) {
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.categoryName = categoryName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
