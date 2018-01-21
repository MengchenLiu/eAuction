package com.eAuction.Account.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eAuction.Account.Mapper.AddressMapper;
import com.eAuction.Account.Service.AddressService;
import com.eAuction.Account.domain.Address;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressMapper addressMapper;
	
	@Override
	public List<Address> findByUserName(String userName) {
		return addressMapper.findByUserName(userName);
	}

	@Override
	public void insert(Address address) {
		addressMapper.insertAddress(address);
	}

	@Override
	public void delete(int id) {
		addressMapper.delete(id);
	}

	@Override
	public void deleteByUserName(String userName) {
		addressMapper.deleteByUserName(userName);
	}
}
