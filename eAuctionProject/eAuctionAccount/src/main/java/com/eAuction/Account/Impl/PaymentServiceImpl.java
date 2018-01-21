package com.eAuction.Account.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTransactionSynchronizationAdapter.RollbackTransaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eAuction.Account.Mapper.PaymentMapper;
import com.eAuction.Account.Service.PaymentService;
import com.eAuction.Account.domain.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentMapper paymentMapper;
	
	@Override
	public List<Payment> findByUserName(String userName) {
		return paymentMapper.findByUserName(userName);
	}

	@Override
	public void insert(Payment payment) {
		paymentMapper.insert(payment);
	}

	@Override
	public boolean delete(int id,String name) {
		String userName = paymentMapper.findById(id);
		if(!name.equals(userName)) return false;
		paymentMapper.delete(id);
		return true;
	}

	@Override
	public void deleteByUserName(String userName) {
		paymentMapper.deleteByUserName(userName);
	}

	
}
