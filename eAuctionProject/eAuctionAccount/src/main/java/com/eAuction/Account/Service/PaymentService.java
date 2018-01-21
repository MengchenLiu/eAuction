package com.eAuction.Account.Service;

import java.util.List;

import com.eAuction.Account.domain.Payment;

public interface PaymentService {
	
	public List<Payment> findByUserName(String userName);
	
	public void insert(Payment payment);
	
	public boolean delete(int id, String userName);
	
	public void deleteByUserName(String name);
}
