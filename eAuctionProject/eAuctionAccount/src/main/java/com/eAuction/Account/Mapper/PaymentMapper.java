package com.eAuction.Account.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import com.eAuction.Account.domain.Payment;

@Mapper
public interface PaymentMapper {
	
	@Select("select * from payment where userName = #{userName}")
	List<Payment> findByUserName(String userName);
	
	@Insert("Insert into payment(cardNumber,holderName,cvs,expiryMonth,expiryYear,userName) VALUES (#{cardNumber},#{holderName},#{cvs},#{expiryMonth},#{expiryYear},#{userName})")
	void insert(Payment payment);
	
	@Delete("Delete from payment where id = #{id}")
	void delete(int id);
	
	@Delete("Delete from payment where userName = #{userName}")
	void deleteByUserName(String userName);
	
	@Select("Select userName from payment where id = #{id}")
	String findById(int id);
}
