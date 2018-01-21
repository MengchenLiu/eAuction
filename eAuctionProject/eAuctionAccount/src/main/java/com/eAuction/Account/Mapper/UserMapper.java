package com.eAuction.Account.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.eAuction.Account.domain.User;

@Mapper
public interface UserMapper {
	
	@Insert("Insert into user(userName,firstName,lastName,password,email,role) VALUES(#{userName},#{firstName},#{lastName},#{password},#{email},#{role})")
	void insert(User user);
	
	@Select("SELECT * FROM user WHERE userName = #{userName}")
	User findByName(String name);
	
	@Delete("Delete from user where userName = #{userName}")
	void deleteUser(String userName);
	
	@Update("Update user set role = #{role} where userName = #{userName}")
	void updateUser(@Param("userName") String userName, @Param("role") String role);
	
	@Select("SELECT * FROM user WHERE id = #{id}")
	User findById(int id);
	
	@Update("Update user set firstName = #{firstName},lastName = #{lasgtName}, email = #{email} where id = #{id}")
	void updateInformation(@Param("firstName") String firstName, @Param("lasgtName")String lastName, 
			@Param("email")String email, @Param("id")int id);
}
