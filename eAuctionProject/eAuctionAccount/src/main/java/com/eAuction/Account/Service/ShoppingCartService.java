package com.eAuction.Account.Service;

import java.util.List;

import com.eAuction.Account.domain.ShoppingCart;

public interface ShoppingCartService {
	
	public List<ShoppingCart> findByUserName(String userName);
	
	
	public void addToShoppingCart(ShoppingCart shoppingCart);
	
	public void delete(String userName);
	
	
	public void deleteById(int id);
}
