package com.eAuction.Notification.service;

import com.eAuction.Notification.Mapper.WatchlistMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eAuction.Notification.domain.watchlist;
import com.eAuction.Notification.service.WatchlistService;

@Service
public class WatchlistServiceImpl implements WatchlistService{

    @Autowired
    WatchlistMapper watchlistMapper;


    @Override
    public void addToList (watchlist list) {
        watchlistMapper.insert(list);
    }


	@Override
	public List<watchlist> findByPriceAndItemId(int itemId, double price) {
		return watchlistMapper.find(itemId, price);
	}


	@Override
	public void delete(int id) {
		watchlistMapper.deleteById(id);
		
	}

	@Override
	public List<watchlist> findByUserName(String userName) {
		return watchlistMapper.findByUserName(userName);
	}




}
