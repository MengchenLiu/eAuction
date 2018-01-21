package com.eAuction.Account.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eAuction.Account.Mapper.ShoppingCartMapper;
import com.eAuction.Account.Service.ShoppingCartService;
import com.eAuction.Account.domain.ShoppingCart;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	ShoppingCartMapper shoppingCartMapper;
	
	@Override
	public List<ShoppingCart> findByUserName(String userName) {
		return shoppingCartMapper.findByUserName(userName);	
	}

	@Override
	public void addToShoppingCart(ShoppingCart shoppingCart) {
		shoppingCartMapper.addToShoppingCart(shoppingCart);
	}

	@Override
	public void delete(String userName) {
		shoppingCartMapper.delete(userName);
	}

	@Override
	public void deleteById(int id) {
		shoppingCartMapper.deleteById(id);
	}
	
	

}
