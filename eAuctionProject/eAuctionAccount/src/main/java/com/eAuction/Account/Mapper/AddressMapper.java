package com.eAuction.Account.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.eAuction.Account.domain.Address;

@Mapper
public interface AddressMapper {
	
	@Select("select * from address where userName = #{userName}")
	List<Address> findByUserName(String userName);
	
	@Insert("Insert into address(country,state,addressStreet1,addressStreet2,city,userName,zip) VALUES (#{country},#{state},#{addressStreet1},#{addressStreet2},#{city},#{userName},#{zip})")
	void insertAddress(Address address);
	
	@Delete("Delete from address where id = #{id}")
	void delete(int id);
	
	@Delete("Delete from address where userName = #{userName}")
	void deleteByUserName(String userName);
}


