package com.eAuction.Notification.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserEmailMapper {
	
	@Select("select email from useremail where userId = #{id}")
	public String findByUserId(int id);
	
	@Insert("insert into useremail(email,userId) values(#{0},#{1})")
	public void addEmail(String email, int id);
}
