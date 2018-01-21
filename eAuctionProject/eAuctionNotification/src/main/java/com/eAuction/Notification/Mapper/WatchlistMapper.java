package com.eAuction.Notification.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import com.eAuction.Notification.domain.watchlist;


@Mapper
public interface WatchlistMapper {

    @Insert("Insert into watchlist(email,itemId,itemName,price,userName) VALUES (#{email},#{itemId},#{itemName},#{price},#{userName})")
    void insert(watchlist watchlist);

    @Select("Select * from watchlist where itemId = #{0} and price >= #{1}")
    List<watchlist> find(int itemId, double price);
    
    @Delete("Delete from watchlist where id = #{id}")
    void deleteById(int id);
    
    @Select("Select * from watchlist where userName = #{userName}")
    List<watchlist> findByUserName(String userName);
   
}

