package com.eAuction.Account.Service;

import java.util.List;

import com.eAuction.Account.domain.Address;

public interface AddressService {
	public List<Address> findByUserName(String userName);
	
	public void insert(Address address);
	
	public void delete(int id);
	
	public void deleteByUserName(String userName);
	
	
}
