package com.eAuction.Notification.service;

import org.springframework.stereotype.Service;
import com.eAuction.Notification.domain.watchlist;
import java.util.*;

@Service
public interface WatchlistService {
	
    public void addToList (watchlist list);
    
    public List<watchlist> findByPriceAndItemId(int itemId, double price);
    
    public void delete(int id);
    
    public List<watchlist> findByUserName(String userName);
    
}

