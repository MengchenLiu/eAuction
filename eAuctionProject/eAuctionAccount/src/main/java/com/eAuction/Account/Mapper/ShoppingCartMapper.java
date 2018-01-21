package com.eAuction.Account.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.eAuction.Account.domain.ShoppingCart;

@Mapper
public interface ShoppingCartMapper {
	
	@Select("select * from shoppingcart where userName = #{userName}")
	public List<ShoppingCart> findByUserName(String userName);
	
	
	@Insert("insert into shoppingcart (userName, postingId,quantity,price,imageUrl,itemName,typeId) VALUES (#{userName},#{postingId},#{quantity},#{price},#{imageUrl},#{itemName},#{typeId})")
	public void addToShoppingCart(ShoppingCart shoppingCart);
	
	@Delete("Delete from shoppingcart where userName = #{userName}")
	public void delete(String userName);
	
	@Delete("Delete from shoppingcart where id = #{id}")
	public void deleteById(int id);
	
}
